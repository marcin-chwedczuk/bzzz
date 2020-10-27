package pl.marcinchwedczuk.bzzz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.marcinchwedczuk.bzzz.jfxutil.JfxDialogs;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            new JfxDialogs().exception(e);
        });

        Parent root = FXMLLoader.load(
                getClass().getResource("GuiMain.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("bzzz Simulator");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
