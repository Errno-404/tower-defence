package agh.ics.oop.Hitboxes;

import agh.ics.oop.Vector;

public class CircularHitbox implements Hitbox{

    Vector centre;
    double radius;

    @Override
    public boolean collidesWith(Hitbox hb) {

        if(hb instanceof CircularHitbox hb1){
            double dist = this.centre.distance(hb1.centre);

            return (dist <= this.radius - hb1.radius) || (dist <= this.radius + hb1.radius);

        }
        else if(hb instanceof RectangularHitbox hb1){

            return (hb1.centre.distance(this.centre) <= this.radius);
        }

        return false;
    }

    @Override
    public void moveAlongVector(Vector direction) {
        this.centre.addVector(direction);
    }

    @Override
    public boolean isIn(Vector position) {
        return false;
    }
}
