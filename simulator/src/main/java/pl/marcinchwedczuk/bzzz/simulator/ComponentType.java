package pl.marcinchwedczuk.bzzz.simulator;

public class ComponentType {
    public static ComponentType of(Class<?> klass) {
        return new ComponentType(klass.getSimpleName());
    }

    private final String typeName;

    private ComponentType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return typeName;
    }
}
