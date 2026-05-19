package sequenceStructures;


class AvlNode {
    int element;
    AvlNode left;
    AvlNode right;
    int height;

    public AvlNode(int element) {
        this(element, null, null);
    }

    public AvlNode(int element, AvlNode left, AvlNode right) {
        this.element = element;
        this.left = left;
        this.right = right;
        this.height = 0;
    }
}

public class simpleAVL implements Idrawable{
    private static final int ALLOWED_IMBALANCE = 1;
    private AvlNode root;
    private double initPos;

    public simpleAVL() {
        this.root = null;
    }

    public AvlNode getRoot(){
        return root;
    }

    public void insert(int element) {
        this.root = this.insert(element, this.root);
    }

    
    private int height(AvlNode t) {
        return t == null ? -1 : t.height;
    }

    private AvlNode insert(int x, AvlNode t) {
        if (t == null) {
            return new AvlNode(x);
        }

        if (x < t.element) {
            t.left = insert(x, t.left);
        } else if (x > t.element) {
            t.right = insert(x, t.right);
        }
        
        return balance(t);
    }


    //Funciones para acomodar los nodos conforme se insertan
    private AvlNode balance(AvlNode t) {
        if (t == null)
            return t;

        if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE) {
            if (height(t.left.left) >= height(t.left.right)) {
                t = rotateWithLeftChild(t);
            } else {
                t = doubleWithLeftChild(t);
            }
        } else if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE) {
            if (height(t.right.right) >= height(t.right.left)) {
                t = rotateWithRightChild(t);
            } else {
                t = doubleWithRightChild(t);
            }
        }

        t.height = Math.max(height(t.left), height(t.right)) + 1;
        return t;
    }

    private AvlNode rotateWithLeftChild(AvlNode k2) {
        AvlNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), k2.height) + 1;
        return k1;
    }

    private AvlNode rotateWithRightChild(AvlNode k2) {
        AvlNode k1 = k2.right;
        k2.right = k1.left;
        k1.left = k2;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), k2.height) + 1;
        return k1;
    }

    private AvlNode doubleWithLeftChild(AvlNode k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    private AvlNode doubleWithRightChild(AvlNode k3) {
        k3.right = rotateWithLeftChild(k3.right);
        return rotateWithRightChild(k3); // Corregido: antes llamaba a rotateWithLeftChild erróneamente
    }


    public void set_initPos(double val){
        this.initPos = val;
    }

    //region Funciones implementadas para dibujar el nodo

    //Método que va a llamar la ventana main
    @Override
    public void recorrerEjecutar(Dibujante callback) {
        //Ejecuta recurisivamente esta función la cuál recorra el arbol nodo por nodo
        ayudanteRecursivo(root, callback,200,this.initPos,100);
    }

    public interface Dibujante<T> {
        //Donde true es nodo y false es línea
        //Al ser interfaz dentro de la aplicación cambia la función dibujar por draw_tree
        void dibujar(T valor, double x, double y,boolean action,double gap);
    }

    private void ayudanteRecursivo(AvlNode actual, Dibujante<Integer> callback,double x, double y, double gap) {
        /*Recursivamente recorre nodo por nodo y hace lo siguiente
        -Si es null (No hay nodo) termina
        -Hace el cálculo de distancias
        -Recorre el nodo izq y der, además de dibujar las líneas
        -Dibuja el nodo
        */
        if (actual == null) return;

        //Cálculo de distancias entre nodos
        double proximoGap = gap / 2; 
        double proximaY = y + 50;

        //Recorre el nodo izquierdo.
        if (actual.left != null) {
            callback.dibujar((Integer) actual.element, x, y,false,gap);
            ayudanteRecursivo(actual.left, callback, x - gap, proximaY, proximoGap);
        }
        
        //Recorre el nodo derecho.
        if (actual.right != null) {
            callback.dibujar((Integer) actual.element, x, y,false,gap*-1);
            ayudanteRecursivo(actual.right, callback, x + gap, proximaY, proximoGap);
        }

        //Dibujar el nodo actual.
        callback.dibujar((Integer) actual.element, x, y,true,gap);
    }
}
