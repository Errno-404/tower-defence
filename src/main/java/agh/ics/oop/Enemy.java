package agh.ics.oop;

import Attacks.Attack;
import agh.ics.oop.Hitboxes.RectangularHitbox;
import agh.ics.oop.Interfaces.HealthChangeObserver;
import agh.ics.oop.Interfaces.Hittable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Enemy implements Hittable {


    double maxHealth;
    double currentHealth;
    double x;
    double y;
    Image sprite;

    HealthChangeObserver hpObs;

    RectangularHitbox hitbox;


    @Override
    public void getHit(Attack a) {
        a.hit(this);
    }

    @Override
    public void reduceHealth(double h){
        this.currentHealth-=h;
        this.hpObs.reportHealthChange(currentHealth/maxHealth);
    }

    public void drawOnCanvas(GraphicsContext gc){
        gc.drawImage(sprite,x,y);
    }

    public RectangularHitbox getHitbox(){
        return  this.hitbox;
    }
}
