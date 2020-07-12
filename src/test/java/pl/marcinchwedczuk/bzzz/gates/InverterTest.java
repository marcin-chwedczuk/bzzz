package pl.marcinchwedczuk.bzzz.gates;

import org.junit.Test;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.gates.Inverter;
import pl.marcinchwedczuk.bzzz.primitives.passives.Probe;
import pl.marcinchwedczuk.bzzz.primitives.passives.Switch;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.simulator.EventLoopSimulator;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public class InverterTest {
    private final Simulator simulator = new EventLoopSimulator();
    private final CircuitBuilder builder = new CircuitBuilder(simulator);

    @Test public void inverter_works() {
        Inverter inv = builder.inverter("inv");
        Probe outputProbe = builder.probeFor(inv.output());

        Switch inputSwitch = builder.switchFor(inv.input());

        // after initialization output should be in LOW state
        simulator.run();
        outputProbe.assertState(LogicState.ZERO);

        // After input is set to ZERO, output should go to ONE
        inputSwitch.zero();
        // Output is not changed immediately
        outputProbe.assertState(LogicState.ZERO);
        simulator.run();
        outputProbe.assertState(LogicState.ONE);

        // After input is set to ONE, output should go to ZERO
        inputSwitch.one();
        outputProbe.assertState(LogicState.ONE);
        simulator.run();
        outputProbe.assertState(LogicState.ZERO);
    }
}
