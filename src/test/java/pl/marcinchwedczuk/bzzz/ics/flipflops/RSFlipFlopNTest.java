package pl.marcinchwedczuk.bzzz.ics.flipflops;

import org.junit.Test;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.simulator.EventLoopSimulator;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public class RSFlipFlopNTest {
    private final Simulator simulator = new EventLoopSimulator();
    private final CircuitBuilder builder = new CircuitBuilder(simulator);

    @Test public void rs_flipflop_works() {
        /*
        SN  RN
        0	0	Q = 1, Q = 1; not allowed
        0	1	Q = 1
        1	0	Q = 0
        1	1	No change; random initial
         */
        var rs = new RSFlipFlopN(builder, ComponentId.of("RS"));
        var qProbe = builder.probeFor(rs.q());
        var qNProbe = builder.probeFor(rs.qN());
        var sSwitch = builder.switchFor(rs.setN());
        var rSwitch = builder.switchFor(rs.resetN());

        // RS Switch, 1-1 on input is not allowed!
        // Set one
        sSwitch.one();
        rSwitch.one();

        // Initialize
        simulator.run();

        qProbe.assertOne();
        qNProbe.assertZero();

        // Set zero
        sSwitch.zero();
        rSwitch.one();

        simulator.run();

        qProbe.assertZero();
        qNProbe.assertOne();

        // Preserve value
        rSwitch.zero();
        sSwitch.zero();

        simulator.run();

        qProbe.assertZero();
        qNProbe.assertOne();
    }
}