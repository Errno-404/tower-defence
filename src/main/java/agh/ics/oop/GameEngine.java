package agh.ics.oop;


import agh.ics.oop.Proejctiles.HomingProjectileTestClass;
import agh.ics.oop.Proejctiles.Projectile;
import agh.ics.oop.buildings.AttackingBuilding;
import agh.ics.oop.buildings.Building;
import agh.ics.oop.buildings.DefensiveBuilding;
import agh.ics.oop.gui.GameScreen;
import agh.ics.oop.maps.GameMap;

import java.util.LinkedList;
import java.util.Random;

public class GameEngine {
    public GameScreen gs;
    public GameMap gameMap;

    public LinkedList<Projectile> projectiles = new LinkedList<>();
    public LinkedList<Attackers> enemies = new LinkedList<>();

    LinkedList<AttackingBuilding> activeTowers = new LinkedList<>();
    LinkedList<AttackingBuilding> waitingTowers = new LinkedList<>();

    LinkedList<DefensiveBuilding> defensiveBuildings = new LinkedList<>();

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
        if(b instanceof DefensiveBuilding b1){
            this.defensiveBuildings.add(b1);
        }
        else if(b instanceof AttackingBuilding b2){
            this.activeTowers.add(b2);
        }
        b.setDestroyedObserver(this.gameMap);
        this.gameMap.placeMap(b);
    }
    public void moveProjectiles(){
        this.projectiles.forEach(Projectile::move);
    }
}
