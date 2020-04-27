package visualisation;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import model.Sky;


public class ContextCanvas extends Application {
    private static final int width = 1200;
    private static final int height = 800;


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Boids");
        primaryStage.setMinHeight(height);
        primaryStage.setMinWidth(width);
        Group root = new Group();
        Scene scene = new Scene(root, Color.WHITE);
        primaryStage.setScene(scene);
        animateBoids(scene);
        primaryStage.show();

//
//        List<ImageView> boidImages = Arrays.asList(
//                new ImageView("file:resources/images/yellow_boid.png"),
//                new ImageView("file:resources/images/blue_boid.png"),
//                new ImageView("file:resources/images/green_boid.png"),
//                new ImageView("file:resources/images/pink_boid.png"),
//                new ImageView("file:resources/images/purple_boid.png"),
//                new ImageView("file:resources/images/red_boid.png"),
//                new ImageView("file:resources/images/teal_boid.png")
//        );
    }

    public void animateBoids(final Scene scene) {
        Sky context = new Sky(width, height, 10, 10);
        List<ImageView> boidSprites = context.getBoidSprites();
        final Group root = (Group) scene.getRoot();
        root.getChildren().addAll(boidSprites);
        Timeline tl = new Timeline();
        tl.setCycleCount(Animation.INDEFINITE);
        KeyFrame update = new KeyFrame(Duration.seconds(0.2),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        context.updateContext();
                    }
                });
        tl.getKeyFrames().add(update);
        tl.play();
    }

    public void updateBoidPositions(List<ImageView> boidImages) {

    }

    public static void main(String[] args) {
        launch(args);
    }


}