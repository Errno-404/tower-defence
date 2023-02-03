package agh.ics.oop.maps;

import agh.ics.oop.Enemy;
import agh.ics.oop.Constants;
import agh.ics.oop.Hitboxes.RectangularHitbox;
import agh.ics.oop.Interfaces.BuildingDestroyedObserver;
import agh.ics.oop.Interfaces.EnemyObserver;
import agh.ics.oop.Interfaces.ProjectileObserver;
import Attacks.Projectile;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.AttackingBuilding;
import agh.ics.oop.buildings.Building;
import agh.ics.oop.gui.GameScreen;


public class GameMap implements ProjectileObserver, EnemyObserver, BuildingDestroyedObserver {
    protected int width;
    protected int height;
    GameScreen gameScreen;

    public mapElement[][] map;



    public GameMap(int width, int height, GameScreen gs){
        this.height = height;
        this.width = width;
        this.gameScreen = gs;

        this.map = new mapElement[Constants.boxNoWidth+1][Constants.boxNoHeight+1];

        for(int i = 0;i<Constants.boxNoWidth+1;i++){
            for(int j = 0;j<Constants.boxNoHeight+1;j++){
                map[i][j] = new mapElement(i,j, gs.elements[i][j]);
            }
        }
    }

    public boolean canPlace(RectangularHitbox hb){
        for(int i = (int) hb.upperLeft.getXindex(); i<hb.lowerRight.getXindex(); i++){
            for(int j = (int) hb.upperLeft.getYindex(); j<hb.lowerRight.getYindex(); j++){
                if(i < 0 || i >= Constants.boxNoWidth || j < 0 || j >= Constants.boxNoHeight){
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

        for(int i = xIndex;i< xIndex + building.getWidth();i++){
            for(int j = yIndex;j< yIndex + building.getHeight();j++){
                this.map[i][j].updateCanvas(building.getView(i- xIndex,j - yIndex));
                this.map[i][j].placeable = false;
                this.map[i][j].reachable = false;
            }
        }

        if(building instanceof AttackingBuilding){
            //Add building to inRangeOf lists
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

    @Override
    public void reportNewIndexProjectile(Vector old, Vector newpos, Projectile p) {
        try {
            int oldX = (int) old.getX();
            int oldY = (int) old.getY();

            int newX = (int) newpos.getX();
            int newY = (int) newpos.getY();
            if(this.map[oldX][oldY].friendlyProjectileList.contains(p)){
                this.map[oldX][oldY].friendlyProjectileList.remove(p);
                this.map[(int) newpos.getX()][(int) newpos.getY()].friendlyProjectileList.add(p);
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

        for(int i = upx; i<upx+b.getWidth();i++){
            for(int j = upy;j<upy + b.getHeight();j++){
                this.gameScreen.elements[i][j].setOriginalView();
                this.map[i][j].reachable = true;
                this.map[i][j].placeable = true;
            }
        }

        if(b instanceof AttackingBuilding){
            //remove building from all inRangeOf lists
        }
    }

    public int sumProj(){
        int acc = 0;
        for(int i = 0;i<Constants.boxNoWidth+1;i++){
            for(int j = 0;j<Constants.boxNoHeight+1;j++){
                acc+=map[i][j].friendlyProjectileList.size();
            }
        }

        return acc;
    }
}
