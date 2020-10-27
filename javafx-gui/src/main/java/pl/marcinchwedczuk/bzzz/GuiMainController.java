package pl.marcinchwedczuk.bzzz;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.marcinchwedczuk.bzzz.jfxutil.JfxDialogs;
import pl.marcinchwedczuk.bzzz.simulator.EventLoopSimulator;
import pl.marcinchwedczuk.bzzz.simulator.Simulator;

import java.net.URL;
import java.util.ResourceBundle;

public class GuiMainController implements Initializable {
    private Simulator sim = new EventLoopSimulator();

    @FXML
    private VBox root;

    @FXML
    void onQuit() {
        sim.run("foo");
        new JfxDialogs().info("exiting...");

        ((Stage)root.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var mcDigit = new mcSevenSegmentDigit();
        root.getChildren().add(mcDigit);
    }
}
