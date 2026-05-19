package main;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.util.Random;
import java.time.Duration;
import java.time.Instant;

import arboles.AvlTree;
import arboles.BST;
import arboles.RBT;
import estruc_datos.ArrayList;
import estruc_datos.LinkedList;
import estruc_datos.int_estructura;


public class MainWindow{
    // Valores predeterminados (presets) usados por el botón "Valores predeterminados"
    // Definirlos como constantes facilita su uso y modificación.
    private static final int DEFAULT_WARMUP = 2;
    private static final int DEFAULT_REPETITIONS = 3;
    private static final int DEFAULT_N_DATOS = 1000;
    private static final String DEFAULT_SEED = "10131";
    private static final String DEFAULT_FIRST_TREE = "BTS Tree";
    private static final String DEFAULT_SECOND_TREE = "AVL Tree";
    
    //Tab donde se encuenta la tabla para los datos
    @FXML private Tab tabTables;
    @FXML private AnchorPane table_pane;
    //Creación de tabla para guardar datos
    private TableView<AnaEstruc> table;
    private ObservableList<AnaEstruc> listaDatos;

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
    @FXML private Button btn_toCSV;
    @FXML private Button btn_default; //Botón para establecer la configuración a una predeterminada

    @FXML
    public void initialize() {
        // Inicialización del controlador JavaFX.
        // Aquí se configura el rango de los spinners, se pueblan combobox
        // y se aplica el preset por defecto al arrancar la vista.
        System.out.println("Iniciando");

        //region Definir el rango de los spinners.
        SpinnerValueFactory<Integer> valueFW = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0, 1);
        SpinnerValueFactory<Integer> valueFR = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1);
        SpinnerValueFactory<Integer> valueFN = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000000, 0, 1);
        
        spi_warmup.setValueFactory(valueFW);
        spi_repetitions.setValueFactory(valueFR);
        spi_nDatos.setValueFactory(valueFN);
        //endregion 

        //Definir los combobox con las estructuras de datos:
        cbo_firstData.getItems().addAll("BTS Tree", "AVL Tree", "Splay Tree","Red-Black Tree");
        cbo_secondData.getItems().addAll("BTS Tree", "AVL Tree", "Splay Tree","Red-Black Tree");

        applyDefaultValues();

        //Creación de tabla y lista de datos
        btn_toCSV.setDisable(true);
        table = new TableView<>();
        listaDatos = FXCollections.observableArrayList();

        //Creación de columnas
        TableColumn<AnaEstruc, String> col0 = new TableColumn<>("Estructura");
        col0.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<AnaEstruc, String> col1 = new TableColumn<>("Inserción");
        col1.setCellValueFactory(new PropertyValueFactory<>("insertionTime"));

        TableColumn<AnaEstruc, String> col2 = new TableColumn<>("Búsqueda");
        col2.setCellValueFactory(new PropertyValueFactory<>("searchTime"));

        TableColumn<AnaEstruc, String> col3 = new TableColumn<>("Eliminación");
        col3.setCellValueFactory(new PropertyValueFactory<>("deletionTime"));

        //Armado Tabla
        table.getColumns().addAll(col0,col1, col2, col3);
        table.setItems(listaDatos);
        AnchorPane.setTopAnchor(table, 0.0);
        AnchorPane.setBottomAnchor(table, 0.0);
        AnchorPane.setLeftAnchor(table, 0.0);
        AnchorPane.setRightAnchor(table, 0.0);
        //Agregamos al AnchorPane
        table_pane.getChildren().add(table);

        cbo_firstData.setOnAction(event -> {
            //Agregar aquí el comportamiento cuando se selecciona alguna de las opciones
            System.out.println("Selected A: " + cbo_firstData.getValue());
        });

        cbo_secondData.setOnAction(event -> {
            //Agregar aquí el comportamiento cuando se selecciona alguna de las opciones
            System.out.println("Selected B: " + cbo_secondData.getValue());
        });
    }

    // Método auxiliar que aplica los valores predeterminados a los controles
    // - Setea los `Spinner` con sus valores por defecto
    // - Escribe la semilla por defecto en `txt_seed`
    // - Selecciona las opciones por defecto en los `ComboBox`
    // - Marca/desmarca los `CheckBox` contenidos en `cbo_estructuras`
    @FXML
    private void applyDefaultValues() {
        // Spinners
        spi_warmup.getValueFactory().setValue(DEFAULT_WARMUP);
        spi_repetitions.getValueFactory().setValue(DEFAULT_REPETITIONS);
        spi_nDatos.getValueFactory().setValue(DEFAULT_N_DATOS);

        // Semilla y comboboxes
        txt_seed.setText(DEFAULT_SEED);
        cbo_firstData.setValue(DEFAULT_FIRST_TREE);
        cbo_secondData.setValue(DEFAULT_SECOND_TREE);

        // Itera los items del MenuButton para localizar CheckBox y marcarlos
        for (MenuItem item : cbo_estructuras.getItems()) {
            Node contenido = null;

            if (item instanceof CustomMenuItem) {
                contenido = ((CustomMenuItem) item).getContent();
            } else {
                contenido = item.getGraphic();
            }

            if (contenido instanceof CheckBox) {
                CheckBox cb = (CheckBox) contenido;
                // Marcamos ArrayList y LinkedList por defecto; ajusta según necesites
                cb.setSelected("ArrayList".equals(cb.getText()) || "LinkedList".equals(cb.getText()));
            }
        }
    }

    @FXML
    private void play(){
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
                    
        //Generación de semilla y lista de N aleatorios.
        Random generator = new Random();
        generator.setSeed(seed);
        int[] listaNrandom = new int[N];  
        listaDatos.clear();

        generator.setSeed(seed);        
        for(int p=0;p<N;p++){
            Integer digit = generator.nextInt(100);
            listaNrandom[p] = digit;
        }
        
        //Warm up
        for(int n=0;n<W;n++){
            analizarEstructuras(estruc_list, listaNrandom, false,n);
        }

        //R
        for(int n=0;n<R;n++){
            analizarEstructuras(estruc_list, listaNrandom, true,n);
        }

        btn_toCSV.setDisable(false);
        
    }

    public void analizarEstructuras(ArrayList<int_estructura<Integer>> estructs, int[] datosLst, boolean actualizar,int rep){
        int_estructura<Integer> obj;
        Instant init,fin;
        Duration duracion;
        String insercion,busqueda,eliminacion;
        AnaEstruc filaNueva;

        for(int l=0;l<estructs.getSize();l++){
            obj = (int_estructura<Integer>) estructs.getAt(l);
            
            //Inserción
            init = Instant.now();
            for(int i=0;i<datosLst.length;i++){
                obj.insertar(datosLst[i]);
            }
            fin = Instant.now();
            duracion = Duration.between(init, fin);
            insercion = String.valueOf(duracion.toMillis()) + " ms";

            //Busqueda
            init = Instant.now();
            for(int i=0;i<datosLst.length;i+=2){
                obj.buscar(datosLst[i]);
            }
            fin = Instant.now();
            duracion = Duration.between(init, fin);
            busqueda = String.valueOf(duracion.toMillis()) + " ms";

            //Eliminación
            init = Instant.now();
            for(int i=0;i<datosLst.length;i++){
                obj.eliminar(datosLst[i]);
            }
            fin = Instant.now();
            duracion = Duration.between(init, fin);
            eliminacion = String.valueOf(duracion.toMillis()) + " ms";
            if(obj.getClass() == RBT.class){
                eliminacion = "N/A";
            }

            //System.out.println("Tiempo en milisegundos: " + duracion.toMillis());
            if (actualizar){
                filaNueva = new AnaEstruc(obj.getName()+" Repetición "+rep, insercion, busqueda, eliminacion);
                listaDatos.add(filaNueva);
            }
        }
    }

    @FXML
    public void exportToCSV() throws Exception{
        System.out.println("Exportando a CSV...");
        String[][]  filas = dataToArray();
        convertToCSV.writeLineByLineExample(filas);
    }

    public String[][] dataToArray(){
        String[][]  filas = new String[listaDatos.size()+1][4];
        String[] title = {"Estructura","Inserción","Búsqueda","Eliminación"};
        filas[0] = title;

        for (int i=1;i<listaDatos.size();i++) {
            AnaEstruc data = listaDatos.get(i);
            // Extraemos los valores de cada objeto y los convertimos a String
            String col1 = String.valueOf(data.getTitle());
            String col2 = String.valueOf(data.getInsertionTime());
            String col3 = String.valueOf(data.getSearchTime());
            String col4 = String.valueOf(data.getDeletionTime());

            String[] fila = {col1,col2,col3,col4};
            filas[i] = fila;
        }

        return filas;
    }


    @FXML
    void openSequenceModeWindow(){

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sequenceWindow.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));  
            stage.show();
        } catch (Exception e) {
            System.out.println("Can't open sequence window");
        }

    }
}
