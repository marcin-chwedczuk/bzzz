package pl.marcinchwedczuk.bzzz.primitives;

public interface LogicStateChangedListener {
    void onStateChanged(LogicState newState, ComponentId sourceId);
}
