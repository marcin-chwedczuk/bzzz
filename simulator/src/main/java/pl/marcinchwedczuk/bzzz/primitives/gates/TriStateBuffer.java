package pl.marcinchwedczuk.bzzz.primitives.gates;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.BaseElement;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.Duration;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public class TriStateBuffer extends BaseElement {
    public final Wire input;
    public final Wire output;
    // Enabled input is inverted, low state means enabled
    public final Wire enableN;

    @Override
    protected Duration propagationDelay() {
        return Duration.of(100);
    }

    public TriStateBuffer(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);

        input = new Wire(simulator, componentId.inputPin());
        output = new Wire(simulator, componentId.outputPin());
        enableN = new Wire(simulator, componentId.enableNPin());

        input.registerListener((newState, sourceId) -> onInputsChanged());
        enableN.registerListener((newState, sourceId) -> onInputsChanged());

        scheduleInitialization(() -> {
            LogicState enabledNLS = enableN.logicState();
            LogicState inputLS = input.logicState();

            LogicState outputLS = ttlTriState(enabledNLS, inputLS);

            logger.log("[init] enabledN: %s, %s -> %s",
                    enabledNLS, inputLS, outputLS);
            output.applyState(outputLS, componentId());
        });
    }

    private void onInputsChanged() {
        LogicState enabledNLS = enableN.logicState();
        LogicState inputLS = input.logicState();

        LogicState outputLS = ttlTriState(enabledNLS, inputLS);

        scheduleWithPropagationDelay(() -> {
            logger.log("enabledN: %s, %s -> %s", enabledNLS, inputLS, outputLS);
            output.applyState(outputLS, componentId());
        });
    }

    private static LogicState ttlTriState(LogicState enabled, LogicState input) {
        if (enabled.isZero()) {
            return input.toTTL();
        }
        else {
            return LogicState.NOT_CONNECTED;
        }
    }
}
