/*
 * A Route that has no U-turns in it.
 * Authors: Riley originally wrote, but Kimberly fixed some bugs.
 */


package Simulation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

public class NoUturnRoute implements RouteMethod
{
    private static Route route;
    private static Truck truck;

    @Override
    public Route calculateRoute(PriorityQueue<Order> orders, Point distrCenter)
    {
        // Make sure we have orders
        if (orders.isEmpty())
            generalCrash("We have no orders and were fired.");

        route = new Route(0, 0, new ArrayList<>());
        truck = new Truck(new Address((int) distrCenter.getX(), (int) distrCenter.getY()));

        // Set truck's initial direction
        if (orders.peek().getAddress().getY() < truck.getAddress().getY())
            truck.setDirection(Direction.NORTH);
        else
            truck.setDirection(Direction.SOUTH);

        Iterator<Order> iterator = orders.iterator();
        while (iterator.hasNext()) {
            Order currentOrder = iterator.next();
            int curX = truck.getAddress().getX();
            int curY = truck.getAddress().getY();
            int destX = currentOrder.getAddress().getX();
            int destY = currentOrder.getAddress().getY();

            int vertDel = curY - destY; // Vertical delta
            int horDel = curX - destX;  // Horizontal delta

            // On vertical street
            if (curY % 100 != 0) {
                // If the truck is moving in the correct direction, move that way
                if (truck.getDirection() == Direction.NORTH && vertDel >= 0)
                    noUturnVertical(Direction.NORTH, horDel, destX, destY);
                else if (truck.getDirection() == Direction.SOUTH && vertDel < 0)
                    noUturnVertical(Direction.SOUTH, horDel, destX, destY);
                    // Else if the truck needs to avoid a U-turn, handle U-turn
                else
                    handleUturn(truck.getDirection(), destX, destY);
            }
            // On horizontal street
            else if (curX % 100 != 0) {
                // If the truck is moving in the correct direction, move that way
                if (truck.getDirection() == Direction.EAST && horDel <= 0)
                    noUturnHorizontal(Direction.EAST, horDel, destX, destY);
                else if (truck.getDirection() == Direction.WEST && horDel > 0)
                    noUturnHorizontal(Direction.WEST, horDel, destX, destY);
                    // Else if the truck needs to avoid a U-turn, handle U-turn
                else
                    handleUturn(truck.getDirection(), destX, destY);
            } else
                cornerCrash();

            // Each stop takes 5 units of time
            route.addCommand(new Command(5, Direction.NONE));
        }

        return route;
    }



    // Helper functions that call move functions to create the route
    private void noUturnVertical(Direction direction, int horDel, int destX, int destY)
    {
        // If destination is on a horizontal street or not on the truck's street, move vertically to the closest street
        if (destY % 100 == 0 || (destY % 100 != 0 && horDel != 0))
        {
            moveNearestBlockToDestination(direction, truck.getAddress().getX(), truck.getAddress().getY(), destX, destY);
            // We make a turn depending on the shortest route
            if ((direction == Direction.SOUTH && horDel > 0) || direction == Direction.NORTH && horDel < 0)
            {
                route.setTime(route.getTime() + 2);
                if (direction == Direction.SOUTH)
                    direction = Direction.WEST;
                else
                    direction = Direction.EAST;
            }
            else
            {
                route.setTime(route.getTime() + 4);
                if (direction == Direction.SOUTH)
                    direction = Direction.EAST;
                else
                    direction = Direction.WEST;
            }
        }
        // If the destination is on a vertical street that the truck is not on, move to it
        if (destY % 100 != 0 && horDel != 0)
        {
            int curY = truck.getAddress().getY();
            if (curY < destY )
            moveNearestBlockToDestination(direction, truck.getAddress().getX(), curY, destX, destY);
            // We make a turn depending on the shortest route
            if ((direction == Direction.EAST && curY > destY) || direction == Direction.WEST && curY < destY)
                route.setTime(route.getTime() + 2);
            else
                route.setTime(route.getTime() + 4);
        }
        // This is either the final move of the above conditions or the truck is already on the same street and can move
        // directly to the destination
        moveDestination(truck.getAddress().getX(), truck.getAddress().getY(), destX, destY);
    }

    private void noUturnHorizontal(Direction direction, int vertDel, int destX, int destY)
    {
        // If destination is on a vertical street or not on the truck's street, move horizontally to the closest street
        if (destX % 100 == 0 || (destX % 100 != 0 && vertDel != 0))
        {
            moveNearestBlockToDestination(direction, truck.getAddress().getX(), truck.getAddress().getY(), destX, destY);
            // We make a turn depending on the shortest route
            if ((direction == Direction.EAST && vertDel < 0) || direction == Direction.WEST && vertDel > 0)
            {
                route.setTime(route.getTime() + 2);
                if (direction == Direction.EAST)
                    direction = Direction.SOUTH;
                else
                    direction = Direction.NORTH;
            }
            else
            {
                route.setTime(route.getTime() + 4);
                if (direction == Direction.EAST)
                    direction = Direction.NORTH;
                else
                    direction = Direction.SOUTH;
            }
        }
        // If the destination is on a horizontal street that the truck is not on, move to it
        if (destX % 100 != 0 && vertDel != 0)
        {
            int curX = truck.getAddress().getX();
            moveNearestBlockToDestination(direction, curX, truck.getAddress().getY(), destX, destY);
            // We make a turn depending on the shortest route
            if ((direction == Direction.SOUTH && curX > destX) || direction == Direction.NORTH && curX < destX)
                route.setTime(route.getTime() + 2);
            else
                route.setTime(route.getTime() + 4);
        }
        // This is either the final move of the above conditions or the truck is already on the same street and can move
        // directly to the destination
        moveDestination(truck.getAddress().getX(), truck.getAddress().getY(), destX, destY);
    }

