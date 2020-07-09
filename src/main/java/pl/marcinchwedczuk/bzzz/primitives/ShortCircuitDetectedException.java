package pl.marcinchwedczuk.bzzz.primitives;

import com.google.common.collect.ImmutableSet;

public class ShortCircuitDetectedException extends RuntimeException {
    private final ImmutableSet<ComponentId> highStateComponents;
    private final ImmutableSet<ComponentId> lowStateComponents;

    public ShortCircuitDetectedException(
            ImmutableSet<ComponentId> highStateComponents,
            ImmutableSet<ComponentId> lowStateComponents) {
        this.highStateComponents = highStateComponents;
        this.lowStateComponents = lowStateComponents;
    }
}
