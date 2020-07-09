package pl.marcinchwedczuk.bzzz.primitives;

import java.util.Objects;

public class ComponentId {
    public static ComponentId of(String s) {
        return new ComponentId(s);
    }

    private final String path;

    private ComponentId(String path) {
        this.path = path;
    }

    public ComponentId extend(String part) {
        return new ComponentId(path + "/" + part);
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
