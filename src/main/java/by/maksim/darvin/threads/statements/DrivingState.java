package by.maksim.darvin.threads.statements;

import by.maksim.darvin.threads.Passenger;
import by.maksim.darvin.threads.Taxi;

import java.util.concurrent.TimeUnit;

public class DrivingState implements TaxiState {

    private Passenger passenger;
    public DrivingState(Passenger passenger) {
        this.passenger = passenger;
    }

    @Override
    public void handleTaxi(Taxi taxi) {
        System.out.println(taxi.getId() + " assepted " + passenger.getName());
        try {
            TimeUnit.MILLISECONDS.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        taxi.setLocation(passenger.getDestination());
        System.out.println(taxi.getId() + " drive to " + passenger.getName() + " in " + passenger.getDestination());
        taxi.setState(new IdleState());
    }

    @Override
    public String getState() {
        return "Driving";
    }

}
