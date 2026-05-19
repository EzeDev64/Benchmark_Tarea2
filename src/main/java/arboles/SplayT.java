package arboles;

import estruc_datos.int_estructura;

public class SplayT<T extends Comparable<? super T>> implements int_estructura<T> {

    static class SplayNode<T> {
        T key;
        SplayNode<T> left;
        SplayNode<T> right;
        SplayNode<T> parent;

        public SplayNode(T key) {
            this.key = key;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    private SplayNode<T> root;
    private String name = "Splay Tree";

    public SplayT() {
        this.root = null;
    }

    // --- SPLAY OPERATION ---

    private void splay(SplayNode<T> x) {
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

    private void leftRotate(SplayNode<T> x) {
        SplayNode<T> y = x.right;
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

    private void rightRotate(SplayNode<T> x) {
        SplayNode<T> y = x.left;
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

    // --- int_estructura Implementation ---

    @Override
    public void insertar(T key) {
        if (key == null) return;

        SplayNode<T> node = new SplayNode<>(key);
        SplayNode<T> y = null;
        SplayNode<T> x = this.root;

        while (x != null) {
            y = x;
            int compareResult = node.key.compareTo(x.key);
            if (compareResult < 0) {
                x = x.left;
            } else if (compareResult > 0) {
                x = x.right;
            } else {
                // Duplicate: Splay the existing node and return
                splay(x);
                return;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else {
            int compareResult = node.key.compareTo(y.key);
            if (compareResult < 0) {
                y.left = node;
            } else {
                y.right = node;
            }
        }

        splay(node);
    }

    @Override
    public Object buscar(T key) {
        if (key == null) return false;
        SplayNode<T> node = searchTreeHelper(this.root, key);
        if (node != null) {
            splay(node);
            return true;
        }
        return false;
    }

    private SplayNode<T> searchTreeHelper(SplayNode<T> node, T key) {
        if (node == null) return null;
        
        int compareResult = key.compareTo(node.key);
        if (compareResult == 0) {
            return node;
        }

        if (compareResult < 0) {
            return searchTreeHelper(node.left, key);
        }
        return searchTreeHelper(node.right, key);
    }

    @Override
    public void eliminar(T key) {
        if (key == null || this.root == null) return;

        SplayNode<T> x = searchTreeHelper(this.root, key);
        if (x == null) return;

        splay(x);

        SplayNode<T> leftSubtree = x.left;
        if (leftSubtree != null) {
            leftSubtree.parent = null;
        }

        SplayNode<T> rightSubtree = x.right;
        if (rightSubtree != null) {
            rightSubtree.parent = null;
        }

        if (leftSubtree != null) {
            // Find max in left subtree to become the new root
            SplayNode<T> maxLeft = leftSubtree;
            while (maxLeft.right != null) {
                maxLeft = maxLeft.right;
            }
            splayMaxInSubtree(maxLeft); // Custom splay that only goes up to the subtree root
            this.root = maxLeft;
            this.root.right = rightSubtree;
            if (rightSubtree != null) {
                rightSubtree.parent = this.root;
            }
        } else {
            this.root = rightSubtree;
        }
    }

    // Helper for deletion to avoid setting global root prematurely during subtree splay
    private void splayMaxInSubtree(SplayNode<T> x) {
        while (x.parent != null) {
            if (x.parent.parent == null) {
                if (x == x.parent.left) rightRotate(x.parent);
                else leftRotate(x.parent);
            } else if (x == x.parent.left && x.parent == x.parent.parent.left) {
                rightRotate(x.parent.parent);
                rightRotate(x.parent);
            } else if (x == x.parent.right && x.parent == x.parent.parent.right) {
                leftRotate(x.parent.parent);
                leftRotate(x.parent);
            } else if (x == x.parent.right && x.parent == x.parent.parent.left) {
                leftRotate(x.parent);
                rightRotate(x.parent);
            } else {
                rightRotate(x.parent);
                leftRotate(x.parent);
            }
        }
    }

    @Override
    public void obtenerDato() {
        inorder(this.root);
        System.out.println();
    }

    private void inorder(SplayNode<T> node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.key + " ");
            inorder(node.right);
        }
    }

    @Override
    public String getName() {
        return this.name;
    }
}
