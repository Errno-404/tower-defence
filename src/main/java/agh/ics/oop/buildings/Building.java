package agh.ics.oop.buildings;

import agh.ics.oop.Attack;
import agh.ics.oop.Constants;
import agh.ics.oop.Hitboxes.RectangularHitbox;
import agh.ics.oop.Interfaces.BuildingDestroyedObserver;
import agh.ics.oop.Vector;
import agh.ics.oop.gui.GameScreen;
import agh.ics.oop.gui.HealthBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.util.ArrayList;


public abstract class Building {
    protected final double width;
    protected final double height;

    public RectangularHitbox hitbox;

    protected double currentHealth;
    protected double maxHealth;
    private HealthBar healthBar;

    private Image image;

    private ImageView[][] viewArray;
    private GameScreen gs;

    private ArrayList<BuildingDestroyedObserver> observer = new ArrayList<>();

    protected Building(int width, int height, Vector position, int health) {
        this.width = width;
        this.height = height;
        this.currentHealth = health;
    }

    protected Building(double width, double height, int upperLeftx, int upperLefty, int health, Image img, GameScreen gs) {
        this.width = width;
        this.height = height;

        this.currentHealth = health;
        this.maxHealth = health;
        this.gs = gs;

        this.healthBar = new HealthBar();

        this.hitbox = new RectangularHitbox(new Vector(upperLeftx*Constants.boxWidth, upperLefty*Constants.boxHeight),
                new Vector((upperLeftx + width)*Constants.boxWidth, (upperLefty + height)*Constants.boxHeight));

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

    public void addDestroyedObserver(BuildingDestroyedObserver o){
        this.observer.add(o);
    }

    public void destroyBuilding(){
        this.observer.forEach((BuildingDestroyedObserver o) -> {
            o.reportBuildingDestroyed(this);
        });
    }

    //@Override
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

    public double getCurrentHealth(){
        return this.currentHealth;
    }

    public ImageView getView(int i, int j){
        return this.viewArray[i][j];
    }

    public void drawHealthBar(){
        this.healthBar.draw(gs.gc, this.hitbox);
    }

    //test
    public void reduceHealth(double t){
        this.currentHealth-=t;
        System.out.println(this.currentHealth/this.maxHealth);
        this.healthBar.reportHealthChange(this.currentHealth/this.maxHealth);
        if (this.currentHealth <= 0){
            this.destroyBuilding();
        }
    }
}
