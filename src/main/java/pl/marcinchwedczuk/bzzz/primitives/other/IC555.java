package pl.marcinchwedczuk.bzzz.primitives.other;

import pl.marcinchwedczuk.bzzz.primitives.BaseElement;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.wires.Wire;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

public class IC555 extends BaseElement {
    private final Wire output;

    private int version = 1000;
    private boolean one = false;
    private int onePulseWidth = 200;
    private int zeroPulseWidth = 200;

    public IC555(Simulator simulator, ComponentId componentId) {
        super(simulator, componentId);

        output = new Wire(simulator, componentId.extend("outPin"));
        restart();
    }

    private void restart() {
        this.version++;

        simulator().schedule(
                1,
                componentId(),
                describeAs("restart generator"),
                () -> this.run(version));
    }

    private void run(int versionSnapshot) {
        if (versionSnapshot != this.version) {
            // Configuration changed
            return;
        }

        if (one) {
            output.applyState(LogicState.ONE, componentId());
            simulator().schedule(
                    onePulseWidth,
                    componentId(),
                    describeAs("set output to 1"),
                    () -> run(versionSnapshot));
        }
        else {
            output.applyState(LogicState.ZERO, componentId());
            simulator().schedule(
                    zeroPulseWidth,
                    componentId(),
                    describeAs("set output to 0"),
                    () -> run(versionSnapshot));
        }

        one = !one;
    }

    public void squareWave(int pulseDuration) {
        this.onePulseWidth = pulseDuration;
        this.zeroPulseWidth = pulseDuration;
        restart();
    }

    public Wire output() { return output; }
}
