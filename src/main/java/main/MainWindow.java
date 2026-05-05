package main;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

public class MainWindow {
    
    //Tab donde se encuenta la tabla para los datos
    @FXML private Tab tabTables;
    @FXML private TableView<?> Table; //Tabla donde se almacenan los datos

    //Tab para la visualización de árboles
    @FXML private Tab tabTree;
    @FXML private AnchorPane draw_pane; //Donde se dibujarían los árboles 

    //Pane para los setting aquí están todos los parámetros para modificar la ejecución.
    @FXML private AnchorPane settings_pane;
    @FXML private MenuButton cbo_estructuras;
    @FXML private Spinner<Integer> spi_warmup; //Spinner que define la cantidad de repeticiones "Warm-up" que debe hacer el sistema.
    @FXML private Spinner<Integer> spi_repetitions; //Spinner que define la cantidad de repeticiones de ejecución de las comparaciones.
    @FXML private TextField txt_seed; //Campo de texto donde el usuario coloca la semilla.
    @FXML private Spinner<Integer> spi_nDatos; //Spinner que define la cantidad de datos que hay que meter dentro de las estructuras.
    @FXML private ComboBox<String> cbo_firstData; //ComboBox que sirve para seleccionar el primer arbol a visualizar.
    @FXML private ComboBox<String> cbo_secondData; //ComboBox que sirve para seleccionar el segundo arbol a visualizar.

    //Pane para los botones de inicio y valores predeterminados (Talvez modo manual y automático)
    @FXML private AnchorPane buttons_pane;
    @FXML private Button btn_play; //Botón de inicio para ejecutar el programa
    @FXML private Button btn_default; //Botón para establecer la configuración a una predeterminada

    @FXML
    public void initialize() {
        System.out.println("Iniciando");
        //region Definir el rango de los spinners.
        SpinnerValueFactory<Integer> valueFW = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0, 1);
        SpinnerValueFactory<Integer> valueFR = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1);
        SpinnerValueFactory<Integer> valueFN = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5000, 0, 1);
        
        spi_warmup.setValueFactory(valueFW);
        spi_repetitions.setValueFactory(valueFR);
        spi_nDatos.setValueFactory(valueFN);
        //endregion 

        //Definir los combobox con las estructuras de datos:
        cbo_firstData.getItems().addAll("BTS Tree", "AVL Tree", "Splay Tree","Red-Black Tree");
        cbo_firstData.setValue("BTS Tree");
        cbo_secondData.getItems().addAll("BTS Tree", "AVL Tree", "Splay Tree","Red-Black Tree");
        cbo_secondData.setValue("AVL Tree");

        btn_play.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("Button Play");
                //Agregar aquí el comportamiento para iniciar la ejecusión.
            }
        });

        btn_default.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("Button Valores predeterminados");
                //Agregar aquí modificar los valores de de @settings_pane para establecer los predeterminados
            }
        });

        cbo_firstData.setOnAction(event -> {
            //Agregar aquí el comportamiento cuando se selecciona alguna de las opciones
            System.out.println("Selected A: " + cbo_firstData.getValue());
        });

        cbo_secondData.setOnAction(event -> {
            //Agregar aquí el comportamiento cuando se selecciona alguna de las opciones
            System.out.println("Selected B: " + cbo_secondData.getValue());
        });
    }

    /*Manera de capturar los controles dentro de @cbo_estructuras:
                for (MenuItem item : cbo_estructuras.getItems()) {
        
                    // En JavaFX, el CheckBox suele estar en la propiedad 'graphic' 
                    // o dentro de un CustomMenuItem en 'content'.
                    
                    Node contenido = null;

                    if (item instanceof CustomMenuItem) {
                        contenido = ((CustomMenuItem) item).getContent();
                    } else {
                        contenido = item.getGraphic();
                    }

                    // Verificamos si lo que encontramos es un CheckBox
                    if (contenido instanceof CheckBox) {
                        CheckBox cb = (CheckBox) contenido;
                        System.out.println("Seleccionado: " + cb.getText() + "  " + cb.isSelected());
                    }
                }
    */

}
