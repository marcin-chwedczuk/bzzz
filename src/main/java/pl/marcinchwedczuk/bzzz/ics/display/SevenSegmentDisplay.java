package pl.marcinchwedczuk.bzzz.ics.display;

import pl.marcinchwedczuk.bzzz.primitives.BaseElement;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;
import pl.marcinchwedczuk.bzzz.utils.X;

public class SevenSegmentDisplay extends BaseElement {
    private static final int[][] DISPLAY_MAP = new int[][] {
            // A, B, C, D, E, F, G, DP
            { 1, 1, 1, 1, 1, 1, 0, 0 }, // 0
            { 0, 1, 1, 0, 0, 0, 0, 0 }, // 1
            { 1, 1, 0, 1, 1, 0, 1, 0 }, // 2
            { 1, 1, 1, 1, 0, 0, 1, 0 }, // 3
            { 0, 1, 1, 0, 0, 1, 1, 0 }, // 4
            { 1, 0, 1, 1, 0, 1, 1, 0 }, // 5
            { 1, 0, 1, 1, 1, 1, 1, 0 }, // 6
            { 1, 1, 1, 0, 0, 0, 0, 0 }, // 7
            { 1, 1, 1, 1, 1, 1, 1, 0 }, // 8
            { 1, 1, 1, 1, 0, 1, 1, 0 }, // 9
            { 1, 1, 1, 0, 1, 1, 1, 0 }, // A
            { 0, 0, 1, 1, 1, 1, 1, 0 }, // b
            { 1, 0, 0, 1, 1, 1, 0, 0 }, // C
            { 0, 1, 1, 1, 1, 0, 1, 0 }, // d
            { 1, 0, 0, 1, 1, 1, 1, 0 }, // E
            { 1, 0, 0, 0, 1, 1, 1, 0 }, // F
    };

    //
    //    -a-
    //   f   b
    //    -g-
    //   e   c
    //    -d-   (dp)
    //
    public final Wire a;
    public final Wire b;
    public final Wire c;
    public final Wire d;
    public final Wire e;
    public final Wire f;
    public final Wire g;
    public final Wire dp;

    public SevenSegmentDisplay(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);

        a = new Wire(simulator, componentId.extend("a"));
        b = new Wire(simulator, componentId.extend("b"));
        c = new Wire(simulator, componentId.extend("c"));
        d = new Wire(simulator, componentId.extend("d"));
        e = new Wire(simulator, componentId.extend("e"));
        f = new Wire(simulator, componentId.extend("f"));
        g = new Wire(simulator, componentId.extend("g"));
        dp = new Wire(simulator, componentId.extend("dp"));
    }

    private int[] inputs() {
        int[] tmp = new int[8];
        tmp[0] = X.toInt(a.logicState().isOne());
        tmp[1] = X.toInt(b.logicState().isOne());
        tmp[2] = X.toInt(c.logicState().isOne());
        tmp[3] = X.toInt(d.logicState().isOne());
        tmp[4] = X.toInt(e.logicState().isOne());
        tmp[5] = X.toInt(f.logicState().isOne());
        tmp[6] = X.toInt(g.logicState().isOne());
        tmp[7] = X.toInt(dp.logicState().isOne());
        return tmp;
    }

    public String[] print3Lines() {
        return new String[] {
                String.format(" %s ", led(a, "_")),
                String.format("%s%s%s", led(f, "|"), led(g, "_"), led(b, "|")),
                String.format("%s%s%s", led(e, "|"), led(d, "_"), led(c, "|"))
        };
    }

    private String led(Wire w, String nonEmpty) {
        if (w.logicState().isZero()) {
            return nonEmpty;
        }

        return " ";
    }
}
