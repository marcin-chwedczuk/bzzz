module bzzz.javafx_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires bzzz.simulator;

    // Allow `javafx.fxml` call methods from `pl.marcinchwedczuk.bzzz`.
    opens pl.marcinchwedczuk.bzzz to javafx.fxml;

    exports pl.marcinchwedczuk.bzzz;
}