package main;

import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import javafx.fxml.FXML;

public class MainWindow {

    @FXML private Tab tabTables;
    @FXML private Tab tabTree;
    @FXML private AnchorPane main_pane;
    @FXML private AnchorPane settings_pane;
    @FXML private AnchorPane buttons_pane;

    @FXML
    public void initialize() {
        System.out.println("Iniciando");
    }

}
