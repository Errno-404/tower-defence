package agh.ics.oop.Hitboxes;

import agh.ics.oop.Vector;

public interface Hitbox {

    boolean collidesWith(Hitbox hb);

    public void moveAlongVector(Vector direction);
}
