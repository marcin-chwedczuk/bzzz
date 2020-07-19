package pl.marcinchwedczuk.bzzz.ics.display;

import pl.marcinchwedczuk.bzzz.primitives.BaseElement;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.gates.*;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

// Based on 74LS47 (see docs/ directory)
public class SevenSegmentDisplayDriver extends BaseElement {
    public final Wire a;
    public final Wire b;
    public final Wire c;
    public final Wire d;
    public final Wire e;
    public final Wire f;
    public final Wire g;

    public final Wire input1;
    public final Wire input2;
    public final Wire input3;
    public final Wire input4;

    public final Wire rippleBlankingOutputN;
    public final Wire lampTestN;
    public final Wire rippleBlankingInputN;

    public SevenSegmentDisplayDriver(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);

        a = new Wire(simulator, componentId.extend("a"));
        b = new Wire(simulator, componentId.extend("b"));
        c = new Wire(simulator, componentId.extend("c"));
        d = new Wire(simulator, componentId.extend("d"));
        e = new Wire(simulator, componentId.extend("e"));
        f = new Wire(simulator, componentId.extend("f"));
        g = new Wire(simulator, componentId.extend("g"));

        Inverter[] nots = new Inverter[7];
        for (int i = 0; i < nots.length; i++) {
            nots[i] = new Inverter(simulator, componentId.extend("outputInverter" + i));
        }

        // Connect Inverters to outputs
        Wire[] outputs = new Wire[] { a, b, c, d, e, f, g };
        for (int i = 0; i < nots.length; i++) {
            nots[i].output().connectWith(outputs[i]);
        }

        // Wire matrix - 1 BASED (not zero based)
        Wire[] m = new Wire[9];
        for (int i = 1; i < m.length; i++) {
            m[i] = new Wire(simulator, componentId.extend("matrix_" + i));
        }

        // Output A
        {
            Nor3 nor3 = new Nor3(simulator, componentId.extend("a-nor"));
            nor3.output.connectWith(nots[0].input());

            And a1 = new And(simulator, componentId.extend("a-and1"));
            And a2 = new And(simulator, componentId.extend("a-and2"));
            And4 a3 = new And4(simulator, componentId.extend("a-and3"));

            nor3.input1.connectWith(a1.output());
            nor3.input2.connectWith(a2.output());
            nor3.input3.connectWith(a3.output());

            a1.input1().connectWith(m[3]);
            a1.input2().connectWith(m[7]);

            a2.input1().connectWith(m[2]);
            a2.input2().connectWith(m[5]);

            a3.input1().connectWith(m[1]);
            a3.input2().connectWith(m[4]);
            a3.input3().connectWith(m[6]);
            a3.input4().connectWith(m[8]);
        }

        // Output B
        {
            Nor3 nor3 = new Nor3(simulator, componentId.extend("b-nor"));
            nor3.output.connectWith(nots[1].input());

            And a1 = new And(simulator, componentId.extend("b-and1"));
            And3 a2 = new And3(simulator, componentId.extend("b-and2"));
            And3 a3 = new And3(simulator, componentId.extend("b-and3"));

            nor3.input1.connectWith(a1.output());
            nor3.input2.connectWith(a2.output());
            nor3.input3.connectWith(a3.output());

            a1.input1().connectWith(m[3]);
            a1.input2().connectWith(m[7]);

            a2.input1().connectWith(m[1]);
            a2.input2().connectWith(m[4]);
            a2.input3().connectWith(m[5]);

            a3.input1().connectWith(m[2]);
            a3.input2().connectWith(m[3]);
            a3.input3().connectWith(m[5]);
        }

        // Output C
        {
            Nor2 nor = new Nor2(simulator, componentId.extend("c-nor"));
            nor.output.connectWith(nots[2].input());

            And a1 = new And(simulator, componentId.extend("c-and1"));
            And3 a2 = new And3(simulator, componentId.extend("c-and2"));

            nor.input1.connectWith(a1.output());
            nor.input2.connectWith(a2.output());

            a1.input1().connectWith(m[5]);
            a1.input2().connectWith(m[7]);

            a2.input1().connectWith(m[2]);
            a2.input2().connectWith(m[3]);
            a2.input3().connectWith(m[6]);
        }

        // Output D
        {
            Nor3 nor3 = new Nor3(simulator, componentId.extend("d-nor"));
            nor3.output.connectWith(nots[3].input());

            And3 a1 = new And3(simulator, componentId.extend("d-and1"));
            And3 a2 = new And3(simulator, componentId.extend("d-and2"));
            And3 a3 = new And3(simulator, componentId.extend("d-and3"));

            nor3.input1.connectWith(a1.output());
            nor3.input2.connectWith(a2.output());
            nor3.input3.connectWith(a3.output());

            a1.input1().connectWith(m[1]);
            a1.input2().connectWith(m[4]);
            a1.input3().connectWith(m[6]);

            a2.input1().connectWith(m[2]);
            a2.input2().connectWith(m[4]);
            a2.input3().connectWith(m[5]);

            a3.input1().connectWith(m[1]);
            a3.input2().connectWith(m[3]);
            a3.input3().connectWith(m[5]);
        }

