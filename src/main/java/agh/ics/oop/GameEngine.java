package agh.ics.oop;


import agh.ics.oop.Hitboxes.RectangularHitbox;
import agh.ics.oop.Interfaces.BuildingDestroyedObserver;
import Attacks.HomingProjectileTestClass;
import Attacks.Projectile;
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

    public LinkedList<Projectile> friendlyProjectiles = new LinkedList<>();

    public LinkedList<Projectile> friendlyProjectilesToRemove = new LinkedList<>();
    public LinkedList<Projectile> enemyProjectiles = new LinkedList<>();

    public LinkedList<Projectile> enemyProjectilesToRemove = new LinkedList<>();

    public LinkedList<Enemy> enemies = new LinkedList<>();

    public LinkedList<AttackingBuilding> activeTowers = new LinkedList<>();
    public LinkedList<AttackingBuilding> waitingTowers = new LinkedList<>();

    public LinkedList<DefensiveBuilding> defensiveBuildings = new LinkedList<>();

    public LinkedList<Building> destroyedBuildings = new LinkedList<>();

    public LinkedList<Enemy> deadEnemies = new LinkedList<>();

    public GameEngine(GameScreen gs){
        this.gs = gs;

        this.gameMap = new GameMap(Constants.boxNoWidth, Constants.boxNoHeight, this.gs);
    }


    //test
    public void addProjectile(boolean type){
        Random rand = new Random();
        HomingProjectileTestClass h1 = new HomingProjectileTestClass(new Vector(rand.nextDouble(0,600),
                rand.nextDouble(0,600)),
                rand.nextDouble(3,4));
        h1.setObserver(this.gameMap);
        if(type){
            this.friendlyProjectiles.add(h1);
        }
        else{
            this.enemyProjectiles.add(h1);
        }

        this.gameMap.addProjectile(h1, type);
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
        this.friendlyProjectiles.forEach(Projectile::move);
        this.enemyProjectiles.forEach(Projectile::move);
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

    private boolean checkEnemyHitAtPosition(int x, int y, Enemy e){
        for (Projectile projectile : this.gameMap.map[x][y].friendlyProjectileList) {
            if(projectile.getHitbox().collidesWith(e.getHitbox())){
                projectile.hit(e);
                this.gameMap.projectileHit(projectile,true);

                if(e.currentHealth <= 0){
                    this.deadEnemies.add(e);
                    return true;
                }

            }
        }
        return false;
    }
    
    
    private void checkEnemyCollisions(){
        this.enemies.forEach((Enemy e) -> {
            RectangularHitbox h1 = e.getHitbox();

            boolean died = false;

            int upLeftX = h1.upperLeft.getXindex();
            int upLeftY = h1.upperLeft.getYindex();

            int lowRightX = h1.lowerRight.getXindex();
            int lowRightY = h1.lowerRight.getYindex();

            int centerX = h1.centre.getXindex();
            int centerY = h1.centre.getYindex();

            died = checkEnemyHitAtPosition(upLeftX, upLeftY, e);

            if(!died){
                died = checkEnemyHitAtPosition(lowRightX,lowRightY,e);
            }
            if(!died){
                died = checkEnemyHitAtPosition(centerX, centerY, e);
            }
            if(!died){
                died = checkEnemyHitAtPosition(Math.max(0,centerX-1), centerY,e);
            }
            if(!died){
                died = checkEnemyHitAtPosition(centerX, Math.max(0,centerY-1), e);
            }
            if(!died){
                died = checkEnemyHitAtPosition(Math.min(Constants.boxNoWidth-1, centerX+1),centerY, e);
            }
            if(!died){
                died = checkEnemyHitAtPosition(centerX,Math.min(Constants.boxNoHeight-1, centerY+1), e);
            }
            if(!died){
                died = checkEnemyHitAtPosition(Math.min(Constants.boxNoWidth-1,centerX+1),Math.min(Constants.boxNoHeight-1, centerY+1), e);
            }
            if(!died){
                died = checkEnemyHitAtPosition(Math.min(Constants.boxNoWidth-1,centerX-1),Math.min(Constants.boxNoHeight-1, centerY-1), e);
            }
        });
    }
    
    private void checkBuildingCollisions(LinkedList<Building> buildings){
        
        buildings.forEach((Building b) -> {
            Vector ul = b.hitbox.upperLeft;
            Vector lr = b.hitbox.lowerRight;
            for(int i = ul.getXindex();i<ul.getXindex() + b.getWidth();i++){
                for(int j = lr.getYindex();j < lr.getYindex() + b.getHeight(); j++){
                    for (Projectile projectile : this.gameMap.map[i][j].enemyProjectileList) {
                        if(projectile.getHitbox().collidesWith(b.hitbox)){
                            projectile.hit(b);
                            this.gameMap.projectileHit(projectile, false);
                        }
                    }

                }
            }
        });
    }

    public void checkCollisions(){
        checkEnemyCollisions();


        this.defensiveBuildings.forEach((Building b) -> {
            Vector ul = b.hitbox.upperLeft;
            Vector lr = b.hitbox.lowerRight;
            for(int i = ul.getXindex();i<lr.getXindex();i++){
                for(int j = ul.getYindex();j < lr.getYindex(); j++){
                    for (Projectile projectile : this.gameMap.map[i][j].enemyProjectileList) {
                        //System.out.println(this.enemyProjectiles);
                        if(projectile.getHitbox().collidesWith(b.hitbox)){
                            //System.out.println("hit detected!");
                            projectile.hit(b);
                            this.enemyProjectilesToRemove.add(projectile);
                            this.gameMap.projectileHit(projectile, false);
                        }
                    }

                }
            }

            this.gameMap.clearUsedProjectilesInRange(ul.getXindex()-1, ul.getYindex()-1, lr.getXindex()+1, lr.getYindex()+1, false);
            this.enemyProjectiles.removeAll(this.enemyProjectilesToRemove);
            this.enemyProjectilesToRemove.clear();
        });



        this.waitingTowers.forEach((Building b) -> {
            Vector ul = b.hitbox.upperLeft;
            Vector lr = b.hitbox.lowerRight;
            for(int i = ul.getXindex();i<lr.getXindex() + b.getWidth();i++){
                for(int j = ul.getYindex();j < lr.getYindex() + b.getHeight(); j++){
                    for (Projectile projectile : this.gameMap.map[i][j].enemyProjectileList) {
                        if(projectile.getHitbox().collidesWith(b.hitbox)){
                            projectile.hit(b);
                            this.enemyProjectilesToRemove.add(projectile);
                            this.gameMap.projectileHit(projectile, false);
                        }
                    }

                }
            }
            this.gameMap.clearUsedProjectilesInRange(ul.getXindex()-1, ul.getYindex()-1, lr.getXindex()+1, lr.getYindex()+1, false);
            this.enemyProjectiles.removeAll(this.enemyProjectilesToRemove);
            this.enemyProjectilesToRemove.clear();
        });

        this.enemyProjectiles.removeAll(this.enemyProjectilesToRemove);
        this.enemyProjectilesToRemove.clear();

        this.activeTowers.forEach((Building b) -> {
            Vector ul = b.hitbox.upperLeft;
            Vector lr = b.hitbox.lowerRight;
            for(int i = ul.getXindex();i<lr.getXindex() + b.getWidth();i++){
                for(int j = ul.getYindex();j < lr.getYindex() + b.getHeight(); j++){
                    for (Projectile projectile : this.gameMap.map[i][j].enemyProjectileList) {
                        if(projectile.getHitbox().collidesWith(b.hitbox)){
                            projectile.hit(b);
                            this.enemyProjectilesToRemove.add(projectile);
                            this.gameMap.projectileHit(projectile, false);
                        }
                    }

                }
            }
            this.gameMap.clearUsedProjectilesInRange(ul.getXindex()-1, ul.getYindex()-1, lr.getXindex()+1, lr.getYindex()+1, false);
            this.enemyProjectiles.removeAll(this.enemyProjectilesToRemove);
            this.enemyProjectilesToRemove.clear();
        });

        this.enemyProjectiles.removeAll(this.enemyProjectilesToRemove);
        this.enemyProjectilesToRemove.clear();
    }
}
