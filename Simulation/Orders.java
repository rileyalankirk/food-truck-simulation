package Simulation;

import java.util.PriorityQueue;

public final class Orders {

    private static Orders INSTANCE = new Orders();
    private static PriorityQueue<Order> orders;

    private Orders() {}

    public static Orders getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new Orders();
        }

        return INSTANCE;
    }

    public PriorityQueue<Order> getOrders()
    {
        if (orders != null)
            return orders;
        return new PriorityQueue<Order>();
    }

    public void updateOrders(PriorityQueue<Order> newOrders)
    {
        orders = newOrders;
    }

    public Order poll()
    {
        return orders.poll();
    }
}
