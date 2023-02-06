package agh.ics.oop.buildings.AttackingBuildings;

import agh.ics.oop.Enemies.Enemy;
import agh.ics.oop.GameEngine;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.Building;
import agh.ics.oop.gui.GameScreen;
import javafx.scene.image.Image;

import java.util.Timer;
import java.util.TreeSet;

public abstract class AttackingBuilding extends Building {

    TreeSet<Enemy> enemiesInRange = new TreeSet<>((Enemy e1, Enemy e2) -> (int) (e1.distanceFromCastle() - e2.distanceFromCastle()));

    public GameEngine ge;

    TowerAttackManager attackTimerTask;
    public Timer attackManager;

    private double attackSpeed;

    protected double attackStrength;

    protected AttackingBuilding(int widthInTiles, int heightInTiles, Vector position,
                                double attackSpeed, double attackStrength, GameScreen gs, Image img, GameEngine ge) {
        super(widthInTiles, heightInTiles, position, 100, img, gs);
        this.ge = ge;

        this.attackStrength = attackStrength;
        this.attackSpeed = attackSpeed;
        this.attackTimerTask = new TowerAttackManager(this);
        this.attackManager = new Timer();
        this.attackManager.scheduleAtFixedRate(this.attackTimerTask, 0L, (long) this.attackSpeed);

    }

    public void clearEnemies(){
        this.enemiesInRange.clear();
    }

    public void addEnemyInRange(Enemy e){
        this.enemiesInRange.add(e);
    }

    public abstract void attack();

    @Override
    public void upgrade(){

        // TODO zmienić na stałe
        this.level ++;
        switch (this.level) {
            case 2 -> {
                this.attackStrength += 20;
                this.maxHealth += 100;
            }
            case 3 -> {
                this.attackStrength += 50;
                this.maxHealth += 200;
            }
            case 4 -> {
                this.attackStrength += 100;
                this.maxHealth += 300;
            }
            case 5 -> {
                this.attackStrength += 200;
                this.maxHealth += 1000;
                this.attackSpeed += 0.2 * this.attackSpeed;
            }
            default -> throw new IllegalArgumentException(this.getName() + " reached maximum level: " + this.level);
        }
    }

}
