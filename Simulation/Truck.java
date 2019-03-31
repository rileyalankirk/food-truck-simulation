/*
 * The Truck class keeps track of the truck's location throughout the simulation, whether the truck is moving or not,
 * and the direction that the truck is facing. The Truck can be handled with an Address, double precision coordinates
 * x and y, or both; although the constructors only accept one form or the other. When direction is NONE, we know that
 * the truck is not moving.
 */


package Simulation;


public class Truck
{
    private Address   address;
    private double    x, y;
    private Direction direction;

    Truck(double x, double y,  Direction direction)
    {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    Truck(Address address, boolean moving, Direction direction)
    {
        this.address = address;
        this.direction = direction;
    }

    Truck(double x, double y)
    {
        this(x, y, null);
    }

    Truck(Address address)
    {
        this(address, false, null);
    }

    Truck()
    {
       this(0, 0);
    }

    public Address getAddress()
    {
        return address;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public Direction getDirection()
    {
        return direction;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public void setX(double x)
    {
        this.x = x;
    }


    public void setY(double y)
    {
        this.y = y;
    }


    public void setDirection(Direction direction)

    {
        this.direction = direction;
    }
}
