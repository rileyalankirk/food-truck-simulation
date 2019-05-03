package Simulation;

import java.io.IOException;

//Abdullah Alramyan
public interface Observer {


    void update(Order order) throws IOException;
}

