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

//    public NormalProjectile(Vector position, double velocity, Vector target) {
//        super(position, velocity);
//        this.direction = position.getDirectionVector(target);
//        this.direction.normalise();
//        this.direction.multiplyScalar(this.velocity);
//        try {
//            this.sprite = new ImageView(new Image(new FileInputStream("src/main/resources/yellowRect.png")));
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    public NormalProjectile(Vector position, double velocity, double strength, Vector target, Image sprite) {
        super(position, velocity, strength);
        this.direction = position.getDirectionVector(target);
        this.direction.normalise();
        this.direction.multiplyScalar(this.velocity);
        this.sprite = new ImageView(sprite);
        }



    @Override
    public void move() {
        this.hitbox.moveAlongVector(this.direction);

    }

    @Override
    public void hit(Hittable collided) {
        collided.getHit(this);
    }
}
