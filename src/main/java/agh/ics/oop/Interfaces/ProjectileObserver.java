package agh.ics.oop.Interfaces;

import agh.ics.oop.Proejctiles.Projectile;
import agh.ics.oop.Vector;

public interface ProjectileObserver {
    public void reportNewIndexProjectile(Vector old, Vector newpos, Projectile p);
}
