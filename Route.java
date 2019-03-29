package Simulation;

import java.util.ArrayList;

public class Route
{
    private int                distance;
    private int                time;
    private ArrayList<Command> commands;

    Route(int distance, int time, ArrayList<Command> commands)
    {
        this.distance = distance;
        this.time = time;
        this.commands = commands;
    }

    Route() {}

    public int getDistance()
    {
        return distance;
    }

    public int getTime()
    {
        return time;
    }

    public ArrayList<Command> getCommands()
    {
        return commands;
    }

    public void setDistance(int distance)
    {
        this.distance = distance;
    }

    public void setTime(int time)
    {
        this.time = time;
    }

    public void setCommands(ArrayList<Command> commands)
    {
        this.commands = commands;
    }

    public void addCommand(Command command)
    {
        commands.add(command);
    }
}
