package agh.ics.oop.Interfaces;

import agh.ics.oop.Attacks.Projectile;
import agh.ics.oop.Vector;

public interface ProjectileObserver {
    void reportNewIndexProjectile(Vector old, Vector newpos, Projectile p);

    void projectileHit(Projectile p, boolean type);
}
