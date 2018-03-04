package dictionary_ui;

/*
 * Hanchen (Zero) Liu
 * 03/03/2018
 * DictionarySearchApp.java
 *
 * This is the class that helps user to search in a dictionary
 */

import dictionary_manager.DictionaryBuilder;
import java.util.Scanner;

/**
 * This is the class that helps user to search in a dictionary
 *
 * @author Hanchen (Zero) Liu
 * @version 1.0
 */
public class DictionarySearchApp
{
    //constant Scanner for global use
    private static final Scanner CONSOLE = new Scanner(System.in);

    private static DictionaryBuilder builder;

    /**
     *
     * @param args
     */
    public static void main(String[] args)
    {
        //greets user
        System.out.println("\nWelcome to my Dictionary APP\n");

        //display out menu
        printMenu();

    }

    //this method displays the app menu
    private static void printMenu()
    {
        int userInput = 0;

        while (userInput != 5)
        {
            System.out.println("What would you like to do next? (1-5)");
            System.out.println("1. Load dictionary from unordered pairs (Approximate wait time 2-3 min!)");
            System.out.println("2. Load dictionary from serialized tree (Approximate wait time 2-3 min!)");
            System.out.println("3. Define");
            System.out.println("4. Save dictionary");
            System.out.println("5. Exit");
            userInput = CONSOLE.nextInt();
            CONSOLE.nextLine();
            while (userInput < 1 || userInput > 5)
            {
                System.out.print("1 - 5 only, enter again: ");
                userInput = CONSOLE.nextInt();
                CONSOLE.nextLine();
            }
            menuAction(userInput);
        }
    }

    //this method handles all the menu actions
    private static void menuAction(int userInput)
    {
        switch (userInput)
        {
            //load unsorted dictionary
            case 1:
                builder = new DictionaryBuilder("dictionaries/dictionary.txt");
                break;

            //load sorted dictionary
            case 2:
                builder = new DictionaryBuilder("dictionaries/sortedDictionary.txt");
                break;

            //search the definition of a word
            case 3:
                System.out.println("\nWhat word would you like to search? ");
                System.out.println(builder.search(CONSOLE.nextLine()) + "\n");
                break;

            //save a sorted dictionary
            case 4:
                builder.saveDictionary("dictionaries/sortedDictionary.txt");
                break;

            //quit program
            case 5:
                break;

        }
    }
}
