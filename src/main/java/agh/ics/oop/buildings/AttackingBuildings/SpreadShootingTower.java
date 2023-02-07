package agh.ics.oop.buildings.AttackingBuildings;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.Attacks.Projectile;
import agh.ics.oop.Enemies.Enemy;
import agh.ics.oop.GameEngine;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.AttackingBuildings.AttackingBuilding;
import agh.ics.oop.buildings.BuildingsName;
import agh.ics.oop.gui.GameScreen;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class SpreadShootingTower extends AttackingBuilding {

    int numOfTargets = 1;
    public SpreadShootingTower(int widthInTiles, int heightInTiles, Vector position, double attackSpeed, double attackStrength, GameScreen gs, Image img, GameEngine ge) {
        super(widthInTiles, heightInTiles, position, attackSpeed, attackStrength, gs, img, ge);
        this.bname = BuildingsName.SPREADSHOOTING;
    }

    @Override
    public void attack() {

        LinkedList<Vector> targets = new LinkedList<>();
        int it = 0;
        Object[] temp = this.enemiesInRange.toArray(new Enemy[0]);
        for(Object e: temp){
            targets.add(((Enemy) e).getHitbox().getCentre());
            it++;
            if(it == this.numOfTargets){
                break;
            }
        }

        targets.forEach((Vector v) -> {
            this.ge.addProjectile(true, (Projectile) AttackFactory.SpreadShootingTowerAttack(new Vector(v),new Vector(this.hitbox.centre), this.attackStrength));
        });


    }

    @Override
    public boolean canAttack() {
        return this.enemiesInRange != null && !this.enemiesInRange.isEmpty();
    }

    @Override
    public void upgradeEffect() {
        this.numOfTargets++;

    }

    @Override
    public void getHit(Attack attack) {
        this.reduceHealth(attack.getStrength());
    }
}
