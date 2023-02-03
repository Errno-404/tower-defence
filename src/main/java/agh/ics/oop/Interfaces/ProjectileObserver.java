package agh.ics.oop.Interfaces;

import Attacks.Projectile;
import agh.ics.oop.Vector;

public interface ProjectileObserver {
    void reportNewIndexProjectile(Vector old, Vector newpos, Projectile p);

    void projectileHit(Projectile p, boolean type);
}
