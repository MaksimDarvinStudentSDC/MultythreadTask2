package by.maksim.darvin.threads;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;



//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        Dispatcher dispatcher = Dispatcher.getInstance();

        List<Taxi> taxis = DataLoader.loadTaxis("src/main/resources/data.txt");
        List<Passenger> passengers = DataLoader.loadPassengers("src/main/resources/data.txt", dispatcher);
        int totalThreads = taxis.size() + passengers.size();
        if (totalThreads == 0) {
            System.err.println("Нет такси или пассажиров. Проверьте содержимое файла data.txt.");
            return;
        }
        ExecutorService executor = Executors.newFixedThreadPool(totalThreads);
        //ExecutorService executor = Executors.newFixedThreadPool(taxis.size() + passengers.size());

        // Запускаем такси
        for (Taxi taxi : taxis) {
            executor.submit(taxi);
        }

        // Небольшая задержка перед запуском пассажиров
        TimeUnit.MILLISECONDS.sleep(100);

        // Запускаем пассажиров
        for (Passenger p : passengers) {
            executor.submit(p);
        }

        // Работаем N секунд, затем выключаем всё
        TimeUnit.SECONDS.sleep(10);
        executor.shutdownNow();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
}