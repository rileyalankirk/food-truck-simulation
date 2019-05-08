/*
 * Authors: Originally written by Abdullah.
 */

package Simulation;

import java.io.IOException;

public interface
truckGenerator {

     void registerObserver(Observer display);

    void removeObserver(Observer display);


    void notifyObserver(Order truckCurLocation) throws IOException;
}
