package pl.marcinchwedczuk.bzzz.ics.registers;

import pl.marcinchwedczuk.bzzz.ics.flipflops.edgetriggered.DFlipFlopAlt;
import pl.marcinchwedczuk.bzzz.primitives.BaseElement;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.gates.And;
import pl.marcinchwedczuk.bzzz.primitives.gates.Inverter;
import pl.marcinchwedczuk.bzzz.primitives.gates.Or;
import pl.marcinchwedczuk.bzzz.primitives.gates.TriStateBuffer;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

// Based on 74LS173
// See docs/ directory for details.
public class FourBitRegister extends BaseElement {
    // Inputs:
    public final Wire d1;
    public final Wire d2;
    public final Wire d3;
    public final Wire d4;

    // Outputs:
    public final Wire q1;
    public final Wire q2;
    public final Wire q3;
    public final Wire q4;

    // Control:
    public final Wire clock;
    public final Wire clear;

    public final Wire dataEnableN;
    public final Wire outputControlN;


    public FourBitRegister(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);

        // Control
        this.outputControlN = new Wire(simulator, componentId.extend("output-enabled-N-pin"));

        var dataEnableInverter = new Inverter(simulator, componentId.extend("data-enabled-inverter"));
        this.dataEnableN = dataEnableInverter.input;
        var dataEnableInternal = dataEnableInverter.output;

        var dataEnableInverter2 = new Inverter(simulator, componentId.extend("data-enabled-inverter-2"));
        var dataEnableInternalN = dataEnableInverter2.output;
        dataEnableInverter2.input.connectWith(dataEnableInverter.output);

        var clockInv = new Inverter(simulator, componentId.extend("clock-inv"));
        this.clock = clockInv.input;
        var clockN = clockInv.output;

        var clearInv = new Inverter(simulator, componentId.extend("clear-inv"));
        this.clear = clearInv.input;
        var clearN = clearInv.output;

        // Registers

        // ! Both arrays are 1 based
        Wire[] inputs = new Wire[5];
        Wire[] outputs = new Wire[5];

        var builder = new CircuitBuilder(simulator);
        for (int i = 1; i <= 4; i++) {
            ComponentId bitId = componentId.extend("bit-" + i);

            var dff = new DFlipFlopAlt(builder, bitId.extend("dflipflop"));
            dff.clock.connectWith(clockN);
            dff.resetN.connectWith(clearN);

            var qNInv = new Inverter(simulator, bitId.extend("d-qn-inv"));
            qNInv.input.connectWith(dff.qN);

            var triState = new TriStateBuffer(simulator, bitId.extend("tri-state-buff"));
            outputs[i] = triState.output;
            triState.input.connectWith(qNInv.output);
            triState.enableN.connectWith(outputControlN);

            var a1 = new And(simulator, bitId.extend("and-1"));
            var a2 = new And(simulator, bitId.extend("and-2"));
            var or1 = new Or(simulator, bitId.extend("or-1"));

            a1.input1.connectWith(dff.q);
            a1.input2.connectWith(dataEnableInternalN);

            inputs[i] = a2.input1;
            a2.input2.connectWith(dataEnableInternal);

            or1.input1.connectWith(a1.output);
            or1.input2.connectWith(a2.output);

            dff.d.connectWith(or1.output);
        }

        this.d1 = inputs[1];
        this.d2 = inputs[2];
        this.d3 = inputs[3];
        this.d4 = inputs[4];

        this.q1 = outputs[1];
        this.q2 = outputs[2];
        this.q3 = outputs[3];
        this.q4 = outputs[4];
    }
}
