package pl.marcinchwedczuk.bzzz.simulator;

public class Duration {
    public static final Duration zero = Duration.of(0);

    public static Duration of(long units) {
        return new Duration(units);
    }

    private final long units;

    private Duration(long units) {
        this.units = units;
    }

    long toUnits() {
        return units;
    }
}
