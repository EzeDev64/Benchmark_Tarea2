package sequenceStructures;

import sequenceStructures.simpleAVL.Dibujante;

class SplayNode {
    int key;
    SplayNode left;
    SplayNode right;
    SplayNode parent;

    public SplayNode(int key) {
        this.key = key;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}

public class simpleSplay implements Idrawable {
    private SplayNode root;
    private double initPos;

    public simpleSplay() {
        this.root = null;
    }

    public SplayNode getRoot() {
        return root;
    }

    // --- Splay Logic ---

    private void leftRotate(SplayNode x) {
        SplayNode y = x.right;
        if (y != null) {
            x.right = y.left;
            if (y.left != null) {
                y.left.parent = x;
            }
            y.parent = x.parent;
        }

        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        if (y != null) {
            y.left = x;
        }
        x.parent = y;
    }

    private void rightRotate(SplayNode x) {
        SplayNode y = x.left;
        if (y != null) {
            x.left = y.right;
            if (y.right != null) {
                y.right.parent = x;
            }
            y.parent = x.parent;
        }

        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }

        if (y != null) {
            y.right = x;
        }
        x.parent = y;
    }

    private void splay(SplayNode x) {
        while (x.parent != null) {
            if (x.parent.parent == null) {
                // Zig step
                if (x == x.parent.left) {
                    rightRotate(x.parent);
                } else {
                    leftRotate(x.parent);
                }
            } else if (x == x.parent.left && x.parent == x.parent.parent.left) {
                // Zig-zig step
                rightRotate(x.parent.parent);
                rightRotate(x.parent);
            } else if (x == x.parent.right && x.parent == x.parent.parent.right) {
                // Zag-zag step
                leftRotate(x.parent.parent);
                leftRotate(x.parent);
            } else if (x == x.parent.right && x.parent == x.parent.parent.left) {
                // Zig-zag step
                leftRotate(x.parent);
                rightRotate(x.parent);
            } else {
                // Zag-zig step
                rightRotate(x.parent);
                leftRotate(x.parent);
            }
        }
    }

    // --- Public API ---

    public void insert(int key) {
        SplayNode node = new SplayNode(key);
        SplayNode y = null;
        SplayNode x = this.root;

        while (x != null) {
            y = x;
            if (node.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.key < y.key) {
            y.left = node;
        } else {
            y.right = node;
        }

        splay(node);
    }

    // --- Idrawable Implementation ---

    public void set_initPos(double val) {
        this.initPos = val;
    }

    @Override
    public void recorrerEjecutar(Dibujante callback) {
        ayudanteRecursivo(root, callback, 200, this.initPos, 100);
    }

    private void ayudanteRecursivo(SplayNode actual, Dibujante<Integer> callback, double x, double y, double gap) {
        if (actual == null) return;

        double proximoGap = gap / 2;
        double proximaY = y + 50;

        if (actual.left != null) {
            callback.dibujar(actual.key, x, y, false, gap);
            ayudanteRecursivo(actual.left, callback, x - gap, proximaY, proximoGap);
        }

        if (actual.right != null) {
            callback.dibujar(actual.key, x, y, false, gap * -1);
            ayudanteRecursivo(actual.right, callback, x + gap, proximaY, proximoGap);
        }

        callback.dibujar(actual.key, x, y, true, gap);
    }
}
