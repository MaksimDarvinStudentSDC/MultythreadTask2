package by.maksim.darvin.threads;

import by.maksim.darvin.threads.location.Location;

import java.util.concurrent.Callable;

public class Passenger implements Callable<String> {
    private final String name;
    private final Location start, destination;
    private final Dispatcher dispatcher;

    public Passenger(String name, Location start, Location destination, Dispatcher dispatcher) {
        this.name = name;
        this.start = start;
        this.destination = destination;
        this.dispatcher = dispatcher;
    }

    public String getName() { return name; }
    public Location getStart() { return start; }
    public Location getDestination() { return destination; }

    @Override
    public String call() {
        Taxi taxi = dispatcher.findBestTaxi(this);
        if (taxi != null) {
            taxi.assignPassenger(this);
            return name + ": назначено такси " + taxi.getId();
        } else {
            return name + ": такси не найдено";
        }
    }
}

