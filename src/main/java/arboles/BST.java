package arboles;

// Clase que representa un nodo del BST
class BSTNode 
{
    int key;           // valor almacenado en el nodo
    BSTNode left;      // referencia al hijo izquierdo
    BSTNode right;     // referencia al hijo derecho

    // Constructor: inicializa el nodo con una clave
    public BSTNode(int key) {
        this.key = key;
        this.left = null;
        this.right = null;
    }
}

// Clase principal del BST
public class BST {
    private BSTNode root; // raíz del árbol

    // Constructor: inicializa el árbol vacío
    public BST() {
        root = null;
    }

    /**
     * Inserta una nueva clave en el árbol.
     * Si el árbol está vacío, la clave se convierte en la raíz.
     * Si no, se recorre recursivamente hasta encontrar la posición correcta.
     */
    public void insert(int key) // método público para insertar una clave en el árbol
    {
        root = insertRec(root, key);  // llama al método recursivo para insertar la clave
    }

    /**
     * Método recursivo para insertar una clave en el árbol.
     * - Si el nodo actual es null, se crea un nuevo nodo.
     * - Si la clave es menor, se inserta en el subárbol izquierdo.
     * - Si la clave es mayor, se inserta en el subárbol derecho.
     * - Si la clave ya existe, no se inserta (evita duplicados).
     */
    private BSTNode insertRec(BSTNode node, int key) {
        if (node == null) {
            return new BSTNode(key); // caso base: nodo vacío
        }
        if (key < node.key) {
            node.left = insertRec(node.left, key); // ir a la izquierda
        } else if (key > node.key) {
            node.right = insertRec(node.right, key); // ir a la derecha
        }
        return node; // devolver el nodo actualizado
    }

    /**
     * Recorre el árbol en orden (in-order traversal).
     * Esto imprime las claves en orden ascendente.
     */
    public void inorder() {
        inorderRec(root);
    }

    private void inorderRec(BSTNode node) {
        if (node != null) {
            inorderRec(node.left);          // visitar subárbol izquierdo
            System.out.print(node.key + " "); // visitar nodo actual
            inorderRec(node.right);         // visitar subárbol derecho
        }
    }
}
