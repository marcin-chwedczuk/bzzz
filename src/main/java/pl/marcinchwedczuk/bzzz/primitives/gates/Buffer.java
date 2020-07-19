package pl.marcinchwedczuk.bzzz.primitives.gates;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.BaseElement;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.Duration;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public class Buffer extends BaseElement {
    private final Wire input;
    private final Wire output;

    @Override
    protected Duration propagationDelay() {
        return Duration.of(100);
    }

    public Buffer(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);

        input = new Wire(simulator, componentId.inputPin());
        output = new Wire(simulator, componentId.outputPin());

        input.registerListener((newState, sourceId) -> onInputChanged());

        scheduleInitialization(() -> {
            LogicState inputLS = input.logicState();
            LogicState outputLS = ttlIdentity(inputLS);
            logger.log("[init] set %s -> %s", inputLS, outputLS);
            output.applyState(outputLS, componentId());
        });
    }

    private void onInputChanged() {
        LogicState inputLS = input.logicState();
        LogicState outputLS = ttlIdentity(input.logicState());

        scheduleWithPropagationDelay(() -> {
            logger.log("set %s -> %s", inputLS, outputLS);
            output.applyState(outputLS, componentId());
        });
    }

    private static LogicState ttlIdentity(LogicState input) {
        return input.toTTL();
    }

    public Wire input() { return input; }
    public Wire output() { return output; }
}
