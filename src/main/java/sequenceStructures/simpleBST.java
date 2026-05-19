package sequenceStructures;

import sequenceStructures.simpleAVL.Dibujante;

class BstNode {
    int element;
    BstNode left;
    BstNode right;

    public BstNode(int element) {
        this.element = element;
        this.left = null;
        this.right = null;
    }
}

public class simpleBST implements Idrawable {
    
    private BstNode root;
    private double initPos;

    public simpleBST() {
        this.root = null;
    }

    public BstNode getRoot() {
        return root;
    }

    // --- Insertion ---
    
    public void insert(int element) {
        this.root = insertRec(this.root, element);
    }

    private BstNode insertRec(BstNode current, int element) {
        if (current == null) {
            return new BstNode(element);
        }

        if (element < current.element) {
            current.left = insertRec(current.left, element);
        } else if (element > current.element) {
            current.right = insertRec(current.right, element);
        }
        // If element == current.element, we ignore it (no duplicates)
        
        return current;
    }

    // --- Idrawable Implementation ---

    public void set_initPos(double val) {
        this.initPos = val;
    }

    @Override
    public void recorrerEjecutar(Dibujante callback) {
        ayudanteRecursivo(root, callback, 200, this.initPos != 0 ? this.initPos : 10, 100);
    }

    private void ayudanteRecursivo(BstNode actual, Dibujante<Integer> callback, double x, double y, double gap) {
        if (actual == null) return;

        double proximoGap = gap / 2;
        double proximaY = y + 50;

        // Traverse left child
        if (actual.left != null) {
            callback.dibujar(actual.element, x, y, false, gap);
            ayudanteRecursivo(actual.left, callback, x - gap, proximaY, proximoGap);
        }

        // Traverse right child
        if (actual.right != null) {
            callback.dibujar(actual.element, x, y, false, gap * -1);
            ayudanteRecursivo(actual.right, callback, x + gap, proximaY, proximoGap);
        }

        // Draw current node
        callback.dibujar(actual.element, x, y, true, gap);
    }
}
