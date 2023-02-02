package agh.ics.oop.maps;

import agh.ics.oop.Enemy;
import agh.ics.oop.Hitboxes.Hitbox;
import agh.ics.oop.Proejctiles.Projectile;
import agh.ics.oop.buildings.AttackingBuilding;
import agh.ics.oop.gui.CanvasElement;
import javafx.scene.image.ImageView;


import java.util.HashSet;
import java.util.LinkedList;

public class mapElement {
    Integer x;
    Integer y;

    Hitbox border;

    public CanvasElement canvasElement; //odpowiadajaca czesc canvasu
    Integer buildingID;

    boolean reachable;
    boolean placeable;

    public double flowFieldValue; //do znajdywania najkrotszych sciezek

    public HashSet<Projectile> projectileList; //lista projectili nad danym polem mapy
    public HashSet<Enemy> enemyList; //list przeciwnikow na danym polu

    public LinkedList<AttackingBuilding> inRangeOf;

    public mapElement(int i, int j, CanvasElement ce){
        this.x = i;
        this.y = j;

        this.buildingID = null;
        this.reachable = true;
        this.placeable = true;

        this.flowFieldValue = 0;
        this.projectileList =  new HashSet<>();
        this.enemyList = new HashSet<>();

        this.inRangeOf = new LinkedList<>();

        this.canvasElement = ce;
    }

    public void updateCanvas(ImageView iv){
        this.canvasElement.updateImage(iv);
    }

    public void revertCanvas(){
        this.canvasElement.setOriginalView();
    }
}
