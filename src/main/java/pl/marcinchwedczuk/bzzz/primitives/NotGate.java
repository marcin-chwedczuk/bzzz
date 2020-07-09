package pl.marcinchwedczuk.bzzz.primitives;

// Assumes TTL Logic not connected pin has high state
public class NotGate {
    private final ComponentId componentId = ComponentId.of("not");
    // private final Pin input = new Pin(componentId.extend("input"));
    // private final Pin output = new Pin(componentId.extend("output"));

    public void onPowerOn() {

    }
}
