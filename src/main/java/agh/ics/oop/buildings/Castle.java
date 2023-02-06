package agh.ics.oop.buildings;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.DefensiveBuildings.DefensiveBuilding;
import agh.ics.oop.gui.GameScreen;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Castle extends DefensiveBuilding {


    public Castle(Vector position, GameScreen gs) throws FileNotFoundException {
        super(3,3, position,1000,new Image(new FileInputStream("src/main/resources/test.png")), gs, 0.0);
        this.bname = BuildingsName.CASTLE;
    }
    @Override
    public void getHit(Attack attack){
        this.reduceHealth(attack.getStrength());
    }
}
