package agh.ics.oop.maps;

import agh.ics.oop.Enemies.Enemy;
import agh.ics.oop.Constants;
import agh.ics.oop.Hitboxes.RectangularHitbox;
import agh.ics.oop.Interfaces.BuildingDestroyedObserver;
import agh.ics.oop.Interfaces.EnemyObserver;
import agh.ics.oop.Interfaces.OutOfMapObserver;
import agh.ics.oop.Interfaces.ProjectileObserver;
import agh.ics.oop.Attacks.Projectile;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.AttackingBuildings.AttackingBuilding;
import agh.ics.oop.buildings.Building;
import agh.ics.oop.buildings.Castle;
import agh.ics.oop.buildings.DefensiveBuildings.DefensiveBuilding;
import agh.ics.oop.gui.GameScreen;
import javafx.util.Pair;

import java.util.*;


public class GameMap implements ProjectileObserver, EnemyObserver, BuildingDestroyedObserver, OutOfMapObserver {
//    protected int mapWidth;
//    protected int height;
    GameScreen gameScreen;

    public mapElement[][] map;

    public mapElement castleCentre;


    public GameMap(GameScreen gs){
        this.gameScreen = gs;

        this.map = new mapElement[Constants.numberOfTiles][Constants.numberOfTiles];

        for(int i = 0; i<Constants.numberOfTiles; i++){
            for(int j = 0; j<Constants.numberOfTiles; j++){
                map[i][j] = new mapElement(i,j, gs.elements[i][j]);
            }
        }
    }

//    public GameMap(int width, int height, GameScreen gs){
////        this.height = height;
////        this.mapWidth = width;
//        this.gameScreen = gs;
//
//        this.map = new mapElement[Constants.boxNoWidth+1][Constants.boxNoHeight+1];
//
//        for(int i = 0;i<Constants.boxNoWidth+1;i++){
//            for(int j = 0;j<Constants.boxNoHeight+1;j++){
//                map[i][j] = new mapElement(i,j, gs.elements[i][j]);
//            }
//        }
//    }

