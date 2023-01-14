package agh.ics.oop.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Application;

public class App extends Application {

    private GameScreen gameScreen;

    final int width = 600;
    final int height = 600;
    @Override
    public void start(Stage primaryStage) {
        this.gameScreen = new GameScreen(width,height);
        primaryStage.setTitle("title");
        Timeline tl = new Timeline(new KeyFrame(Duration.millis((20)), e-> {gameScreen.run();}));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
        Canvas c = new Canvas(500,500);
        primaryStage.setScene(new Scene(new StackPane(gameScreen.canvas)));
        primaryStage.show();
    }
}
