package pl.marcinchwedczuk.bzzz.simulated;

import org.junit.Ignore;
import org.junit.Test;
import pl.marcinchwedczuk.bzzz.ics.flipflops.edgetriggered.DFlipFlop;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.simulator.EventLoopSimulator;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

import javax.print.attribute.standard.PresentationDirection;

import static org.junit.Assert.*;

public class FrequencyDividerTest {
    private final Simulator simulator = new EventLoopSimulator();
    private final CircuitBuilder builder = new CircuitBuilder(simulator);

    @Test
    public void smoke() {
        var test = ComponentId.of("test");
        var divider = new FrequencyDivider(builder, ComponentId.of("divider"));
        // ERROR: Apply two voltages in the same time frame
        // divider.input().applyState(LogicState.ZERO, test);
        simulator.run();

        var probe = builder.probeFor(divider.output());
        probe.logStateChanges();

        for (int i = 0; i < 20; i++) {
            changeState(divider.input());
        }
    }

    @Test public void foo() {
        var test = ComponentId.of("test");

        var dFlipFlop = new DFlipFlop(builder, ComponentId.of("div2"));
        dFlipFlop.d().connectWith(dFlipFlop.qN());

        var dFlipFlop2 = new DFlipFlop(builder, ComponentId.of("div2-2"));
        dFlipFlop2.d().connectWith(dFlipFlop2.qN());

        var dFlipFlop3 = new DFlipFlop(builder, ComponentId.of("div2-2"));
        dFlipFlop3.d().connectWith(dFlipFlop3.qN());

        dFlipFlop.q().connectWith(dFlipFlop2.clock());
        // dFlipFlop2.clock().connectWith(dFlipFlop.q());
        // dFlipFlop3.clock().connectWith(dFlipFlop2.q());
        dFlipFlop2.q().connectWith(dFlipFlop3.clock());
        // dFlipFlop.clock().applyState(LogicState.ONE, test);
        simulator.run();

        var probe = builder.probeFor(dFlipFlop3.q());
        probe.logStateChanges();

        for (int i = 0; i < 40; i++) {

            changeState(dFlipFlop.clock());
        }
    }

    private void changeState(Wire input) {
        var reversed = input.logicState().toTTL().reverse();
        System.out.printf("INPUT %s -> %s%n", input.logicState(), reversed);
        simulator.schedule(
                1,
                ComponentId.of("test"),
                "TEST - reverse wire state",
                () -> input.applyState(reversed, ComponentId.of("test")));

        simulator.run();
    }
}