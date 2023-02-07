package agh.ics.oop.Enemies;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.Attacks.Projectile;
import agh.ics.oop.Constants;
import agh.ics.oop.GameEngine;
import agh.ics.oop.Interfaces.BuildingDestroyedObserver;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.AttackingBuildings.AttackFactory;
import agh.ics.oop.buildings.Building;
import agh.ics.oop.buildings.Castle;
import agh.ics.oop.maps.GameMap;
import javafx.scene.image.Image;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeSet;

public class ShootingEnemy extends Enemy implements BuildingDestroyedObserver{

    public TreeSet<Building> buildingsInRange;
    protected int range;
    protected double attackPower;

    public ShootingEnemy(double px, double py, double hp, double attackPower, int range, GameEngine ge, Image sprite) {
        super(px, py, 20,20, hp, null, ge, sprite);
        this.attackPower = attackPower;
        this.range = range;
        this.buildingsInRange = new TreeSet<>(Comparator.comparingInt((Building a) -> a.getName().convert()));
        this.addTowersInRange();
        this.goldOnDeath = 5;
    }

    public void addTowersInRange(){
        int centerX = this.hitbox.centre.getXindex();
        int centerY = this.hitbox.centre.getYindex();

        int i0 = Math.max(0,centerX-this.range);
        int i1 = Math.min(centerX + this.range,Constants.numberOfTiles);

        int j0 = Math.max(0, centerY - this.range);
        int j1 = Math.min(Constants.numberOfTiles, centerY+this.range);

        for(int i = i0;i<i1;i++){
            for(int j = j0;j<j1;j++){
                if(this.map.map[i][j].buildingID != null){
                    this.buildingsInRange.add(this.map.map[i][j].buildingID);
                }
            }
        }
    }

    public void removeTowersInRange(){
        this.buildingsInRange.clear();
    }

    @Override
    public void attack() {
        this.buildingsInRange.first().addDestroyedObserver(this);
        Vector target = this.buildingsInRange.first().hitbox.centre;
        System.out.println("Attacking: "+ target);
        this.gameEngine.addProjectile(false, (Projectile) AttackFactory.BasicEnemyAttack(target,new Vector(this.hitbox.centre),this.attackPower));

    }

    @Override
    public boolean canAttack() {
        return this.buildingsInRange!= null && !this.buildingsInRange.isEmpty();
    }

    @Override
    public void move(){
        int oldposX = hitbox.centre.getXindex();
        int oldposY = hitbox.centre.getYindex();

        if((this.currentDestination != null &&this.map.map[currentDestination.getKey()][currentDestination.getValue()].buildingID != null) || this.canAttack()){
            return;
        }
        else{
            ArrayList<Pair<Integer, Integer>> moves = new ArrayList<Pair<Integer, Integer>>() {
                {
                    add(new Pair<>(1,0));
                    add(new Pair<>(0,1));
                    add(new Pair<>(-1,0));
                    add(new Pair<>(0,-1));

                    add(new Pair<>(1,1));
                    add(new Pair<>(1,-1));
                    add(new Pair<>(-1,1));
                    add(new Pair<>(-1,-1));


                }
            };

            int currX = this.hitbox.centre.getXindex();
            int currY = this.hitbox.centre.getYindex();

            int minIndex = 0;
            double minVal = Integer.MAX_VALUE;
            for(Pair<Integer, Integer> move: moves){
                int nextX = currX + move.getKey();
                int nextY = currY + move.getValue();

                if(this.map.isOnMap(nextX, nextY)){
                    if(this.map.map[nextX][nextY].mapWeightValue < minVal){
                        minVal = this.map.map[nextX][nextY].mapWeightValue;
                        minIndex = moves.indexOf(move);
                    }
                }
            }

            int nextXsquare = currX + moves.get(minIndex).getKey();
            int nextYsquare = currY + moves.get(minIndex).getValue();

            this.currentDestination = new Pair<>(nextXsquare, nextYsquare);


            this.hitbox.moveAlongVector(this.hitbox.centre.getDirectionVector(this.map.map[nextXsquare][nextYsquare].squareCentre));

            int newposX = this.hitbox.centre.getXindex();
            int newposY = this.hitbox.centre.getYindex();

            if(!(oldposX == newposX && oldposY == newposY)){
                this.map.reportNewIndexEnemy(new Vector(oldposX, oldposY),new Vector(newposX, newposY), this);
                this.removeTowersInRange();
                this.addTowersInRange();
            }

        }
    }

    @Override
    public void reportBuildingDestroyed(Building b) {
        this.buildingsInRange.remove(b);
    }
}
