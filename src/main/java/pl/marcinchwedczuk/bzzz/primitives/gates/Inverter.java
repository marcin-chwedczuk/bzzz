package pl.marcinchwedczuk.bzzz.primitives.gates;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.BaseElement;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.LogicStateChangedListener;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

// Assumes TTL Logic not connected pin has high state
public class Inverter extends BaseElement {
    private final Wire input;
    private final Wire output;

    @Override
    protected long propagationDelay() {
        return 100;
    }

    public Inverter(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);

        input = new Wire(simulator, componentId.extend("inputPin"));
        output = new Wire(simulator, componentId.extend("outputPin"));

        input.registerListener(new LogicStateChangedListener() {
            @Override
            public void onStateChanged(LogicState newState, ComponentId sourceId) {
                onInputChanged(newState);
            }
        });

        scheduleInitialization(describeAs("schedule initialization"), () -> {
            onInputChanged(input.logicState());
        });
    }

    private void onInputChanged(LogicState newInput) {
        LogicState newOutput = switch (newInput) {
            case ONE, NOT_CONNECTED -> LogicState.ZERO;
            case ZERO -> LogicState.ONE;
        };

        scheduleWithPropagationDelay(describeAs("set output to %s", newOutput), () -> {
            output.applyState(newOutput, componentId());
        });
    }

    public Wire input() { return input; }
    public Wire output() { return output; }
}
