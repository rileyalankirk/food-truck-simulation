/*
 * The Route interface is the implementation of a Strategy design pattern within Simulation. The calculateRoute method
 * is passedd a PriorityQueue of orders and the distribution center's location.
 */


package Simulation;

import java.awt.Point;
import java.util.PriorityQueue;

public interface RouteMethod
{
    Route calculateRoute(PriorityQueue<Order> orders, Point distrCenter);
}