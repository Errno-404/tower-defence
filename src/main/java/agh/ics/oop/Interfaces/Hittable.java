package agh.ics.oop.Interfaces;

import Attacks.Attack;

public interface Hittable {

    void getHit(Attack a);

    void reduceHealth(double h);

}
