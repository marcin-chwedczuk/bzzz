package pl.marcinchwedczuk.bzzz.primitives.gates;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.BaseElement;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.LogicStateChangedListener;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public class TriStateBuffer extends BaseElement {
    private final Wire input;
    private final Wire output;
    // Enabled input is inverted, low state means enabled
    private final Wire enabledN;

    @Override
    protected long propagationDelay() {
        return 100;
    }

    public TriStateBuffer(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);

        input = new Wire(simulator, componentId.extend("inputPin"));
        output = new Wire(simulator, componentId.extend("outputPin"));
        enabledN = new Wire(simulator, componentId.extend("enabledPin"));

        input.registerListener(new LogicStateChangedListener() {
            @Override
            public void onStateChanged(LogicState newState, ComponentId sourceId) {
                onInputsChanged();
            }
        });

        enabledN.registerListener(new LogicStateChangedListener() {
            @Override
            public void onStateChanged(LogicState newState, ComponentId sourceId) {
                onInputsChanged();
            }
        });


        scheduleInitialization(() -> {
            onInputsChanged();
        });
    }

    private void onInputsChanged() {
        LogicState enabledLS = enabledN.logicState();
        LogicState inputLS = input.logicState();

        if (enabledLS == LogicState.ZERO) {
            scheduleWithPropagationDelay(() -> {
                output.applyState(inputLS, componentId());
            });
        }
        else {
            scheduleWithPropagationDelay(() -> {
                output.applyState(LogicState.NOT_CONNECTED, componentId());
            });
        }
    }

    public Wire input() { return input; }
    public Wire output() { return output; }
    public Wire enabledN() { return enabledN; }
}
