package agh.ics.oop.Enemies;

import agh.ics.oop.Attacks.HomingProjectileTestClass;
import agh.ics.oop.Attacks.StandardMeleeAttack;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.Building;
import agh.ics.oop.maps.GameMap;
import javafx.scene.image.Image;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BasicEnemy extends Enemy{



    public BasicEnemy(double px, double py, double attackPower,GameMap map) throws FileNotFoundException {
        super(px, py, 15, 15,250,new StandardMeleeAttack(attackPower), map,new Image(new FileInputStream("src/main/resources/EnemySquare1.png")));
        this.goldOnDeath = 5;

    }

    @Override
    public void attack() {
        Building target = this.map.map[this.currentDestination.getKey()][this.currentDestination.getValue()].buildingID;
        target.getHit(this.attack);
    }

    @Override
    public boolean canAttack() {
        if(this.currentDestination == null){
            return false;
        }
        return this.map.map[this.currentDestination.getKey()][this.currentDestination.getValue()].buildingID != null;
    }
}
