package Attacks;

import agh.ics.oop.Interfaces.Hittable;

public abstract class Attack {
    private double strength;

    public Attack(double strength){
        this.strength = strength;
    }

    public double getStrength() {
        return strength;
    }

    public abstract void hit(Hittable h);
}
