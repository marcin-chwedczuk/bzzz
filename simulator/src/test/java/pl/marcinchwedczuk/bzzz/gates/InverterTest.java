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

        simulator.run("initialize components");
        outputProbe.assertState(LogicState.ZERO);

        inputSwitch.zero();
        // Output is not changed immediately
        outputProbe.assertState(LogicState.ZERO);
        simulator.run("input set to 0");
        outputProbe.assertState(LogicState.ONE);

        // After input is set to ONE, output should go to ZERO
        inputSwitch.one();
        outputProbe.assertState(LogicState.ONE);
        simulator.run("input set to 1");
        outputProbe.assertState(LogicState.ZERO);
    }
}
