/*
 * Authors: Originally written by Abdullah.
 */

package Simulation;


import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;


public class display implements Observer {



    @Override
    public void update(Order address) throws IOException {
            System.out.println(address.getAddress());
            try {
                Thread.sleep(300);
            } catch (Exception ex) {
            }
    }
}

