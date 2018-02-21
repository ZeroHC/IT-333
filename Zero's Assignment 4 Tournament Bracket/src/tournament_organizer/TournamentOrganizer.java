package tournament_organizer;

/*
 * Hanchen (Zero) Liu
 * 2/20/2018
 * TournamentOrganizer.java
 * This is the class that organizes and manipulate the tournament
 */

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This is the class that organizes and manipulate the tournament
 *
 * @author Hanchen (Zero) Liu
 * @version 1.0
 */
public class TournamentOrganizer<T>
{
    //queue for storing contestants' names
    private Queue<Node> contestantQueue = new LinkedBlockingQueue<>();
    //final winner
    private Node winner;
    //for generating winners randomly
    private Random random = new Random();

    /**
     * This method initialize the tournament tree
     *
     * @param nameQueue a queue full of contestants' names
     */
    public TournamentOrganizer(Queue<T> nameQueue)
    {
        queueSetup(nameQueue);
        winner = generateTournament();
    }

    //this method setup the contestantQueue
    private void queueSetup(Queue<T> nameQueue)
    {
        //clear up the queue
        contestantQueue.clear();
        for (T element : nameQueue)
        {
            contestantQueue.add(new Node(element));
        }
    }

    //this method generates the tournament tree
    private Node generateTournament()
    {
        //check if the queue is empty
        if (!contestantQueue.isEmpty())
        {
            //first poll 2 contestant out from the queue
            Node contestant1 = contestantQueue.poll();
            Node contestant2 = contestantQueue.poll();

            //creates a node that will be storing the round winner
            Node roundWinner;

            //while the contestants polled from queue aren't null
            //create a round winner node that holds null temporarily,
            //but will store the winner of the round eventually
            //then poll two more contestants out from the queue
            //and repeat till no more contestants
            while (contestant1 != null && contestant2 != null)
            {
                roundWinner = new Node(null, contestant1, contestant2);
                contestantQueue.add(roundWinner);
                contestant1 = contestantQueue.poll();
                contestant2 = contestantQueue.poll();
            }

            //return contestant1 because it will poll the last contestant from the queue
            return contestant1;
        }
        else
        {
            //null means the queue is empty, no contestants
            return null;
        }
    }

    //starts the tournament
    private void startTournament(Node contestant)
    {
        if (contestant.left.data == null)
        {
            startTournament(contestant.left);
        }
        if (contestant.right.data == null)
        {
            startTournament(contestant.right);
        }
        if (contestant.left.data != null && contestant.right.data != null)
        {
            contestant.data = getWinner(contestant.left, contestant.right);
        }
    }

    //Get the winner randomly
    private T getWinner(Node contestant1, Node contestant2)
    {
        if (random.nextBoolean()) return contestant1.data;
        else return contestant2.data;
    }

    /**
     * this method prints out the final result of the whole tournament
     */
    public void printResult()
    {
        //starts the tournament
        startTournament(winner);

        contestantQueue.clear();
        contestantQueue.offer(winner);

        if (contestantQueue.isEmpty())
        {
            return;
        }

        System.out.println("Winner: " + winner.data);

        int roundCount = getTreeHeight();

        while (true)
        {
            // contestantsCount (queue size) indicates number of nodes
            // at current level.
            int contestantsCount = contestantQueue.size();

            if (contestantsCount == 0 || roundCount == 0) break;

            System.out.print("Round #" + roundCount + ": ");

            // Dequeue all nodes of current level and Enqueue all
            // nodes of next level
            while (contestantsCount > 0)
            {
                Node currentContestant = contestantQueue.poll();

                if (currentContestant.left != null)
                {
                    if (currentContestant.data.equals(currentContestant.left.data))
                    {
                        System.out.print(currentContestant.left.data + "(W) - " + currentContestant.right.data + ", ");
                    }
                    contestantQueue.offer(currentContestant.left);
                }

                if (currentContestant.right != null)
                {
                    if (currentContestant.data.equals(currentContestant.right.data))
                    {
                        System.out.print(currentContestant.left.data + " - " + currentContestant.right.data + "(W), ");
                    }
                    contestantQueue.offer(currentContestant.right);
                }

                contestantsCount--;
            }
            roundCount--;
            System.out.println();
        }
    }

    //this method gets the total height of the tree
    private int getTreeHeight()
    {
        return calculateNodeHeight(winner);
    }

    //this method calculates the height of one node
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

    //creates a private Node inner class
    private class Node
    {
        private T data;
        private Node left;
        private Node right;
        private int height;

        //private constructor only can be used by the outer class
        private Node(T data)
        {
            this.data = data;
        }

        private Node(T data, Node left, Node right)
        {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        private void setHeight(int height)
        {
            this.height = height;
        }

        /**
         * this method stores the structure of the node as a string
         *
         * @return a string that represents the node structure
         */
        @Override
        public String toString()
        {
            String dataString = (data == null) ? "null" : data.toString();
            String leftChild = (left == null) ? "null" : left.data.toString();
            String rightChild = (right == null) ? "null" : right.data.toString();

            return leftChild + " <-- " + dataString + " --> " + rightChild;
        }
    }
}
