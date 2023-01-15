package agh.ics.oop.Proejctiles;

import agh.ics.oop.Attackers;
import agh.ics.oop.Vector;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class NormalProjectile extends Projectile{
    Vector target;

    public NormalProjectile(Vector position, double velocity, Vector target) {
        super(position, velocity);
        this.target = target;
        try {
            this.sprite = new ImageView(new Image(new FileInputStream("src/main/resources/yellowRect.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

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
