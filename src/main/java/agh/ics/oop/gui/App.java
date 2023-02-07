package agh.ics.oop.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;

public class App extends Application {

    private GameScreen gameScreen;

    
    private TowerPane towerPane;

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

        this.towerPane= new TowerPane(this.gameScreen);
        this.towerPane.setPadding(new Insets(0, 10, 0, 10));
        bpane.setRight(this.towerPane);












        Pane bottomPane = new Pane(); //TODO: Zmienic na InfoPane.coś



        bottomPane.getChildren().add(new Label("Bottom Pane (info o obecnie wybranej wiezy/jednostce albo cos w tym stylu?)"));
        bpane.setBottom(bottomPane);




        // ========================================= Informacje o fali =================================================
        Pane topPane = new TitlePane(this.gameScreen);
        bpane.setTop(topPane);

        Pane leftPane = new Pane(); //TODO: Zmienić na LeftPane.coś
        leftPane.getChildren().add(new Label(""));
        bpane.setLeft(leftPane);

        //gameScreen.addObserver(...)
        //TODO: Może zmienić observerów na https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/beans/PropertyChangeListener.html


        primaryStage.setOnCloseRequest(event -> {
            gameScreen.endGame();
            Platform.exit();
        });
        primaryStage.setScene(new Scene(bpane));
        primaryStage.show();


    }

}
