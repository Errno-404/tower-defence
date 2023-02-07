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

        // ====================================== Stage and timeline ===================================================

        this.gameScreen = new GameScreen();

        primaryStage.setTitle("Tower Defence");
        primaryStage.setOnCloseRequest(event -> {
            gameScreen.endGame();
            Platform.exit();
        });

        Timeline tl = new Timeline(new KeyFrame(Duration.millis((15)), e -> gameScreen.run()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();

        // ======================================== Setting GridPane ===================================================

        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        primaryStage.setScene(new Scene(gridPane));

        // ============================================ Top Bar ========================================================

        TitlePane topPane = new TitlePane(this.gameScreen);
        gridPane.add(topPane, 1, 0, 1, 1);


        // ============================================ Left Bar =======================================================

        //TODO: Zmienić na LeftPane.coś
//        Pane leftPane = new Pane();
//        leftPane.getChildren().add(new Label(""));

        // =========================================== Main View =======================================================

        gridPane.add(this.gameScreen.canvas, 1, 1, 1, 1);

        // =========================================== Right Bar =======================================================

        this.towerPane = new TowerPane(this.gameScreen);
        this.towerPane.setPadding(new Insets(0, 10, 0, 10));

        gridPane.add(this.towerPane, 2, 1, 1, 1);
        GridPane.setHgrow(this.towerPane, Priority.ALWAYS);

        // =========================================== Bottom Bar ======================================================

        Pane bottomPane = new Pane(); //TODO: Zmienic na InfoPane.coś
        bottomPane.getChildren().add(new Label("Bottom Pane (info o obecnie wybranej wiezy/jednostce albo cos w tym stylu?)"));

        gridPane.add(bottomPane, 1, 2, 1, 1);

        primaryStage.show();


    }

}
