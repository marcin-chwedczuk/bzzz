package pl.marcinchwedczuk.bzzz.primitives;

public interface Listener<S> {
    void stateChanged(S oldState, S newState);
}
