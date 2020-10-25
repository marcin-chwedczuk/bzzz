package pl.marcinchwedczuk.bzzz.util;

import java.util.function.Consumer;
import java.util.function.Function;

public class Pipe<V> {
    public static <V> Pipe<V> of(V value) {
        return new Pipe<>(value);
    }

    private final V value;

    private Pipe(V v) {
        this.value = v;
    }

    public <R> Pipe<R> map(Function<V,R> mapper) {
        return of(mapper.apply(value));
    }

    public void consume(Consumer<V> consumer) {
        consumer.accept(value);
    }

    public V value() { return value; }
}
