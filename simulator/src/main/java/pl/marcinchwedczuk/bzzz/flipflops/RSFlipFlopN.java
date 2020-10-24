package pl.marcinchwedczuk.bzzz.ics.flipflops;

import pl.marcinchwedczuk.bzzz.primitives.BaseElement;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.gates.Nand;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public class RSFlipFlopN {
    private final Nand topGate;
    private final Nand bottomGate;

    public RSFlipFlopN(CircuitBuilder builder, ComponentId componentId) {
        topGate = builder.nand(componentId.extend("RS-Nand#1"));
        bottomGate = builder.nand(componentId.extend("RS-Nand#2"));

        topGate.input2().connectWith(bottomGate.output());
        bottomGate.input1().connectWith(topGate.output());
    }

    public Wire setN() { return topGate.input1(); }
    public Wire resetN() { return bottomGate.input2(); }

    public Wire q() { return topGate.output(); }
    public Wire qN() { return bottomGate.output(); }
}
