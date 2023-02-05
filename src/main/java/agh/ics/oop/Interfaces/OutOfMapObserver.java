package agh.ics.oop.Interfaces;

import agh.ics.oop.Attacks.Projectile;

public interface OutOfMapObserver {
    void reportOutOfMap(int lastX, int lastY, Projectile p);
}
