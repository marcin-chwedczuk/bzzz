package pl.marcinchwedczuk.bzzz.simulated;

import pl.marcinchwedczuk.bzzz.ics.flipflops.edgetriggered.DFlipFlop;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;

public class FrequencyDivider {
    private final Wire input;
    private final Wire output;

    public FrequencyDivider(CircuitBuilder builder, ComponentId componentId) {
        var div2 = new DFlipFlop(builder, ComponentId.of("div2"));
        div2.d().connectWith(div2.qN());

        var div4 = new DFlipFlop(builder, ComponentId.of("div4"));
        div4.d().connectWith(div4.qN());

        var div8 = new DFlipFlop(builder, ComponentId.of("div8"));
        div8.d().connectWith(div8.qN());

        div2.qN().connectWith(div4.clock());
        div4.qN().connectWith(div8.clock());

        input = div2.clock();
        output = div8.q();
    }

    public Wire input() { return input; }
    public Wire output() { return output; }
}
