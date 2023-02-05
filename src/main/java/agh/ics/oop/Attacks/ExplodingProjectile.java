package agh.ics.oop.Attacks;

import agh.ics.oop.Interfaces.Hittable;
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
        Vector oldPos = this.hitbox.centre;
        int oldposX = hitbox.centre.getXindex();
        int oldposY = hitbox.centre.getYindex();

        Vector direction = this.hitbox.centre.getDirectionVector(this.target);
        direction.multiplyScalar(this.velocity);
        this.hitbox.moveAlongVector(direction);

        int newposX = this.hitbox.centre.getXindex();
        int newposY = this.hitbox.centre.getYindex();

        if(oldposX != newposX || oldposY != newposY){
            this.pobs.reportNewIndexProjectile(oldPos,this.hitbox.centre,this);
        }
    }

    @Override
    public void hit(Hittable h) {

    }
}
