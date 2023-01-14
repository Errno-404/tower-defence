package agh.ics.oop;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Attackers {

    double x;
    double y;
    Image sprite;


    public void drawOnCanvas(GraphicsContext gc){
        gc.drawImage(sprite,x,y);
    }
}
