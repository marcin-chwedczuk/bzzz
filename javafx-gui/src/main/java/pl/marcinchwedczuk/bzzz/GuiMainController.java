package pl.marcinchwedczuk.bzzz;

import javafx.application.Platform;
import javafx.fxml.FXML;
import pl.marcinchwedczuk.bzzz.simulator.EventLoopSimulator;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;
import pl.marcinchwedczuk.bzzz.util.JfxDialogs;

public class GuiMainController {
    private Simulator sim = new EventLoopSimulator();

    @FXML
    void onQuit() {
        sim.run("foo");
        new JfxDialogs().info("exiting...");
        Platform.exit();
    }
}
