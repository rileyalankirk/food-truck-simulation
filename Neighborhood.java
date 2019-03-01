import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Neighborhood
{
    private String[][] grid;
    private LinkedList<Address> addresses = new LinkedList<>();

    public Neighborhood()
    {
        grid = new String[201][201];
    }

    public void generateNeighborhood(String filename)
    {
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

        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            String value[];
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                value = line.split(" ");
                int houseNum = Integer.parseInt(value[0]);
                int streetNum = Integer.parseInt(value[2]);
                if (value[1].compareTo("East") == 0)
                    add(new Address(houseNum,true,streetNum));
                else
                    add(new Address(houseNum,false,streetNum));
            }
        }
        catch (IOException e) {

        }

    }

    public void add(Address ad)
    {
        if (!ad.isDirection())
            grid[ad.houseNum/10][ad.streetNum*10] = "x ";
        else
            grid[ad.streetNum*10][ad.houseNum/10] = "x ";
    }

    public void printNeighborhood()
    {
        for (int x = 0; x < 201; x++) {
            for (int y = 0; y < 201; y++)
                System.out.print(grid[x][y]);
            System.out.println();
        }
    }

}
