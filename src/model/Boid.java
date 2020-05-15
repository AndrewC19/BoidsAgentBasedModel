package model;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javafx.scene.image.ImageView;
import utilities.BoidVector;

public class Boid extends Agent implements Cloneable {

    public BoidVector position;
    public BoidVector velocity;
    public double heading;
    public ImageView sprite;

    /**
     * Initialises the Boid to a given position with no velocity, drawing it to the canvas as a random colour triangle
     * @param x horizontal position
     * @param y vertical position
     */
    public Boid(int x, int y, int heading) {
        this.position = new BoidVector(x, y);
        this.velocity = new BoidVector();
        this.heading = heading;
        this.sprite = new ImageView("file:resources/images/" + selectRandomBoidColour() + "_boid.png");
        drawSprite();
        this.sprite.setSmooth(true);
        this.sprite.setCache(true);
    }

    /**
     * Selects a random colour for the boid sprite from a static list of colours
     * @return colour as a string
     */
    public String selectRandomBoidColour() {
        List<String> colourList = Arrays.asList("black", "blue", "green", "pink", "purple", "red", "teal", "yellow");
        Random random = new Random();
        return colourList.get(random.nextInt(colourList.size()));
    }

    /**
     * Draws the Boid on the canvas at the current position, pointing towards its current heading
     */
    public void drawSprite() {
        this.sprite.setRotate(this.heading);
        this.sprite.setX(this.position.x);
        this.sprite.setY(this.position.y);
    }

    /**
     * Rule 1 - Cohesion: Boids try to fly towards the centre of mass of neighbouring boids
     * @param neighbours The list of neighbouring boids
     * @param cohesionFactor The scaling parameter that decides how strong cohesion should be
     * @return The movement to make towards the calculated centre of mass as a BoidVector
     */
    public BoidVector cohesion(List<Boid> neighbours, int cohesionFactor) {
        BoidVector sumOfBoidVectorPositions = sumOfOtherBoidPositions(neighbours);
        BoidVector perceivedCentreOfMass = sumOfBoidVectorPositions.divide(neighbours.size()-1);
        return (perceivedCentreOfMass.subtract(this.position)).divide(cohesionFactor);
    }

    /**
     * Rule 2 - Separation: Boids adjust their position in an attempt to avoid colliding with neighbours
     * @param neighbours The list of neighbouring boids
     * @param separationFactor The parameter that decides how close boids can get before attempting to separate
     * @param separationSmoothing The smoothing parameter that decides how extreme the adjustment should be, a lower
     *                            value will result in a more sudden adjustment
     * @return The movement away from neighbours as a BoidVector
     */
    public BoidVector separation(List<Boid> neighbours, int separationFactor, int separationSmoothing) {
        BoidVector movementAwayFromNeighbours = new BoidVector();
        for (Boid boid : neighbours) {
            if (!boid.equals(this)) {
                BoidVector neighbourPosition = new BoidVector(boid.position);
                BoidVector boidsSeparation = neighbourPosition.subtract(this.position);
                double boidsSeparationMagnitude = boidsSeparation.magnitude();
                if (boidsSeparationMagnitude < separationFactor) {
                    movementAwayFromNeighbours = movementAwayFromNeighbours.subtract(boidsSeparation).divide(separationSmoothing);
                }
            }
        }
        return movementAwayFromNeighbours;
    }

    /**
     * Rule 3 - Velocity Alignment: Boids try to match the speed of their neighbours
     * @param neighbours The list of neighbouring boids
     * @param velocityAlignmentFactor The scaling parameter that decides the severity of the change in speed, a lower
     *                                value will result in a more sudden adjustment
     * @return The velocity adjustment to make as a BoidVector
     */
    public BoidVector velocityAlignment(List<Boid> neighbours, int velocityAlignmentFactor) {
        BoidVector perceivedVelocity = new BoidVector();
        for (Boid boid : neighbours) {
            if (!boid.equals(this)) {
                perceivedVelocity = perceivedVelocity.add(boid.velocity);
            }
        }
        perceivedVelocity = perceivedVelocity.divide(neighbours.size()-1);
        return perceivedVelocity.subtract(this.velocity).divide(velocityAlignmentFactor);
    }

    /**
     * Rule 4 - Heading Alignment: Boids try to match the heading, or flight direction, of their neighbours
     * @param neighbours The list of neighbouring boids
     */
    public void headingAlignment(List<Boid> neighbours) {
        double perceivedHeading = 0;
        // Don't adjust if you only have one neighbour
        if (neighbours.size() > 1) {
            for (Boid boid : neighbours) {
                if (!boid.equals(this)) {
                    perceivedHeading += boid.heading;
                }
            }
            this.heading = perceivedHeading / (neighbours.size() - 1);
        }
    }

    /**
     * Rule 5 - Bound Position: Boids cannot leave a given square and will be turned around if they reach the edges
     * @param minWidth The western-most point that the boids can fly to
     * @param maxWidth The eastern-most point that the boids can fly to
     * @param minHeight The southern-most point that the boids can fly to
     * @param maxHeight The northern-most point that the boids can fly to
     * @return The correcting movement to keep the boids within the square
     */
    public BoidVector boundPosition(int minWidth, int maxWidth, int minHeight, int maxHeight) {
        BoidVector correctionMovement = new BoidVector();
        if (this.position.x < minWidth)
            correctionMovement.x = 10;
        else if (this.position.x > maxWidth)
            correctionMovement.x = -10;
        if (this.position.y < minHeight)
            correctionMovement.y = 10;
        else if (this.position.y > maxHeight)
            correctionMovement.y = -10;
        return correctionMovement;
    }

    /**
     * Rule 6 - Limit Velocity: Boids cannot exceed a given maximum velocity, so never allow speed to exceed the given
     * limit
     * @param velocityLimit The maximum velocity of the boids
     */
    public void limitVelocity(int velocityLimit) {
        double speed = this.velocity.magnitude();
        if (speed > velocityLimit)
            this.velocity = (this.velocity.divide(speed)).multiply(velocityLimit);
    }

    /**
     * Calculate the sum of the positions of a list of boids other than itself
     * @param boids The list of neighbouring boids
     * @return BoidVector containing the sum of the individual boid positions other than itself
     */
    public BoidVector sumOfOtherBoidPositions(List<Boid> boids) {
        BoidVector sumOfOtherBoidPositions = new BoidVector();
        for (Boid boid :boids) {
            if (!boid.equals(this))
                sumOfOtherBoidPositions = sumOfOtherBoidPositions.add(boid.position);
        }
        return sumOfOtherBoidPositions;
    }

    public String toString() {
        return "Position: " + this.position.toString() + "\nVelocity: " + this.velocity.toString();
    }
}
