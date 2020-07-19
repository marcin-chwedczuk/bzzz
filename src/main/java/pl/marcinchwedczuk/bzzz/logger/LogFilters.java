package pl.marcinchwedczuk.bzzz.logger;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.primitives.LogicState;

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

    public static LogFilter logNothing() {
        return new LogFilter() {
            @Override
            public boolean shouldLog(ComponentId source) {
                return false;
            }
        };
    }
}
