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
    private static final int width = 1920;
    private static final int height = 1000;


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
    }

    public void animateBoids(final Scene scene) {
        Sky context = new Sky(width, height, 100, 1000, 20, 15, 15, 40, 100, 1);
        List<ImageView> boidSprites = context.getBoidSprites();
        final Group root = (Group) scene.getRoot();
        root.getChildren().addAll(boidSprites);
        Timeline tl = new Timeline();
        tl.setCycleCount(Animation.INDEFINITE);
        KeyFrame update = new KeyFrame(Duration.seconds(0.05),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        context.updateContext();
                    }
                });
        tl.getKeyFrames().add(update);
        tl.play();
    }

    public static void main(String[] args) {
        launch(args);
    }


}