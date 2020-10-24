package pl.marcinchwedczuk.bzzz.primitives;

public enum LogicState {
    ONE, ZERO, NOT_CONNECTED;

    public boolean isZero() {
        return this == ZERO;
    }

    public boolean isOne() {
        return this == ONE;
    }

    public boolean isNotConnected() {
        return this == NOT_CONNECTED;
    }

    public LogicState toTTL() {
        return switch (this) {
            case ONE, NOT_CONNECTED -> LogicState.ONE;
            case ZERO -> LogicState.ZERO;
        };
    }

    public LogicState reverse() {
        return switch (this) {
            case ONE -> ZERO;
            case ZERO -> ONE;
            case NOT_CONNECTED -> NOT_CONNECTED;
        };
    }
}
