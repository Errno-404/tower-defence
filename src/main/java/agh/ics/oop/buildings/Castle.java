package agh.ics.oop.buildings;

import agh.ics.oop.Attack;
import agh.ics.oop.Vector;

public class Castle extends DefensiveBuilding {



    public Castle(Vector position, int width, int height, int health, int defence) {
        super(position, width, height, health, defence);
    }

    @Override
    public void getHit(Attack attack){
        super.getHit(attack);
        if(this.health == 0){
            System.out.println("Zamek zniszczony!");
        }
    }
}
