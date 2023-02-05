package agh.ics.oop;


import agh.ics.oop.Enemies.Enemy;
import agh.ics.oop.Hitboxes.RectangularHitbox;
import agh.ics.oop.Interfaces.BuildingDestroyedObserver;
import agh.ics.oop.Attacks.HomingProjectileTestClass;
import agh.ics.oop.Attacks.Projectile;
import agh.ics.oop.buildings.AttackingBuildings.AttackingBuilding;
import agh.ics.oop.buildings.Building;
import agh.ics.oop.buildings.DefensiveBuildings.DefensiveBuilding;
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

    public LinkedList<AttackingBuilding> towers = new LinkedList<>();

    public LinkedList<DefensiveBuilding> defensiveBuildings = new LinkedList<>();

    public LinkedList<Building> destroyedBuildings = new LinkedList<>();

    public LinkedList<Enemy> deadEnemies = new LinkedList<>();

    public GameEngine(GameScreen gs){
        this.gs = gs;

//        this.gameMap = new GameMap(Constants.boxNoWidth, Constants.boxNoHeight, this.gs);
        this.gameMap = new GameMap(this.gs);
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

    public void addProjectileReal(boolean type, Projectile p){
        p.setObserver(this.gameMap);
        p.addOutObserver(this.gameMap);
        if(type){
            this.friendlyProjectiles.add(p);
        }
        else{
            this.enemyProjectiles.add(p);
        }

        this.gameMap.addProjectile(p, type);

    }

    public void addEnemiesToTowers(){
        this.enemies.forEach((Enemy e) -> {
            int x = e.getHitbox().centre.getXindex();
            int y = e.getHitbox().getCentre().getYindex();

            this.gameMap.map[x][y].inRangeOf.forEach((AttackingBuilding a) -> {
                a.addEnemyInRange(e);
            });
        });
    }

    public void clearEnemiesInTowers(){
        this.towers.forEach(AttackingBuilding::clearEnemies);
    }

    public void addBuilding(Building b){
        if(this.gameMap.canPlace(b.hitbox)){
            if(b instanceof DefensiveBuilding b1){
                this.defensiveBuildings.add(b1);
            }
            else if(b instanceof AttackingBuilding b2){
                this.towers.add(b2);
            }
            b.addDestroyedObserver(this);
            b.addDestroyedObserver(this.gameMap);
            this.gameMap.placeMap(b);
        }
        else{
            System.out.println("cant place!!!!");
        }
    }

    public void addEnemy(Enemy e){
        this.enemies.add(e);
        this.gameMap.addEnemy(e);
    }
    public void moveProjectiles(){
        this.gameMap.clearAllProjectiles();

        this.friendlyProjectiles.forEach((Projectile p) ->{
            p.move();
            if(p.getHitbox().centre.getX() <= 5 || p.getHitbox().centre.getY() <= 5 ||
            p.getHitbox().centre.getX() >= 595 || p.getHitbox().centre.getY() >= 595){
                this.friendlyProjectilesToRemove.add(p);
            }
        });
        this.enemyProjectiles.forEach((Projectile p) ->{
            p.move();
            if(p.getHitbox().centre.getX() <= 5 || p.getHitbox().centre.getY() <= 5 ||
                    p.getHitbox().centre.getX() >= 595 || p.getHitbox().centre.getY() >= 595){
                this.enemyProjectilesToRemove.add(p);
            }
        });

        this.friendlyProjectiles.removeAll(this.friendlyProjectilesToRemove);
        this.friendlyProjectilesToRemove.clear();

        this.enemyProjectiles.removeAll(this.enemyProjectilesToRemove);
        this.enemyProjectilesToRemove.clear();

        addProjectilesToTiles();
    }

    public void addProjectilesToTiles(){
        this.friendlyProjectiles.forEach((Projectile p) -> {
            int x = p.getHitbox().centre.getXindex();
            int y = p.getHitbox().centre.getYindex();

            if(gameMap.isOnMap(x,y)){
                this.gameMap.map[x][y].friendlyProjectileList.add(p);
            }
        });

        this.enemyProjectiles.forEach((Projectile p) -> {
            int x = p.getHitbox().centre.getXindex();
            int y = p.getHitbox().centre.getYindex();

            if(gameMap.isOnMap(x,y)){
                this.gameMap.map[x][y].enemyProjectileList.add(p);
            }
        });
    }


    @Override
    public void reportBuildingDestroyed(Building b) {
        if (b instanceof DefensiveBuilding){
            this.defensiveBuildings.remove(b);
        }
        else if(b instanceof AttackingBuilding){
            this.towers.remove(b);
            //TODO
        }
    }

    private boolean checkEnemyHitAtPosition(int x, int y, Enemy e){
        if(!this.gameMap.isOnMap(x,y)){
            return false;
        }


        for (Projectile projectile : this.gameMap.map[x][y].friendlyProjectileList) {

            if(projectile.getHitbox().collidesWith(e.getHitbox())){
                projectile.hit(e);
                this.friendlyProjectilesToRemove.add(projectile);
                this.gameMap.projectileHit(projectile,true);

                if(e.currentHealth <= 0){
                    this.deadEnemies.add(e);


                    this.gameMap.clearUsedFriendlyProjectiles(this.friendlyProjectilesToRemove);
                    this.friendlyProjectiles.removeAll(friendlyProjectilesToRemove);
                    this.friendlyProjectilesToRemove.clear();
                    return true;
                }

            }
        }

        this.gameMap.clearUsedFriendlyProjectiles(this.friendlyProjectilesToRemove);

        this.friendlyProjectiles.removeAll(friendlyProjectilesToRemove);
        this.friendlyProjectilesToRemove.clear();
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
                died = checkEnemyHitAtPosition(Math.min(Constants.numberOfTiles -1, centerX+1),centerY, e);
            }
            if(!died){
                died = checkEnemyHitAtPosition(centerX,Math.min(Constants.numberOfTiles -1, centerY+1), e);
            }
            if(!died){
                died = checkEnemyHitAtPosition(Math.min(Constants.numberOfTiles -1,centerX+1),Math.min(Constants.numberOfTiles -1, centerY+1), e);
            }
            if(!died){
                died = checkEnemyHitAtPosition(Math.min(Constants.numberOfTiles -1,centerX-1),Math.min(Constants.numberOfTiles -1, centerY-1), e);
            }
        });


    }
    
    private void checkBuildingCollisions(LinkedList<Building> buildings){
        
        buildings.forEach((Building b) -> {
            Vector ul = b.hitbox.upperLeft;
            Vector lr = b.hitbox.lowerRight;
            for(int i = ul.getXindex(); i<ul.getXindex() + b.getWidthInTiles(); i++){
                for(int j = lr.getYindex(); j < lr.getYindex() + b.getHeightInTiles(); j++){
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


        this.deadEnemies.forEach((Enemy e) -> {
            this.gameMap.removeEnemy(e);
        });

        this.enemies.removeAll(deadEnemies);
        this.deadEnemies.clear();


        this.defensiveBuildings.forEach((Building b) -> {
            Vector ul = b.hitbox.upperLeft;
            Vector lr = b.hitbox.lowerRight;
            for(int i = ul.getXindex();i<lr.getXindex();i++){
                for(int j = ul.getYindex();j < lr.getYindex(); j++){
                    for (Projectile projectile : this.gameMap.map[i][j].enemyProjectileList) {
                        if(projectile.getHitbox().collidesWith(b.hitbox)){
                            projectile.hit(b);


                            this.enemyProjectilesToRemove.add(projectile);
                            this.gameMap.projectileHit(projectile, false);
                            if(b.getCurrentHealth() <= 0){
                                this.destroyedBuildings.add(b);
                                break;
                            }
                        }
                    }

                }
            }

            this.gameMap.clearUsedEnemyProjectiles(this.enemyProjectilesToRemove);
            this.enemyProjectiles.removeAll(this.enemyProjectilesToRemove);
            this.enemyProjectilesToRemove.clear();
        });



        this.towers.forEach((Building b) -> {
            Vector ul = b.hitbox.upperLeft;
            Vector lr = b.hitbox.lowerRight;
            for(int i = ul.getXindex(); i<lr.getXindex() + b.getWidthInTiles(); i++){
                for(int j = ul.getYindex(); j < lr.getYindex() + b.getHeightInTiles(); j++){
                    for (Projectile projectile : this.gameMap.map[i][j].enemyProjectileList) {
                        if(projectile.getHitbox().collidesWith(b.hitbox)){
                            projectile.hit(b);
                            this.enemyProjectilesToRemove.add(projectile);
                            this.gameMap.projectileHit(projectile, false);

                            if(b.getCurrentHealth() <= 0){
                                this.destroyedBuildings.add(b);
                                break;
                            }
                        }
                    }

                }
            }
            this.gameMap.clearUsedEnemyProjectiles(this.enemyProjectilesToRemove);
            this.enemyProjectiles.removeAll(this.enemyProjectilesToRemove);
            this.enemyProjectilesToRemove.clear();
        });

        //this.enemyProjectiles.removeAll(this.enemyProjectilesToRemove);
        //this.enemyProjectilesToRemove.clear();

        /*this.activeTowers.forEach((Building b) -> {
            Vector ul = b.hitbox.upperLeft;
            Vector lr = b.hitbox.lowerRight;
            for(int i = ul.getXindex(); i<lr.getXindex() + b.getWidthInTiles(); i++){
                for(int j = ul.getYindex(); j < lr.getYindex() + b.getHeightInTiles(); j++){
                    for (Projectile projectile : this.gameMap.map[i][j].enemyProjectileList) {
                        if(projectile.getHitbox().collidesWith(b.hitbox)){
                            projectile.hit(b);
                            this.enemyProjectilesToRemove.add(projectile);
                            this.gameMap.projectileHit(projectile, false);

                            if(b.getCurrentHealth() <= 0){
                                this.destroyedBuildings.add(b);
                                break;
                            }
                        }
                    }

                }
            }
            this.gameMap.clearUsedEnemyProjectiles(this.enemyProjectilesToRemove);
            this.enemyProjectiles.removeAll(this.enemyProjectilesToRemove);
            this.enemyProjectilesToRemove.clear();
        });*/
        /*this.gameMap.clearUsedProjectiles(this.friendlyProjectilesToRemove, this.enemyProjectilesToRemove);

        this.enemyProjectiles.removeAll(this.enemyProjectilesToRemove);
        this.enemyProjectilesToRemove.clear();

        this.friendlyProjectiles.removeAll(this.friendlyProjectilesToRemove);
        this.friendlyProjectilesToRemove.clear();*/
    }

    public void removeRemainingProjectiles(){
        this.friendlyProjectiles.removeAll(this.friendlyProjectilesToRemove);
        this.enemyProjectiles.removeAll(this.enemyProjectilesToRemove);

        this.friendlyProjectilesToRemove.clear();
        this.enemyProjectilesToRemove.clear();
    }

}
