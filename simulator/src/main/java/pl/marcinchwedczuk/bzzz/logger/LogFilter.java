package pl.marcinchwedczuk.bzzz.logger;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;

public interface LogFilter {
    boolean shouldLog(ComponentId source);
}
