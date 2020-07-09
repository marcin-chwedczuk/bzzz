package pl.marcinchwedczuk.bzzz.primitives;

import org.junit.Test;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.simulator.DoNothingSimulator;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

import static org.junit.Assert.assertEquals;
import static pl.marcinchwedczuk.bzzz.primitives.LogicState.*;

public class WireTest {

    private final Simulator simulator = new DoNothingSimulator();
    private final CircuitBuilder builder = new CircuitBuilder(simulator);

    @Test public void broadcast_test() {
        var input = builder.wire("input");

        var leftUp = builder.wire("leftUp");
        var leftDown = builder.wire("leftDown");
        var vertical = builder.wire("vertical");
        var rightUp = builder.wire("rightUp");
        var rightDown = builder.wire("rightDown");

        var output = builder.wire("output");

        Wire.connect(input, leftUp, leftDown);
        Wire.connect(leftUp, vertical, rightUp);
        Wire.connect(leftDown, vertical, rightDown);
        Wire.connect(output, rightUp, rightDown);

        var outputProbe = builder.probeFor(output);

        var aSwitch = builder.aSwitch("switch#1");
        Wire.connect(aSwitch.output(), input);

        outputProbe.assertState(HIGH_IMPEDANCE);

        aSwitch.highState();
        outputProbe.assertState(HIGH);

        aSwitch.lowState();
        outputProbe.assertState(LOW);

        aSwitch.off();
        outputProbe.assertState(HIGH_IMPEDANCE);
    }
}
