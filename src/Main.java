import java.util.PriorityQueue;

public class Main {


    public static void main(String[] args)
    {
        // Write 100 random addresses to a file
        AddressIO.writeAddresses(AddressIO.FILE, 100);


        // Read the addresses from the file and place them in a PriorityQueue
        PriorityQueue<Address> addresses = AddressIO.readAddresses(AddressIO.FILE);

        // Draw the neighborhood with the addresses and distribution center shown
        Neighborhood.drawNeighborhood(addresses);
        Neighborhood neighborhood = new Neighborhood();
        neighborhood.generateNeighborhood(addresses);
        neighborhood.printNeighborhood();
    }
}

