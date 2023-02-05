package agh.ics.oop.Hitboxes;

import agh.ics.oop.Vector;

public class RectangularHitbox implements Hitbox {
    public Vector upperLeft;
    public Vector lowerRight;
    public Vector centre;

    public RectangularHitbox(Vector ul, Vector lr) { //ul - upperleft, lr - lowerright
        this.upperLeft = ul;
        this.lowerRight = lr;
        this.centre = new Vector((ul.getX() + lr.getX()) / 2, (ul.getY() + lr.getY()) / 2);
    }

    public RectangularHitbox(Vector centre, double size) {
        this.centre = centre;

        this.upperLeft = new Vector(centre.getX() - size / 2, centre.getY() - size / 2);
        this.lowerRight = new Vector(centre.getX() + size / 2, centre.getY() + size / 2);
    }

    /*public boolean collidesWith(RectangularHitbox hb) {
        return (this.upperLeft.getX() >= hb.upperLeft.getX() && this.upperLeft.getX() <= hb.lowerRight.getX() &&
                this.upperLeft.getY() >= hb.upperLeft.getY() && this.upperLeft.getY() <= hb.lowerRight.getY());
    }*/

    @Override
    public boolean collidesWith(Hitbox hb) {
        if (hb instanceof RectangularHitbox hb1) {
            // A - lewy górny róg hitboxa 1
            // B - prawy dolny róg hitboxa 1
            Vector A = this.upperLeft;
            Vector B = this.lowerRight;

            // C - lewy górny róg hitboxa 2
            // D - prawy dolny róg hitboxa 2
            Vector C = hb1.upperLeft;
            Vector D = hb1.lowerRight;


            return (A.getX() <= C.getX() && C.getX() <= B.getX() && C.getY() <= B.getY() && D.getY() >= A.getY()) ||
                    (C.getX() <= A.getX() && A.getX() <= D.getX() && A.getY() <= D.getY() && B.getY() >= C.getY());


        } else if (hb instanceof CircularHitbox hb1) {

            return (this.centre.distance(hb1.centre) <= hb1.radius);
        }

        return false;
    }

    @Override
    public void moveAlongVector(Vector direction) {
        this.centre.addVector(direction);
        this.upperLeft.addVector(direction);
        this.lowerRight.addVector(direction);
    }


    // TODO do czego isIn?
    @Override
    public boolean isIn(Vector position) {
        return false;
    }


    // === Testy ===

    public Vector getCentre() {
        return this.centre;
    }

    public Vector getUpperLeft() {
        return this.upperLeft;
    }

    public Vector getLowerRight() {
        return this.lowerRight;
    }

}
