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

    public Sky(int width, int height, int numberOfBoids, int cohesionFactor, int separationFactor, int alignmentFactor,
               int velocityLimit, int separationSmoothing, int neighbourhoodRadius) {
        this.width = width;
        this.height = height;
        this.numberOfBoids = numberOfBoids;
        this.cohesionFactor = cohesionFactor;
        this.separationFactor = separationFactor;
        this.alignmentFactor = alignmentFactor;
        this.velocityLimit = velocityLimit;
        this.separationSmoothing = separationSmoothing;
        this.neighbourhoodRadius = neighbourhoodRadius;
        this.boids = new ArrayList<>();
        populateContext();
    }

    /**
     * Populate the context with randomly positioned boids
     */
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

    /**
     * Update the method on each time step by applying the Boid rules
     */
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
            boid.headingAlignment(boidsInRadius);

            // Create a copy of the old position
            BoidVector oldPosition = new BoidVector(boid.position);
            boid.velocity = boid.velocity.add(movement);
            boid.position = boid.position.add(boid.velocity);

            // Calculate rotation bearing from old position to new position
            boid.heading = oldPosition.relativeBearing(boid.position);
            boid.drawSprite();
        }
    }

    /**
     * Get a list of all Boid sprites as ImageView objects
     * @return A list of Boid sprites as ImageView objects
     */
    public List<ImageView> getBoidSprites() {
        List<ImageView> boidSprites = new ArrayList<>();
        for (Boid boid : this.boids) {
            boidSprites.add(boid.sprite);
        }
        return boidSprites;
    }

    /**
     * Get a list of all Boid sprites within a given radius of a given Boid
     * @param boid The Boid at the centre of the search
     * @param radius The radius from the given Boid to search within
     * @return A list of the sprites of all Boids within the given radius of the given Boid
     */
    public List<Boid> getBoidsInRadius(Boid boid, int radius) {
        List<Boid> boidsInRadius = new ArrayList<>();
        for (Boid neighbour : this.boids) {
            BoidVector neighbourPosition = new BoidVector(neighbour.position);
            BoidVector boidsSeparation = neighbourPosition.subtract(boid.position);
            if (boidsSeparation.magnitude() < radius)
                boidsInRadius.add(neighbour);
        }
        return boidsInRadius;
    }
}