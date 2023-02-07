package agh.ics.oop.Enemies;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.Attacks.Projectile;
import agh.ics.oop.GameEngine;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.AttackingBuildings.AttackFactory;
import agh.ics.oop.buildings.AttackingBuildings.AttackingBuilding;
import javafx.scene.image.Image;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;

public class BossEnemy extends Enemy{

    private final ArrayList<Vector> directionVectors;
    private double attackPower;


    public BossEnemy(double px, double py, double hp, double attackPower, GameEngine ge, Image sprite) {
        super(px, py, 30,30, hp, null, ge, sprite);
        this.attackPower = attackPower;
        this.directionVectors = new ArrayList<>(){
            {
                add(new Vector(-10, 0));
                add(new Vector(10,0));
                add(new Vector(0,10));
                add(new Vector(0,-10));

                add(new Vector(-10,10));
                add(new Vector(-10,-10));
                add(new Vector(10,-10));
                add(new Vector(10,10));

                add(new Vector(-3,10));
                add(new Vector(-3,-10));
                add(new Vector(3,-10));
                add(new Vector(3,10));

                add(new Vector(-10,3));
                add(new Vector(-10,-3));
                add(new Vector(10,-3));
                add(new Vector(10,3));


            }
        };

        this.goldOnDeath = 100;

    }

    @Override
    public void attack() {
        for(Vector v: this.directionVectors){
            Vector targetDirection = new Vector(this.hitbox.centre);
            targetDirection.addVector(v);
            this.gameEngine.addProjectile(false, (Projectile) AttackFactory.BasicEnemyAttack(targetDirection,new Vector(hitbox.getCentre()), this.attackPower));
        }

        this.gameEngine.towers.forEach((AttackingBuilding a) -> {
            Vector position = a.hitbox.centre;

            this.gameEngine.addProjectile(false, (Projectile) AttackFactory.BasicBossAttack(new Vector(position),new Vector(hitbox.getCentre()), this.attackPower/2));
        });
    }

    @Override
    public boolean canAttack() {
        return true;
    }

    @Override
    public void move(){
        int oldposX = hitbox.centre.getXindex();
        int oldposY = hitbox.centre.getYindex();

        if(this.currentDestination != null &&this.map.map[currentDestination.getKey()][currentDestination.getValue()].buildingID != null){
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

            Vector dirVec = this.hitbox.centre.getDirectionVector(this.map.map[nextXsquare][nextYsquare].squareCentre);
            dirVec.multiplyScalar(0.1);
            this.hitbox.moveAlongVector(dirVec);

            int newposX = this.hitbox.centre.getXindex();
            int newposY = this.hitbox.centre.getYindex();

            if(!(oldposX == newposX && oldposY == newposY)){
                this.map.reportNewIndexEnemy(new Vector(oldposX, oldposY),new Vector(newposX, newposY), this);
            }

        }
    }
}
