package pl.marcinchwedczuk.bzzz.ics.flipflops.edgetriggered;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.gates.Nand3;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

// D flip-flop
// Alternative design.
public class DFlipFlopAlt {
    public final Wire q;
    public final Wire qN;
    public final Wire clock;
    public final Wire d;
    public final Wire resetN;
    public final Wire setN;

    public DFlipFlopAlt(CircuitBuilder builder, ComponentId componentId) {
        final Simulator sim = builder.simulator;
        // Level A 1 - 2 - 3
        // Level B 1 - 2 - 3

        var a1 = new Nand3(sim, componentId.extend("a1"));
        var a2 = new Nand3(sim, componentId.extend("a2"));
        var a3 = new Nand3(sim, componentId.extend("a3"));

        var b1 = new Nand3(sim, componentId.extend("b1"));
        var b2 = new Nand3(sim, componentId.extend("b2"));
        var b3 = new Nand3(sim, componentId.extend("b3"));

        a1.input1.connectWith(a2.output);
        resetN = a1.input2;
        d = a1.input3;

        clock = a2.input1;
        a2.input2.connectWith(a1.output);
        a2.input3.connectWith(b2.output);

        a3.input1.connectWith(resetN);
        a3.input2.connectWith(a2.output);
        a3.input3.connectWith(b3.output);

        b1.input1.connectWith(a1.output);
        setN = b1.input2;
        b1.input3.connectWith(b2.output);

        b2.input1.connectWith(resetN);
        b2.input2.connectWith(b1.output);
        b2.input3.connectWith(clock);

        b3.input1.connectWith(a3.output);
        b3.input2.connectWith(b2.output);
        b3.input3.connectWith(setN);

        q = b3.output;
        qN = a3.output;
    }

    public Wire clock() { return clock; }
    public Wire d() { return d; }
    public Wire q() { return q; }
    public Wire qN() { return qN; }
}
