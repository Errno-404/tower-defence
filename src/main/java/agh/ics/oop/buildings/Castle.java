package agh.ics.oop.buildings;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.Vector;
import agh.ics.oop.gui.GameScreen;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Castle extends DefensiveBuilding {



    public Castle(Vector position, int width, int height, int health, int defence) {
        super(position, width, height, health, defence);
    }

    public Castle(int width, int height, int px, int py, int health, Image img, GameScreen gs){
        super(width, height, px, py, health, img, gs);

    }

    public Castle(int px, int py, GameScreen gs) throws FileNotFoundException {
        super(3,3,px,py,100,new Image(new FileInputStream("src/main/resources/test.png")), gs);
    }
    @Override
    public void getHit(Attack attack){
        //System.out.println("getting hit by " + attack.getStrength());
        this.reduceHealth(attack.getStrength());
    }
}
