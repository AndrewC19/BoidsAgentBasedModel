package utilities;

import model.Boid;

public class BoidVector implements Cloneable {
    public double x;
    public double y;

    public BoidVector() {
        this.x = 0.0;
        this.y = 0.0;
    }

    public BoidVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public BoidVector(BoidVector copyInstance) {
        this.x = copyInstance.x;
        this.y = copyInstance.y;
    }

    public BoidVector add(BoidVector boidVector) {
        return new BoidVector(this.x += boidVector.x, this.y += boidVector.y);
    }

    public BoidVector subtract(BoidVector boidVector) {
        return new BoidVector(this.x -= boidVector.x, this.y -= boidVector.y);
    }

    public BoidVector multiply(double multiplier) {
        double x = this.x / multiplier;
        double y = this.y / multiplier;
        return new BoidVector(x, y);
    }

    public BoidVector divide(double divisor) {
        double x = this.x / divisor;
        double y = this.y / divisor;
        return new BoidVector(x, y);
    }

    public double relativeBearing(BoidVector boidVector) {
        double dx = boidVector.x - this.x;
        double dy = boidVector.y - this.y;
        return Math.toDegrees(Math.atan2(dy, dx));
    }

    public BoidVector abs() {
        return new BoidVector(Math.abs(this.x), Math.abs(this.y));
    }

    public double magnitude() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
