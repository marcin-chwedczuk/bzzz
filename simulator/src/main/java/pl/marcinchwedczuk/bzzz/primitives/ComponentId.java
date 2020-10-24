package pl.marcinchwedczuk.bzzz.primitives;

import java.util.Objects;

public class ComponentId {
    public static final ComponentId empty = of("");

    private static final String SEPARATOR = "/";

    public static ComponentId of(String s) {
        if (s.contains(SEPARATOR))
            throw new IllegalArgumentException("path cannot contain separator character.");

        return new ComponentId(s);
    }

    private final String path;

    private ComponentId(String path) {
        this.path = path;
    }

    public ComponentId extend(String part) {
        if (part.contains(SEPARATOR))
            throw new IllegalArgumentException("path cannot contain separator character.");

        return new ComponentId(path + SEPARATOR + part);
    }

    public ComponentId outputPin() {
        return this.extend("outputPin");
    }

    public ComponentId inputPin() {
        return this.extend("inputPin");
    }

    public ComponentId inputPin(int index) {
        return this.extend("input" + index + "Pin");
    }

    public ComponentId enableNPin() {
        return this.extend("enableNPin");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentId that = (ComponentId) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    @Override
    public String toString() {
        return path;
    }
}
