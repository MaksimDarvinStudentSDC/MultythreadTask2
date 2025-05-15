package by.maksim.darvin.threads.app;

import by.maksim.darvin.threads.model.Dispatcher;
import by.maksim.darvin.threads.model.Passenger;
import by.maksim.darvin.threads.model.Taxi;
import by.maksim.darvin.threads.util.DataLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) throws Exception {
        Dispatcher dispatcher = Dispatcher.getInstance();

        List<Taxi> taxis = DataLoader.loadTaxis("src/main/resources/data.txt");
        List<Passenger> passengers = DataLoader.loadPassengers("src/main/resources/data.txt", dispatcher);
        int totalThreads = taxis.size() + passengers.size();
        if (totalThreads == 0) {
            logger.error("\n" + "No taxi or passengers. Check the contents of the data.txt file.");
            return;
        }
        ExecutorService executor = Executors.newFixedThreadPool(totalThreads);

        for (Taxi taxi : taxis) {
            executor.submit(taxi);
        }


        TimeUnit.MILLISECONDS.sleep(100);

        for (Passenger p : passengers) {
            executor.submit(p);
        }

        TimeUnit.SECONDS.sleep(10);
        executor.shutdownNow();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
}