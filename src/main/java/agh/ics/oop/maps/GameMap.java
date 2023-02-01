package agh.ics.oop.maps;

import agh.ics.oop.Attackers;
import agh.ics.oop.Constants;
import agh.ics.oop.Hitboxes.RectangularHitbox;
import agh.ics.oop.Interfaces.EnemyObserver;
import agh.ics.oop.Interfaces.ProjectileObserver;
import agh.ics.oop.Proejctiles.Projectile;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.AttackingBuilding;
import agh.ics.oop.buildings.Building;
import agh.ics.oop.gui.GameScreen;


public class GameMap implements ProjectileObserver, EnemyObserver {
    protected int width;
    protected int height;
    GameScreen gameScreen;

    mapElement[][] map = new mapElement[Constants.boxNoWidth][Constants.boxNoHeight];



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

    private boolean canPlace(Building b){
        RectangularHitbox hb = b.hitbox;
        for(int i = (int) hb.upperLeft.getX(); i<hb.lowerRight.getX(); i++){
            for(int j = (int) hb.upperLeft.getY(); j<hb.lowerRight.getY(); j++){
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

        if(!canPlace(building)){
            return;
        }

        for(int i = xIndex;i< xIndex + building.getWidth();i++){
            for(int j = yIndex;j< yIndex + building.getHeight();j++){
                System.out.println("updating element: " + i + " " + j);
                this.map[i][j].updateCanvas(building.getView(i- xIndex,j - yIndex));
            }
        }

        if(building instanceof AttackingBuilding){
            //
        }
    }

    public void addProjectile(Projectile p){
        int xIndex = p.position.getXindex();
        int yIndex = p.position.getYindex();

        this.map[xIndex][yIndex].projectileList.add(p);
    }

    @Override
    public void reportNewIndexProjectile(Vector old, Vector newpos, Projectile p) {
        try {
            this.map[old.getXindex()][old.getYindex()].projectileList.remove(p);
            this.map[newpos.getXindex()][newpos.getYindex()].projectileList.add(p);
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println(old.getX() +" " + old.getXindex() + " " + old.getY() + " " + old.getYindex());
            System.out.println(newpos.getX() +" " + newpos.getXindex() + " " + newpos.getY() + " " + newpos.getYindex());
        }
    }

    @Override
    public void reportNewIndexEnemy(Vector old, Vector newpos, Attackers a) {
        this.map[old.getXindex()][old.getYindex()].enemyList.remove(a);
        this.map[newpos.getXindex()][newpos.getYindex()].enemyList.add(a);
    }
}
