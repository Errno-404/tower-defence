package agh.ics.oop.buildings.AttackingBuildings;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.Enemies.Enemy;
import agh.ics.oop.GameEngine;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.Building;
import agh.ics.oop.gui.GameScreen;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.TreeSet;

public abstract class AttackingBuilding extends Building {

    TreeSet<Enemy> enemiesInRange = new TreeSet<>((Enemy e1, Enemy e2) -> (int) (e1.distanceFromCastle() - e2.distanceFromCastle()));

    private double radius;
    protected Attack attack;

    public GameEngine ge;

    protected AttackingBuilding(int width, int height,int px, int py, GameScreen gs, Image img) {
        super(width, height ,px,py,100,img, gs);
        this.attack = attack;
        this.ge = ge;
    }

    public void clearEnemies(){
        this.enemiesInRange.clear();
    }

    public void addEnemyInRange(Enemy e){
        this.enemiesInRange.add(e);
    }

    public abstract void attack();

}