        // Output E
        {
            Nor2 nor = new Nor2(simulator, componentId.extend("e-nor"));
            nor.output.connectWith(nots[4].input());

            And a1 = new And(simulator, componentId.extend("e-and1"));
            Buffer a2 = new Buffer(simulator, componentId.extend("e-buff1"));

            nor.input1.connectWith(a1.output());
            nor.input2.connectWith(a2.output());

            a2.input().connectWith(m[1]);
            a1.input1().connectWith(m[4]);
            a1.input2().connectWith(m[5]);
        }

        // Output F
        {
            Nor3 nor3 = new Nor3(simulator, componentId.extend("f-nor"));
            nor3.output.connectWith(nots[5].input());

            And a1 = new And(simulator, componentId.extend("f-and1"));
            And a2 = new And(simulator, componentId.extend("f-and2"));
            And3 a3 = new And3(simulator, componentId.extend("f-and3"));

            nor3.input1.connectWith(a1.output());
            nor3.input2.connectWith(a2.output());
            nor3.input3.connectWith(a3.output());

            a1.input1().connectWith(m[1]);
            a1.input2().connectWith(m[3]);

            a2.input1().connectWith(m[3]);
            a2.input2().connectWith(m[6]);

            a3.input1().connectWith(m[1]);
            a3.input2().connectWith(m[6]);
            a3.input3().connectWith(m[8]);
        }

        Wire bufLampTest = new Wire(simulator, componentId.extend("wBufLT"));

        // Output G
        {
            Nor2 nor = new Nor2(simulator, componentId.extend("g-nor"));
            nor.output.connectWith(nots[6].input());

            And3 a1 = new And3(simulator, componentId.extend("g-and1"));
            And4 a2 = new And4(simulator, componentId.extend("g-and2"));

            nor.input1.connectWith(a1.output());
            nor.input2.connectWith(a2.output());

            a1.input1().connectWith(m[1]);
            a1.input2().connectWith(m[3]);
            a1.input3().connectWith(m[5]);

            a2.input1().connectWith(m[4]);
            a2.input2().connectWith(m[6]);
            a2.input3().connectWith(m[8]);
            a2.input4().connectWith(bufLampTest);
        }

        input1 = new Wire(simulator, componentId.inputPin(1));
        input2 = new Wire(simulator, componentId.inputPin(2));
        input3 = new Wire(simulator, componentId.inputPin(3));
        input4 = new Wire(simulator, componentId.inputPin(4));

        this.rippleBlankingInputN = new Wire(simulator, componentId.extend("rbiN"));
        this.rippleBlankingOutputN = new Wire(simulator, componentId.extend("rboN"));
        this.lampTestN = new Wire(simulator, componentId.extend("lampTestN"));

        {
            var buff = new Buffer(simulator, componentId.extend("lampBuff1"));
            buff.output().connectWith(bufLampTest);
            buff.input().connectWith(lampTestN);
        }

        Nand6 mega = new Nand6(simulator, componentId.extend("mega"));
        mega.input1().connectWith(bufLampTest);
        {
            var inv = new Inverter(simulator, componentId.extend("mega-inv1"));
            inv.input().connectWith(rippleBlankingInputN);
            inv.output().connectWith(mega.input2());
        }
        mega.input3().connectWith(m[8]);
        mega.input4().connectWith(m[6]);
        mega.input5().connectWith(m[4]);
        mega.input6().connectWith(m[2]);

        Nand n1 = new Nand(simulator, componentId.extend("n1"));
        n1.input1().connectWith(input1);
        n1.input2().connectWith(bufLampTest);
        n1.output().connectWith(m[2]);

        Nand n2 = new Nand(simulator, componentId.extend("n2"));
        n2.input1().connectWith(n1.output());
        n2.input2().connectWith(mega.output());
        n2.output().connectWith(m[1]);

        Nand n3 = new Nand(simulator, componentId.extend("n3"));
        n3.input1().connectWith(input2);
        n3.input2().connectWith(bufLampTest);
        n3.output().connectWith(m[4]);

        Nand n4 = new Nand(simulator, componentId.extend("n4"));
        n4.input1().connectWith(n3.output());
        n4.input2().connectWith(mega.output());
        n4.output().connectWith(m[3]);

        Nand n5 = new Nand(simulator, componentId.extend("n5"));
        n5.input1().connectWith(input3);
        n5.input2().connectWith(bufLampTest);
        n5.output().connectWith(m[6]);

        Nand n6 = new Nand(simulator, componentId.extend("n6"));
        n6.input1().connectWith(n5.output());
        n6.input2().connectWith(mega.output());
        n6.output().connectWith(m[5]);

        Inverter n7 = new Inverter(simulator, componentId.extend("n7"));
        n7.input().connectWith(input4);
        n7.output().connectWith(m[8]);

        Nand n8 = new Nand(simulator, componentId.extend("n8"));
        n8.input1().connectWith(n7.output());
        n8.input2().connectWith(mega.output());
        n8.output().connectWith(m[7]);
    }

    public void connect(SevenSegmentDisplay display) {
        display.a.connectWith(a);
        display.b.connectWith(b);
        display.c.connectWith(c);
        display.d.connectWith(d);
        display.e.connectWith(e);
        display.f.connectWith(f);
        display.g.connectWith(g);
    }
}
