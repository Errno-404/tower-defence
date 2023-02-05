package agh.ics.oop.buildings.AttackingBuildings;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.Attacks.Projectile;
import agh.ics.oop.Enemies.Enemy;
import agh.ics.oop.GameEngine;
import agh.ics.oop.Vector;
import agh.ics.oop.gui.GameScreen;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BasicTower extends AttackingBuilding {

    public BasicTower(int px, int py, GameScreen gs) throws FileNotFoundException {
        super(2,2,px,py,gs, new Image(new FileInputStream("src/main/resources/Tower1.png")));
    }

    @Override
    public void attack() {
        Enemy target = this.enemiesInRange.first();
        this.ge.addProjectileReal(true, (Projectile) AttackFactory.BasicTowerAttack(target,this.hitbox.getCentre()));

    }


    @Override
    public void getHit(Attack attack) {
        this.reduceHealth(attack.getStrength());
    }
}
