package by.maksim.darvin.threads.statements;

import by.maksim.darvin.threads.Taxi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IdleState implements TaxiState {

    private static final Logger logger = LogManager.getLogger(IdleState.class);
    @Override
    public void handleTaxi (Taxi taxi){
        logger.debug("{} in awaiting state", taxi.getId());
    }

    @Override
    public String getState() {
        return "Idle";
    }
}
