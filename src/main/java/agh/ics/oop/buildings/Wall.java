package agh.ics.oop.buildings;

import Attacks.Attack;
import agh.ics.oop.Vector;

public class Wall extends DefensiveBuilding {
    public Wall(Vector position, int width, int height, int health, int defence) {
        super(position, width, height, health, defence);
    }


   /* @Override
    public void getHit(Attack attack) {
        super.getHit(attack);
        if(this.currentHealth == 0){
            System.out.println("Mur zniszczony!");
        }
    }*/

    @Override
    public void getHit(Attack a) {

    }
}
