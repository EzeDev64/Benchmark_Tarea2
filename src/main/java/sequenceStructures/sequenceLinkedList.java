package sequenceStructures;

public class sequenceLinkedList {

    // Internal Node class for the doubly linked list
    public class SnapshotNode {
        public Idrawable treeSnapshot;
        public SnapshotNode next;
        public SnapshotNode prev;

        public SnapshotNode(Idrawable treeSnapshot) {
            this.treeSnapshot = treeSnapshot;
            this.next = null;
            this.prev = null;
        }
    }

    private SnapshotNode head;
    private SnapshotNode tail;
    private int size;

    public sequenceLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // --- Insertion ---
    
    /**
     * Appends a new snapshot to the end of the doubly linked list.
     * @param snapshot The Idrawable tree snapshot (Remember to pass a Deep Copy!)
     */
    public void insert(Idrawable snapshot) {
        SnapshotNode newNode = new SnapshotNode(snapshot);

        if (this.head == null) {
            // The list is empty
            this.head = newNode;
            this.tail = newNode;
        } else {
            // Append to the end
            this.tail.next = newNode;
            newNode.prev = this.tail;
            this.tail = newNode;
        }
        this.size++;
    }

    // --- Retrieval ---

    /**
     * Retrieves the first node in the list. Useful for starting the step-by-step view.
     */
    public SnapshotNode getHead() {
        return this.head;
    }

    // --- Utilities ---
    
    public int getSize() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }
    
    /**
     * Clears the list. Useful if the user generates a new sequence.
     */
    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
}
