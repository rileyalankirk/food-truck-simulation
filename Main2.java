package Simulation;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main2 {
    public static void main(String[] args) {

      try{
        File file = new File("AddressList100.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        for (int i = 0; i < 100; i++) {
            Address address = new Address();
            writer.write(address.toString() + "\n");
        }
        writer.flush();
        writer.close();

    } catch (
    IOException e) {
        e.printStackTrace();
    }
}}
