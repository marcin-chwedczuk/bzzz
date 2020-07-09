package pl.marcinchwedczuk.bzzz.primitives.wires;

import com.google.common.collect.ImmutableSet;
import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;
import pl.marcinchwedczuk.bzzz.primitives.ShortCircuitDetectedException;
import pl.marcinchwedczuk.bzzz.utils.X;

import java.util.Objects;

class WireState {
    public final ImmutableSet<ComponentId> highStateSources;
    public final ImmutableSet<ComponentId> lowStateSources;

    public WireState() {
        highStateSources = ImmutableSet.of();
        lowStateSources = ImmutableSet.of();
    }

    public WireState(ImmutableSet<ComponentId> highStateSources,
                     ImmutableSet<ComponentId> lowStateSources) {
        this.highStateSources = highStateSources;
        this.lowStateSources = lowStateSources;
    }

    public WireState withChangedState(LogicState state, ComponentId source) {
        switch (state) {
            case ONE -> {
                return new WireState(
                    X.add(highStateSources, source),
                    X.remove(lowStateSources, source));
            }

            case ZERO -> {
                return new WireState(
                        X.remove(highStateSources, source),
                        X.add(lowStateSources, source));
            }

            case NOT_CONNECTED -> {
                return new WireState(
                        X.remove(highStateSources, source),
                        X.remove(lowStateSources, source));
            }
        }

        throw new AssertionError();
    }

    public LogicState logicState() {
        // Ignore short-circuits

        if (highStateSources.size() > 0) {
            return LogicState.ONE;
        }

        if (lowStateSources.size() > 0) {
            return LogicState.ZERO;
        }

        return LogicState.NOT_CONNECTED;
    }

    public void detectShortCircuit() {
        boolean shortCircuit =
               highStateSources.size() > 0 &&
               lowStateSources.size() > 0;

        if (shortCircuit) {
            throw new ShortCircuitDetectedException(
                    highStateSources, lowStateSources);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WireState wireState = (WireState) o;
        return Objects.equals(highStateSources, wireState.highStateSources) &&
                Objects.equals(lowStateSources, wireState.lowStateSources);
    }

    @Override
    public int hashCode() {
        return Objects.hash(highStateSources, lowStateSources);
    }
}
