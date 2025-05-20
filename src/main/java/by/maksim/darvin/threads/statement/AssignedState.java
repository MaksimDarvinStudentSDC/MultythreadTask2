package by.maksim.darvin.threads.statement;

import by.maksim.darvin.threads.model.Passenger;
import by.maksim.darvin.threads.model.Taxi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class AssignedState implements TaxiState {
    private static final Logger logger = LogManager.getLogger(AssignedState.class);
    private final Passenger passenger;

    public AssignedState(Passenger passenger) {
        this.passenger = passenger;
    }

    @Override
    public void handleTaxi (Taxi taxi){
        logger.info("{} go to passenger {}", taxi.getId(), passenger.getName());
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("{}\n" + "interrupted while waiting before going to the passenger", taxi.getId(), e);
        }
        taxi.setState(new DrivingState(passenger));
        taxi.state.handleTaxi(taxi);
    }

  /*  @Override
    public String getState() {
        return "Assigned";
    }*/
}
