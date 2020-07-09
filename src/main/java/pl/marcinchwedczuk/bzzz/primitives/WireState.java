package pl.marcinchwedczuk.bzzz.primitives;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import pl.marcinchwedczuk.bzzz.utils.X;

import java.util.Objects;
import java.util.Set;

public class WireState {
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

    public WireState withSourceStateChanged(LogicState state, ComponentId source) {
        switch (state) {
            case HIGH -> {
                return new WireState(
                    X.add(highStateSources, source),
                    X.remove(lowStateSources, source));
            }

            case LOW -> {
                return new WireState(
                        X.remove(highStateSources, source),
                        X.add(lowStateSources, source));
            }

            case HIGH_IMPEDANCE -> {
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
            return LogicState.HIGH;
        }

        if (lowStateSources.size() > 0) {
            return LogicState.LOW;
        }

        return LogicState.HIGH_IMPEDANCE;
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
