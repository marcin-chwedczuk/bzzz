package pl.marcinchwedczuk.bzzz.gates;

import org.junit.Test;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.gates.TriStateBuffer;
import pl.marcinchwedczuk.bzzz.primitives.passives.Probe;
import pl.marcinchwedczuk.bzzz.primitives.passives.Switch;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.simulator.EventLoopSimulator;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public class TriStateBufferTest {
    private final Simulator simulator = new EventLoopSimulator();
    private final CircuitBuilder builder = new CircuitBuilder(simulator);

    @Test public void inverter_works() {
        TriStateBuffer triState = builder.triStateBuffer("inv");
        Probe outputProbe = builder.probeFor(triState.output());

        Switch inputSwitch = builder.switchFor(triState.input());
        Switch enableSwitch = builder.switchFor(triState.enabledN());

        // after initialization output should be in NOT_CONNECTED state
        // because enabled is in high state (not connected).
        simulator.run("initialize components");
        outputProbe.assertState(LogicState.NOT_CONNECTED);

        // changing input does not change the state
        inputSwitch.zero();
        simulator.run("input set to zero, enabled not connected");
        outputProbe.assertState(LogicState.NOT_CONNECTED);

        inputSwitch.one();
        simulator.run("input set to one, enabled not connected");
        outputProbe.assertState(LogicState.NOT_CONNECTED);

        // Setting enable line to ZERO enables output
        enableSwitch.zero();

        inputSwitch.one();
        simulator.run("input = 1, enabled = 0 (active)");
        outputProbe.assertState(LogicState.ONE);

        inputSwitch.zero();
        simulator.run("input = 0, enabled = 0 (active)");
        outputProbe.assertState(LogicState.ZERO);

        enableSwitch.one();
        simulator.run("input = 0, enabled = 1 (not active)");
        outputProbe.assertState(LogicState.NOT_CONNECTED);
    }
}
