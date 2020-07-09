package pl.marcinchwedczuk.bzzz.primitives;

import com.google.common.collect.ImmutableSet;
import org.junit.Assert;
import org.junit.Test;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.simulator.DoNothingSimulator;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
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

    @Test public void short_circuit_detection_test() {
        var wire = builder.wire("input");

        var switch1 = builder.aSwitch("switch#1");
        Wire.connect(switch1.output(), wire);

        var switch2 = builder.aSwitch("switch#2");
        Wire.connect(switch2.output(), wire);

        switch1.highState();

        try {
            switch2.lowState();
            fail("Short circuit not detected.");
        }
        catch (ShortCircuitDetectedException e) {
            assertEquals(
                    ImmutableSet.of(switch1.componentId()),
                    e.highStateComponents);

            assertEquals(
                    ImmutableSet.of(switch2.componentId()),
                    e.lowStateComponents);
        }
    }
}
