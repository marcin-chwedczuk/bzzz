package pl.marcinchwedczuk.bzzz.ics.registers;

import org.junit.Test;
import pl.marcinchwedczuk.bzzz.ics.display.MultiSegmentPrinter;
import pl.marcinchwedczuk.bzzz.ics.display.SevenSegmentDisplay;
import pl.marcinchwedczuk.bzzz.ics.display.SevenSegmentDisplayDriver;
import pl.marcinchwedczuk.bzzz.ics.other.Summator4;
import pl.marcinchwedczuk.bzzz.logger.LogFilters;
import pl.marcinchwedczuk.bzzz.logger.Logger;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.simulator.EventLoopSimulator;
import pl.marcinchwedczuk.bzzz.simulator.FiniteSimulationResult;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

import static org.junit.Assert.*;

public class FourBitRegisterTest {
    private final Simulator simulator = new EventLoopSimulator();
    private final CircuitBuilder builder = new CircuitBuilder(simulator);

    @Test public void foo() throws InterruptedException {
        var test = ComponentId.of("test");
        var r4 = new FourBitRegister(simulator, ComponentId.of("r4"));
        var sum4 = new Summator4(simulator, ComponentId.of("sum4"));

        Logger.filter = LogFilters.logNothing();

        sum4.A1.connectWith(r4.q1);
        sum4.A2.connectWith(r4.q2);
        sum4.A3.connectWith(r4.q3);
        sum4.A4.connectWith(r4.q4);

        sum4.carryIn.applyState(LogicState.ZERO, test);
        sum4.B1.applyState(LogicState.ONE, test);
        sum4.B2.applyState(LogicState.ZERO, test);
        sum4.B3.applyState(LogicState.ZERO, test);
        sum4.B4.applyState(LogicState.ZERO, test);

        r4.d1.connectWith(sum4.S1);
        r4.d2.connectWith(sum4.S2);
        r4.d3.connectWith(sum4.S3);
        r4.d4.connectWith(sum4.S4);

        // Display
        var display = new SevenSegmentDisplay(simulator, ComponentId.of("7segmentDisplay-1"));
        var driver = new SevenSegmentDisplayDriver(simulator, ComponentId.of("7segmentDriver-1"));
        driver.connect(display);

        driver.input1.connectWith(r4.q1);
        driver.input2.connectWith(r4.q2);
        driver.input3.connectWith(r4.q3);
        driver.input4.connectWith(r4.q4);

        r4.clear.applyState(LogicState.ZERO, ComponentId.of("test"));
        r4.clock.applyState(LogicState.ONE, ComponentId.of("test"));
        r4.dataEnableN.applyState(LogicState.ZERO, test);
        r4.outputControlN.applyState(LogicState.ZERO, test);

        driver.lampTestN.applyState(LogicState.ONE, test);
        driver.rippleBlankingInputN.applyState(LogicState.ONE, test);

        var printer = new MultiSegmentPrinter(display);


        var r = simulator.run(40000, "Register 4-bit init");
        assertEquals(FiniteSimulationResult.FINISHED, r);

        for (int i = 0; i < 50; i++) {
            LogicState tmp = r4.clock.logicState().reverse();
            r4.clock.applyState(tmp, test);

            r = simulator.run(40000, "Register 4-bit init");
            assertEquals(FiniteSimulationResult.FINISHED, r);

            printer.printToStdOut();
            Thread.sleep(500);
        }
    }
}