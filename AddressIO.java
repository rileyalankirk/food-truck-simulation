package Simulation;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class AddressIO
{
    public static final int NUM_ADDRESSES = 100;

    public static PriorityQueue<Address> readAddresses(String filename)
    {
        PriorityQueue<Address> addresses = new PriorityQueue<>();
        try
        {
            Scanner scanner = new Scanner(new File(filename));
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String[] values = line.split(" ");
                int houseNum = Integer.parseInt(values[0]);
                int streetNum = Integer.parseInt(values[2]);
                int offset = values[3].length() % 2;
                int hours = (Integer.parseInt(values[3].substring(0, 1 + offset)) + ((values[3].substring(4 + offset, 6 + offset).equals("PM")) ? 12 : 0)) * 100;
                int minutes = Integer.parseInt(values[3].substring(2 + offset,  4 + offset));
                int time = hours + minutes;

                /*
                value[1].compareTo("East") works fine but useless, (method of camparing string object)
                but instead we use value[1].equals("East")
                compareTo will be called once calling priorityQueue.add method
                and it will call the overrided method campareTo. since priorityqueue is accepting
                instance of class address
                */

                addresses.add(new Address(houseNum, values[1].equals("East"), streetNum, time));
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
                writer.write((new Address(NUM_ADDRESSES)).toString() + "\n");
            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            System.out.println("IOException encountered: " + e);
        }
    }


}
