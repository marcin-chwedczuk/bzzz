package pl.marcinchwedczuk.bzzz.ics.flipflops.edgetriggered;

import org.junit.Test;
import pl.marcinchwedczuk.bzzz.ics.flipflops.RSFlipFlopN;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.simulator.EventLoopSimulator;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

import static org.junit.Assert.*;

public class DFlipFlopTest {
    private final Simulator simulator = new EventLoopSimulator();
    private final CircuitBuilder builder = new CircuitBuilder(simulator);

    @Test public void smoke() {
        var rs = new DFlipFlop(builder, ComponentId.of("RS"));
        var qProbe = builder.probeFor(rs.q());
        var qNProbe = builder.probeFor(rs.qN());
        var dSwitch = builder.switchFor(rs.d());
        var clockSwitch = builder.switchFor(rs.clock());

        clockSwitch.zero();
        // Initialize
        simulator.run(100, "initialize");

        // Set one
        dSwitch.one();
        clockSwitch.one();

        simulator.run(100, "set d=1, clock raisingEDGE");
    }
}