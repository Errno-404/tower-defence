package agh.ics.oop.buildings.AttackingBuildings;

import agh.ics.oop.Enemies.Enemy;
import agh.ics.oop.GameEngine;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.Building;
import agh.ics.oop.gui.GameScreen;
import javafx.scene.image.Image;

import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

public abstract class AttackingBuilding extends Building {

    TreeSet<Enemy> enemiesInRange = new TreeSet<>((Enemy e1, Enemy e2) -> (int) (e1.distanceFromCastle() - e2.distanceFromCastle()));

    private double radius;
    public GameEngine ge;

    TowerAttackManager attackTimerTask;
    public Timer attackManager;

    private double attackSpeed;

    protected AttackingBuilding(int widthInTiles, int heightInTiles, Vector position, double attackSpeed, GameScreen gs, Image img, GameEngine ge) {
        super(widthInTiles, heightInTiles, position, 100, img, gs);
        this.ge = ge;

        this.attackSpeed = attackSpeed;
        this.attackTimerTask = new TowerAttackManager(this);
        this.attackManager = new Timer();
        this.attackManager.scheduleAtFixedRate((TimerTask) this.attackTimerTask, 0L, (long) this.attackSpeed*500);

    }

    public void clearEnemies(){
        this.enemiesInRange.clear();
    }

    public void addEnemyInRange(Enemy e){
        this.enemiesInRange.add(e);
    }

    public abstract void attack();

}
