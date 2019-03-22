package Simulation;


import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Neighborhood
{
    static final int MAP_HEIGHT = 900;
    static final int MAP_WIDTH = 900;
    static final int MARKER_SIZE = 5;
    static final int BLOCK_WIDTH = 45;
    static final double RATIO_ADDRESSES_TO_MAP = MAP_WIDTH / 2000.0;
    public static ArrayList<Address> instructions = new ArrayList<>();

    private String[][] grid;
    private static double routeLength = 0;
    private static short truckFacing = 0; // direction truck is facing -- 0: any, 1: north, 2: east, 3: south, 4: west

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
                int time = Integer.parseInt(address[3]);
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
                g.setColor(Color.BLUE);
                double x = Address.DISTRIBUTION_STREETNUM * 100.0;
                double y = Address.DISTRIBUTION_HOUSENUM;
                g.fillRect((int)(RATIO_ADDRESSES_TO_MAP*(x - MARKER_SIZE)), (int)(RATIO_ADDRESSES_TO_MAP*(y - MARKER_SIZE)), MARKER_SIZE, MARKER_SIZE);

                // draw deliveries
                g.setColor(Color.RED);
                Iterator<Address> iterator = addresses.iterator();
                while (iterator.hasNext())
                {
                    Address address = iterator.next();
                    x = (address.isDirection()) ? address.getHouseNum() : address.getStreetNum() * 100;
                    y = (!address.isDirection()) ? address.getHouseNum() : address.getStreetNum() * 100;
                    g.fillOval((int)(RATIO_ADDRESSES_TO_MAP*(x - MARKER_SIZE)), (int)(RATIO_ADDRESSES_TO_MAP*(y - MARKER_SIZE)), MARKER_SIZE, MARKER_SIZE);

                }
            }
        };
        map.getContentPane().add(canvas);
        map.repaint();

        map.setTitle("Neighborhood");
        map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        map.setSize(MAP_WIDTH - 44, MAP_HEIGHT - 10);
        map.setResizable(false);
        map.setLocationRelativeTo(null); // center the map
        map.setVisible(true);
    }

    public static void drawNeighborhood(String filename)
    {
        drawNeighborhood(AddressIO.readAddresses(filename));
    }

    public double getRouteLength()
    {
        return routeLength;
    }

    public void addRoute(Collection<Address> addresses) throws UTurnException
    {
        // Reset the static routeLength class attribute
        routeLength = 0;

        // start at the distribution center at 10:00 AM
        Address truckLocation = new Address(Address.DISTRIBUTION_HOUSENUM, Address.SOUTH, Address.DISTRIBUTION_STREETNUM, 1000);
        Iterator<Address> iterator = addresses.iterator();
        while (iterator.hasNext())
        {
            Address address = iterator.next();
            int time = address.getTime();
            while (!(address.equals(truckLocation)))
            {
                int x1 = (address.isDirection()) ? address.getHouseNum() : address.getStreetNum() * 100;
                int y1 = (!address.isDirection()) ? address.getHouseNum() : address.getStreetNum() * 100;
                int x2 = (truckLocation.isDirection()) ? truckLocation.getHouseNum() : truckLocation.getStreetNum() * 100;
                int y2 = (!truckLocation.isDirection()) ? truckLocation.getHouseNum() : truckLocation.getStreetNum() * 100;

                boolean sameDirection = address.isDirection() == truckLocation.isDirection(); // Same direction streets
                boolean sameStreetNum = address.getStreetNum() == truckLocation.getStreetNum(); // Same street number
                boolean truckOnSouth = x2 % 100 == 0; // The truck is on a southern street
                boolean truckOnEast = y2 % 100 == 0; // The truck is on an eastern street

                // The truck can get to the address in two or less moves (excluding the moves necessary to avoid U-turns)
                if ((truckOnEast && truckOnSouth) || !sameDirection || sameStreetNum)
                {
                    if (y1 != y2 && (!truckOnEast || truckOnSouth) && ((!truckOnSouth || sameStreetNum) || address.isDirection()))
                    {
                        Address handleUturn = handleUturn(x2, y2, x2, y1, x1, y1, time);
                        if (!handleUturn.equals(new Address(-10, -10, time)))
                        {
                            truckLocation = handleUturn;
                            continue;
                        }
                        truckLocation = new Address(x2, y1, time);
                        routeLength += Math.abs(y1 - y2);
                    }
                    else if (x1 != x2)
                    {
                        Address handleUturn = handleUturn(x2, y2, x1, y2, x1, y1, time);
                        if (!handleUturn.equals(new Address(-10, -10, time)))
                        {
                            truckLocation = handleUturn;
                            continue;
                        }
                        truckLocation = new Address(x1, y2, time);
                        routeLength += Math.abs(x1 - x2);
                    }
                }
                /*
                 * If the truck needs to make more than two moves, it must move to a corner first (excluding the moves
                 * necessary to avoid U-turns)
                 */
                else
                {
                    if (truckOnEast)
                    {
                        int corner = x1 - (x1 % 100); // corner location
                        if (Math.abs(corner + 100 - x2) < Math.abs(corner - x2))
                        {
                            Address handleUturn = handleUturn(x2, y2, corner + 100, y2, x1, y1, time);
                            if (!handleUturn.equals(new Address(-10, -10, time)))
                            {
                                truckLocation = handleUturn;
                                continue;
                            }
                            truckLocation = new Address(corner + 100, y2, time);
                            routeLength += Math.abs(corner + 100 - x2);
                        }
                        else
                        {
                            Address handleUturn = handleUturn(x2, y2, corner, y2, x1, y1, time);
                            if (!handleUturn.equals(new Address(-10, -10, time)))
                            {
                                truckLocation = handleUturn;
                                continue;
                            }
                            truckLocation = new Address(corner, y2, time);
                            routeLength += Math.abs(corner - x2);
                        }
                    }
                    else
                    {
                        int corner = y1 - (y1 % 100); // corner location
                        if (Math.abs(corner + 100 - y2) < Math.abs(corner - y2))
                        {
                            Address handleUturn = handleUturn(x2, y2, x2, corner + 100, x1, y1, time);
                            if (!handleUturn.equals(new Address(-10, -10, time)))
                            {
                                truckLocation = handleUturn;
                                continue;
                            }
                            truckLocation = new Address(x2, corner + 100, time);
                            routeLength += Math.abs(corner + 100 - y2);
                        }
                        else
                        {
                            Address handleUturn = handleUturn(x2, y2, x2, corner, x1, y1, time);
                            if (!handleUturn.equals(new Address(-10, -10, time)))
                            {
                                truckLocation = handleUturn;
                                continue;
                            }
                            truckLocation = new Address(x2, corner, time);
                            routeLength += Math.abs(corner - y2);
                        }
                    }
                }
                instructions.add(truckLocation);
            }
        }
        routeLength /= 100.0; // to increase accuracy of measurement, we divide after adding up all of the values
    }

    // Returns the new address of the truck if a U-turn had to be avoided, Address(-10, -10) otherwise
    private Address handleUturn(int truckX, int truckY, int destX, int destY, int finalX, int finalY, int time) throws UTurnException
    {
        boolean horDelta = truckX != destX; // Has horizontal change
        boolean verDelta = truckY != destY; // Has vertical change

        // Handle bad arguments
        if ((!horDelta && !verDelta) || (horDelta && verDelta)) throw new UTurnException("Bad arguments");

        // Truck is in a position to go in any direction and thus does not need to avoid a U-turn
        if (truckFacing == 0) return new Address(-10, -10, time);

        // Moving north or south
        if (!horDelta)
        {
            // Moving north
            if (destY - truckY > 0)
            {
                // Truck is facing the same direction it is moving
                if (truckFacing == 1) return new Address(-10, -10, time);
            }
            // Moving south
            else
            {
                // Truck is facing the same direction it is moving
                if (truckFacing == 3) return new Address(-10, -10, time);
            }
        }
        // Moving east or west
        else
        {
            // Moving east
            if (destX - truckX < 0)
            {
                // Truck is facing the same direction it is moving
                if (truckFacing == 2) return new Address(-10, -10, time);
            }
            // Moving west
            else
            {
                // Truck is facing the same direction it is moving
                if (truckFacing == 4) return new Address(-10, -10, time);

            }
        }

        // Truck must avoid a U-turn
        Address ret; // New address of truck after move
        if (truckX % 100 == 0 && truckY % 100 == 0) // Truck is on a corner
        {
            if (truckFacing % 2 == 0 && horDelta)
            {
                // Move north or south a block, whichever is closer to the final destination
                routeLength += 1;
                ret = new Address(truckX, truckY + ((finalY < truckY) ? -100 : 100), time);
            }
            else if (truckFacing % 2 == 1 && verDelta)
            {
                // Move east or west a block, whichever is closer to the final destination
                routeLength += 1;
                ret = new Address(truckX + ((finalX < truckX) ? -100 : 100), truckY, time);
            }
            else
            {
                return new Address(-10, -10, time); // There is no U-turn to be accounted for
            }
        }
        else // Truck will go to the nearest corner
        {
            if (truckFacing % 2 == 1) // Facing north or south
            {
                int corner = truckY - (truckY % 100); // nearest corner location
                if (truckFacing == 3) corner += 100; // move south a block so the truck moves south
                routeLength += Math.abs(corner - truckY);
                ret = new Address(truckX, corner, time);
            }
            else // Facing east or west
            {
                int corner = truckX - (truckX % 100); // nearest corner location
                if (truckFacing == 2) corner += 100; // move east a block so the truck moves east
                routeLength += Math.abs(corner - truckX);
                ret = new Address(corner, truckY, time);
            }
        }
        instructions.add(ret);
        return ret;
    }
}

class UTurnException extends Exception
{
    UTurnException(String message)
    {
        super(message);
    }

    UTurnException()
    {
        super();
    }
}
