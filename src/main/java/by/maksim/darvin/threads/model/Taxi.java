package by.maksim.darvin.threads.model;

import by.maksim.darvin.threads.location.Location;
import by.maksim.darvin.threads.statement.AssignedState;
import by.maksim.darvin.threads.statement.IdleState;
import by.maksim.darvin.threads.statement.TaxiState;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Taxi implements Runnable {
    private static final Logger logger = LogManager.getLogger(Taxi.class);

    private final String id;
    private Location location;

    private final Lock lock = new ReentrantLock();
    private final Condition hasPassenger = lock.newCondition();
    private final Queue<Passenger> queuePassenger = new LinkedList<>();

    public TaxiState state = new IdleState();

    public Taxi(String id, Location location) {
        this.id = id;
        this.location = location;
    }

    public String getId() { return id; }
    public Location getLocation() { return location; }
    public void setLocation(Location loc) { this.location = loc; }

    public void setState(TaxiState newState) {
        lock.lock();
        try {
            this.state = newState;
        } finally {
            lock.unlock();
        }
    }

    public void assignPassenger(Passenger p) {
        lock.lock();
        try {
            queuePassenger.offer(p);
            hasPassenger.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        Dispatcher.getInstance().registerTaxi(this);

        while (!Thread.currentThread().isInterrupted()) {
            Passenger p = null;
            lock.lock();
            try {
                while (queuePassenger.isEmpty()) {
                    hasPassenger.await();
                }
                p = queuePassenger.poll();
            } catch (InterruptedException e) {
                break;
            } finally {
                lock.unlock();
            }

            if (p != null) {
                setState(new AssignedState(p));
                state.handleTaxi(this);
            } else {
                setState(new IdleState());
                state.handleTaxi(this);
            }
        }
        }
    }

