package model;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javafx.scene.image.ImageView;
import utilities.BoidVector;

public class Boid extends Agent {

    private BoidVector position;
    private BoidVector velocity;
    private ImageView sprite;

    /**
     * Initialises the Boid to a given position with no velocity, drawing it to the canvas as a random colour triangle
     * @param x horizontal position
     * @param y vertical position
     */
    public Boid(int x, int y) {
        this.position = new BoidVector(x, y);
        this.velocity = new BoidVector();
        this.sprite = new ImageView("file:resources/images/" + selectRandomBoidColour() + "_boid.png");
        drawSprite(x, y);
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
     * @param x horizontal position
     * @param y vertical position
     */
    public void drawSprite(double x, double y) {
        this.sprite.setX(x);
        this.sprite.setY(y);
        this.sprite.setSmooth(true);
        this.sprite.setCache(true);
    }

    /**
     * Rotates the Boid to point towards a given position it will move to in the next iteration
     * @param nextPosition
     */
    public void rotateSpriteTowardsNextPosition(BoidVector nextPosition) {
        double bearingRelativeToNextPosition = this.position.relativeBearing(nextPosition);
        this.sprite.setRotate(bearingRelativeToNextPosition);
    }
}
