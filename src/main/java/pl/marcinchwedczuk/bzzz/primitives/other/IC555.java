package pl.marcinchwedczuk.bzzz.primitives.other;

import pl.marcinchwedczuk.bzzz.primitives.BaseElement;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.Duration;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public class IC555 extends BaseElement {
    private final Wire output;

    private int version = 1000;
    private boolean one = false;
    private Duration onePulseWidth = Duration.of(200);
    private Duration zeroPulseWidth = Duration.of(200);

    public IC555(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);

        output = new Wire(simulator, componentId.extend("outPin"));
        restart();
    }

    private void restart() {
        this.version++;

        simulator().schedule(componentId(), Duration.of(1), () -> this.run(version));
    }

    private void run(int versionSnapshot) {
        if (versionSnapshot != this.version) {
            // Configuration changed
            return;
        }

        if (one) {
            output.applyState(LogicState.ONE, componentId());
            simulator().schedule(componentId(), onePulseWidth, () -> run(versionSnapshot));
        }
        else {
            output.applyState(LogicState.ZERO, componentId());
            simulator().schedule(componentId(), zeroPulseWidth, () -> run(versionSnapshot));
        }

        one = !one;
    }

    public void squareWave(Duration pulseDuration) {
        this.onePulseWidth = pulseDuration;
        this.zeroPulseWidth = pulseDuration;
        restart();
    }

    public Wire output() { return output; }
}
