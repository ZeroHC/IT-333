package browsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HistoryHandler
{
    private File file;
    private String[] browserHistories;

    public HistoryHandler(String historyFile)
    {
        file = new File(historyFile);
        file.getParentFile().mkdir();
        browserHistories = readFile();
    }

    private String[] readFile()
    {
        ArrayList<String> lines = new ArrayList<>();


        try (Scanner scanner = new Scanner(file))
        {
            while (scanner.hasNextLine())
            {
                lines.add(scanner.nextLine());
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error reading from file: " + e.getMessage());
        }

        return lines.toArray(new String[lines.size()]);
    }

    public String[] getBrowserHistories()
    {
        return browserHistories;
    }

    public void historyWriter(List<String> currentSession)
    {
        try (FileWriter fileWriter = new FileWriter(file, true))
        {
            for (String website : currentSession)
            {
                fileWriter.write(website);
            }

            fileWriter.flush();

            fileWriter.close();
        }
        catch (IOException e)
        {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void clearHistory()
    {
        try (FileWriter fileWriter = new FileWriter(file))
        {
            fileWriter.write("");

            fileWriter.flush();

            fileWriter.close();
        }
        catch (IOException e)
        {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}