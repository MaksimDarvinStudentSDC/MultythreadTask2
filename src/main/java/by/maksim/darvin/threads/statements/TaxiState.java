package by.maksim.darvin.threads.statements;

import by.maksim.darvin.threads.model.Taxi;

public interface TaxiState {
    void handleTaxi(Taxi taxi);
    String getState();
}
