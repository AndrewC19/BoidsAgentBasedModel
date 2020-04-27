package model;

import model.Context;
import utilities.BoidVector;

import javafx.scene.image.ImageView;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Sky extends Context {

    public int width;
    public int height;
    public List<Boid> boids;
    public int numberOfBoids;
    public int cohesionFactor;

    public Sky(int width, int height, int numberOfBoids, int cohesionFactor) {
        this.width = width;
        this.height = height;
        this.numberOfBoids = numberOfBoids;
        this.cohesionFactor = cohesionFactor;
        this.boids = new ArrayList<Boid>();
        populateContext();
    }

    @Override
    public void populateContext() {
        Random random = new Random();
        for (int i = 0; i<numberOfBoids; i++) {
            int randomX = random.nextInt(width);
            int randomY = random.nextInt(height);
            Boid randomBoid = new Boid(randomX, randomY);
            this.boids.add(randomBoid);
        }
    }

    @Override
    public void updateContext() {
//        Random random = new Random();
        for (Boid boid : this.boids) {
            BoidVector cohesion = boid.cohesion(this.boids, this.cohesionFactor);
            BoidVector oldPosition = new BoidVector(boid.position);
            boid.velocity = boid.velocity.add(cohesion);
            boid.position = boid.position.add(boid.velocity);
            double rotationBearing = oldPosition.relativeBearing(boid.position);
            boid.drawSprite(boid.position, rotationBearing);






//            BoidVector randomPosition = new BoidVector(random.nextInt(width-200)+100,
//                    random.nextInt(height-200)+100);
//            int randomRotation = random.nextInt(360);
//            boid.drawSprite(randomPosition, randomRotation);
//            boid.position = randomPosition;
        }
    }

    public List<ImageView> getBoidSprites() {
        List<ImageView> boidSprites = new ArrayList<ImageView>();
        for (Boid boid : this.boids) {
            boidSprites.add(boid.sprite);
        }
        return boidSprites;
    }
}