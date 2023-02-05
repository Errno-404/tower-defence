package agh.ics.oop.Enemies;

import agh.ics.oop.Attacks.HomingProjectileTestClass;
import agh.ics.oop.Vector;
import agh.ics.oop.maps.GameMap;
import javafx.scene.image.Image;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BasicEnemy extends Enemy{



    public BasicEnemy(double px, double py, GameMap map) throws FileNotFoundException {
        super(px, py, 15, 15,50,new HomingProjectileTestClass(new Vector(2,2),2), map,new Image(new FileInputStream("src/main/resources/EnemySquare1.png")));

    }
}
