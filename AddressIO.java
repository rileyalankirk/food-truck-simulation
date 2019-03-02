package Simulation;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class AddressIO
{
    public static final String FILE = "Simulation/AddressList100.txt";

    public static PriorityQueue<Address> readAddresses(String filename)
    {
        PriorityQueue<Address> priorityQueue = new PriorityQueue<>();
        try
        {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            String value[];
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                //System.out.println(line);//check line
                value = line.split(" ");
                int houseNum = Integer.parseInt(value[0]);
                int streetNum = Integer.parseInt(value[2]);
                //System.out.println(value[0] + "|" + value[1] + "|" + value[2]);//check line split
                priorityQueue.add(new Address(houseNum, value[1].compareTo("East") == 0, streetNum));
            }
        }
        catch (IOException e)
        {
            System.out.println("IOException encountered: " + e);
        }
        return priorityQueue;
    }

    public static void writeAddresses(String filename, int numberAddresses)
    {
        try {
            File file = new File(filename);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < numberAddresses; i++) {
                Address address = new Address();
                writer.write(address.toString() + "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("IOException encountered: " + e);
        }
    }
}
