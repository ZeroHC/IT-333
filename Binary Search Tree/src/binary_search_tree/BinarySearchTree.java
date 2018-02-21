package binary_search_tree;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class BinarySearchTree<T extends Comparable<T>> implements Iterable<T>
{
    private Node root;
    private int size;

    public BinarySearchTree()
    {
        //do nothing
    }

    public void add(T element)
    {
        //empty tree?
        if (root == null)
        {
            root = new Node(element);
            size++;
        }
        else
        {
            root = add(element, root);
        }
    }

    private Node add(T element, Node current)
    {
        //if we have a null current node, then we found an open spot
        if(current == null)
        {
            size++;
            return new Node(element);
        }

        //are we looking to the left and right
        int compare = current.data.compareTo(element);

        if(compare < 0) //current is smaller
        {
            current.right = add(element, current.right);
        }
        else if(compare > 0) //current larger
        {
            current.left = add(element, current.left);
        }

        return current;
    }

    public boolean contains(T element)
    {
        return contains(element, root);
    }

    private boolean contains(T element, Node current)
    {
        if(current == null)
        {
            return false;
        }

        int compare = current.data.compareTo(element);

        if(compare < 0)
        {
            return contains(element, current.right);
        }
        else if(compare > 0)
        {
            return contains(element, current.right);
        }
        else // compare == 0
        {
            return true; // found it
        }
    }

    public boolean remove(T search)
    {
        //start searching at the root
        root = remove(root, search);

        //fix this later
        return true;
    }

    private Node remove(Node node, T search)
    {
        //element not found in tree
        if (node == null)
        {
            return null;// assign our parent pointer to this value
        }

        //compare against the current element
        int compare = node.data.compareTo(search);

        if (compare < 0) //search to the right
        {
            node.right = remove(node.right, search);
        }
        else if (compare > 0)
        {
            node.left = remove(node.left, search);
        }
        else //found the node
        {
            //case #1 - no children
            if (node.left  == null && node.right == null)
            {
                return null;
            }

            //case #2 - one child
            else if (node.left == null)
            {
                return node.right;
            }
            else if (node.right == null)
            {
                return node.left;
            }

            //case #3 - two children
            else
            {
                //find the smallest element in the right subtree
                Node replacement = findMin(node.right);

                //replace the data in our removed node
                node.data = replacement.data;

                //recursively remove the smallest element in the right subtree
                node.right = remove(node.right, replacement.data);
            }
        }

        //this will allow our parent to assign a reference to this node
        return node;
    }

    private Node findMin(Node search)
    {
        if (search.left == null)
        {
            return search;
        }
        return findMin(search.left);
    }

    private Node findMax(Node search)
    {
        if (search.right == null)
        {
            return search;
        }
        return findMax(search.right);
    }

    public void printTraversal(Traversal traversal)
    {
        switch (traversal)
        {
            case IN_ORDER:
                inOrder(root);
                System.out.println();
                break;
            case PRE_ORDER:
                preOrder(root);
                System.out.println();
                break;
            case POST_ORDER:
                postOrder(root);
                System.out.println();
                break;
            case BREADTH_FIRST:
                breadthFirst();
                break;
        }
    }

    private void inOrder(Node current)
    {
        if(current != null)
        {
            //print the element and recursively search for others
            inOrder(current.left);
            System.out.print(current.data + " ");
            inOrder(current.right);
        }
    }

    private void preOrder(Node current)
    {
        if(current != null)
        {
            //print the element and recursively search for others
            System.out.print(current.data + " ");
            preOrder(current.left);
            preOrder(current.right);
        }
    }

    private void postOrder(Node current)
    {
        if(current != null)
        {
            //print the element and recursively search for others
            postOrder(current.left);
            postOrder(current.right);
            System.out.print(current.data + " ");
        }
    }

    private void breadthFirst()
    {
        Queue<Node> nodeQueue = new LinkedBlockingQueue<>();

        nodeQueue.offer(root);

        while (!nodeQueue.isEmpty())
        {
            Node nextNode = nodeQueue.poll();
            System.out.print(nextNode.data + " ");

            if (nextNode.left != null)
            {
                nodeQueue.offer(nextNode.left);
            }

            if (nextNode.right != null)
            {
                nodeQueue.offer(nextNode.right);
            }
        }
        System.out.println();
    }

    //calculate the total height of all nodes combined
    public int totalNodeHeight()
    {
        calculateAllNodeHeights();

        return totalNodeHeight(root);
    }

    private int totalNodeHeight(Node current)
    {
        if (current == null) return 0;

        return current.height + totalNodeHeight(current.left) + totalNodeHeight(current.right);
    }

    public int getTreeHeight()
    {
        //make sure our nodes have updated height values
        calculateAllNodeHeights();

        return root.height;
    }

    public void calculateAllNodeHeights()
    {
        calculateNodeHeight(root);
    }

    private int calculateNodeHeight(Node current)
    {
        if (current == null)
        {
            return -1;
        }
        else
        {
            int height = 1 + Math.max(calculateNodeHeight(current.left), calculateNodeHeight(current.right));
            current.setHeight(height);
            return height;
        }
    }

    public void calculateAllNodeDepths()
    {
        calculateNodeDepth(root, 0);
    }

    private int calculateNodeDepth(Node current, int depth)
    {
        if (current == null)
        {
            return 0;
        }
        else
        {
            current.setDepth(depth);
            depth++;
            calculateNodeDepth(current, depth);
            return depth;
        }
    }

    @Override
    public Iterator<T> iterator()
    {
        return null;
    }

    private class Node
    {
        private T data;
        private Node left;
        private Node right;

        //scratch fields
        private int height;
        private int depth;

        public Node(T data)
        {
            this.data = data;
        }

        public Node(T data, Node left, Node right)
        {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public int getHeight()
        {
            return height;
        }

        public void setHeight(int height)
        {
            this.height = height;
        }

        public int getDepth()
        {
            return depth;
        }

        public void setDepth(int depth)
        {
            this.depth = depth;
        }

        public String toString()
        {
            String dataString = (data == null) ? "null" : data.toString();
            String leftChild = (left == null) ? "null" : left.data.toString();
            String rightChild = (right == null) ? "null" : right.data.toString();

            return leftChild + " <-- " + dataString + " --> " + rightChild;
        }
    }
}
