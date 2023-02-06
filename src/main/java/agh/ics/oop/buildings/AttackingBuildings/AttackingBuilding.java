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

    protected double attackPower;

    protected AttackingBuilding(int widthInTiles, int heightInTiles, Vector position, double attackSpeed, double attackPower,GameScreen gs, Image img, GameEngine ge) {
        super(widthInTiles, heightInTiles, position, 100, img, gs);
        this.ge = ge;

        this.attackPower = attackPower;
        this.attackSpeed = attackSpeed;
        this.attackTimerTask = new TowerAttackManager(this);
        this.attackManager = new Timer();
        this.attackManager.scheduleAtFixedRate(this.attackTimerTask, 0L, (long) this.attackSpeed*1000);

    }

    public void clearEnemies(){
        this.enemiesInRange.clear();
    }

    public void addEnemyInRange(Enemy e){
        this.enemiesInRange.add(e);
    }

    public abstract void attack();

}
