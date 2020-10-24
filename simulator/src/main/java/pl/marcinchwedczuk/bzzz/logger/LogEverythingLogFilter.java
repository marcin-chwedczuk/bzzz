package pl.marcinchwedczuk.bzzz.logger;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;

class LogEverythingLogFilter implements LogFilter {
    @Override
    public boolean shouldLog(ComponentId source) {
        return true;
    }
}
