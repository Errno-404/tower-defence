package agh.ics.oop.Interfaces;

import agh.ics.oop.Attacks.Attack;

public interface Hittable {

    void getHit(Attack a);

    void reduceHealth(double h);

}
