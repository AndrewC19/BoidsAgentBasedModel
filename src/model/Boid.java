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
     * @param neighbours
     * @param cohesionFactor
     * @return The movement to make towards the calculated centre of mass as a BoidVector
     */
    public BoidVector cohesion(List<Boid> neighbours, int cohesionFactor) {
        BoidVector sumOfBoidVectorPositions = sumOfOtherBoidPositions(neighbours);
        BoidVector perceivedCentreOfMass = sumOfBoidVectorPositions.divide(neighbours.size()-1);
        BoidVector movementTowardsCentreOfMass = (perceivedCentreOfMass.subtract(this.position)).divide(cohesionFactor);
        return movementTowardsCentreOfMass;
    }

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

    public void headingAlignment(List<Boid> neighbours, int headingAlignmentFactor) {
        double perceivedHeading = 0;
        for (Boid boid : neighbours) {
            if (!boid.equals(this)) {
                perceivedHeading += boid.heading;
            }
        }
        this.heading = perceivedHeading/(neighbours.size()-1);
    }

    /**
     * Calculate the sum of the positions of a list of boids other than itself
     * @param boids
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

    public void limitVelocity(int velocityLimit) {
        double speed = velocity.magnitude();
        if (speed > velocityLimit)
            this.velocity = (this.velocity.divide(speed)).multiply(velocityLimit);
    }

    public String toString() {
        return "Position: " + this.position.toString() + "\nVelocity: " + this.velocity.toString();
    }
}
