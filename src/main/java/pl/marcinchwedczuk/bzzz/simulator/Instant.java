package pl.marcinchwedczuk.bzzz.simulator;

import java.util.Objects;

public class Instant implements Comparable<Instant> {
    public static Instant of(long units) {
        return new Instant(units);
    }

    private final long units;

    public Instant(long units) {
        this.units = units;
    }

    public Instant add(Duration d) {
        return new Instant(units + d.toUnits());
    }

    public boolean isBefore(Instant other) {
        return this.units < other.units;
    }

    public boolean isAfter(Instant other) {
        return this.units > other.units;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instant instant = (Instant) o;
        return units == instant.units;
    }

    @Override
    public int hashCode() {
        return Objects.hash(units);
    }

    @Override
    public String toString() {
        return Long.toString(units);
    }

    @Override
    public int compareTo(Instant other) {
        return Long.signum(this.units - other.units);
    }
}
