package pl.marcinchwedczuk.bzzz.primitives;

import java.util.ArrayList;
import java.util.List;

public class ListenerGroup<S> implements Listener<S> {
    private final List<Listener<S>> listeners = new ArrayList<>();

    public void add(Listener<S> listener) {
        listeners.add(listener);
    }

    @Override
    public void stateChanged(S oldState, S newState) {
        for (Listener<S> listener : listeners) {
            listener.stateChanged(oldState, newState);
        }
    }
}
