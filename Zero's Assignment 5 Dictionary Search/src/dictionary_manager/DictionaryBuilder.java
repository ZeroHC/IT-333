package dictionary_manager;

/*
 * Hanchen (Zero) Liu
 * 03/03/2018
 * DictionaryBuilder.java
 *
 * This is the class that builds a Dictionary Search Tree
 */

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This is the class that builds a Dictionary Search Tree
 *
 * @author Hanchen (Zero) Liu
 * @version 1.0
 */
public class DictionaryBuilder
{
    //private fields
    private Node root;
    private TreeMap<String, String> sortedDictionaryMap;

    /**
     * This method initializes a dictionary search tree
     *
     * @param fileName the dictionary file that needs to be loaded into the tree
     */
    public DictionaryBuilder(String fileName)
    {
        //load the dictionary file
        loadDictionary(fileName);

        //generate the dictionary tree
        root = generateDictionaryTree(0, sortedDictionaryMap.size() - 1);

        System.out.println("\n" + fileName + "Loaded\n");
    }

    //read the dictionary file and write the contents into a map
    private void loadDictionary(String dictionaryFile)
    {
        Map<String, String> tempMap = new HashMap<>();

        //stores word and definition pairs into map
        try (Scanner fileScanner = new Scanner(new FileReader(dictionaryFile)))
        {
            String[] parsedStrings;
            while (fileScanner.hasNextLine())
            {
                //split the line at ": " and store the split strings into an array
                parsedStrings = fileScanner.nextLine().split(": ");

                //stores the key value pair into the map
                // 0 - word(key), 1 - definition(value)
                tempMap.put(parsedStrings[0], parsedStrings[1]);
            }
        }
        catch (IOException | ArrayIndexOutOfBoundsException e)
        {
            System.out.println("\nSomething went wrong with " + dictionaryFile + "\n");
        }

        //use TreeMap to auto sort the tempMap
        sortedDictionaryMap = new TreeMap<>(tempMap);
    }

    //this method generates the dictionary tree
    private Node generateDictionaryTree(int minIndex, int maxIndex)
    {
        //check if the map is empty or the min index is greater than max
        if (sortedDictionaryMap.isEmpty() || minIndex > maxIndex)
        {
            return null;
        }
        else
        {
            //make sure the tree is balanced by taking the middle pair as the parent node
            int middleIndex = (maxIndex - minIndex) / 2 + minIndex;

            Node current = new Node((String)sortedDictionaryMap.keySet().toArray()[middleIndex],
                                    (String)sortedDictionaryMap.values().toArray()[middleIndex]);

            current.left = generateDictionaryTree(minIndex, middleIndex - 1);
            current.right = generateDictionaryTree(middleIndex + 1, maxIndex);

            return current;
        }
    }

    /**
     * This method searches the definition of a word in the dictionary
     *
     * @param word a word that need to be searched in the dictionary
     * @return the definition of the word
     */
    public String search(String word)
    {
        return search(root, word);
    }

    //This method searches the definition of a word in the dictionary
    private String search(Node current, String word)
    {
        //search miss
        if (current == null)
        {
            return "Word not found!";
        }

        int result = current.compareTo(word);

        //if the result code is less than 0, search the right side of the node
        if (result < 0)
        {
            return search(current.right, word);
        }

        //if the result code is greater than 0, search the left side of the node
        else if (result > 0)
        {
            return search(current.left, word);
        }

        //search success
        else
        {
            return current.definition;
        }
    }

    /**
     * This method saves each word - definition pair in the tree back to
     * a new text file using an insertion order for the tree
     *
     * @param fileName name of the file
     */
    public void saveDictionary(String fileName)
    {
        Queue<Node> tempQueue = new LinkedBlockingQueue<>();
        tempQueue.offer(root);

        try (PrintWriter fileWriter = new PrintWriter(fileName))
        {
            while (!tempQueue.isEmpty())
            {
                Node current = tempQueue.poll();
                fileWriter.println(current.word + ": " + current.definition);

                if (current.left != null)
                {
                    tempQueue.offer(current.left);
                }
                if (current.right != null)
                {
                    tempQueue.offer(current.right);
                }
            }
            fileWriter.close();
        }
        catch (IOException e)
        {
            System.out.println("Something went wrong when trying to save the dictionary file");
        }
        System.out.println("\nDictionary Saved\n");
    }

    //creates a private Node inner class
    private class Node
    {
        private String word;
        private String definition;
        private Node left;
        private Node right;

        //private constructor only can be used by the outer class
        private Node(String word, String definition)
        {
            this.word = word;
            this.definition = definition;
        }

        private Node(String word, String definition, Node left, Node right)
        {
            this.word = word;
            this.definition = definition;
            this.left = left;
            this.right = right;
        }

        //compares the word of each node
        private int compareTo(String word)
        {
            return this.word.compareTo(word);
        }
    }
}
