package visualisation;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.List;
import model.Sky;


public class ContextCanvas extends Application {
    private static final int width = 1920;
    private static final int height = 1000;

    /**
     * Initialise the stage on which the simulation will be animated and visualised
     * @param primaryStage The stage of the simulation
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Boids");
        primaryStage.setMinHeight(height);
        primaryStage.setMinWidth(width);
        Group root = new Group();
        Scene scene = new Scene(root, Color.WHITE);
        primaryStage.setScene(scene);
        animateBoids(scene);
        primaryStage.show();
    }

    /**
     * Initialises and runs the animation of the Boid simulation
     * @param scene The scene in which the animation will take place
     */
    public void animateBoids(final Scene scene) {
        Sky context = new Sky(width, height, 100, 1000, 20, 15, 15, 40, 100);
        List<ImageView> boidSprites = context.getBoidSprites();
        final Group root = (Group) scene.getRoot();
        root.getChildren().addAll(boidSprites);
        Timeline tl = new Timeline();
        tl.setCycleCount(Animation.INDEFINITE);
        KeyFrame update = new KeyFrame(Duration.seconds(0.05),
                event -> context.updateContext());
        tl.getKeyFrames().add(update);
        tl.play();
    }

    public static void main(String[] args) {
        launch(args);
    }


}