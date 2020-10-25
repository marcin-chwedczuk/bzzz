package pl.marcinchwedczuk.bzzz.util;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.Streams;

import java.util.Arrays;
import java.util.stream.Stream;

public class X {
    public static int toInt(boolean b) {
        return b ? 1 : 0;
    }

    public static <E> ImmutableSet<E> add(ImmutableSet<E> set, E element) {
        if (set.contains(element)) return set;
        return new ImmutableSet.Builder<E>()
                .addAll(set)
                .add(element)
                .build();
    }

    public static <E> ImmutableSet<E> remove(ImmutableSet<E> set, E element) {
        if (!set.contains(element)) return set;
        return ImmutableSet.copyOf(
            Sets.difference(set, ImmutableSet.of(element)));
    }

    public static <T> T[] defensiveCopy(T[] arr) {
        return Arrays.copyOf(arr, arr.length);
    }

    @SuppressWarnings("unchecked")
    public static <A,B,C,R> Stream<R> zip(Stream<A> a, Stream<B> b, Stream<C> c, TriFunction<A,B,C,R> f) {
        return Streams.zip(
            Streams.zip(a, b, (aElem, bElem) -> new Object[] { aElem, bElem }),
            c,
            (ab, cElem) -> f.apply((A)ab[0], (B)ab[1], cElem));
    }


}
