package agh.ics.oop.buildings;

import agh.ics.oop.Attack;
import agh.ics.oop.Constants;
import agh.ics.oop.Hitboxes.RectangularHitbox;
import agh.ics.oop.Vector;
import agh.ics.oop.gui.GameScreen;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Rectangle;


import java.util.ArrayList;

public abstract class Building {
    protected final double width;
    protected final double height;

    public RectangularHitbox hitbox;
    protected int health;

    private Image image;

    private ImageView[][] viewArray;
    private GameScreen gs;

    protected Building(int width, int height, Vector position, int health) {
        this.width = width;
        this.height = height;
        this.health = health;
    }

    protected Building(double width, double height, int upperLeftx, int upperLefty, int health, Image img, GameScreen gs) {
        this.width = width;
        this.height = height;

        this.health = health;
        this.gs = gs;

        this.hitbox = new RectangularHitbox(new Vector(upperLeftx*Constants.boxWidth, upperLefty*Constants.boxHeight),
                new Vector((upperLeftx + width)*Constants.boxWidth, (upperLefty - height)*Constants.boxHeight));

        this.viewArray = new ImageView[(int) width][(int) height];

        PixelReader reader = img.getPixelReader();
        for(int i = 0;i<width;i++){
            for(int j = 0;j<height;j++){
                //Rectangle2D viewport = new Rectangle2D(i* Constants.boxWidth, j*Constants.boxHeight, Constants.boxWidth, Constants.boxHeight);
                WritableImage croppedImage = new WritableImage(reader, (int) (i*Constants.boxWidth), (int) (j*Constants.boxHeight), (int) Constants.boxWidth, (int) Constants.boxHeight);
                ImageView temp = new ImageView(croppedImage);
                viewArray[i][j] = temp;
                //gs.elements[upperLeftx+i][upperLefty+j].updateImage(temp);
            }
        }
    }

    public void destroyBuilding(){
        for(int i = 0; i<this.width;i++){
            for(int j = 0;j<this.height;j++){
                this.gs.elements[this.hitbox.upperLeft.getXindex() + i][this.hitbox.upperLeft.getYindex() + j].setOriginalView();
            }
        }
    }

    // każdy budynek może zostać zaatakowany
    public abstract void getHit(Attack attack);


    public Vector getAnchorPosition() {
        return this.hitbox.upperLeft;
    }

    public double getHeight() {
        return this.height;
    }

    public double getWidth() {
        return this.width;
    }

    public int getHealth(){
        return this.health;
    }

    public ImageView getView(int i, int j){
        return this.viewArray[i][j];
    }
}
