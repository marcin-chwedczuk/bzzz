package pl.marcinchwedczuk.bzzz.primitives;

import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public class Probe extends ActiveElement implements BroadcastReceiver {
    private LogicState logicState = LogicState.HIGH_IMPEDANCE;

    public Probe(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);
    }

    public LogicState state() { return logicState; }

    @Override
    public void onBroadcast(ComponentId source, LogicState sourceNewState) {
        logicState = sourceNewState;

        System.out.printf("PROBE: %s NEW STATE: %s%n", componentId(), state());
    }

    public void assertState(LogicState expected) {
        if (state() != expected)
            throw new RuntimeException(String.format(
                    "Expected state: %s but was: %s.%n" +
                    "Probe: %s", expected, state(), componentId()));
    }
}
