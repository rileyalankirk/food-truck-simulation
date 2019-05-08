/*
 * The Command class has a length and a Direction. Commands will be used in Simulation to move the truck. The length can
 * represent a distance when the direction is one that can be moved in, but when the direction is NONE, length represents
 * a length of time.
 * Authors: Originally written by Riley.
 */


package Simulation;

public class Command
{
    private int       length;
    private Direction direction;

    // Create an command with an order and a command type
    Command(int length, Direction direction)
    {
        this.length = length;
        this.direction = direction;
    }

    // Create a command that moves nowhere
    Command()
    {
        this(0, null);
    }

    public int getLength()
    {
        return length;
    }

    public Direction getDirection()
    {
        return direction;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }

    @Override
    public String toString()
    {
        return "{length: " + length + "; direction: " + direction +  '}';
    }
}