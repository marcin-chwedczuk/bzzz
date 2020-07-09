package pl.marcinchwedczuk.bzzz.utils;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class X {

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
}
