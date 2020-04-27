package model;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javafx.scene.image.ImageView;
import utilities.BoidVector;

public class Boid extends Agent implements Cloneable {

    public BoidVector position;
    public BoidVector velocity;
    public ImageView sprite;

    /**
     * Initialises the Boid to a given position with no velocity, drawing it to the canvas as a random colour triangle
     * @param x horizontal position
     * @param y vertical position
     */
    public Boid(int x, int y) {
        this.position = new BoidVector(x, y);
        this.velocity = new BoidVector();
        this.sprite = new ImageView("file:resources/images/" + selectRandomBoidColour() + "_boid.png");
        drawSprite(this.position);
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
     * Draws the Boid on the canvas at a given position
     * @param position current location of the boid
     */
    public void drawSprite(BoidVector position) {
        this.sprite.setX(position.x);
        this.sprite.setY(position.y);
    }

    /**
     * Draws the Boid on the canvas at a given position
     * @param position current location of the boid
     * @param rotation angle to rotate sprite
     */
    public void drawSprite(BoidVector position, double rotation) {
        this.sprite.setRotate(rotation);
        this.sprite.setX(position.x);
        this.sprite.setY(position.y);
        System.out.println(rotation);

    }

    /**
     * Rotates the Boid to point towards a given position it will move to in the next iteration
     * @param nextPosition
     */
    public void rotateSpriteTowardsNextPosition(BoidVector nextPosition) {
        double bearingRelativeToNextPosition = this.position.relativeBearing(nextPosition);
        this.sprite.setRotate(bearingRelativeToNextPosition);
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

//    public BoidVector separation() {
//
//    }
//
//    public BoidVector alignment() {
//
//    }

    /**
     * Calculate the sum of the positions of a list of boids other than itself
     * @param boids
     * @return BoidVector containing the sum of the individual boid positions other than itself
     */
    public BoidVector sumOfOtherBoidPositions(List<Boid> boids) {
        BoidVector sumOfOtherBoidPositions = new BoidVector();
        for (Boid boid :boids) {
            if (!this.equals(boid))
                sumOfOtherBoidPositions = sumOfOtherBoidPositions.add(boid.position);
        }
        return sumOfOtherBoidPositions;
    }

    public String toString() {
        return "Position: " + this.position.toString() + "\nVelocity: " + this.velocity.toString();
    }
}
