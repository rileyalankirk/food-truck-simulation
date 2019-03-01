package Simulation;


import java.io.File;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class ReadAddress
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
                if (value[1].compareTo("East") == 0)
                    priorityQueue.add(new Address(houseNum,true,streetNum));
                else
                    priorityQueue.add(new Address(houseNum,false,streetNum));
            }
        }
        catch (IOException e){
            System.out.println("IOException encountered: " + e);
        }
        return priorityQueue;
    }

    public static void main(String[] args)
    {
       /* Address one = new Address(110,true,1);
        Address two = new Address(220,true,2);
        Address three = new Address(330,false,3);
        Address four = new Address(330,true,3);
        PriorityQueue<Address> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(one);priorityQueue.add(two);priorityQueue.add(three);priorityQueue.add(four);
        for(int x = 0; x < 4; x++){
            System.out.println(priorityQueue.poll());
        }*///TEST COMPARABLE

        PriorityQueue<Address> priorityQueue = readAddresses(FILE);

        while(!priorityQueue.isEmpty())
            System.out.println(priorityQueue.poll());

    }
}
