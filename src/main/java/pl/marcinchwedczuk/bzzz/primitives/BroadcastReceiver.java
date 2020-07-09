package pl.marcinchwedczuk.bzzz.primitives;

interface BroadcastReceiver {
    void onBroadcast(ComponentId source, LogicState sourceNewState);
}
