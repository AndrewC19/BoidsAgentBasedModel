package utilities;

public class BoidVector implements Cloneable {
    public double x;
    public double y;

    /**
     * Empty constructor initialises a zero BoidVector
     */
    public BoidVector() {
        this.x = 0.0;
        this.y = 0.0;
    }

    /**
     * Overloaded constructor given parameters x and y initialises a BoidVector with the given values of x and y
     * @param x The x position for the new BoidVector
     * @param y The y position for the new BoidVector
     */
    public BoidVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Overloaded constructor given a BoidVector instance will create a copy of this instance
     * @param copyInstance The BoidVector that will be copied
     */
    public BoidVector(BoidVector copyInstance) {
        this.x = copyInstance.x;
        this.y = copyInstance.y;
    }

    /**
     * Add a given BoidVector to this BoidVector
     * @param boidVector The BoidVector to add to this one
     * @return The sum of this and the given BoidVector
     */
    public BoidVector add(BoidVector boidVector) {
        return new BoidVector(this.x += boidVector.x, this.y += boidVector.y);
    }

    /**
     * Subtract a given BoidVector from this BoidVector
     * @param boidVector The BoidVector to subtract from this one
     * @return The value of this BoidVector minus the given BoidVector
     */
    public BoidVector subtract(BoidVector boidVector) {
        return new BoidVector(this.x -= boidVector.x, this.y -= boidVector.y);
    }

    /**
     * Multiply this BoidVector by a given value
     * @param multiplier The value to multiple this BoidVector by
     * @return This BoidVector with x and y multiplied by the given value
     */
    public BoidVector multiply(double multiplier) {
        if (multiplier == 0)
            return new BoidVector();
        else {
            double x = this.x * multiplier;
            double y = this.y * multiplier;
            return new BoidVector(x, y);
        }
    }

    /**
     * Divide this BoidVector by a given value
     * @param divisor The value to divide this BoidVector by
     * @return This BoidVector with x and y divided by the given value
     */
    public BoidVector divide(double divisor) {
        if (divisor == 0)
            return new BoidVector();
        else {
            double x = this.x / divisor;
            double y = this.y / divisor;
            return new BoidVector(x, y);
        }
    }

    /**
     * Calculate the bearing from this BoidVector's heading to a given BoidVector's heading
     * @param boidVector The "to" BoidVector
     * @return The bearing from this BoidVector's heading to the given BoidVector's heading
     */
    public double relativeBearing(BoidVector boidVector) {
        double dx = boidVector.x - this.x;
        double dy = boidVector.y - this.y;
        return Math.toDegrees(Math.atan2(dy, dx));
    }

    /**
     * Calculate the magnitude of this BoidVector
     * @return The magnitude of this BoidVector
     */
    public double magnitude() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
