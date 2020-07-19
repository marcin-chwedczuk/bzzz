package pl.marcinchwedczuk.bzzz.simulator;

import java.util.Comparator;

public class EventsByFireAt implements Comparator<Event> {
    @Override
    public int compare(Event o1, Event o2) {
        return o1.fireAt.compareTo(o2.fireAt);
    }
}
