/*
 * The Order class is has an address value, time, and list of items ordered. The static values addresses, minTimeDiff,
 * minTime, and maxTime are values set by our config file for different calculations. lastTime allows us to keep track
 * of the last time used for our next random time calculation.
 * Authors: Originally written by Riley.
 */



/*
 * The Order class is has an address value, time, and list of items ordered. The static values addresses, minTimeDiff,
 * minTime, and maxTime are values set by our config file for different calculations. lastTime allows us to keep track
 * of the last time used for our next random time calculation.
 * Authors: Originally written by Riley.
 */


package Simulation;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

public class Order implements Comparable<Order> {
    // Instance variables
    private ArrayList<Food> items;          // The items ordered
    private Address address;        // Location where the order is to be delivered
    private boolean orderDelivered; // Whether or not the order has been delivered
    private int time;           // The time the order was ordered

    // Config variables
    private static int numAddresses = 1; // The number of addresses being simulated
    private static int minTimeDiff = 0;  // The minimum time difference between orders for the simulation
    private static int minTime = 0;      // The minimum time the order can be ordered at
    private static int maxTime = 1;      // The maximum time the order can be ordered at

    // Class variables
    private static int lastTime = 0; // The time the last order was ordered at

    public Order(Address address, int time, ArrayList<Food> items) {
        this.address = address;
        this.time = time;
        this.items = items;
        orderDelivered = false;
    }

    // Creates a random order
    protected Order(Address address) {
        this.address = address;
        time = getRandomTime();


        items = new ArrayList<>();
        orderDelivered = false;

        //TODO
        // Adds random items to the order
        Random rand = new Random();



        for(int k = 0; k< numAddresses;k++) {


            int randomMeat = rand.nextInt((2) + 1) + 1;
            int randomBread = rand.nextInt((1) + 1) + 1;
            int randomCondiment = rand.nextInt((7) + 1) + 1;
            int randomVegetables = rand.nextInt((3) + 1) + 1;


            if (randomBread == 1) {
                items.add(new Food("Bread kind:" + "roll"));

            } else {
                items.add(new Food("Bread kind:" + "warp"));

            }



            if (randomMeat == 1) {
                items.add(new Food("Meat kind:" + "turkey"));

            } else
                items.add(new Food("Meat kind:" + "Ham"));


            if (randomCondiment == 1) {
                items.add(new Food("Condiment kind:" + "mustard:mayo" ));

            } else {
                items.add(new Food("Mayo"));
            }


            if (randomVegetables == 1) {
                items.add(new Food("Vegetables kind:" + "lettuce:tomato" ));

            } else {
                items.add(new Food("Vegetables kind:" + "lettuce"));
            }


        }
    }








    // Creates a random order
    protected Order()
    {
        this(new Address());
    }

    /*
     * Returns a random time in military form in the range minTime to maxTime which gives us (maxTime - minTime) minutes
     * to work with. The random value added to the lastTime is normalized by the time interval, number of addresses, and
     * a minimum time difference between the last time and the new time.
     */
    public int getRandomTime()
    {
        if (lastTime == 0)
        {
            lastTime = minTime;
            return lastTime;
        }
        int timeDelta = (int)(maxTime / 100.0) * 60 + (maxTime % 100) - ((int)(minTime / 100.0) * 60 + (minTime % 100));
        int increment = (new Random()).nextInt((timeDelta / numAddresses) - minTimeDiff) + minTimeDiff; // Normalize the total minutes to the increment needed
        int time = (int)(lastTime / 100.0) * 60 + (lastTime % 100) + increment;    // Convert time sum to minutes
        lastTime = ((int)(time / 60.0) * 100) + time % 60;                         // Convert time to military time
        if (lastTime >= maxTime)
        {
            lastTime = 1000;
            return 1900;
        }
        return lastTime;
    }

