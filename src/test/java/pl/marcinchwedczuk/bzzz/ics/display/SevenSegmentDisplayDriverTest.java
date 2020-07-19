package pl.marcinchwedczuk.bzzz.ics.display;

import org.junit.Test;
import pl.marcinchwedczuk.bzzz.logger.LogFilter;
import pl.marcinchwedczuk.bzzz.logger.LogFilters;
import pl.marcinchwedczuk.bzzz.logger.Logger;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.passives.Switch;
import pl.marcinchwedczuk.bzzz.simulator.CircuitBuilder;
import pl.marcinchwedczuk.bzzz.simulator.EventLoopSimulator;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

import static org.junit.Assert.*;

public class SevenSegmentDisplayDriverTest {
    private final Simulator simulator = new EventLoopSimulator();
    private final CircuitBuilder builder = new CircuitBuilder(simulator);

    @Test public void smoke() {
        var display = new SevenSegmentDisplay(simulator, ComponentId.of("7segmentDisplay-1"));
        var driver = new SevenSegmentDisplayDriver(simulator, ComponentId.of("7segmentDriver-1"));

        driver.connect(display);

        var bit0 = builder.switchFor(driver.input1);
        var bit1 = builder.switchFor(driver.input2);
        var bit2 = builder.switchFor(driver.input3);
        var bit3 = builder.switchFor(driver.input4);

        Logger.filter = LogFilters.logNothing();
        simulator.run("init");

        var test = ComponentId.of("test");
        driver.lampTestN.applyState(LogicState.ONE, test);
        driver.rippleBlankingInputN.applyState(LogicState.ONE, test);

        Switch[] switches = { bit0, bit1, bit2, bit3 };
        var printer = new MultiSegmentPrinter(display, display, display, display);

        // Logger.filter = LogFilters.noFilter();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 4; j++) {
                if ((i & (1 << j)) == 0) {
                    switches[j].zero();
                }
                else {
                    switches[j].one();
                }
            }

            simulator.run("Display " + i);
            printer.printToStdOut();
        }
    }
}