    private void handleUturn(Direction direction, int destX, int destY)
    {
        // Facing wrong direction, so need to loop around
        if (direction != null)
        {
            // Move to end of the current block and take a right to avoid a U-turn
            moveToEndOfBlock(direction, truck.getAddress().getX(), truck.getAddress().getY());
            moveRightOneBlock(direction, truck.getAddress().getX(), truck.getAddress().getY());
            truck.setDirection(getOppositeDirection(direction));
        }
        else
            generalCrash("Tried to handle a U-turn by becoming nothing");

        // We take a right before moving to the destination; takes 2 units of time
        route.setTime(route.getTime() + 2);
        // Now that the U-turn has been avoided, move to the destination
        if (direction == Direction.NORTH || direction == Direction.SOUTH)
            noUturnVertical(truck.getDirection(), truck.getAddress().getX() - destX, destX, destY);
        else
            noUturnHorizontal(truck.getDirection(), truck.getAddress().getY() - destY, destX, destY);
    }



    // Move functions
    private void moveDestination(int curX, int curY, int destX, int destY)
    {
        Direction direction = null;
        int       distance  = Math.abs(curX - destX) + Math.abs(curY - destY);

        // If the move is vertical, create the appropriate command
        if (destX % 100 == 0)
        {
            // Move North
            if ((curY - destY) > 0)
                direction = Direction.NORTH;
            // Move South
            else
                direction = Direction.SOUTH;
        }
        // If the move is horizontal, create the appropriate command
        else if (destY % 100 == 0)
        {
            // Move West
            if ((curX - destX) > 0)
                direction = Direction.WEST;
            // Move East
            else
                direction = Direction.EAST;
        }
        // Crashing into corner
        else
            cornerCrash();
        // Update truck and add the command to the route; handle turn times in the functions that call this function
        updateTruckAndRoute(direction, new Address(destX, destY), distance / 10, 0);
    }

    // Move in the direction of the direction argument to the nearest block
    private void moveNearestBlockToDestination(Direction direction, int curX, int curY, int destX, int destY)
    {
        int distance = 0;

        if (direction == Direction.NORTH || direction == Direction.SOUTH)
        {
            destY = 100 * (destY / 100) + ((destY % 100 >= 50) ? 100 : 0);
            distance = Math.abs(curY - destY);
            // We only want to move vertically
            destX = curX;
        }
        else if (direction == Direction.EAST || direction == Direction.WEST)
        {
            destX = 100 * (destX / 100) + ((destX % 100 >= 50) ? 100 : 0);
            distance = Math.abs(curX - destX);
            // We only want to move horizontally
            destY = curY;
        }

        // Update truck and add the command to the route; handle turn times in the functions that call this function
        updateTruckAndRoute(direction, new Address(destX, destY), distance / 10, 0);
    }

    private void moveToEndOfBlock(Direction direction, int curX, int curY)
    {
        int distance = 0;
        int destX    = 0;
        int destY    = 0;

        if (direction == Direction.NORTH || direction == Direction.SOUTH)
        {
            destY = 100 * (curY / 100) + ((direction == Direction.SOUTH) ? 100 : 0);
            distance = Math.abs(curY - destY);
            // We only want to move vertically
            destX = curX;
        }
        else if (direction == Direction.EAST || direction == Direction.WEST)
        {
            destX = 100 * (curX / 100) + ((direction == Direction.EAST) ? 100 : 0);
            distance = Math.abs(curX - destX);
            // We only want to move horizontally
            destY = curY;
        }

        // Update truck and add the command to the route; time is 0 since no turn was made
        updateTruckAndRoute(direction, new Address(destX, destY), distance / 10, 0);
    }

    private void moveRightOneBlock(Direction direction, int curX, int curY)
    {
        // Turn right and travel one block
        direction = getDirectionToRight(direction);
        if (direction == Direction.NORTH)
            curY -= 100;
        else if (direction == Direction.SOUTH)
            curY += 100;
        else if (direction == Direction.EAST)
            curX += 100;
        else
            curX -= 100;

        // Update truck and add the command to the route; time is 2 since one right turn is taken
        updateTruckAndRoute(direction, new Address(curX, curY), 10, 2);
    }



    // Other helper functions
    private static void updateTruckAndRoute(Direction direction, Address destination, int distance, int time)
    {
        // Update truck and add the command to the route
        truck.setDirection(direction);
        truck.setAddress(destination);
        route.addCommand(new Command(distance, direction));
        route.setDistance(route.getDistance() + distance);
        route.setTime(route.getTime() + distance + time);
    }

    private static Direction getOppositeDirection(Direction direction)
    {
        if (direction == Direction.NORTH)
            return Direction.SOUTH;
        else if (direction == Direction.SOUTH)
            return Direction.NORTH;
        else if (direction == Direction.EAST)
            return Direction.WEST;
        return Direction.EAST;
    }

    private static Direction getDirectionToRight(Direction direction)
    {
        if (direction == Direction.NORTH)
            return Direction.EAST;
        else if (direction == Direction.SOUTH)
            return Direction.WEST;
        else if (direction == Direction.EAST)
            return Direction.SOUTH;
        return Direction.NORTH;
    }

    private static void generalCrash(String failure)
    {
        System.out.println("NoUturnRoute Failure: " + failure + "\nExiting program...");
        System.exit(1);
    }

    private static void cornerCrash()
    {
        generalCrash("Truck crashed into corner of a street");
    }
}