package agh.ics.oop.buildings.AttackingBuildings;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.Attacks.Projectile;
import agh.ics.oop.GameEngine;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.BuildingsName;
import agh.ics.oop.gui.GameScreen;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class CircleShootingBuilding extends AttackingBuilding{

    private ArrayList<Vector> directionVectors;

    public CircleShootingBuilding(int widthInTiles, int heightInTiles, Vector position, double attackSpeed, double attackStrength, GameScreen gs, Image img, GameEngine ge){
        super(widthInTiles,heightInTiles,position,attackSpeed,attackStrength,gs,img, ge);
        this.bname = BuildingsName.CIRCLEBUILDING;
        Vector temp = this.hitbox.centre;
        this.directionVectors = new ArrayList<>(){
            {
                add(new Vector(temp.getX()-10, temp.getY()));
                add(new Vector(temp.getX()+10,temp.getY()));
                add(new Vector(temp.getX(),temp.getY()+10));
                add(new Vector(temp.getX(),temp.getY()-10));
            }
        };
    }


    @Override
    public void attack() {
        if(canAttack()){
            for(Vector v: this.directionVectors){
                this.ge.addProjectile(true, (Projectile) AttackFactory.BasicTowerAttack(v,new Vector(hitbox.getCentre()), this.attackStrength));
            }
        }
    }

    @Override
    public boolean canAttack() {
        return !this.enemiesInRange.isEmpty();
    }

    @Override
    public void upgradeEffect() {
        switch(this.level){
            case 1:{
                Vector temp = this.hitbox.centre;
                ArrayList<Vector> newVectors = new ArrayList<>(){
                    {
                        add(new Vector(temp.getX()-10, temp.getY()-10));
                        add(new Vector(temp.getX()+10,temp.getY()+10));
                    }
                };
                this.directionVectors.addAll(newVectors);
                break;
            }
            case 2:{
                Vector temp = this.hitbox.centre;
                ArrayList<Vector> newVectors = new ArrayList<>(){
                    {
                        add(new Vector(temp.getX()-10,temp.getY()+10));
                        add(new Vector(temp.getX()+10,temp.getY()-10));
                    }
                };
                this.directionVectors.addAll(newVectors);
                break;

            }
            case 3:{
                this.changeAttackSpeed((int)this.attackSpeed/3);
                break;
            }
            case 4:{
                this.changeAttackSpeed((int)this.attackSpeed/3);
                break;
            }
        }
    }

    @Override
    public void getHit(Attack attack) {
        this.reduceHealth(attack.getStrength());
    }
}
