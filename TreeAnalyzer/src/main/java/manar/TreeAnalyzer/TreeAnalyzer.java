package manar.TreeAnalyzer;

public class TreeAnalyzer{
    public static void main(String[] args) {
        BinarySortTree<Double> tree = new BinarySortTree<Double>();

        for (int i = 0; i < 1023; i++) {
            tree.treeInsert(new TreeNode<Double>(Math.random())); 
        }
       
        System.out.println("Number of leaves:         " + tree.numberOfleaves());
        System.out.println("Average depth of leaves:  " + tree.sumDepth()/tree.numberOfleaves());
        System.out.println("Maximum depth of leaves:  " + tree.maxDepth());
    }
}

class BinarySortTree<T extends Comparable<T>>{
    TreeNode<T> root;

    /**
    * inserts the @param node as as a leaf to the tree.
    */
    void treeInsert(TreeNode<T> node){
        if(this.root == null){
            this.root = node;
        }
        else{
            this.treeInsert(node, root);
        }
    }

    /**
    * inserts the @param node as child of  @param currentRoot.
    */
    private void treeInsert(TreeNode<T> node ,TreeNode<T> currentRoot){
        if(currentRoot.getValue().compareTo(node.getValue()) > 0){
            if(currentRoot.getRightchild() == null){
                currentRoot.setRightchild(node);
            }
            else{
                this.treeInsert(node,currentRoot.getRightchild());
            }
        }
        else if(currentRoot.getValue().compareTo(node.getValue()) < 0){
            if(currentRoot.getRightchild() == null){
                currentRoot.setLeftChild(node);
            }
            else{
                this.treeInsert(node,currentRoot.getRightchild());
            }
        }
        else if(currentRoot.getValue().compareTo(node.getValue()) == 0){
            currentRoot.increseCount();
        }
    }


    //numberOfleaves definition.

    /**
    * returns the number of leaves of all the leaves on the tree.
    */
    public int numberOfleaves(){
    return numberOfleaves(this.root);
    }

    /**
    * returns the number of leaves of the node.
    */
    private int numberOfleaves(TreeNode<T> node){
        int num = 0;
        if(node == null){
            return -1;
        }
        if(node.getLeftchild() != null){
            num += numberOfleaves(node.getLeftchild());
        }
        if(node.getRightchild() != null){
            num += numberOfleaves(node.getRightchild());
        }
        if(node.getLeftchild() == null && node.getRightchild() == null){
            return 1;
        }
        return num;
    }
   

    //sum of depths definition

    /**
    * returns the sum of depths of of all the leaves on the tree.
    */
    public int sumDepth(){
        return sumDepth(root,0);
    }

    /**
     * Returns the sum of the depth of all the child leaves of the nodes at the depth of @param currentDepth .
     */
    private int sumDepth(TreeNode<T> node , int currentDepth){
        int sumDepth = 0;
        if(node == null){
            return -1;
        }
        if(node.getLeftchild() != null){
            sumDepth += sumDepth(node.getLeftchild(), currentDepth + 1);
        }
        if(node.getRightchild() != null){
            sumDepth += sumDepth(node.getRightchild(), currentDepth + 1);
        }
        if(node.getLeftchild() == null && node.getRightchild() == null){
            return currentDepth;
        }
        return sumDepth;
    }

    //maximum depth definition.

    /**
     * The tree maximum depth.
     */
    public int maxDepth(){
        return maxDepth(root,0);
    }

    /**
     * Tree depth starting at @param node with a given @param currentDepth .
     */
    private int maxDepth(TreeNode<T> node , int currentDepth){
        int rightDepth = 0;
        int leftDepth = 0;
        //failsafe.
        if(node == null){
            return -1;
        }
        //increment depth and move to the right child.
        if(node.getLeftchild() != null){
            leftDepth = maxDepth(node.getLeftchild(), currentDepth + 1);
        }
        //increment depth and move to the left child.
        if(node.getRightchild() != null){
            rightDepth = maxDepth(node.getRightchild(), currentDepth + 1);
        }
        //leaf reached
        if(node.getLeftchild() == null && node.getRightchild() == null){
            return currentDepth;
        }

        //return the deeper of the depths.
        if(rightDepth > leftDepth) return rightDepth;
        else return leftDepth;
    }
}

/**
 * a class representing nodes for the BinarySortTree class.
 */
class TreeNode<T extends Comparable<T>>{
    T value;
    int count = 1;
    TreeNode<T> rightChild, leftChild;
  
    //constructors.

    /**
     * default constructor of the node, sets only the value.
     */
    TreeNode(T nodeValue){
        this.value = nodeValue;
    }

    /**
     * alternative constructor of the tree, sets the value and the two children.
     */
    TreeNode(T nodeValue , TreeNode<T> nodeRightchild,TreeNode<T> nodeLeftchild){
        this.value = nodeValue;
        this.rightChild = nodeRightchild;
        this.leftChild = nodeLeftchild;
    }

    //setters.

    /**
     * sets the value of the node.
     */
    public void setValue(T val){
        this.value = val;
    }

    /**
     * sets the right child of the node.
     */
    public void setRightchild(TreeNode<T> node){
        this.rightChild = node;
    }

    /**
     * sets the left child of the node.
     */
    public void setLeftChild(TreeNode<T> node){
        this.leftChild = node;
    }

    
    //getters.
    
    /**
     * returns the value of the node.
     */
    public T getValue(){
        return this.value;
    }

     /**
     * returns the right child of the node.
     */
    public TreeNode<T> getRightchild(){
        return this.rightChild;
    }

     /**
     * returns the left child of the node.
     */
    public TreeNode<T> getLeftchild(){
        return this.leftChild;
    }

     /**
     * compares the value of the node with another.
     */
    public int compareTo(TreeNode<T> item){
        return this.getValue().compareTo(item.getValue());
    } 

    /**
     * inreases the count of the of the node occurance at the location.
     */
    public void increseCount(){
        this.count++;
    }
}