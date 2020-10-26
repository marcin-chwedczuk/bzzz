package pl.marcinchwedczuk.bzzz.ics.other;

import pl.marcinchwedczuk.bzzz.primitives.BaseElement;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.gates.*;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

/**
 * 4-bit Summator.
 *
 * A4/B4 is Most Significant Bit.
 */
public class Summator4 extends BaseElement {
    public final Wire carryIn;

    public final Wire A1;
    public final Wire A2;
    public final Wire A3;
    public final Wire A4;

    public final Wire B1;
    public final Wire B2;
    public final Wire B3;
    public final Wire B4;

    public final Wire S1;
    public final Wire S2;
    public final Wire S3;
    public final Wire S4;

    public final Wire carryOut;

    public Summator4(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);

        // Levels X Y Z W

        var x1 = new Inverter(simulator, componentId.extend("x1"));
        var x2 = new Nor(simulator, componentId.extend("x2"));
        var x3 = new Nand(simulator, componentId.extend("x3"));
        var x4 = new Nor(simulator, componentId.extend("x4"));
        var x5 = new Nand(simulator, componentId.extend("x5"));
        var x6 = new Nor(simulator, componentId.extend("x6"));
        var x7 = new Nand(simulator, componentId.extend("x7"));
        var x8 = new Nor(simulator, componentId.extend("x8"));
        var x9 = new Nand(simulator, componentId.extend("x9"));

        // Inputs
        carryIn = x1.input;

        A1 = x2.input1;
        B1 = x3.input2;
        x2.input2.connectWith(B1);
        x3.input1.connectWith(A1);

        A2 = x4.input1;
        B2 = x5.input2;
        x4.input2.connectWith(B2);
        x5.input1.connectWith(A2);

        A3 = x6.input1;
        B3 = x7.input2;
        x6.input2.connectWith(B3);
        x7.input1.connectWith(A3);

        A4 = x8.input1;
        B4 = x9.input2;
        x8.input2.connectWith(B4);
        x9.input1.connectWith(A4);

        // Level Y
        var y1 = new Inverter(simulator, componentId.extend("y1"));
        var y2 = new And1N1(simulator, componentId.extend("y2"));
        var y3 = new And(simulator, componentId.extend("y3"));
        var y4 = new Buffer(simulator, componentId.extend("y4"));
        var y5 = new And1N1(simulator, componentId.extend("y5"));
        var y6 = new And3(simulator, componentId.extend("y6"));
        var y7 = new And(simulator, componentId.extend("y7"));
        var y8 = new Buffer(simulator, componentId.extend("y8"));
        var y9 = new And1N1(simulator, componentId.extend("y9"));
        var y10 = new And4(simulator, componentId.extend("y10"));
        var y11 = new And3(simulator, componentId.extend("y11"));
        var y12 = new And(simulator, componentId.extend("y12"));
        var y13 = new Buffer(simulator, componentId.extend("y13"));
        var y14 = new And1N1(simulator, componentId.extend("y14"));
        var y15 = new And5(simulator, componentId.extend("y15"));
        var y16 = new And4(simulator, componentId.extend("y16"));
        var y17 = new And3(simulator, componentId.extend("y17"));
        var y18 = new And(simulator, componentId.extend("y18"));
        var y19 = new Buffer(simulator, componentId.extend("y19"));

        // Connection matrix
        y1.input.connectWith(x1.output);
        y3.input1.connectWith(x1.output);
        y6.input1.connectWith(x1.output);
        y10.input1.connectWith(x1.output);
        y15.input1.connectWith(x1.output);

        y2.input1N.connectWith(x2.output);
        y4.input.connectWith(x2.output);
        y7.input2.connectWith(x2.output);
        y11.input3.connectWith(x2.output);
        y16.input4.connectWith(x2.output);

        y2.input2.connectWith(x3.output);
        y3.input2.connectWith(x3.output);
        y6.input2.connectWith(x3.output);
        y10.input2.connectWith(x3.output);
        y15.input2.connectWith(x3.output);

        y5.input1N.connectWith(x4.output);
        y8.input.connectWith(x4.output);
        y12.input2.connectWith(x4.output);
        y17.input3.connectWith(x4.output);

        y5.input2.connectWith(x5.output);
        y6.input3.connectWith(x5.output);
        y7.input1.connectWith(x5.output);
        y10.input3.connectWith(x5.output);
        y11.input1.connectWith(x5.output);
        y15.input3.connectWith(x5.output);
        y16.input1.connectWith(x5.output);

        y9.input1N.connectWith(x6.output);
        y13.input.connectWith(x6.output);
        y18.input2.connectWith(x6.output);

        y9.input2.connectWith(x7.output);
        y10.input4.connectWith(x7.output);
        y11.input2.connectWith(x7.output);
        y12.input1.connectWith(x7.output);
        y15.input4.connectWith(x7.output);
        y16.input2.connectWith(x7.output);
        y17.input1.connectWith(x7.output);

        y14.input1N.connectWith(x8.output);
        y19.input.connectWith(x8.output);

        y14.input2.connectWith(x9.output);
        y15.input5.connectWith(x9.output);
        y16.input3.connectWith(x9.output);
        y17.input2.connectWith(x9.output);
        y18.input1.connectWith(x9.output);

        // Z layer
        var z1 = new Nor(simulator, componentId.extend("z1"));
        var z2 = new Nor3(simulator, componentId.extend("z2"));
        var z3 = new Nor4(simulator, componentId.extend("z3"));
        var z4 = new Nor5(simulator, componentId.extend("z4"));

        z1.input1.connectWith(y3.output);
        z1.input2.connectWith(y4.output);

        z2.input1.connectWith(y6.output);
        z2.input2.connectWith(y7.output);
        z2.input3.connectWith(y8.output);

        z3.input1.connectWith(y10.output);
        z3.input2.connectWith(y11.output);
        z3.input3.connectWith(y12.output);
        z3.input4.connectWith(y13.output);

        z4.input1.connectWith(y15.output);
        z4.input2.connectWith(y16.output);
        z4.input3.connectWith(y17.output);
        z4.input4.connectWith(y18.output);
        z4.input5.connectWith(y19.output);

        // Outputs
        var w1 = new Xor(simulator, componentId.extend("w1"));
        S1 = w1.output;
        w1.input1.connectWith(y1.output);
        w1.input2.connectWith(y2.output);

        var w2 = new Xor(simulator, componentId.extend("w2"));
        S2 = w2.output;
        w2.input1.connectWith(z1.output);
        w2.input2.connectWith(y5.output);

        var w3 = new Xor(simulator, componentId.extend("w3"));
        S3 = w3.output;
        w3.input1.connectWith(z2.output);
        w3.input2.connectWith(y9.output);

        var w4 = new Xor(simulator, componentId.extend("w4"));
        S4 = w4.output;
        w4.input1.connectWith(z3.output);
        w4.input2.connectWith(y14.output);

        carryOut = z4.output;
    }


}
