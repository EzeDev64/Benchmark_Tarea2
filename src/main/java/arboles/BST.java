package arboles;

import estruc_datos.int_estructura;

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
public class BST<T> implements int_estructura<T>{
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
    public void insert(int key) 
    {
        root = insertRec(root, key);
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
     * Busca una clave en el árbol.
     * @param key Clave a buscar
     * @return true si la clave existe, false si no
     */
    public boolean search(int key) // método público para iniciar la búsqueda
    {
        return searchRec(root, key);    // llama al método recursivo con la raíz del árbol
    }

    private boolean searchRec(BSTNode node, int key) {  // método recursivo para buscar una clave
        if (node == null) {
            return false; // caso base: nodo vacío
        }
        if (key == node.key) {
            return true; // clave encontrada
        }
        if (key < node.key) {       // si la clave es menor, buscar en el subárbol izquierdo
            return searchRec(node.left, key); // ir a la izquierda
        } else {
            return searchRec(node.right, key); // ir a la derecha
        }
    }

        /**
         * Recorre el árbol en orden (in-order traversal).
         * Esto imprime las claves en orden ascendente.
         */
        public void inorder() {
            inorderRec(this.root);
        }

        private void inorderRec(BSTNode node) {
            if (node != null) {
                inorderRec(node.left);          // visitar subárbol izquierdo
                System.out.print(node.key + " "); // visitar nodo actual
                inorderRec(node.right);         // visitar subárbol derecho
            }
        }

        public void delete(int key) {
        root = deleteRec(root, key);
        }

    /**
     * Método recursivo para eliminar una clave del BST.
     * Casos:
     * 1. Nodo hoja → se elimina directamente.
     * 2. Nodo con un hijo → se reemplaza por su hijo.
     * 3. Nodo con dos hijos → se reemplaza por el sucesor mínimo del subárbol derecho.
     */
    private BSTNode deleteRec(BSTNode node, int key) {
        if (node == null) {
            return null; // caso base: nodo vacío
        }

        if (key < node.key) {
            node.left = deleteRec(node.left, key); // buscar en la izquierda
        } else if (key > node.key) {
            node.right = deleteRec(node.right, key); // buscar en la derecha
        } else {
            // Caso 1: nodo hoja
            if (node.left == null && node.right == null) {
                return null;
            }
            // Caso 2: un hijo
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            // Caso 3: dos hijos → sucesor mínimo del subárbol derecho
            node.key = minValue(node.right);
            node.right = deleteRec(node.right, node.key);
        }
        return node;
    }

    /**
     * Encuentra el valor mínimo en un subárbol.
     * Se usa para reemplazar un nodo con dos hijos.
     */
    private int minValue(BSTNode node) {
        int min = node.key;
        while (node.left != null) {
            min = node.left.key;
            node = node.left;
        }
        return min;
    }

    @Override
    public void obtenerDato() {
        inorder();
    }

    @Override
    public void insertar(T value) {
        this.insert((int) value);
    }

    @Override
    public Object buscar(T value) {
        return this.search((int)value);
    }

    @Override
    public void eliminar(T value) {
        this.delete((int) value);
    }

}
