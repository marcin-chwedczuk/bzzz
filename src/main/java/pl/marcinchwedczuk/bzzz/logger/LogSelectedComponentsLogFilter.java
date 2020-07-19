package pl.marcinchwedczuk.bzzz.logger;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;

import java.util.Set;

class LogSelectedComponentsLogFilter implements LogFilter {
    private final Set<ComponentId> components;

    LogSelectedComponentsLogFilter(ComponentId... components) {
        this.components = Set.of(components);
    }

    @Override
    public boolean shouldLog(ComponentId source) {
        return components.contains(source);
    }
}
