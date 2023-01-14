package agh.ics.oop.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
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

        BorderPane bpane = new BorderPane();
        bpane.setCenter(this.gameScreen.canvas);

        Pane towers = new Pane();
        towers.getChildren().add(new Label("Tower list (right pane)")); //mozliwe ze trzeba bdzie jakos przeniesc do gameScreen
        bpane.setRight(towers);

        Pane bottomPane = new Pane();
        bottomPane.getChildren().add(new Label("Bottom Pane (info o obecnie wybranej wiezy/jednostce albo cos w tym stylu?)"));
        bpane.setBottom(bottomPane);

        Pane topPane = new Pane();
        topPane.getChildren().add(new Label("moze HP/gold i jakies inne rzeczy(topPane)\n\n\n"));
        bpane.setTop(topPane);

        Pane leftPane = new Pane();
        leftPane.getChildren().add(new Label("nwm co tu moze byc jeszcze, moze sie przyda (leftPane)"));
        bpane.setLeft(leftPane);


        primaryStage.setScene(new Scene(bpane));
        primaryStage.show();
    }
}
