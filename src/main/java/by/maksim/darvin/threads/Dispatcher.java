package by.maksim.darvin.threads;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Dispatcher {
    private static Dispatcher instance;
    private static final Lock initLock = new ReentrantLock();

    private final List<Taxi> taxis = new ArrayList<>();
    private final Lock taxiLock = new ReentrantLock();

    private Dispatcher() {}

    public static Dispatcher getInstance() {
        if (instance == null) {
            initLock.lock();
            try {
                if (instance == null) {
                    instance = new Dispatcher();
                }
            } finally {
                initLock.unlock();
            }
        }
        return instance;
    }

    public void registerTaxi(Taxi taxi) {
        taxiLock.lock();
        try {
            taxis.add(taxi);
        } finally {
            taxiLock.unlock();
        }
    }

    public Taxi findBestTaxi(Passenger p) {
        taxiLock.lock();
        try {
            return taxis.stream()
                    .filter(t -> t.getLocation().distanceTo(p.getStart()) <= 10.0)
                    .min(Comparator.comparingDouble(t -> t.getLocation().distanceTo(p.getStart())))
                    .orElse(null);
        } finally {
            taxiLock.unlock();
        }
    }
}

