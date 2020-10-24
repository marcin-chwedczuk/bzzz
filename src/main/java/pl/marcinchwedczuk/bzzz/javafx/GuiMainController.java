package pl.marcinchwedczuk.bzzz.javafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import pl.marcinchwedczuk.bzzz.javafx.util.JfxDialogs;

public class GuiMainController {
    @FXML
    void onQuit() {
        new JfxDialogs().info("exiting...");
        Platform.exit();
    }
}
