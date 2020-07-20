package pl.marcinchwedczuk.bzzz.ics.registers;

import org.junit.Test;
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

    @Test public void foo() {
        var r4 = new FourBitRegister(simulator, ComponentId.of("r4"));

        r4.clear.applyState(LogicState.ZERO, ComponentId.of("test"));
        r4.clock.applyState(LogicState.ZERO, ComponentId.of("test"));
        var r = simulator.run(1000, "Register 4-bit init");
        assertEquals(FiniteSimulationResult.FINISHED, r);

        r4.clock.applyState(LogicState.ONE, ComponentId.of("test"));
        simulator.run("after clock one");
        r4.clock.applyState(LogicState.ZERO, ComponentId.of("test"));
        simulator.run("after clock zero");

        r4.clock.applyState(LogicState.ONE, ComponentId.of("test"));
        simulator.run("after clock one");
        r4.clock.applyState(LogicState.ZERO, ComponentId.of("test"));
        simulator.run("after clock zero");
    }
}