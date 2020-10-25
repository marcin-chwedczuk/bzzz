package pl.marcinchwedczuk.bzzz.util;

@FunctionalInterface
public interface TriFunction<A,B,C,R> {
    R apply(A a, B b, C c);
}
