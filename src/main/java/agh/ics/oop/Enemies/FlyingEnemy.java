package agh.ics.oop.Enemies;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.Castle;
import agh.ics.oop.maps.GameMap;
import javafx.scene.image.Image;
import javafx.util.Pair;

import java.util.ArrayList;

public class FlyingEnemy extends Enemy{


    public FlyingEnemy(double px, double py,double hp, Attack attack, GameMap map, Image sprite) {
        super(px, py, 30,30, hp, attack, map, sprite);
        this.goldOnDeath = 5;
    }

    @Override
    public void attack() {

    }

    @Override
    public boolean canAttack() {
        return false;
    }

    @Override
    public void move(){
        int oldposX = hitbox.centre.getXindex();
        int oldposY = hitbox.centre.getYindex();

        if(this.map.map[oldposX][oldposY] != null && this.map.map[oldposX][oldposY].buildingID instanceof Castle){
            return;
        }
        else{

            this.currentDestination = new Pair<>(this.map.castleCentre.x, this.map.castleCentre.y);


            this.hitbox.moveAlongVector(this.hitbox.centre.getDirectionVector(this.map.map[currentDestination.getKey()][currentDestination.getValue()].squareCentre));

            int newposX = this.hitbox.centre.getXindex();
            int newposY = this.hitbox.centre.getYindex();

            if(!(oldposX == newposX && oldposY == newposY)){
                this.map.reportNewIndexEnemy(new Vector(oldposX, oldposY),new Vector(newposX, newposY), this);
            }

        }
    }
}
