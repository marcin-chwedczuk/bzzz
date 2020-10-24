package pl.marcinchwedczuk.bzzz;

import javafx.application.Platform;
import javafx.fxml.FXML;
import pl.marcinchwedczuk.bzzz.util.JfxDialogs;

public class GuiMainController {
    @FXML
    void onQuit() {
        new JfxDialogs().info("exiting...");
        Platform.exit();
    }
}
