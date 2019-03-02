package Simulation;


import java.util.Iterator;
import java.util.PriorityQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;


public class NeighborhoodMap
{
    static final int HEIGHT = 782, WIDTH = 761;
    static final int MARKER_SIZE = 5;
    static final int BLOCK_WIDTH = 40;

    public static void drawNeighborhood(String filename, PriorityQueue<Address> addresses)
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
                g.fillRect(9*BLOCK_WIDTH - 2, 9*BLOCK_WIDTH + 4, MARKER_SIZE, MARKER_SIZE);

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
        PriorityQueue<Address> addresses = AddressIO.readAddresses(filename);
        drawNeighborhood(filename, addresses);
    }
}

