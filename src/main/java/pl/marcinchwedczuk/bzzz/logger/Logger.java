package pl.marcinchwedczuk.bzzz.logger;

import pl.marcinchwedczuk.bzzz.primitives.ComponentId;
import pl.marcinchwedczuk.bzzz.simulator.ComponentType;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;
import pl.marcinchwedczuk.bzzz.utils.Pipe;

public class Logger {
    private static LogFilter filter = LogFilters.noFilter();

    private final ComponentId owner;
    private final ComponentType type;

    public Logger(ComponentId owner, ComponentType type) {
        this.owner = owner;
        this.type = type;
    }

    public void log(String fmt, Object... args) {
        if (!filter.shouldLog(owner)) { return; }

        var meta = metaInfo();
        var message = String.format(fmt, args);
        var logLine = String.format("%s %s", meta, message);

        logInternal(logLine);
    }

    private String metaInfo() {
        return String.format("[component=%s][type=%s]", owner, type);
    }

    private void logInternal(String logLine) {
        System.out.println(logLine);
    }
}
