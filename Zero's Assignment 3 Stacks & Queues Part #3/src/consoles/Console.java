package consoles;

import browsers.HistoryHandler;
import structures.TwoWayQueue;

import java.util.Scanner;

public class Console
{
    private static HistoryHandler historyHandler;
    private static TwoWayQueue<String> historyQueue;
    private static TwoWayQueue<String> sessionQueue;
    private static Scanner inputScanner;

    private static void setup()
    {
        historyHandler = new HistoryHandler("history/histories.txt");
        sessionQueue = historyQueue = new TwoWayQueue<>();
        historyQueue.enqueueAllLast(historyHandler.getBrowserHistories());
        inputScanner = new Scanner(System.in);
    }

    public static void main(String[] args)
    {
        setup();



        int userInput = 0;

        while (userInput != 7)
        {
            menu();
            userInput = inputScanner.nextInt();
            inputScanner.nextLine();

            while (userInput < 1 || userInput > 7)
            {
                System.out.println();
                System.out.print("Invalid menu choice, 1-7 only, please enter again: ");
                userInput = inputScanner.nextInt();
                inputScanner.nextLine();
            }

            menuHandler(userInput);
        }

    }

    private static void menu()
    {
        System.out.println();
        System.out.println("1. visit page");
        System.out.println("2. back");
        System.out.println("3. forward");
        System.out.println("4. print history");
        System.out.println("5. print session history");
        System.out.println("6. clear history");
        System.out.println("7. close browser");
        System.out.println();
    }

    private static void menuHandler(int userInput)
    {
        TwoWayQueue<String> tempSessionQueue = sessionQueue;

        switch (userInput)
        {
            case 1:
                System.out.println("which page?\n");
                sessionQueue.enqueueLast(inputScanner.nextLine());
                break;

            case 2:
                break;

            case 3:
                break;

            case 4:
                historyPrinter(historyQueue);
                break;

            case 5:
                historyPrinter(sessionQueue);
                break;

            case 6:
                historyQueue.clear();
                sessionQueue.clear();
                historyHandler.clearHistory();
                break;

            case 7:
                historyHandler.historyWriter(sessionQueue.dequeueAll());
                break;
        }
    }

    private static void historyPrinter(TwoWayQueue<String> queue)
    {
        if (queue.size() == 0)
        {
            System.out.println("");
        }
        else
        {
            boolean first = true;
            for (String website : queue)
            {
                if (first)
                {
                    System.out.println(website);
                    first = false;
                }
                else
                {
                    System.out.print("-->" + website);
                }
            }
        }
    }
}