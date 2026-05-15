package arboles;

import estruc_datos.int_estructura;

public class RBT<T extends Comparable<? super T>>  implements int_estructura<T> {

	private static final boolean RED = true;
	private static final boolean BLACK = false;

	static class RBTNode<T> {
		T key;
		boolean color;
		RBTNode<T> left;
		RBTNode<T> right;
		RBTNode<T> parent;

		RBTNode(T key, boolean color) {
			this.key = key;
			this.color = color;
		}
	}

	private RBTNode<T> root;
	private int size;

	public RBT() {
		this.root = null;
		this.size = 0;
	}

	public RBTNode<T> getRoot() {
		return this.root;
	}

	public int getSize() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public boolean contains(T key) {
		return search(key) != null;
	}

	public RBTNode<T> search(T key) {
		RBTNode<T> current = this.root;

		while (current != null) {
			int compareResult = key.compareTo(current.key);

			if (compareResult == 0) {
				return current;
			}

			current = (compareResult < 0) ? current.left : current.right;
		}

		return null;
	}

	public RBTNode<T> findMin() {
		return findMin(this.root);
	}

	public RBTNode<T> findMax() {
		return findMax(this.root);
	}

	private RBTNode<T> findMin(RBTNode<T> node) {
		if (node == null) {
			return null;
		}

		while (node.left != null) {
			node = node.left;
		}

		return node;
	}

	private RBTNode<T> findMax(RBTNode<T> node) {
		if (node == null) {
			return null;
		}

		while (node.right != null) {
			node = node.right;
		}

		return node;
	}

	public void insert(T key) {
		if (key == null) {
			throw new IllegalArgumentException("No se puede insertar null");
		}

		RBTNode<T> newNode = new RBTNode<>(key, RED);

		if (this.root == null) {
			this.root = newNode;
			this.root.color = BLACK;
			this.size++;
			return;
		}

		// Inserción tipo BST
		RBTNode<T> current = this.root;
		RBTNode<T> parent = null;

		while (current != null) {
			parent = current;
			int compareResult = key.compareTo(current.key);

			if (compareResult < 0) {
				current = current.left;
			} else if (compareResult > 0) {
				current = current.right;
			} else {
				// Duplicado: no se inserta
				return;
			}
		}

		// Conectar el nuevo nodo
		newNode.parent = parent;
		int compareResult = key.compareTo(parent.key);

		if (compareResult < 0) {
			parent.left = newNode;
		} else {
			parent.right = newNode;
		}

		this.size++;

		// Rebalanceo Red-Black
		insertFixup(newNode);
	}

	private void insertFixup(RBTNode<T> node) {
		while (node.parent != null && node.parent.color == RED) {
			if (node.parent == node.parent.parent.left) {
				// Caso A: padre es hijo izquierdo del abuelo
				RBTNode<T> uncle = node.parent.parent.right;

				if (colorOf(uncle) == RED) {
					// Caso 1: Tío es rojo → recoloreamos
					setColor(node.parent, BLACK);
					setColor(uncle, BLACK);
					setColor(node.parent.parent, RED);
					node = node.parent.parent;
				} else {
					// Tío es negro
					if (node == node.parent.right) {
						// Caso 2: Triángulo → rotación izquierda
						node = node.parent;
						rotateLeft(node);
					}
					// Caso 3: Línea → rotación derecha + recolor
					setColor(node.parent, BLACK);
					setColor(node.parent.parent, RED);
					rotateRight(node.parent.parent);
				}
			} else {
				// Caso B: padre es hijo derecho del abuelo (simétrico)
				RBTNode<T> uncle = node.parent.parent.left;

				if (colorOf(uncle) == RED) {
					// Caso 1: Tío es rojo → recoloreamos
					setColor(node.parent, BLACK);
					setColor(uncle, BLACK);
					setColor(node.parent.parent, RED);
					node = node.parent.parent;
				} else {
					// Tío es negro
					if (node == node.parent.left) {
						// Caso 2: Triángulo → rotación derecha
						node = node.parent;
						rotateRight(node);
					}
					// Caso 3: Línea → rotación izquierda + recolor
					setColor(node.parent, BLACK);
					setColor(node.parent.parent, RED);
					rotateLeft(node.parent.parent);
				}
			}
		}

		// Garantizar que la raíz sea negra
		this.root.color = BLACK;
	}

	// ============ Rotaciones ============

	private void rotateLeft(RBTNode<T> node) {
		RBTNode<T> rightChild = node.right;

		if (rightChild == null) {
			return;
		}

		// Rotación
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

	private void rotateRight(RBTNode<T> node) {
		RBTNode<T> leftChild = node.left;

		if (leftChild == null) {
			return;
		}

		// Rotación
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

	// ============ Métodos auxiliares de color y estructura ============

	private boolean colorOf(RBTNode<T> node) {
		return node == null ? BLACK : node.color;
	}

	private void setColor(RBTNode<T> node, boolean color) {
		if (node != null) {
			node.color = color;
		}
	}

	private RBTNode<T> parentOf(RBTNode<T> node) {
		return node == null ? null : node.parent;
	}

	private RBTNode<T> grandparentOf(RBTNode<T> node) {
		return parentOf(parentOf(node));
	}

	private RBTNode<T> uncleOf(RBTNode<T> node) {
		RBTNode<T> grandparent = grandparentOf(node);

		if (grandparent == null) {
			return null;
		}

		return (parentOf(node) == grandparent.left) ? grandparent.right : grandparent.left;
	}

	public void delete(T key) {
		throw new UnsupportedOperationException("delete no implementado: árbol Red-Black de solo lectura (insert/search)");
	}

	public void printTree(RBTNode<T> root){
        if (root==null){
            return;
        }

        System.out.println(root.key);
        if(root.left != null){
            System.out.print(root.key+ " Left "); 
            printTree(root.left);
        }

        if(root.right != null){
            System.out.print(root.key+ " Right ");
            printTree(root.right);
        }
    }

	@Override
	public void insertar(T value) {
		insert(value);
	}

	@Override
	public void obtenerDato() {
		printTree(root);
	}
}
