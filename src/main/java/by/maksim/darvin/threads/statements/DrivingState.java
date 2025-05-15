package by.maksim.darvin.threads.statements;

import by.maksim.darvin.threads.model.Passenger;
import by.maksim.darvin.threads.model.Taxi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class DrivingState implements TaxiState {

    private static final Logger logger = LogManager.getLogger(DrivingState.class);

    private Passenger passenger;
    public DrivingState(Passenger passenger) {
        this.passenger = passenger;
    }

    @Override
    public void handleTaxi(Taxi taxi) {
        logger.info("{} accepted {}", taxi.getId(), passenger.getName());
        try {
            TimeUnit.MILLISECONDS.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("{} \n" + "interrupted during the trip", taxi.getId(), e);
        }
        taxi.setLocation(passenger.getDestination());
        logger.info("{} delivered {} to {}", taxi.getId(), passenger.getName(), passenger.getDestination());
        taxi.setState(new IdleState());
    }

    @Override
    public String getState() {
        return "Driving";
    }

}
