package pl.marcinchwedczuk.bzzz.simulated;

import pl.marcinchwedczuk.bzzz.ics.flipflops.edgetriggered.DFlipFlop;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;

public class FrequencyDivider {
    private final Wire input;
    private final Wire output;

    public FrequencyDivider(CircuitBuilder builder, ComponentId componentId) {
        var dFlipFlop = new DFlipFlop(builder, ComponentId.of("div2"));
        dFlipFlop.d().connectWith(dFlipFlop.qN());

        var dFlipFlop2 = new DFlipFlop(builder, ComponentId.of("div2-2"));
        dFlipFlop2.d().connectWith(dFlipFlop2.qN());

        var dFlipFlop3 = new DFlipFlop(builder, ComponentId.of("div2-2"));
        dFlipFlop3.d().connectWith(dFlipFlop3.qN());

        dFlipFlop.q().connectWith(dFlipFlop2.clock());
        dFlipFlop2.q().connectWith(dFlipFlop3.clock());

        input = dFlipFlop.clock();
        output = dFlipFlop3.q();
    }

    private DFlipFlop stage(CircuitBuilder builder, ComponentId componentId) {
        var dFlipFlop = new DFlipFlop(builder, componentId);
        dFlipFlop.d().connectWith(dFlipFlop.qN());
        return dFlipFlop;
    }

    public Wire input() { return input; }
    public Wire output() { return output; }
}
