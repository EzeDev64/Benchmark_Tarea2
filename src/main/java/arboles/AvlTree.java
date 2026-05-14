package arboles;

class AvlNode <T extends Comparable<? super T>>  {
    T element;
    AvlNode<T> left;
    AvlNode<T> right;
    int height;

    public AvlNode(T element) {
        this(element, null, null);
    }

    public AvlNode(T element, AvlNode<T> left, AvlNode<T> right) {
        this.element = element;
        this.left = left;
        this.right = right;
        this.height = 0;
    }
}

public class AvlTree <T extends Comparable<? super T>> {
    private static final int ALLOWED_IMBALANCE = 1;
    private AvlNode<T> root;

    public AvlTree() {
        this.root = null;
    }

    public AvlNode<T> getRoot(){
        return root;
    }

    public void insert(T element) {
        this.root = this.insert(element, this.root);
    }

    public void delete(T element){
        delete(element, root);
    }

    public AvlNode<T> find(T element){
        return find(element,this.root);
    }
    
    private int height(AvlNode<T> t) {
        return t == null ? -1 : t.height;
    }

    private AvlNode<T> insert(T x, AvlNode<T> t) {
        if (t == null) {
            return new AvlNode<T>(x);
        }

        // Usamos compareTo en lugar de < o >
        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) {
            t.left = insert(x, t.left);
        } else if (compareResult > 0) {
            t.right = insert(x, t.right);
        }
        
        return balance(t);
    }

    private AvlNode<T> find(T element,AvlNode<T> root){
        if(root == null){
            return  null;
        }

        int compareResult = element.compareTo(root.element);

        if (compareResult == 0){
            return root;
        }
        //Compruebo si el valor que pide es menor o mayor y me voy por los lados
        if(compareResult < 0){
            System.out.println(root.element + " Me fui para la izquierda");
            return find(element,root.left);
        }else{
            System.out.println(root.element + " Me fui para la derecha");
            return find(element,root.right);
        }

    }
    
    public AvlNode<T> findMin() {
        if (this.root == null) {
            return null;
        } else {
            return this.findMin(this.root);
        }
    }

    public AvlNode<T> findMax() {
        if (this.root == null) {
        return null;
        } else {
        return this.findMax(this.root);
        }
    }

    private AvlNode<T> findMin(AvlNode<T> node) {
        if (node == null)
            return null;
        else if (node.left == null)
            return node;
        else
            return findMin(node.left);
    }

    private AvlNode<T> findMax(AvlNode<T> node) {
        if (node!= null)
            while (node.right != null) {
                node = node.right;
            }
        return node;
    }

    private AvlNode<T> delete(T element, AvlNode<T> node){
        if (node == null){
            return node;
        }

        int compareResult = element.compareTo(node.element);
        if (compareResult < 0){
            node.left= delete(element, node.left);
        }else if (compareResult > 0){
            node.right = delete(element, node.right);
        }else if (node.left != null && node.right != null){
            node.element = findMin(node.right).element;
            node.right = delete(node.element, node.right);
        } else {
            node = node.left != null ? node.left : node.right;
        }

        return balance(node);
    }
    //Funciones para acomodar los nodos conforme se insertan
    private AvlNode<T> balance(AvlNode<T> t) {
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

    private AvlNode<T> rotateWithLeftChild(AvlNode<T> k2) {
        AvlNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), k2.height) + 1;
        return k1;
    }

    private AvlNode<T> rotateWithRightChild(AvlNode<T> k2) {
        AvlNode<T> k1 = k2.right;
        k2.right = k1.left;
        k1.left = k2;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), k2.height) + 1;
        return k1;
    }

    private AvlNode<T> doubleWithLeftChild(AvlNode<T> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    private AvlNode<T> doubleWithRightChild(AvlNode<T> k3) {
        k3.right = rotateWithLeftChild(k3.right);
        return rotateWithRightChild(k3); // Corregido: antes llamaba a rotateWithLeftChild erróneamente
    }

    public void printTree(AvlNode<T> root){
        if (root==null){
            return;
        }

        System.out.println(root.element);
        if(root.left != null){
            System.out.print(root.element+ " Left "); 
            printTree(root.left);
        }

        if(root.right != null){
            System.out.print(root.element+ " Right ");
            printTree(root.right);
        }
    }
}
