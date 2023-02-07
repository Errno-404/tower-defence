package agh.ics.oop.buildings;


import agh.ics.oop.Constants;
import agh.ics.oop.GameEngine;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.AttackingBuildings.BasicTower;
import agh.ics.oop.buildings.AttackingBuildings.CircleShootingBuilding;
import agh.ics.oop.buildings.AttackingBuildings.SpreadShootingTower;
import agh.ics.oop.buildings.DefensiveBuildings.HorizontalWall;
import agh.ics.oop.buildings.DefensiveBuildings.VerticalWall;
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

                    // TODO give parameters

                    Image img = new Image( new FileInputStream(Constants.basicTowerImagePath));
                    return new BasicTower(Constants.basicTowerWidth, Constants.basicTowerHeight, position,Constants.basicTowerAttackSpeed, Constants.basicTowerAttackStrength, gs, img, ge);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            case HORIZONTALWALL:{
                Vector position = new Vector(px ,py);
                try {
                    return new HorizontalWall(5, 1, position, new Image(new FileInputStream("src/main/resources/WallHorizontal.png")), gs, 200, 0.5);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            case VERTICALWALL:{
                Vector position = new Vector(px ,py);
                try {
                    return new VerticalWall(1, 5, position, new Image(new FileInputStream("src/main/resources/VerticalWall.png")), gs, 200, 0.5);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            case CIRCLEBUILDING:{
                Vector position = new Vector(px ,py);
                try {
                    return new CircleShootingBuilding(2, 2, position, 1000,30,gs, new Image(new FileInputStream("src/main/resources/CircleTower.png")),ge);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            case SPREADSHOOTING:{
                Vector position = new Vector(px ,py);
                try {
                    return new SpreadShootingTower(2, 2, position, 1000,30,gs, new Image(new FileInputStream("src/main/resources/SpreadShootingTower.png")),ge);
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
