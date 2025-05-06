package by.maksim.darvin.threads.statements;

import by.maksim.darvin.threads.Passenger;
import by.maksim.darvin.threads.Taxi;

import java.util.concurrent.TimeUnit;

public class AssignedState implements TaxiState {

    private final Passenger passenger;

    public AssignedState(Passenger passenger) {
        this.passenger = passenger;
    }

    @Override
    public void handleTaxi (Taxi taxi){
        System.out.println(taxi.getId() + " go to " + passenger.getName());
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        taxi.setState(new DrivingState(passenger));
    }

    @Override
    public String getState() {
        return "Assigned";
    }
}
