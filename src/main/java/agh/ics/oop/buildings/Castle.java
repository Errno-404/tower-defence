package agh.ics.oop.buildings;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.Vector;
import agh.ics.oop.gui.GameScreen;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Castle extends DefensiveBuilding {


    // int widthInTiles, int heightInTiles, Vector position, double health, Image img, GameScreen gs
    public Castle(Vector position, GameScreen gs) throws FileNotFoundException {
        super(3,3, position,500,new Image(new FileInputStream("src/main/resources/test.png")), gs, 0.0);
    }
    @Override
    public void getHit(Attack attack){
        //System.out.println("getting hit by " + attack.getStrength());
        this.reduceHealth(attack.getStrength());
    }
}
