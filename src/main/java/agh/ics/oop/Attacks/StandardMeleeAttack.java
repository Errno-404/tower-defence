package agh.ics.oop.Attacks;

import agh.ics.oop.Interfaces.Hittable;

public class StandardMeleeAttack extends Attack{

    public StandardMeleeAttack(double strength){
        super(strength);
    }

    @Override
    public void hit(Hittable h) {
        h.getHit(this);
    }
}
