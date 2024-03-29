package agh.ics.oop.buildings;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.Constants;
import agh.ics.oop.Hitboxes.RectangularHitbox;
import agh.ics.oop.Interfaces.BuildingDestroyedObserver;
import agh.ics.oop.Interfaces.DamageTakenObserver;
import agh.ics.oop.Interfaces.Hittable;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.AttackingBuildings.AttackingBuilding;
import agh.ics.oop.gui.GameScreen;
import agh.ics.oop.gui.HealthBar;
import agh.ics.oop.gui.Shop;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.util.ArrayList;


public abstract class Building implements Hittable {
    protected final int widthInTiles;
    protected final int heightInTiles;

    protected final Vector position;

    public RectangularHitbox hitbox;

    protected double currentHealth;
    protected double maxHealth;
    protected HealthBar healthBar;

    private Image image;

    private ImageView[][] viewArray;
    protected final GameScreen gs;

    protected BuildingsName bname;

    private final ArrayList<BuildingDestroyedObserver> observer = new ArrayList<>();

    private DamageTakenObserver damageObs;

    public void addDamageObs(DamageTakenObserver o){
        this.damageObs = o;
    }

    public void removeDamageObs(DamageTakenObserver o){
        this.damageObs = null;
    }




    // Building level

    protected int level = 1;

    protected Building(int widthInTiles, int heightInTiles, Vector position, double health, Image img, GameScreen gs) {
        this.widthInTiles = widthInTiles;
        this.heightInTiles = heightInTiles;
        this.position = position;

        this.image = img;

        double upperLeftx = position.getX();
        double upperLefty = position.getY();

        this.currentHealth = health;
        this.maxHealth = health;
        this.gs = gs;

        this.healthBar = new HealthBar();

        this.hitbox = new RectangularHitbox(new Vector(upperLeftx*Constants.tileSize, upperLefty*Constants.tileSize),
                new Vector((upperLeftx + widthInTiles)*Constants.tileSize, (upperLefty + heightInTiles)*Constants.tileSize));

        this.viewArray = new ImageView[(int) widthInTiles][(int) heightInTiles];



        PixelReader reader = img.getPixelReader();
        for(int i = 0; i< widthInTiles; i++){
            for(int j = 0; j< heightInTiles; j++){
                //Rectangle2D viewport = new Rectangle2D(i* Constants.boxWidth, j*Constants.boxHeight, Constants.boxWidth, Constants.boxHeight);
                WritableImage croppedImage = new WritableImage(reader, (int) (i*Constants.tileSize), (int) (j*Constants.tileSize), (int) Constants.tileSize, (int) Constants.tileSize);
                ImageView temp = new ImageView(croppedImage);
                viewArray[i][j] = temp;
                //gs.elements[upperLeftx+i][upperLefty+j].updateImage(temp);
            }
        }
    }


    public void addDestroyedObserver(BuildingDestroyedObserver o){
        if(!this.observer.contains(o)){
            this.observer.add(o);
        }
    }

    public void removeDestroyedObserver(BuildingDestroyedObserver o){
        this.observer.remove(o);
    }

    public void destroyBuilding(){
        this.observer.forEach((BuildingDestroyedObserver o) -> {
            o.reportBuildingDestroyed(this);
        });

        if(this instanceof AttackingBuilding a1){
            a1.attackManager.cancel();
        }

        this.observer.clear();
    }

    public BuildingsName getName(){
        return this.bname;
    }

    //@Override
    public abstract void getHit(Attack attack);


    public Vector getAnchorPosition() {
        return this.hitbox.upperLeft;
    }

    public double getHeightInTiles() {
        return this.heightInTiles;
    }

    public double getWidthInTiles() {
        return this.widthInTiles;
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

    public Image getImage() {
        return image;
    }

    //test
    public void reduceHealth(double t){
        this.currentHealth-=t;
        if(this.currentHealth <= 0){
            if(this instanceof AttackingBuilding a1){
                a1.attackManager.cancel();
            }
            this.observer.forEach((BuildingDestroyedObserver o) -> {
                o.reportBuildingDestroyed(this);
            });
        }
        if(this.damageObs!= null){
            this.damageObs.damageTaken();
        }

        this.healthBar.reportHealthChange(this.currentHealth/this.maxHealth);
    }

    public abstract void upgrade();

    public int getLevel(){
        return this.level;
    }

    public double getMaxHealth(){
        return this.maxHealth;
    }

    public void drawHealthBarFixed(GraphicsContext gc){
        this.healthBar.drawFixedSize(gc);
    }

    public void sell(){
        Shop.sellTower(this.bname, this.level);
        this.destroyBuilding();
    }

}
