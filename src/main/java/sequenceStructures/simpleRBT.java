package sequenceStructures;

import sequenceStructures.simpleAVL.Dibujante;

class RbtNode {
    int element;
    boolean color;
    RbtNode left;
    RbtNode right;
    RbtNode parent;

    public RbtNode(int element, boolean color) {
        this.element = element;
        this.color = color;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}

public class simpleRBT implements Idrawable {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private RbtNode root;
    private double initPos;

    public simpleRBT() {
        this.root = null;
    }

    public RbtNode getRoot() {
        return root;
    }

    // --- Insertion and Red-Black Logic ---

    public void insert(int element) {
        RbtNode newNode = new RbtNode(element, RED);

        if (this.root == null) {
            this.root = newNode;
            this.root.color = BLACK;
            return;
        }

        RbtNode current = this.root;
        RbtNode parent = null;

        while (current != null) {
            parent = current;
            if (element < current.element) {
                current = current.left;
            } else if (element > current.element) {
                current = current.right;
            } else {
                // Duplicate value, ignore
                return;
            }
        }

        newNode.parent = parent;
        if (element < parent.element) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        insertFixup(newNode);
    }

    private void insertFixup(RbtNode node) {
        while (node.parent != null && node.parent.color == RED) {
            if (node.parent == node.parent.parent.left) {
                RbtNode uncle = node.parent.parent.right;

                if (colorOf(uncle) == RED) {
                    // Case 1: Uncle is red
                    setColor(node.parent, BLACK);
                    setColor(uncle, BLACK);
                    setColor(node.parent.parent, RED);
                    node = node.parent.parent;
                } else {
                    // Uncle is black
                    if (node == node.parent.right) {
                        // Case 2: Triangle (Left-Right)
                        node = node.parent;
                        rotateLeft(node);
                    }
                    // Case 3: Line (Left-Left)
                    setColor(node.parent, BLACK);
                    setColor(node.parent.parent, RED);
                    rotateRight(node.parent.parent);
                }
            } else {
                RbtNode uncle = node.parent.parent.left;

                if (colorOf(uncle) == RED) {
                    // Case 1: Uncle is red
                    setColor(node.parent, BLACK);
                    setColor(uncle, BLACK);
                    setColor(node.parent.parent, RED);
                    node = node.parent.parent;
                } else {
                    // Uncle is black
                    if (node == node.parent.left) {
                        // Case 2: Triangle (Right-Left)
                        node = node.parent;
                        rotateRight(node);
                    }
                    // Case 3: Line (Right-Right)
                    setColor(node.parent, BLACK);
                    setColor(node.parent.parent, RED);
                    rotateLeft(node.parent.parent);
                }
            }
        }
        this.root.color = BLACK;
    }

    private void rotateLeft(RbtNode node) {
        RbtNode rightChild = node.right;
        if (rightChild == null) return;

        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }

        rightChild.parent = node.parent;
        if (node.parent == null) {
            this.root = rightChild;
        } else if (node == node.parent.left) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }

        rightChild.left = node;
        node.parent = rightChild;
    }

    private void rotateRight(RbtNode node) {
        RbtNode leftChild = node.left;
        if (leftChild == null) return;

        node.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }

        leftChild.parent = node.parent;
        if (node.parent == null) {
            this.root = leftChild;
        } else if (node == node.parent.right) {
            node.parent.right = leftChild;
        } else {
            node.parent.left = leftChild;
        }

        leftChild.right = node;
        node.parent = leftChild;
    }

    private boolean colorOf(RbtNode node) {
        return node == null ? BLACK : node.color;
    }

    private void setColor(RbtNode node, boolean color) {
        if (node != null) {
            node.color = color;
        }
    }

    // --- Idrawable Implementation ---

    public void set_initPos(double val) {
        this.initPos = val;
    }

    @Override
    public void recorrerEjecutar(Dibujante callback) {
        ayudanteRecursivo(root, callback, 200, this.initPos != 0 ? this.initPos : 10, 100);
    }

    private void ayudanteRecursivo(RbtNode actual, Dibujante<Integer> callback, double x, double y, double gap) {
        if (actual == null) return;

        double proximoGap = gap / 2;
        double proximaY = y + 50;

        if (actual.left != null) {
            callback.dibujar(actual.element, x, y, false, gap);
            ayudanteRecursivo(actual.left, callback, x - gap, proximaY, proximoGap);
        }

        if (actual.right != null) {
            callback.dibujar(actual.element, x, y, false, gap * -1);
            ayudanteRecursivo(actual.right, callback, x + gap, proximaY, proximoGap);
        }

        callback.dibujar(actual.element, x, y, true, gap);
    }
}
