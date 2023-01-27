package agh.ics.oop.gui;

import agh.ics.oop.GameEngine;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

    private GameScreen gameScreen;
    private GameEngine gameEngine;
    final int width = 600;
    final int height = 600;
    @Override
    public void start(Stage primaryStage) {

        this.gameScreen = new GameScreen();
        primaryStage.setTitle("BTD6");
        Timeline tl = new Timeline(new KeyFrame(Duration.millis((15)), e-> {gameScreen.run();}));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();

        BorderPane bpane = new BorderPane();
        bpane.setCenter(this.gameScreen.canvas);

        Pane towers = new Pane(); //TODO: Zmienic na TowerPane.VBox/HBox/Pane czy coś, jeśli trzeba dodać coś w stylu SelectionObserver
        towers.getChildren().add(new Label("Tower list (right pane)"));
        bpane.setRight(towers);

        Pane bottomPane = new Pane(); //TODO: Zmienic na InfoPane.coś
        bottomPane.getChildren().add(new Label("Bottom Pane (info o obecnie wybranej wiezy/jednostce albo cos w tym stylu?)"));
        bpane.setBottom(bottomPane);

        Pane topPane = new Pane(); //TODO: Zmienic na StatPane.coś
        topPane.getChildren().add(new Label("moze HP/gold i jakies inne rzeczy(topPane)\n\n\n"));
        bpane.setTop(topPane);

        Pane leftPane = new Pane(); //TODO: Zmienić na LeftPane.coś
        leftPane.getChildren().add(new Label("nwm co tu moze byc jeszcze, moze sie przyda (leftPane)"));
        bpane.setLeft(leftPane);

        //gameScreen.addObserver(...)
        //TODO: Może zmienić observerów na https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/beans/PropertyChangeListener.html


        primaryStage.setScene(new Scene(bpane));
        primaryStage.show();
    }
}
