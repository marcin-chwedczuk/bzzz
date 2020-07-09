package pl.marcinchwedczuk.bzzz.primitives;

import com.google.common.collect.ImmutableSet;

public class ShortCircuitDetectedException extends RuntimeException {
    public final ImmutableSet<ComponentId> highStateComponents;
    public final ImmutableSet<ComponentId> lowStateComponents;

    public ShortCircuitDetectedException(
            ImmutableSet<ComponentId> highStateComponents,
            ImmutableSet<ComponentId> lowStateComponents) {
        this.highStateComponents = highStateComponents;
        this.lowStateComponents = lowStateComponents;
    }
}
