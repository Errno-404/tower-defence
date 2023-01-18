package agh.ics.oop.buildings;

import agh.ics.oop.Attack;
import agh.ics.oop.Vector;

public abstract class Building {
    protected final int width;
    protected final int height;
    protected final Vector position;
    protected int health;

    protected Building(int width, int height, Vector position, int health) {
        this.width = width;
        this.height = height;
        this.position = position;
        this.health = health;
    }

    // każdy budynek może zostać zaatakowany
    public abstract void getHit(Attack attack);


    public Vector getPosition() {
        return this.position;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHealth(){
        return this.health;
    }


    @Override
    public String toString(){
        return "*";
    }

}
