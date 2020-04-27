package utilities;

public class BoidVector {
    private double x;
    private double y;

    public BoidVector() {
        this.x = 0.0;
        this.y = 0.0;
    }

    public BoidVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void add(BoidVector boidVector) {
        this.x += boidVector.x;
        this.y += boidVector.y;
    }

    public void subtract(BoidVector boidVector) {
        this.x -= boidVector.x;
        this.y -= boidVector.y;
    }

    public void multiply(double multiplier) {
        this.x *= multiplier;
        this.y *= multiplier;
    }

    public void divide(double divisor) {
        this.x /= divisor;
        this.y /= divisor;
    }

    public double relativeBearing(BoidVector boidVector) {
        double dx = boidVector.x - this.x;
        double dy = boidVector.y - this.y;
        return Math.toDegrees(Math.atan2(dy, dx));
    }

    public double absX() {
        return Math.sqrt(Math.pow(this.x, 2));
    }

    public double absY() {
        return Math.sqrt(Math.pow(this.y, 2));
    }

    public double abs() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }


}
