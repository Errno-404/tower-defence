package agh.ics.oop.Enemies;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.GameEngine;
import agh.ics.oop.Hitboxes.RectangularHitbox;
import agh.ics.oop.Interfaces.HealthChangeObserver;
import agh.ics.oop.Interfaces.Hittable;
import agh.ics.oop.Vector;
import agh.ics.oop.gui.GameScreen;
import agh.ics.oop.gui.HealthBar;
import agh.ics.oop.maps.GameMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Timer;

public abstract class Enemy implements Hittable {


    double maxHealth;
    public double currentHealth;
    Image sprite;

    HealthBar hpObs;

    RectangularHitbox hitbox;

    GameMap map;
    GameEngine gameEngine;
    Attack attack;

    public Timer enemyAttackTimer;

    protected Integer goldOnDeath;

    protected Pair<Integer, Integer> currentDestination = null;


    public Enemy(double px, double py, double sizex, double sizey, double hp, Attack attack, GameEngine ge, Image sprite){
        this.currentHealth = hp;
        this.maxHealth = hp;
        this.hitbox = new RectangularHitbox(new Vector(px,py),sizex);

        this.enemyAttackTimer = new Timer();

        this.enemyAttackTimer.scheduleAtFixedRate(new EnemyAttackManager(this),0L,1000L); //zmienić 1000L na attackSpeed albo coś takiego

        this.attack = attack;

        this.hpObs = new HealthBar();

        this.sprite = sprite;
        this.map = ge.gameMap;
        this.gameEngine = ge;
    }

    public void multiplyHealth(double d){
        this.maxHealth*=d;
        this.currentHealth = maxHealth;
    }

    public abstract void attack();

    public abstract boolean canAttack();

    public void turnOff(){
        this.enemyAttackTimer.cancel();
    }

    @Override
    public void getHit(Attack a) {
        this.reduceHealth(a.getStrength());
    }

    @Override
    public void reduceHealth(double h){
        this.currentHealth-=h;
        if(this.currentHealth <= 0){
            this.enemyAttackTimer.cancel();
        }
        this.hpObs.reportHealthChange(currentHealth/maxHealth);
    }

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


            this.hitbox.moveAlongVector(this.hitbox.centre.getDirectionVector(this.map.map[nextXsquare][nextYsquare].squareCentre));

            int newposX = this.hitbox.centre.getXindex();
            int newposY = this.hitbox.centre.getYindex();

            if(!(oldposX == newposX && oldposY == newposY)){
                this.map.reportNewIndexEnemy(new Vector(oldposX, oldposY),new Vector(newposX, newposY), this);
            }

        }



    }

    public double distanceFromCastle(){
        return Math.sqrt(Math.pow((this.map.castleCentre.squareCentre.getX() - this.hitbox.centre.getX()),2) +
                Math.pow((this.map.castleCentre.squareCentre.getY() - this.hitbox.centre.getY()),2));
    }

    public void draw(GraphicsContext gc){
        gc.drawImage(sprite,hitbox.upperLeft.getX(), hitbox.upperLeft.getY());
        this.hpObs.draw(gc,this.hitbox);
    }

    public RectangularHitbox getHitbox(){
        return  this.hitbox;
    }

    public Integer getGoldGiven(){
        return this.goldOnDeath;
    }
}
