package model;

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
    private int cohesionFactor;
    private int separationFactor;
    private int alignmentFactor;
    private int velocityLimit;
    private int separationSmoothing;
    private int neighbourhoodRadius;
    private int headingAlignmentFactor;

    public Sky(int width, int height, int numberOfBoids, int cohesionFactor, int separationFactor, int alignmentFactor,
               int velocityLimit, int separationSmoothing, int neighbourhoodRadius, int headingAlignmentFactor) {
        this.width = width;
        this.height = height;
        this.numberOfBoids = numberOfBoids;
        this.cohesionFactor = cohesionFactor;
        this.separationFactor = separationFactor;
        this.alignmentFactor = alignmentFactor;
        this.velocityLimit = velocityLimit;
        this.separationSmoothing = separationSmoothing;
        this.neighbourhoodRadius = neighbourhoodRadius;
        this.headingAlignmentFactor = headingAlignmentFactor;
        this.boids = new ArrayList<Boid>();
        populateContext();
    }

    @Override
    public void populateContext() {
        Random random = new Random();
        for (int i = 0; i<numberOfBoids; i++) {
            int randomX = random.nextInt(width);
            int randomY = random.nextInt(height);
            int randomHeading = random.nextInt(360);
            Boid randomBoid = new Boid(randomX, randomY, randomHeading);
            this.boids.add(randomBoid);
        }
    }

    @Override
    public void updateContext() {
        for (Boid boid : this.boids) {
            BoidVector movement = new BoidVector();
            List<Boid> boidsInRadius = getBoidsInRadius(boid, this.neighbourhoodRadius);

            // Calculate movement rules
            BoidVector cohesion = boid.cohesion(boidsInRadius, this.cohesionFactor);
            BoidVector separation = boid.separation(boidsInRadius, this.separationFactor, this.separationSmoothing);
            BoidVector alignment = boid.velocityAlignment(boidsInRadius, this.alignmentFactor);
            BoidVector boundPosition = boid.boundPosition(100, width-100, 100, height-100);
            movement = movement.add(cohesion);
            movement = movement.add(separation);
            movement = movement.add(alignment);
            movement = movement.add(boundPosition);
            boid.limitVelocity(this.velocityLimit);
            System.out.println(boid.heading);
            boid.headingAlignment(boidsInRadius, this.headingAlignmentFactor);
            System.out.println(boid.heading);

            // Create a copy of the old position
            BoidVector oldPosition = new BoidVector(boid.position);
            boid.velocity = boid.velocity.add(movement);
            boid.position = boid.position.add(boid.velocity);

            // Calculate rotation bearing from old position to new position
            double rotationBearing = oldPosition.relativeBearing(boid.position);
            boid.heading = rotationBearing;
            boid.drawSprite();
        }
    }

    public List<ImageView> getBoidSprites() {
        List<ImageView> boidSprites = new ArrayList<ImageView>();
        for (Boid boid : this.boids) {
            boidSprites.add(boid.sprite);
        }
        return boidSprites;
    }

    public List<Boid> getBoidsInRadius(Boid boid, int radius) {
        List<Boid> boidsInRadius = new ArrayList<Boid>();
        for (Boid neighbour : this.boids) {
            BoidVector neighbourPosition = new BoidVector(neighbour.position);
            BoidVector boidsSeparation = neighbourPosition.subtract(boid.position);
            if (boidsSeparation.magnitude() < radius)
                boidsInRadius.add(neighbour);
        }
        return boidsInRadius;
    }
}