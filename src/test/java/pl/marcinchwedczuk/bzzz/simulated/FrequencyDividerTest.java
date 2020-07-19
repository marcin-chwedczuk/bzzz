package pl.marcinchwedczuk.bzzz.simulated;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import pl.marcinchwedczuk.bzzz.ics.flipflops.edgetriggered.DFlipFlop;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.*;

import static org.junit.Assert.fail;

public class FrequencyDividerTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    private final Simulator simulator = new EventLoopSimulator();
    private final CircuitBuilder builder = new CircuitBuilder(simulator);

    @Test
    public void smoke() {
        var test = ComponentId.of("test");
        var divider = new FrequencyDivider(builder, ComponentId.of("divider8"));
        // ERROR: Apply two voltages in the same time frame
        divider.input().applyState(LogicState.ZERO, test);
        simulator.run("run()");

        var probe = builder.probeFor(divider.output());
        probe.logStateChanges();

        for (int i = 0; i < 20; i++) {
            changeState(divider.input());
        }
    }

    @Test public void foo() {
        var test = ComponentId.of("test");

        var div2 = new DFlipFlop(builder, ComponentId.of("div2"));
        div2.d().connectWith(div2.qN());

        var div4 = new DFlipFlop(builder, ComponentId.of("div4"));
        div4.d().connectWith(div4.qN());

        var div8 = new DFlipFlop(builder, ComponentId.of("div8"));
        div8.d().connectWith(div8.qN());

        div2.qN().connectWith(div4.clock());
        div4.qN().connectWith(div8.clock());
        simulator.run("run()");

        var probe = builder.probeFor(div8.q());
        probe.logStateChanges();

        for (int i = 0; i < 40; i++) {
            changeState(div2.clock());
        }
    }

    private void changeState(Wire input) {
        var reversed = input.logicState().toTTL().reverse();
        simulator.schedule(
                ComponentId.of("test"),
                Duration.of(1),
                () -> input.applyState(reversed, ComponentId.of("test")));

        simulator.run("REVERSE INPUT");
    }
}