    public boolean isOnMap(int i, int j){
        return i >= 0 && i < Constants.numberOfTiles && j >= 0 && j < Constants.numberOfTiles;
    }
    public void updateMapWeights(){

        boolean[][] visited = new boolean[Constants.numberOfTiles][Constants.numberOfTiles];
        for(int i = 0; i< Constants.numberOfTiles; i++){
            for(int j = 0; j<Constants.numberOfTiles; j++){
                visited[i][j] = false;
                this.map[i][j].mapWeightValue = Integer.MAX_VALUE;
            }
        }


        Queue<DijkstraNode> queue = new PriorityQueue<>();
        ArrayList<Pair<Integer, Integer>> moves = new ArrayList<>() {
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

        visited[castleCentre.x][castleCentre.y] = true;
        this.map[castleCentre.x][castleCentre.y].mapWeightValue = 0;
        queue.add(new DijkstraNode(0,castleCentre.x, castleCentre.y));


        int itercount = 0;

        while(!queue.isEmpty()){
            itercount+=1;
            DijkstraNode top = queue.remove();
            visited[top.x][top.y] = true;

            for(Pair<Integer, Integer> move: moves){
                 int newX = top.x + move.getKey();
                 int newY = top.y + move.getValue();
                 if(!isOnMap(newX, newY)){
                     continue;
                 }

                 int newDist;

                 if(this.map[newX][newY].buildingID == null){
                     newDist  = this.map[top.x][top.y].mapWeightValue + 1;
                 }
                 else if(this.map[newX][newY].buildingID instanceof AttackingBuilding){
                     newDist  = this.map[top.x][top.y].mapWeightValue + 10;
                 }
                 else{
                     newDist  = this.map[top.x][top.y].mapWeightValue + 50;
                 }


                 if(newDist < this.map[newX][newY].mapWeightValue){
                     this.map[newX][newY].mapWeightValue = newDist;
                     queue.add(new DijkstraNode(newDist,newX, newY));
                }
            }
        }
//        System.out.println("done after: " + itercount);

    }

    public boolean canPlace(RectangularHitbox hb){
        for(int i = hb.upperLeft.getXindex(); i<hb.lowerRight.getXindex(); i++){
            for(int j = hb.upperLeft.getYindex(); j<hb.lowerRight.getYindex(); j++){
                if(i < 0 || i >= Constants.numberOfTiles || j < 0 || j >= Constants.numberOfTiles){
                    return false;
                }
                if (!map[i][j].placeable){
                    return false;
                }
            }
        }
        return true;
    }

    public void placeBuildingOnMap(Building building){
        Vector anchorPoint = building.getAnchorPosition();

        int xIndex = anchorPoint.getXindex();
        int yIndex = anchorPoint.getYindex();

        if(!canPlace(building.hitbox)){
            return;
        }

        if(castleCentre == null && building instanceof Castle){
            this.castleCentre = this.map[building.hitbox.centre.getXindex()][building.hitbox.centre.getYindex()];

        }



        for(int i = xIndex; i< xIndex + building.getWidthInTiles(); i++){
            for(int j = yIndex; j< yIndex + building.getHeightInTiles(); j++){
                this.map[i][j].updateCanvas(building.getView(i- xIndex,j - yIndex));
                this.map[i][j].placeable = false;
                this.map[i][j].buildingID = building;
            }
        }
        updateMapWeights();

        if(building instanceof AttackingBuilding b1){
            for(int i = 0;i < 59; i++){
                for(int j = 0; j<59;j++){
                    this.map[i][j].inRangeOf.add(b1);
                }
            }
        }
    }

    public void addProjectile(Projectile p, boolean isFriendly){
        int xIndex = p.position.getXindex();
        int yIndex = p.position.getYindex();
        if(isFriendly){
            this.map[xIndex][yIndex].friendlyProjectileList.add(p);
        }
        else{
            //System.out.println("adding projectile to " + xIndex + "   " + yIndex);
            this.map[xIndex][yIndex].enemyProjectileList.add(p);
        }

    }

    public void addEnemy(Enemy e){
        this.map[e.getHitbox().centre.getXindex()][e.getHitbox().centre.getYindex()].enemyList.add(e);
    }

    public void removeEnemy(Enemy e){
        this.map[e.getHitbox().getCentre().getXindex()][e.getHitbox().getCentre().getYindex()].enemyList.remove(e);
    }

    @Override
    public void reportNewIndexProjectile(Vector old, Vector newpos, Projectile p) {
        try {
            int oldX = old.getXindex();
            int oldY = old.getYindex();

            int newX = newpos.getXindex();
            int newY = newpos.getYindex();
            if(this.map[oldX][oldY].friendlyProjectileList.contains(p)){
                this.map[oldX][oldY].friendlyProjectileList.remove(p);
                this.map[newX][newY].friendlyProjectileList.add(p);
            }
            else{
                this.map[oldX][oldY].enemyProjectileList.remove(p);
                this.map[newX][newY].enemyProjectileList.add(p);
            }



        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println(old.getX() +" " + old.getXindex() + " " + old.getY() + " " + old.getYindex());
            System.out.println(newpos.getX() +" " + newpos.getXindex() + " " + newpos.getY() + " " + newpos.getYindex());
        }
    }

    @Override
    public void projectileHit(Projectile p, boolean isFriendly) {
        if(isFriendly){
            this.map[p.getHitbox().upperLeft.getXindex()][p.getHitbox().upperLeft.getYindex()].friendlyProjectileToRemove.add(p);
        }
        else{
            this.map[p.getHitbox().upperLeft.getXindex()][p.getHitbox().upperLeft.getYindex()].enemyProjectilesToRemove.add(p);
        }
    }

    public void clearAllProjectiles(){
        for(int i = 0;i<Constants.numberOfTiles;i++){
            for(int j = 0;j<Constants.numberOfTiles;j++){
                this.map[i][j].friendlyProjectileList.clear();
                this.map[i][j].friendlyProjectileToRemove.clear();

                this.map[i][j].enemyProjectileList.clear();
                this.map[i][j].enemyProjectilesToRemove.clear();
            }
        }
    }

    @Override
    public void reportNewIndexEnemy(Vector old, Vector newpos, Enemy a) {
        this.map[old.getXindex()][old.getYindex()].enemyList.remove(a);
        this.map[newpos.getXindex()][newpos.getYindex()].enemyList.add(a);
    }

    @Override
    public void reportBuildingDestroyed(Building b) {
        int upx = b.hitbox.upperLeft.getXindex();
        int upy = b.hitbox.upperLeft.getYindex();

        //System.out.println(upx + " " + upy);

        for(int i = upx; i<upx+b.getWidthInTiles(); i++){
            for(int j = upy; j<upy + b.getHeightInTiles(); j++){
                this.gameScreen.elements[i][j].setOriginalView();
                this.map[i][j].reachable = true;
                this.map[i][j].placeable = true;
                this.map[i][j].buildingID = null;
            }
        }

        updateMapWeights();

        if(b instanceof AttackingBuilding){
            //remove building from all inRangeOf lists
        }
    }

    public void clearUsedEnemyProjectiles(List<Projectile> p1){
        p1.forEach((Projectile p) -> {
            int x = p.getHitbox().centre.getXindex();
            int y = p.getHitbox().centre.getYindex();

            this.map[x][y].enemyProjectileList.remove(p);
        });
    }

    public void clearUsedFriendlyProjectiles(List<Projectile> p2){
        p2.forEach((Projectile p) -> {
            int x = p.getHitbox().centre.getXindex();
            int y = p.getHitbox().centre.getYindex();

            this.map[x][y].friendlyProjectileList.remove(p);
        });
    }

    public int sumProj(){
        int acc = 0;
        for(int i = 0; i<Constants.numberOfTiles; i++){
            for(int j = 0; j<Constants.numberOfTiles; j++){
                System.out.println("at " + i + " " + j + "   " + this.map[i][j].friendlyProjectileList.size());
                acc+=map[i][j].friendlyProjectileList.size();
            }
        }

        return acc;
    }

    @Override
    public void reportOutOfMap(int lastX, int lastY, Projectile p) {
        System.out.println(this.map[lastX][lastY].friendlyProjectileList.remove(p));
        System.out.println(this.map[lastX][lastY].enemyProjectileList.remove(p));
    }
}
