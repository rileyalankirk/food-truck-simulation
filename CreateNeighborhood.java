package Simulation;


public class CreateNeighborhood
{
    public static void main(String[] args)
    {
        Neighborhood neighborhood = new Neighborhood();
        neighborhood.generateNeighborhood("AddressList100.txt");
        neighborhood.printNeighborhood();
    }

}
