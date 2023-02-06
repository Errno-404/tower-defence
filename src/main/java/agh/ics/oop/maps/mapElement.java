package agh.ics.oop.maps;

import agh.ics.oop.Constants;
import agh.ics.oop.Enemies.Enemy;
import agh.ics.oop.Attacks.Projectile;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.AttackingBuildings.AttackingBuilding;
import agh.ics.oop.buildings.Building;
import agh.ics.oop.gui.CanvasElement;
import javafx.scene.image.ImageView;


import java.util.HashSet;
import java.util.LinkedList;

public class mapElement {
    Integer x;
    Integer y;

    public Vector squareCentre;

    public CanvasElement canvasElement; //odpowiadajaca czesc canvasu
    public Building buildingID;

    public boolean reachable;
    public boolean placeable;

    public int mapWeightValue = Integer.MAX_VALUE; //do znajdywania najkrotszych sciezek

    public HashSet<Projectile> friendlyProjectileList;
    public HashSet<Projectile> friendlyProjectileToRemove;
    public HashSet<Projectile> enemyProjectileList;
    public HashSet<Projectile> enemyProjectilesToRemove;
    public HashSet<Enemy> enemyList; //list przeciwnikow na danym polu

    public LinkedList<AttackingBuilding> inRangeOf;

    public mapElement(int i, int j, CanvasElement ce){
        this.x = i;
        this.y = j;

        this.squareCentre = new Vector(i* Constants.tileSize + Constants.tileSize /2, j*Constants.tileSize + Constants.tileSize /2);

        this.buildingID = null;
        this.reachable = true;
        this.placeable = true;

        this.mapWeightValue = 0;
        this.friendlyProjectileList =  new HashSet<>();
        this.enemyProjectileList = new HashSet<>();

        this.friendlyProjectileToRemove = new HashSet<>();
        this.enemyProjectilesToRemove = new HashSet<>();

        this.enemyList = new HashSet<>();

        this.inRangeOf = new LinkedList<>();

        this.canvasElement = ce;
    }

//    public void clearUsedProjectiles(boolean isFriendly){
//        if(isFriendly) {
//            this.friendlyProjectileList.removeAll(this.friendlyProjectileToRemove);
//            this.friendlyProjectileToRemove.clear();
//        }
//        else{
//            this.enemyProjectileList.removeAll(this.enemyProjectilesToRemove);
//            this.enemyProjectilesToRemove.clear();
//        }
//    }

//    public void clearDeadEnemies(HashSet<Enemy> el){
//        this.enemyList.removeAll(el);
//    }

    public void updateCanvas(ImageView iv){
        this.canvasElement.updateImage(iv);
    }

//    public void revertCanvas(){
//        this.canvasElement.setOriginalView();
//    }
}
