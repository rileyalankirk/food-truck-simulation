import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;



public class AddressIO
{
    public static final String FILE = "./AddressList100.txt";

    public static PriorityQueue<Address> readAddresses(String filename)
    {
        PriorityQueue<Address> priorityQueue = new PriorityQueue<>();
        try
        {
            Scanner scanner = new Scanner(new File(filename));
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String value[] = line.split(" ");
                int houseNum = Integer.parseInt(value[0]);
                int streetNum = Integer.parseInt(value[2]);
                String time = value[3];
                
                /* 
                value[1].compareTo("East") works fine but useless, (method of camparing string object)
                but instead we use value[1].equals("East")
                compareTo will be called once calling priorityQueue.add method
                and it will call the overrided method campareTo. since priorityqueue is accepting
                instance of class address
                */
                
                Address newAdd = new Address(houseNum, value[1].equals("East"), streetNum, time);

                priorityQueue.add(newAdd);
            }
        }
        catch (IOException e)
        {
            System.out.println("IOException encountered: " + e);
        }
        
        java.util.Iterator itr = priorityQueue.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
            
        }
        
                    
        return priorityQueue;
    }

    public static void writeAddresses(String filename, int numberAddresses)
    {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)));
            for (int i = 0; i < numberAddresses; i++)
            {
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
