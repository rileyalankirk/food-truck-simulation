package Simulation;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class AddressIO
{
    public static PriorityQueue<Address> readAddresses(String filename)
    {
        PriorityQueue<Address> addresses = new PriorityQueue<>();
        try
        {
            Scanner scanner = new Scanner(new File(filename));
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String[] values = line.split(" "); // [0]: house number [1]: direction [2]: street number [3]: time
                addresses.add(new Address(Integer.parseInt(values[0]), values[1].equals("East"), Integer.parseInt(values[2]), Integer.parseInt(values[3]));
            }
        }
        catch (IOException e)
        {
            System.out.println("IOException encountered: " + e);
        }

        return addresses;
    }

    public static void writeAddresses(String filename, int numberAddresses)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)));
            for (int i = 0; i < numberAddresses; i++)
                writer.write((new Address(numberAddresses)).toString() + "\n");
            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            System.out.println("IOException encountered: " + e);
        }
    }


}
