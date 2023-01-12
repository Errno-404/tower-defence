package agh.ics.oop.buildings;

import agh.ics.oop.Attack;
import agh.ics.oop.Vector;


public abstract class DefensiveBuildings {
    // TODO Wall and castle


    // Defensive parameters
    protected int health;
    private int defence;

    // Position and size parameters
    private final int width;
    private final int height;
    private final Vector position;


    public DefensiveBuildings(Vector position, int width, int height, int health, int defence) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.health = health;
        this.defence = defence;
    }

    // Basic getters
    public Vector getPosition() {
        return position;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getHealth(){
        return this.health;
    }

    // Game methods
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

    public void upgradeDefence(int deltaDefence) {
        // Zakładamy, że zarówno castle jak i Wall mogą zostać ulepszone
        this.defence += deltaDefence;

    }
}
