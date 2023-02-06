package agh.ics.oop.buildings;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.rmi.UnexpectedException;

public enum BuildingsName {
    CASTLE,
    TOWER,
    WALL;

    public int convert(){
        return switch (this){

            case CASTLE -> 0;
            case TOWER -> 1;
            case WALL -> 2;
        };
    }

    public Image getImage(){
        try {
            return switch (this) {
                case CASTLE -> new Image(new FileInputStream("src/main/resources/test.png"));
                case TOWER -> new Image(new FileInputStream("src/main/resources/Tower1.png"));
                case WALL -> new Image(new FileInputStream("src/main/resources/WallHorizontal.png"));
                default ->  throw new RuntimeException();
                };
            } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
