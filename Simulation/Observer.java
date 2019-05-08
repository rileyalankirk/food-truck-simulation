/*
 * Authors: Originally written by Abdullah.
 */

package Simulation;

import java.io.IOException;


public interface Observer {


    void update(Order order) throws IOException;
}

