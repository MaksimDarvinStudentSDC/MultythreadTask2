package by.maksim.darvin.threads.util;

import by.maksim.darvin.threads.model.Dispatcher;
import by.maksim.darvin.threads.model.Passenger;
import by.maksim.darvin.threads.model.Taxi;
import by.maksim.darvin.threads.location.Location;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public static List<Taxi> loadTaxis (String filename) throws IOException {
        List<Taxi> taxis = new ArrayList<Taxi>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Taxi")) {
                    String [] parts = line.split(" ");
                    String id = parts[1];
                    double x = Double.parseDouble(parts[2]);
                    double y = Double.parseDouble(parts[3]);
                    taxis.add(new Taxi(id, new Location(x, y)));

                }
            }
        }
        return taxis;
    }

    public static List<Passenger> loadPassengers (String filename, Dispatcher dispatcher) throws IOException {
        List<Passenger> passengers = new ArrayList<Passenger>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Passenger")) {
                    String [] parts = line.split(" ");
                    String name = parts[1];
                    double sx = Double.parseDouble(parts[2]);
                    double sy = Double.parseDouble(parts[3]);
                    double dx = Double.parseDouble(parts[4]);
                    double dy = Double.parseDouble(parts[5]);
                    passengers.add(new Passenger(name, new Location(sx, sy), new Location(dx, dy), dispatcher));

                }
            }
        }
        return passengers;
    }
}
