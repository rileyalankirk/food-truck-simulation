import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Neighborhood
{
    static final int HEIGHT = 782, WIDTH = 761;
    static final int MARKER_SIZE = 5;
    static final int BLOCK_WIDTH = 40;

    private String[][] grid;

    public Neighborhood()
    {
        grid = new String[201][201];
    }

    public void generateNeighborhood()
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

        // Location of the distribution center, represented as "&"
        grid[91][90] = "& ";
    }


    public void generateNeighborhood(String filename)
    {
        generateNeighborhood();

        // Read file, create new address and add location to neighborhood map
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String address[] = line.split(" ");
                int houseNum = Integer.parseInt(address[0]);
                int streetNum = Integer.parseInt(address[2]);
                String time = address[3];
                add(new Address(houseNum,address[1].compareTo("East") == 0, streetNum, time));
            }
        }
        catch (IOException e)
        {
            System.out.println("IOException encountered: " + e);
        }

        // Location of the distribution center, represented as "&"
        grid[91][90] = "& ";

    }

    public void generateNeighborhood(PriorityQueue<Address> addresses)
    {
        generateNeighborhood();

        // Add locations to neighborhood map
        Iterator<Address> iterator = addresses.iterator();
        while (iterator.hasNext())
            add(iterator.next());

        // Location of the distribution center, represented as "&"
        grid[91][90] = "& ";

    }

    public void add(Address ad)
    {
        // Location of an address, represented as "x"
        if (!ad.isDirection())
            grid[ad.getHouseNum()/10][ad.getStreetNum()*10] = "x ";
        else
            grid[ad.getStreetNum()*10][ad.getHouseNum()/10] = "x ";
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

    public static void drawNeighborhood(PriorityQueue<Address> addresses)
    {
        JFrame map = new JFrame();
        JPanel canvas = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLUE);
                // draw streets
                for (int x = 0; x < 19; x++)
                    for (int y = 0; y < 19; y++)
                        g.drawRect(BLOCK_WIDTH * x, BLOCK_WIDTH * y, BLOCK_WIDTH, BLOCK_WIDTH);
                // draw distribution center
                g.setColor(Color.GREEN);
                g.fillRect(9*BLOCK_WIDTH - 2, 9*BLOCK_WIDTH + 2, MARKER_SIZE, MARKER_SIZE);

                // draw deliveries
                g.setColor(Color.RED);
                Iterator<Address> iterator = addresses.iterator();
                while (iterator.hasNext())
                {
                    Address address = iterator.next();
                   
                    double x = (address.isDirection()) ? address.getHouseNum() / 100.0 : address.getStreetNum();
                    double y = (!address.isDirection()) ? address.getHouseNum() / 100.0 : address.getStreetNum();
                   
                    g.fillOval(((int) x)*BLOCK_WIDTH - 2 + (int)(40.0 * (x % 1)), ((int) y)*BLOCK_WIDTH - 2 + (int)(40.0 * (y % 1)), MARKER_SIZE, MARKER_SIZE);
                }
            }
        };
        map.getContentPane().add(canvas);
        map.repaint();

        map.setTitle("Map");
        map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        map.setSize(WIDTH, HEIGHT);
        map.setResizable(false);
        map.setLocationRelativeTo(null); // center on screen
        map.setVisible(true);
    }

    public static void drawNeighborhood(String filename)
    {
        drawNeighborhood(AddressIO.readAddresses(filename));
    }
}