    // For normal looking time
    public String timeToString()
    {
        int hours = (int)(time / 100.0);
        int minutes = time % 100;
        return ((hours > 12) ? (hours - 12) : hours) + ":" + ((minutes < 10) ? "0" : "") + minutes + ((time > 1159) ? "PM" : "AM");
    }

    public Address getAddress()
    {
        return address;
    }

    public ArrayList<Food> getItems()
    {
        return items;
    }

    public int getTime()
    {
        return time;
    }

    // Returns the list of items as a csv style string
    public String getItemsAsString()
    {
        if (items.size() == 0)
            return "";
        String s = "";
        //  for (Food f : items)
        s += items + ",";
        return s.substring(0, s.length() - 1); // removes the extra comma
    }

    public boolean wasOrderDelivered()
    {
        return orderDelivered;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public void setItems(ArrayList<Food> items)
    {
        this.items = items;
    }

    public void setTime(int time)
    {
        this.time = time;
    }

    public void setOrderDelivered(boolean orderDelivered)
    {
        this.orderDelivered = orderDelivered;
    }

    public static void setNumAddresses(int numAddresses)
    {
        if (numAddresses > 0)
            Order.numAddresses = numAddresses;
    }

    public static void setMinTimeDiff(int minTimeDiff)
    {
        if (numAddresses >= 0)
            Order.minTimeDiff = minTimeDiff;
    }

    public static void setMinTime(int minTime)
    {
        if (minTime > 0)
            Order.minTime = minTime;
    }

    public static void setMaxTime(int maxTime)
    {
        if (maxTime > minTime && maxTime > 0)
            Order.maxTime = maxTime;
    }

    @Override
    public String toString()
    {
        return address.getHouseNumber() + " " + address.getDirection() + " " + address.getStreetNumber() + " at " + timeToString() + ((items.size() > 0) ? ("; Items ordered: " + getItemsAsString()) : "");
    }

    public String toStringSimple()
    {
        return address + " " + time + " " + ((items.size() > 0) ? getItemsAsString() : "");
    }

    @Override
    public int compareTo(Order o)
    {
        if (getTime() < o.getTime())
            return -1;

        if (getTime() > o.getTime())
            return 1;

        return 0;

        // Comparable by distance from distribution center
        /*
        Address distCenter = new Address(DISTRIBUTION_HOUSENUM, SOUTH, DISTRIBUTION_STREETNUM);
        double distanceToDC = distanceTo(distCenter);
        double distanceToDCfromO = o.distanceTo(distCenter);
        if (distanceToDC < distanceToDCfromO)
            return -1;

        if (distanceToDC > distanceToDCfromO)
            return 1;

        return 0;
         */
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Order)
        {
            Order o = (Order) obj;
            return address.equals(o.getAddress()) && items.equals(o.getItems()) && time == o.time;
        }
        return false;
    }

    public static PriorityQueue<Order> readOrders(String filename)
    {
        PriorityQueue<Order> orders = new PriorityQueue<>();
        try
        {
            Scanner scanner = new Scanner(new File(filename));
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String[] values = line.split(" ");
                String[] address = values[0].split(",");
                Address address2 = new Address(Integer.parseInt(address[0]), Integer.parseInt(address[1]));
                ArrayList<Food> items = new ArrayList<>();
                if (values.length > 2)
                {
                    for (String s : values[2].split(","))
                        items.add(new Food(s));
                }
                orders.add(new Order(address2, Integer.parseInt(values[1]), items));
            }
        }
        catch (IOException e)
        {
            System.out.println("IOException encountered: " + e);
        }
        catch (Exception e)
        {
            System.out.println("Error in creation of Address: " + e);
        }

        return orders;
    }

    public static void writeOrders(String filename, int numberAddresses)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)));
            for (int i = 0; i < numberAddresses; i++)
                writer.write((new Order()).toStringSimple() + "\n");
            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            System.out.println("IOException encountered: " + e);
        }
    }
}