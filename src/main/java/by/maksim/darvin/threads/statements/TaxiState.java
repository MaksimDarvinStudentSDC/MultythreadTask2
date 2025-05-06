package by.maksim.darvin.threads.statements;

import by.maksim.darvin.threads.Taxi;

public interface TaxiState {
    void handleTaxi(Taxi taxi);
    String getState();
}
