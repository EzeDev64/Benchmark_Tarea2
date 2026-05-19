package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import sequenceStructures.simpleAVL;

import javafx.event.EventHandler;

public class SequenceWindow {

    @FXML
    private ChoiceBox<String> choiceBox_Estructure;
    private String[] dataStructures = {"Array","Linked List","BST Tree","AVL Tree","Splay Tree","Red-Black Tree"};

    @FXML
    private AnchorPane draw_pane;

    @FXML
    private Button btn_play;


    public void initialize(){
        choiceBox_Estructure.getItems().addAll(dataStructures);

        btn_play.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("Button Play");
                //region Temporalmente para probar el arbol 
                //Agregar aquí el comportamiento para iniciar la ejecusión.
                //Esto debería adaptarse a los datos que le pasen con la semilla y eso
                simpleAVL<Integer> treeAVL = new simpleAVL<Integer>();
                Integer[] data = {7,2,8,4,1,3,5};
                for (Integer integer : data) {
                    treeAVL.insert(integer);
                }
                //treeBST.delete(2);
                //endregion

                //Se llama la función luego de ejecutar toda la parte del benchmark
                draw_tree(treeAVL);
            }
        });

    }


    //función principal para dibujar el arbol
    public void draw_tree(simpleAVL<Integer> arbol1){
        //Hay que implementar una manera eficiente para que reconozca los arboles pasados 
        arbol1.recorrerYEjecutar((valor, x, y,action,gap) -> {
                if (action){
                    drawNode(String.valueOf(valor), x, y);
                }else{
                    drawLine(x+10, y+5,x-gap, y+60);
                }
        });
    }

    //Función auxiliar para dibujar el nodo como tal
    private void drawNode(String data,double posX, double posY){
        //Circulo del nodo
        Circle node = new Circle(15.0);
        
        //Colocamos en el centro para el StackPane
        node.setCenterX(50.0);
        node.setCenterY(50.0);
                
        //Color del Nodo
        node.setFill(Color.RED); // Color de relleno

        //Texto del círculo
        Text texto = new Text(data);
        texto.setFill(Color.WHITE);
        texto.setStyle("-fx-font-weight: bold;");

        //Añadir el círculo al Pane
        StackPane contenedorNodo = new StackPane();
        contenedorNodo.getChildren().addAll(node, texto);
        contenedorNodo.setLayoutX(posX); 
        contenedorNodo.setLayoutY(posY);
        draw_pane.getChildren().add(contenedorNodo);
    }

    //Función auxiliar para dibujar las líneas entre los nodo
    private void drawLine(double x1, double y1, double x2, double y2){
        Line linea = new Line(x1, y1, x2, y2);
        linea.setStroke(Color.RED);
        linea.setStrokeWidth(2);
        
        //Enviamos la línea al fondo para que no cruce sobre el número
        draw_pane.getChildren().add(0, linea); 
    }


    @FXML
    void play(ActionEvent event) {

    }

    
    
}
