package sorted;

public class SortedLinkedList
{
    private Node head;

    public SortedLinkedList()
    {
        //do nothing...
    }

    //adds an element if not present in the list,
    //in the ordered position (ascending)
    public void add(int element)
    {
        //empty list
        if (head == null)
        {
            head = new Node(element);
        }
        else if (element < head.data)
        {
            head = new Node(element, head);
        }
        else if (element == head.data)
        {
            return;//no dups!
        }
        else
        {
            Node current = head;

            //search as long as there is a next element
            while (current.next != null)
            {
                //is this the correct spot
                if (current.next.data > element)
                {
                    //point a new node at the next node
                    current.next = new Node(element, current.next);
                    return;
                }
                else if (current.next.data == element)
                {
                    return; //no dups
                }
                current = current.next;
            }

            current.next = new Node(element);
        }
    }

    public void print()
    {
        Node current = head;
        while (current != null)
        {
            System.out.println(current.data);
            current = current.next;
        }
    }

    private class Node
    {
        private int data;
        private Node next;

        private Node(int data)
        {
            this.data = data;
        }

        private Node(int data, Node next)
        {
            this.data = data;
            this.next = next;
        }
    }
}