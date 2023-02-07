package agh.ics.oop.buildings;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public enum BuildingsName {
    CASTLE,
    TOWER,
    HORIZONTALWALL,
    VERTICALWALL,
    CIRCLEBUILDING,
    SPREADSHOOTING;

    public int convert(){
        return switch (this){

            case CASTLE -> 0;
            case TOWER -> 1;
            case HORIZONTALWALL -> 2;
            case VERTICALWALL -> 3;
            case CIRCLEBUILDING -> 4;
            case SPREADSHOOTING -> 5;
        };
    }

    public Image getImage(){
        try {
            return switch (this) {
                case CASTLE -> new Image(new FileInputStream("src/main/resources/test.png"));
                case TOWER -> new Image(new FileInputStream("src/main/resources/Tower1.png"));
                case HORIZONTALWALL -> new Image(new FileInputStream("src/main/resources/WallHorizontal.png"));
                case VERTICALWALL -> new Image(new FileInputStream("src/main/resources/VerticalWall.png"));
                case CIRCLEBUILDING -> new Image(new FileInputStream("src/main/resources/CircleTower.png"));
                case SPREADSHOOTING -> new Image(new FileInputStream("src/main/resources/SpreadShootingTower.png"));
                default ->  throw new RuntimeException();
                };
            } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
