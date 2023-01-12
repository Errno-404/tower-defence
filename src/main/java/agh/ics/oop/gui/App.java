package agh.ics.oop.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {

        Parent root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Fun begins here ;)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
