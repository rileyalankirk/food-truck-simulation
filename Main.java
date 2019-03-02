package Simulation;


import java.util.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {


    public static void main(String[] args)
    {
        // Write 100 random addresses to a file
        AddressIO.writeAddresses(AddressIO.FILE, 100);

        // Read the addresses from the file and place them in a PriorityQueue
        PriorityQueue<Address> addresses = AddressIO.readAddresses(AddressIO.FILE);

        // Draw the neighborhood with the addresses and distribution center shown
        Neighborhood.drawNeighborhood(AddressIO.FILE, addresses);
        Neighborhood neighborhood = new Neighborhood();
        neighborhood.generateNeighborhood(addresses);
        neighborhood.printNeighborhood();
    }
}

