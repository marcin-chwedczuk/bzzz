package pl.marcinchwedczuk.bzzz.primitives.gates;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.BaseElement;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public class Buffer extends BaseElement {
    private final Wire input;
    private final Wire output;

    @Override
    protected long propagationDelay() {
        return 100;
    }

    public Buffer(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);

        input = new Wire(simulator, componentId.extend("inputPin"));
        output = new Wire(simulator, componentId.extend("outputPin"));

        input.registerListener((newState, sourceId) -> onInputChanged());

        scheduleInitialization(describeAs("schedule initialization"), () -> {
            LogicState outputInitState = ttlIdentity(input.logicState());
            output.applyState(outputInitState, componentId());
        });
    }

    private void onInputChanged() {
        LogicState newOutput = ttlIdentity(input.logicState());

        scheduleWithPropagationDelay(describeAs("set output to %s", newOutput), () -> {
            output.applyState(newOutput, componentId());
        });
    }

    private static LogicState ttlIdentity(LogicState input) {
        return input.toTTL();
    }

    public Wire input() { return input; }
    public Wire output() { return output; }
}
