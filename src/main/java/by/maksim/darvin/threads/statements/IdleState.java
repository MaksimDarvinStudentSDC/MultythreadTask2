package by.maksim.darvin.threads.statements;

import by.maksim.darvin.threads.Taxi;

public class IdleState implements TaxiState {
    @Override
    public void handleTaxi (Taxi taxi){
        System.out.println(taxi.getId() + "Awaiting state");
    }

    @Override
    public String getState() {
        return "Idle";
    }
}
