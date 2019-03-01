import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Neighborhood
{
    private String[][] grid;

    public Neighborhood()
    {
        grid = new String[201][201];
    }

    public void generateNeighborhood(String filename)
    {
        // Location of houses, represented as "o"; crossroads as "-"
        for (int x = 0; x < 201; x++)
        {
            for (int y = 0; y < 201; y++)
            {
                if (x % 10 == 0)
                {
                    if (y % 10 == 0)
                        grid[x][y] = "- ";
                    else
                        grid[x][y] = "o ";
                }
                if (x % 10 != 0)
                {
                    if (y % 10 == 0)
                        grid[x][y] = "o ";
                    else
                        grid[x][y] = "  ";
                }
            }
        }

        // Read file, create new address and add location to neighborhood map
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            String address[];
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                address = line.split(" ");
                int houseNum = Integer.parseInt(address[0]);
                int streetNum = Integer.parseInt(address[2]);
                if (address[1].compareTo("East") == 0)
                    add(new Address(houseNum,true, streetNum));
                else
                    add(new Address(houseNum,false, streetNum));
            }
        }
        catch (IOException e) {

        }

        // Location of the distribution center, represented as "&"
        grid[91][90] = "& ";

    }

    public void add(Address ad)
    {
        // Location of an address, represented as "x"
        if (!ad.isDirection())
            grid[ad.houseNum/10][ad.streetNum*10] = "x ";
        else
            grid[ad.streetNum*10][ad.houseNum/10] = "x ";
    }

    public void printNeighborhood()
    {
        // Print neighborhood
        for (int x = 0; x < 201; x++) {
            for (int y = 0; y < 201; y++)
                System.out.print(grid[x][y]);
            System.out.println();
        }
    }

}
