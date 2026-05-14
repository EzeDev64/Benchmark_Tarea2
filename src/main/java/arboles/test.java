package arboles;

public class test {
    public static void main(String[] args) {
        BST bts = new BST();
        AvlTree<Integer> AVL = new AvlTree<Integer>();
        RBT<Integer> redRbt = new RBT<Integer>();

        //Integer[] data = {72,61,9,30,14,84,79,23,47,39,53,34};
        Integer[] data = {7,2,8,4,1,3,5};

        for (Integer integer : data) {
            bts.insert(integer);
            AVL.insert(integer);
            redRbt.insert(integer);
        }
        
        bts.inorder();
        AVL.printTree(AVL.getRoot());
        System.out.println(redRbt.findMax().key);
    }
}
