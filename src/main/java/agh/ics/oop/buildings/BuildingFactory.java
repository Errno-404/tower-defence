package agh.ics.oop.buildings;


import agh.ics.oop.GameEngine;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.AttackingBuildings.BasicTower;
import agh.ics.oop.buildings.DefensiveBuildings.Wall;
import agh.ics.oop.gui.GameScreen;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BuildingFactory {

    public static Building getBuildingById(BuildingsName id, int px, int py, GameScreen gs, GameEngine ge){
        switch(id){
            case CASTLE:{
                try{

                Vector position = new Vector(px, py);
                return new Castle(position, gs);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            case TOWER:{
                try{
                    Vector position = new Vector(px ,py);
                    return new BasicTower(position,gs, ge);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            case WALL:{
                Vector position = new Vector(px ,py);
                try {
                    return new Wall(5, 1, position, new Image(new FileInputStream("src/main/resources/WallHorizontal.png")), gs, 200, 10);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            default:{
                throw new RuntimeException();
            }
        }

    }

    /*public Castle getCastle(){
        return new Castle()
    }*/

    /*public DefensiveBuilding() getDefensiveBuilding(){
        return new DefensiveBuilding()
    }*/

    /*public AttackingBuilding getAttackingBuilding1(){
        return new AttackingBuilding();
    }*/
}
