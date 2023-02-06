package agh.ics.oop.buildings.AttackingBuildings;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.Attacks.Projectile;
import agh.ics.oop.Enemies.Enemy;
import agh.ics.oop.GameEngine;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.BuildingsName;
import agh.ics.oop.gui.GameScreen;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BasicTower extends AttackingBuilding {


    // int widthInTiles, int heightInTiles, Vector position, GameScreen gs, Image img, Attack attack, GameEngine ge

    public BasicTower(Vector position, GameScreen gs, GameEngine ge) throws FileNotFoundException {
        super(2,2,position,1,gs,new Image(new FileInputStream("src/main/resources/Tower1.png")), ge);
        this.bname = BuildingsName.TOWER;
    }

    @Override
    public void attack() {
        if(!this.enemiesInRange.isEmpty()){
            Enemy target = this.enemiesInRange.first();
            this.ge.addProjectile(true, (Projectile) AttackFactory.BasicTowerAttack(target,new Vector(hitbox.getCentre())));
        }
    }


    @Override
    public void getHit(Attack attack) {
        this.reduceHealth(attack.getStrength());
    }
}
