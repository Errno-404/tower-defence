package agh.ics.oop.Proejctiles;

import agh.ics.oop.Attackers;
import agh.ics.oop.Vector;

public class ExplodingProjectile extends Projectile{
    ExplosionEffect onHitEffect;
    Vector target;

    protected ExplodingProjectile(Vector position, double velocity, Vector target) {
        super(position, velocity);
        this.target = target;
    }

    @Override
    public void move() {
        Vector direction = this.hitbox.centre.getDirectionVector(this.target);
        direction.multiplyScalar(this.velocity);
        this.hitbox.moveAlongVector(direction);
    }

    @Override
    public void hit(Attackers collided) {

    }
}
