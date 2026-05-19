package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import sequenceStructures.Idrawable;
import sequenceStructures.simpleAVL;
import sequenceStructures.simpleSplay;
import sequenceStructures.simpleBST;
import sequenceStructures.simpleRBT;
import sequenceStructures.sequenceLinkedList;

public class SequenceWindow {

    // FXML Section -----------------------------------------------------------------------------------------------------
    @FXML
    private ChoiceBox<String> choiceBox_Estructure;
    private String[] dataStructures = {"BST Tree","AVL Tree","Splay Tree","Red-Black Tree"};

    @FXML
    private AnchorPane draw_pane;

    @FXML
    private Button btn_play;

    @FXML
    private TextField txtField_values;

    @FXML
    private Button btn_Anterior;

    @FXML
    private Button btn_Siguiente;

    // -------------------------------------------------------------------------------------------------------------------

    // Class Attributes --------------------------------------------------------------------------------------------------
    private int[] dataValues = null; 
    private sequenceLinkedList sequencer = new sequenceLinkedList();
    private sequenceLinkedList.SnapshotNode currentSnapshotNode;
    // -------------------------------------------------------------------------------------------------------------------





    public void initialize(){
        choiceBox_Estructure.getItems().addAll(dataStructures);
        //choiceBox_Estructure.setOnAction(this::structureRouter);
        btn_play.setOnAction(this::play);
        btn_Anterior.setOnAction(this::goPrev);
        btn_Siguiente.setOnAction(this::goNext);

    }

    private void clearCanvas(){
        draw_pane.getChildren().clear();
    }


    //función principal para dibujar el arbol
    public void draw_tree(Idrawable arbol1){
        this.clearCanvas();
        arbol1.recorrerEjecutar((valor, x, y,action,gap) -> {
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
        this.clearCanvas();
        try {
            String rawText = txtField_values.getText();
            String[] stringValues = rawText.split(",");
            int[] values = new int[stringValues.length];

            for(int i = 0; i < stringValues.length; i++){
                String currentString = stringValues[i];
                currentString = currentString.trim();
                int parsedNumber = Integer.parseInt(currentString);
                values[i] = parsedNumber;
            }
            dataValues = values;
            structureRouter();
        } catch (Exception e) {
            System.out.println("Data parsing error");
        }

    }

    @FXML
    void goPrev(ActionEvent event) {
        if (currentSnapshotNode != null && currentSnapshotNode.prev != null) {
            currentSnapshotNode = currentSnapshotNode.prev;
            draw_tree(currentSnapshotNode.treeSnapshot);
        }

    }

    @FXML
    void goNext(ActionEvent event) {
        if (currentSnapshotNode != null && currentSnapshotNode.next != null) {
            currentSnapshotNode = currentSnapshotNode.next;
            draw_tree(currentSnapshotNode.treeSnapshot);
        }

    }


    //"Array","Linked List","BST Tree","AVL Tree","Splay Tree","Red-Black Tree"
    private void structureRouter(){

        String structureType = choiceBox_Estructure.getValue();
        switch(structureType) {

            case "BST Tree":
                System.out.println("Chose BST Tree");
                sequencer.clear();
                for (int i = 0; i < dataValues.length; i++) {
                    simpleBST snapshotTree = new simpleBST();
                    for (int j = 0; j <= i; j++) {
                        snapshotTree.insert(dataValues[j]);
                    }
                    sequencer.insert(snapshotTree);
                }
                currentSnapshotNode = sequencer.getHead();
                draw_tree(currentSnapshotNode.treeSnapshot);
                break;

            case "AVL Tree":
                System.out.println("Chose AVL Tree");
                sequencer.clear();
                for (int i = 0; i < dataValues.length; i++) {
                    simpleAVL snapshotTree = new simpleAVL();
                    for (int j = 0; j <= i; j++) {
                        snapshotTree.insert(dataValues[j]);
                    }
                    sequencer.insert(snapshotTree);
                }
                currentSnapshotNode = sequencer.getHead();
                draw_tree(currentSnapshotNode.treeSnapshot);
                break;

            case "Splay Tree":
                System.out.println("Chose Splay Tree");
                sequencer.clear();
                for (int i = 0; i < dataValues.length; i++) {
                    simpleSplay snapshotTree = new simpleSplay();
                    for (int j = 0; j <= i; j++) {
                        snapshotTree.insert(dataValues[j]);
                    }
                    sequencer.insert(snapshotTree);
                }
                currentSnapshotNode = sequencer.getHead();
                draw_tree(currentSnapshotNode.treeSnapshot);
                break;

            case "Red-Black Tree":
                System.out.println("Chose Red-Black Tree");
                sequencer.clear();
                for (int i = 0; i < dataValues.length; i++) {
                    simpleRBT snapshotTree = new simpleRBT();
                    for (int j = 0; j <= i; j++) {
                        snapshotTree.insert(dataValues[j]);
                    }
                    sequencer.insert(snapshotTree);
                }
                currentSnapshotNode = sequencer.getHead();
                draw_tree(currentSnapshotNode.treeSnapshot);;
                break;
            }
    }




    
    
}
