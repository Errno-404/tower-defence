package agh.ics.oop.buildings;

import agh.ics.oop.Attack;
import agh.ics.oop.Vector;
import agh.ics.oop.gui.GameScreen;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;


import java.util.ArrayList;

public abstract class Building {
    protected final int width;
    protected final int height;
    protected  Vector position;
    protected int health;

    private Image image;

    private ImageView[][] viewArray;

    protected Building(int width, int height, Vector position, int health) {
        this.width = width;
        this.height = height;
        this.position = position;
        this.health = health;
    }

    protected Building(int width, int height, int px, int py, int health, Image img, GameScreen gs) {
        this.width = width;
        this.height = height;
        //this.position = position;
        this.health = health;

        this.viewArray = new ImageView[width][height];
        double boxWidth = 10;

        for(int i = 0;i<width;i++){
            for(int j = 0;j<height;j++){
                Rectangle2D viewport = new Rectangle2D(width*boxWidth, height*boxWidth, boxWidth, boxWidth);
                viewArray[i][j] = new ImageView(img);
                viewArray[i][j].setViewport(viewport);
                gs.elements[px+i][py+j].updateImage(this.viewArray[i][j]);
            }
        }
    }

    // każdy budynek może zostać zaatakowany
    public abstract void getHit(Attack attack);


    public Vector getPosition() {
        return this.position;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHealth(){
        return this.health;
    }
}
