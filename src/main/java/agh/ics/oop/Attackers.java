package agh.ics.oop;

import agh.ics.oop.Hitboxes.RectangularHitbox;
import agh.ics.oop.Interfaces.HealthChangeObserver;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.w3c.dom.css.Rect;

public abstract class Attackers {


    Integer maxHealth;
    Integer currentHealth;
    double x;
    double y;
    Image sprite;

    HealthChangeObserver hpObs;

    RectangularHitbox hitbox;


    public void HealthChanged() {
        this.hpObs.reportHealthChange((double)maxHealth/currentHealth);
    }

    public void drawOnCanvas(GraphicsContext gc){
        gc.drawImage(sprite,x,y);
    }

    public RectangularHitbox getHitbox(){
        return  this.hitbox;
    }
}
