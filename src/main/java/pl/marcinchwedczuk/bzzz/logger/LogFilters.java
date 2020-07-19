package pl.marcinchwedczuk.bzzz.logger;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;

public class LogFilters {
    public static LogFilter noFilter() {
        return new LogEverythingLogFilter();
    }

    public static LogFilter singleComponent(ComponentId component) {
        return new LogSelectedComponentsLogFilter(component);
    }

    public static LogFilter logComponents(ComponentId... components) {
        return new LogSelectedComponentsLogFilter(components);
    }
}
