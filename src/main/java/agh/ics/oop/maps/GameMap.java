package agh.ics.oop.maps;

import agh.ics.oop.Enemies.Enemy;
import agh.ics.oop.Constants;
import agh.ics.oop.Hitboxes.RectangularHitbox;
import agh.ics.oop.Interfaces.BuildingDestroyedObserver;
import agh.ics.oop.Interfaces.EnemyObserver;
import agh.ics.oop.Interfaces.ProjectileObserver;
import agh.ics.oop.Attacks.Projectile;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.AttackingBuildings.AttackingBuilding;
import agh.ics.oop.buildings.Building;
import agh.ics.oop.buildings.Castle;
import agh.ics.oop.gui.GameScreen;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class GameMap implements ProjectileObserver, EnemyObserver, BuildingDestroyedObserver {
//    protected int mapWidth;
//    protected int height;
    GameScreen gameScreen;

    public mapElement[][] map;

    public mapElement castleCentre;


    public GameMap(GameScreen gs){
        this.gameScreen = gs;

        this.map = new mapElement[Constants.numberOfTiles +1][Constants.numberOfTiles +1];

        for(int i = 0; i<Constants.numberOfTiles +1; i++){
            for(int j = 0; j<Constants.numberOfTiles +1; j++){
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
        castleCentre.mapWeightValue = 0;

        boolean[][] visited = new boolean[Constants.numberOfTiles][Constants.numberOfTiles];
        for(int i = 0; i< Constants.numberOfTiles; i++){
            for(int j = 0; j<Constants.numberOfTiles; j++){
                visited[i][j] = false;
            }
        }
        visited[castleCentre.x][castleCentre.y] = true;

        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
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

        queue.add(new Pair<>(castleCentre.x, castleCentre.y));

        int itercount = 0;

        while(!queue.isEmpty()){
            itercount+=1;
            Pair<Integer, Integer> top = queue.element();
            queue.remove();
            visited[top.getKey()][top.getValue()] = true;



            for(Pair<Integer, Integer> move: moves){
                int nextPosX = top.getKey() + move.getKey();
                int nextPosY = top.getValue() + move.getValue();

                if(isOnMap(nextPosX, nextPosY)){
                    if(!this.map[nextPosX][nextPosY].reachable || visited[nextPosX][nextPosY]){
                        continue;
                    }
                    else if(this.map[nextPosX][nextPosY].buildingID != null){
                        this.map[nextPosX][nextPosY].mapWeightValue = this.map[top.getKey()][top.getValue()].mapWeightValue + 10;
                        queue.add(new Pair<>(nextPosX, nextPosY));
                    }
                    else{
                        this.map[nextPosX][nextPosY].mapWeightValue = this.map[top.getKey()][top.getValue()].mapWeightValue + 1;
                        queue.add(new Pair<>(nextPosX, nextPosY));
                    }

                    visited[nextPosX][nextPosY] = true;

                }
            }
        }
        System.out.println("done after: " + itercount);

    }

    public boolean canPlace(RectangularHitbox hb){
        for(int i = (int) hb.upperLeft.getXindex(); i<hb.lowerRight.getXindex(); i++){
            for(int j = (int) hb.upperLeft.getYindex(); j<hb.lowerRight.getYindex(); j++){
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

    public void placeMap(Building building){
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

    public void clearUsedProjectilesInRange(int x1, int y1, int x2, int y2, boolean isFriendly){
        for(int i = x1; i< x2;i++){
            for(int j = y1; j < y2; j++){
                this.map[i][j].clearUsedProjectiles(isFriendly);
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
            }
        }

        updateMapWeights();

        if(b instanceof AttackingBuilding){
            //remove building from all inRangeOf lists
        }
    }

    public void clearUsedProjectiles(LinkedList<Projectile> lp1, LinkedList<Projectile> lp2){
        lp1.forEach((Projectile p) -> {
            int x = p.getHitbox().centre.getXindex();
            int y = p.getHitbox().centre.getYindex();

            this.map[x][y].friendlyProjectileList.remove(p);
        });

        lp2.forEach((Projectile p) -> {
            int x = p.getHitbox().centre.getXindex();
            int y = p.getHitbox().centre.getYindex();

            this.map[x][y].enemyProjectileList.remove(p);
        });
    }

    public void clearUsedEnemyProjectiles(LinkedList<Projectile> p1){
        p1.forEach((Projectile p) -> {
            int x = p.getHitbox().centre.getXindex();
            int y = p.getHitbox().centre.getYindex();

            this.map[x][y].enemyProjectileList.remove(p);
        });
    }

    public void clearUsedFriendlyProjectiles(LinkedList<Projectile> p2){
        p2.forEach((Projectile p) -> {
            int x = p.getHitbox().centre.getXindex();
            int y = p.getHitbox().centre.getYindex();

            this.map[x][y].friendlyProjectileList.remove(p);
        });
    }

    public int sumProj(){
        int acc = 0;
        for(int i = 0; i<Constants.numberOfTiles +1; i++){
            for(int j = 0; j<Constants.numberOfTiles +1; j++){
                acc+=map[i][j].friendlyProjectileList.size();
            }
        }

        return acc;
    }
}
