package by.maksim.darvin.threads.location;

public class Location {

    private double x;
    private double y;
    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double distanceTo(Location location) {
        return Math.hypot(this.x - location.getX(), this.y - location.getY());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }



}
