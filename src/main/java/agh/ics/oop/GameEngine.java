package agh.ics.oop;


import agh.ics.oop.Interfaces.BuildingDestroyedObserver;
import agh.ics.oop.Proejctiles.HomingProjectileTestClass;
import agh.ics.oop.Proejctiles.Projectile;
import agh.ics.oop.buildings.AttackingBuilding;
import agh.ics.oop.buildings.Building;
import agh.ics.oop.buildings.DefensiveBuilding;
import agh.ics.oop.gui.GameScreen;
import agh.ics.oop.maps.GameMap;

import java.util.LinkedList;
import java.util.Random;

public class GameEngine implements BuildingDestroyedObserver {
    public GameScreen gs;
    public GameMap gameMap;

    public LinkedList<Projectile> projectiles = new LinkedList<>();
    public LinkedList<Enemy> enemies = new LinkedList<>();

    public LinkedList<AttackingBuilding> activeTowers = new LinkedList<>();
    public LinkedList<AttackingBuilding> waitingTowers = new LinkedList<>();

    public LinkedList<DefensiveBuilding> defensiveBuildings = new LinkedList<>();

    public LinkedList<Building> destroyedBuildings = new LinkedList<>();

    public GameEngine(GameScreen gs){
        this.gs = gs;

        this.gameMap = new GameMap(Constants.boxNoWidth, Constants.boxNoHeight, this.gs);
    }


    //test
    public void addProjectile(){
        Random rand = new Random();
        HomingProjectileTestClass h1 = new HomingProjectileTestClass(new Vector(rand.nextDouble(0,600),
                rand.nextDouble(0,600)),
                rand.nextDouble(3,4));
        h1.setObserver(this.gameMap);
        this.projectiles.add(h1);
        this.gameMap.addProjectile(h1);
    }

    public void addBuilding(Building b){
        if(this.gameMap.canPlace(b.hitbox)){
            if(b instanceof DefensiveBuilding b1){
                this.defensiveBuildings.add(b1);
            }
            else if(b instanceof AttackingBuilding b2){
                this.activeTowers.add(b2);
            }
            b.addDestroyedObserver(this);
            b.addDestroyedObserver(this.gameMap);
            this.gameMap.placeMap(b);
        }
        else{
            System.out.println("cant place!!!!");
        }
    }
    public void moveProjectiles(){
        this.projectiles.forEach(Projectile::move);
    }

    @Override
    public void reportBuildingDestroyed(Building b) {
        if (b instanceof DefensiveBuilding){
            this.defensiveBuildings.remove(b);
        }
        else if(b instanceof AttackingBuilding){
            //TODO
        }
    }
}
