package pl.marcinchwedczuk.bzzz.simulator;

import java.util.Comparator;

public class EventsByTimestamp implements Comparator<Event> {
    @Override
    public int compare(Event o1, Event o2) {
        long tmp = o1.fireAt - o2.fireAt;

        // @formatter:off
        return (tmp > 0) ?  1 :
               (tmp < 0) ? -1 :
               0;
        // @formatter:on
    }
}
