package agh.ics.oop.buildings;

import agh.ics.oop.Attack;
import agh.ics.oop.Vector;


public abstract class DefensiveBuildings extends Buildings {

    // Defensive parameters
    private int defence;


    protected DefensiveBuildings(Vector position, int width, int height, int health, int defence) {
        super(width, height, position, health);
        this.defence = defence;
    }

    // Game methods
    @Override
    public void getHit(Attack attack) {
        // Chwilowo nie mam lepszego pomysłu na zrobienie defense, bo floatów się boję :/

        int damage = attack.getStrength() - this.defence;
        if(damage > this.health){
            this.health = 0;
        }
        else{
            this.health -= (attack.getStrength() - this.defence);
        }


    }

    // Method specific for Defensive Buildings
    public void upgradeDefence(int deltaDefence) {
        // Zakładamy, że zarówno castle jak i Wall mogą zostać ulepszone
        this.defence += deltaDefence;

    }
}
