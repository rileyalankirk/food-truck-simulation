package Simulation;

import java.io.IOException;

//Abdullah Alramyan

public interface
truckGenerator {

     void registerObserver(Observer display);

    void removeObserver(Observer display);


    void notifyObserver(Order truckCurLocation) throws IOException;
}
