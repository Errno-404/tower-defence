package agh.ics.oop.Attacks;

import agh.ics.oop.Interfaces.Hittable;
import agh.ics.oop.Interfaces.OutOfMapObserver;
import agh.ics.oop.Vector;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class NormalProjectile extends Projectile{
    Vector direction;

    public NormalProjectile(Vector position, double velocity, Vector target) {
        super(position, velocity);
        this.direction = position.getDirectionVector(target);
        this.direction.normalise();
        this.direction.multiplyScalar(this.velocity);
        try {
            this.sprite = new ImageView(new Image(new FileInputStream("src/main/resources/yellowRect.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void move() {
        /*Vector oldPos = new Vector(this.hitbox.centre);
        int oldposX = hitbox.centre.getXindex();
        int oldposY = hitbox.centre.getYindex();*/


        this.hitbox.moveAlongVector(this.direction);

        /*int newposX = this.hitbox.centre.getXindex();
        int newposY = this.hitbox.centre.getYindex();*/

        /*if((oldposX != newposX || oldposY != newposY) && (newposX >= 0 && newposY >= 0) && (newposX < 60 && newposY < 60)){
            this.pobs.reportNewIndexProjectile(oldPos,this.hitbox.centre,this);
        }
        else if(this.hitbox.centre.getX() <= 5 || this.hitbox.centre.getY() <= 5 ||
                this.hitbox.getCentre().getX() >= 595 && this.hitbox.getCentre().getY() >= 595){
            this.outObserver.forEach((OutOfMapObserver o) ->
                    o.reportOutOfMap(this.hitbox.getCentre().getXindex(),this.hitbox.getCentre().getYindex(), this));
        }*/
    }

    @Override
    public void hit(Hittable collided) {
        collided.getHit(this);
    }
}
