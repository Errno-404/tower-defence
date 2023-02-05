package agh.ics.oop.buildings;


import agh.ics.oop.GameEngine;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.AttackingBuildings.BasicTower;
import agh.ics.oop.gui.GameScreen;

import java.io.FileNotFoundException;

public class BuildingFactory {

    public static Building getBuildingById(Integer id, int px, int py, GameScreen gs, GameEngine ge){
        switch(id){
            case 1:{
                try{

                Vector position = new Vector(px, py);
                return new Castle(position, gs);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            case 2:{
                try{
                    Vector position = new Vector(px ,py);
                    return new BasicTower(position,gs, ge);
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
