package agh.ics.oop.Attacks;

import agh.ics.oop.Enemies.Enemy;
import agh.ics.oop.Interfaces.Hittable;
import agh.ics.oop.Vector;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class HomingProjectile extends Projectile{
    Enemy target;

    public HomingProjectile(Vector position, double velocity, Enemy target) {
        super(position, velocity);
        this.target = target;

        try {
            this.sprite = new ImageView(new Image(new FileInputStream("src/main/resources/blackRect.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void move() {
        Vector oldPos = this.hitbox.centre;
        int oldposX = hitbox.centre.getXindex();
        int oldposY = hitbox.centre.getYindex();

        Vector direction = this.hitbox.centre.getDirectionVector(this.target.getHitbox().centre);
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
