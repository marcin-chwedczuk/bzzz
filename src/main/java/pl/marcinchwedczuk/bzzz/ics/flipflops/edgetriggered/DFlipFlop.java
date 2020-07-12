package pl.marcinchwedczuk.bzzz.ics.flipflops.edgetriggered;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;

public class DFlipFlop {
    private final Wire q;
    private final Wire qN;
    private final Wire clock;
    private final Wire d;

    public DFlipFlop(CircuitBuilder builder, ComponentId componentId) {
        // Top latch
        var topLatchNand1 = builder.nand(componentId.extend("topLatch-nand#1"));
        var topLatchNand2 = builder.nand(componentId.extend("topLatch-nand#2"));
        topLatchNand1.output().connectWith(topLatchNand2.input1());
        topLatchNand2.output().connectWith(topLatchNand1.input2());
        clock = topLatchNand2.input2();

        var rsNand1 = builder.nand(componentId.extend("rs-nand#1"));
        var rsNand2 = builder.nand(componentId.extend("rs-nand#2"));
        rsNand1.output().connectWith(rsNand2.input1());
        rsNand2.output().connectWith(rsNand1.input2());
        q = rsNand1.output(); qN = rsNand2.output();

        var bottomLatchNand1 = builder.nand3(componentId.extend("bottomLatch-nand#1"));
        var bottomLatchNand2 = builder.nand(componentId.extend("bottomLatch-nand#2"));
        bottomLatchNand1.output().connectWith(bottomLatchNand2.input1());
        bottomLatchNand2.output().connectWith(bottomLatchNand1.input3());
        bottomLatchNand1.input2().connectWith(clock);
        d = bottomLatchNand2.input2();

        bottomLatchNand1.input3().connectWith(topLatchNand1.input1());
        topLatchNand2.output().connectWith(bottomLatchNand1.input1());

        topLatchNand2.output().connectWith(rsNand1.input1());
        bottomLatchNand1.output().connectWith(rsNand2.input2());
    }

    public Wire clock() { return clock; }
    public Wire d() { return d; }
    public Wire q() { return q; }
    public Wire qN() { return qN; }
}
