package linked_lists;

public class LinkedListAlgorithms
{
    public static void main(String[] args)
    {
        //instantiated our class
        LinkedListAlgorithms algorithms = new LinkedListAlgorithms();

        //assemble a linked list
        /*Node fourth = algorithms.new Node(4, null);
        Node third = algorithms.new Node(3, fourth);
        Node second = algorithms.new Node(2, third);
        Node first = algorithms.new Node(1, second);*/

        Node head = null;

        for (int i = 1; i <=500; i++)
        {
            if (head == null)
            {
                head = algorithms.new Node(i, null);
            }
            else
            {
                head = algorithms.new Node(i, head);
            }
        }

        //reverse it
        head = algorithms.reverse(head);

        //verify our results
        Node current = head;

        while (current != null)
        {
            //print the current element and move past it
            System.out.println(current.data);
            current = current.next;
        }
    }

    private Node reverse(Node head)
    {
        if (head == null) return null;

        else if (head.next == null) //one element
        {
            return head;
        }

        //step #1 - point a temporary variable to head
        Node newListHead = head;

        //step #2 - save the elements after head
        Node saveNextNode = head.next;

        //step #3 - remove the next pointer for temp1
        newListHead.next = null;

        while (saveNextNode.next != null)
        {
            //step #4 - move our pointers along
            head = saveNextNode;
            saveNextNode = saveNextNode.next;

            //move
            head.next = newListHead;
            newListHead = head;
        }

        saveNextNode.next = newListHead;
        head = saveNextNode;

        return head;
    }

    private class Node
    {
        private int data;
        private Node next;

        public Node(int data, Node next)
        {
            this.data = data;
            this.next = next;
        }


    }
}
