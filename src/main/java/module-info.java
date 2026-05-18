module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.opencsv;

    opens main to javafx.fxml;
    exports main;
}
