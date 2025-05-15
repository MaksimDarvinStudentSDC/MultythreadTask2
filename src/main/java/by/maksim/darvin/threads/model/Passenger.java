package by.maksim.darvin.threads.model;

import by.maksim.darvin.threads.location.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;

public class Passenger implements Callable<String> {
    private static final Logger logger = LogManager.getLogger(Passenger.class);

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
            logger.info("{} \n" + "got a taxi {}", name, taxi.getId());
            taxi.assignPassenger(this);
            return name + ": taxi assigned " + taxi.getId();
        } else {
            logger.warn("{} \n" + " Could not find available taxi", name);
            return name + ": no taxi found";
        }
    }
}

