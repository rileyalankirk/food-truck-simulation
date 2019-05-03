package Simulation;


import java.util.ArrayList;
import java.util.PriorityQueue;

//Abdullah Alramyan
public class GUI implements Observer {

    private  NeighborhoodFrame neighborhood;


    public GUI(NeighborhoodFrame neighborhood){
        this.neighborhood = neighborhood;
    }

    @Override
    public void update(Order command){

        neighborhood.setVisible(true);

    }
}
