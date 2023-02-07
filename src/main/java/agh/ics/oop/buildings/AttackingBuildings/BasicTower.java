package agh.ics.oop.buildings.AttackingBuildings;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.Attacks.Projectile;
import agh.ics.oop.Enemies.Enemy;
import agh.ics.oop.GameEngine;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.BuildingsName;
import agh.ics.oop.gui.GameScreen;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class BasicTower extends AttackingBuilding {

    public BasicTower(int widthInTiles, int heightInTiles, Vector position, double attackSpeed, double attackStrength, GameScreen gs, Image img, GameEngine ge) throws FileNotFoundException {
        super(widthInTiles,heightInTiles,position,attackSpeed,attackStrength,gs,img, ge);
        this.bname = BuildingsName.TOWER;
    }

    @Override
    public void attack() {
        if(canAttack()){
            Enemy target = this.enemiesInRange.first();
            this.ge.addProjectile(true, (Projectile) AttackFactory.BasicTowerAttack(target.getHitbox().centre,new Vector(hitbox.getCentre()), this.attackStrength));
        }
    }

    @Override
    public boolean canAttack() {
        return this.enemiesInRange != null && !this.enemiesInRange.isEmpty();
    }

    @Override
    public void upgradeEffect() {
        this.changeAttackSpeed((int) (this.attackSpeed*0.7));
    }


    @Override
    public void getHit(Attack attack) {
        this.reduceHealth(attack.getStrength());
    }
}
