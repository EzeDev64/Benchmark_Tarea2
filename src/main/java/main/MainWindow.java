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
import java.util.Random;

import arboles.AvlTree;
import arboles.BST;
import arboles.RBT;
import estruc_datos.ArrayList;
import estruc_datos.LinkedList;
import estruc_datos.int_estructura;
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
                //Capturar los árboles:
                ArrayList<int_estructura<Integer>> estruc_list = new ArrayList<int_estructura<Integer>>();

                //region Captura de las estructuras selecionadas
                for (MenuItem item : cbo_estructuras.getItems()) {
                    Node contenido = null;

                    //Recorrido del MenuButton
                    if (item instanceof CustomMenuItem) {
                        contenido = ((CustomMenuItem) item).getContent();
                    } else {
                        contenido = item.getGraphic();
                    }

                    // Verificamos si lo que encontramos es un CheckBox
                    if (contenido instanceof CheckBox) {
                        CheckBox cb = (CheckBox) contenido;
                        System.out.println("Seleccionado: " + cb.getText() + "  " + cb.isSelected());
                        if(cb.isSelected()){
                            switch (cb.getText()){
                                case "ArrayList":
                                    estruc_list.push(new ArrayList<Integer>());
                                    break;
                                case "LinkedList":
                                    estruc_list.push(new LinkedList<Integer>());
                                    break;
                                case "Splay Tree":
                                    break;
                                case "Red-Black Tree":
                                    estruc_list.push(new RBT<Integer>());
                                    break;
                                case "AVL Tree":
                                    estruc_list.push(new AvlTree<Integer>());
                                    break;
                                case "BST Tree":
                                    estruc_list.push(new BST());
                                    break;  
                                default:
                                    break;
                            }
                        }
                    }    
                }
                //endregion

                //Captura resto de componentes
                int W = (int) spi_warmup.getValue();
                int R = (int) spi_repetitions.getValue();
                long seed = 10131L;
                if (!txt_seed.getText().isEmpty()){
                    seed = Integer.parseInt(txt_seed.getText());
                }
                int N = (int)  spi_nDatos.getValue();
                
                //Inserción de datos según semilla y cantidad.
                Random generator = new Random();
                generator.setSeed(seed);
                
                if(estruc_list.getSize() > 0){
                    int_estructura<Integer> obj;
                    
                    for(int i=0;i<estruc_list.getSize();i++){
                        obj = (int_estructura<Integer>) estruc_list.getAt(i);
                        generator.setSeed(seed);
                        for(int p=0;p<N;p++){
                            //System.out.println(generator.nextInt(100));
                            Integer digit = generator.nextInt(100);
                            obj.insertar(digit);
                        }
                    }

                    for(int i=0;i<estruc_list.getSize();i++){
                        obj = (int_estructura<Integer>) estruc_list.getAt(i);
                        obj.obtenerDato();
                    }
                }
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
