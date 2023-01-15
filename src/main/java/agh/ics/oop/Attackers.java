package agh.ics.oop;

import agh.ics.oop.gui.HealthChangeObserver;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Attackers {


    Integer maxHealth;
    Integer currentHealth;
    double x;
    double y;
    Image sprite;

    HealthChangeObserver hpObs;


    public void HealthChanged() {
        this.hpObs.reportHealthChange((double)maxHealth/currentHealth);
    }

    public void drawOnCanvas(GraphicsContext gc){
        gc.drawImage(sprite,x,y);
    }
}
