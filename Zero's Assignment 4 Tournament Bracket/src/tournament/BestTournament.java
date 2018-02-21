package tournament;

/*
 * Hanchen (Zero) Liu
 * 2/20/2018
 * BestTournament.java
 * This is the class that simulates the tournament
 */

import tournament_organizer.TournamentOrganizer;

import java.io.FileReader;
import java.io.IOException;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This is the class that simulates the tournament
 *
 * @author Hanchen (Zero) Liu
 * @version 1.0
 */
public class BestTournament
{
    private static Queue<String> contestantsQueue = new LinkedBlockingQueue<>();

    /**
     * this is the main method that simulates the tournament
     *
     * @param args command line args
     */
    public static void main(String[] args)
    {
        String[] contestantsFiles = {"name files/names - small.txt", "name files/names - medium.txt", "name files/names - large.txt"};

        TournamentOrganizer organizer;

        int counter = 1;
        for (String contestantsFile : contestantsFiles)
        {
            System.out.println();
            System.out.println("Dumb Tournament " + counter);
            contestantsQueue.clear();
            getContestants(contestantsFile);
            organizer = new TournamentOrganizer(contestantsQueue);
            organizer.printResult();
            System.out.println();
            counter++;
        }
    }

    //Create a TournamentTree object by the player names within the input file.
    private static void getContestants(String contestantsFile)
    {
        //Read the player name into the queue
        try (Scanner fileScanner = new Scanner(new FileReader(contestantsFile)))
        {
            while (fileScanner.hasNextLine())
            {
                contestantsQueue.add(fileScanner.nextLine());
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
