package agh.ics.oop.gui;

import agh.ics.oop.GameEngine;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.AttackingBuildings.BasicTower;
import agh.ics.oop.buildings.Building;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class App extends Application {

    private GameScreen gameScreen;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        this.gameScreen = new GameScreen();
        primaryStage.setTitle("Tower Defence");
        Timeline tl = new Timeline(new KeyFrame(Duration.millis((15)), e-> gameScreen.run()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();


        BorderPane bpane = new BorderPane();
        bpane.setCenter(this.gameScreen.canvas);




        // ============================================ Tower selection ================================================

        ArrayList<Building> towers = new ArrayList<>();
        Building building = new BasicTower(new Vector(0, 0), this.gameScreen, this.gameScreen.gameEngine);
        towers.add(building);
        towers.add(building);




        Pane towerList = new TowerPane(this.gameScreen, towers);
        towerList.setPadding(new Insets(0, 10, 0, 10));


        bpane.setRight(towerList);











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
