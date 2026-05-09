package arboles;

public class RBT<T extends Comparable<? super T>> {

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
		throw new UnsupportedOperationException("insert pendiente para el siguiente commit");
	}

	public void delete(T key) {
		throw new UnsupportedOperationException("delete pendiente para el siguiente commit");
	}
}
