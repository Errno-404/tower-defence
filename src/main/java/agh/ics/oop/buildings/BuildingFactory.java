package agh.ics.oop.buildings;


import agh.ics.oop.gui.GameScreen;

import java.io.FileNotFoundException;

public class BuildingFactory {

    public static Building getBuildingById(Integer id, int px, int py, GameScreen gs){
        switch(id){
            case 1:{
                try{
                return new Castle(px, py, gs);
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
