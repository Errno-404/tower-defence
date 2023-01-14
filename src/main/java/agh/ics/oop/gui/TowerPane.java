package agh.ics.oop.gui;

import agh.ics.oop.buildings.Building;

import java.util.ArrayList;

public class TowerPane {

    ArrayList<Building> buildingList;
    GameScreen gs;

    public TowerPane(GameScreen gs ,ArrayList<Building> blist){
        this.gs = gs;
        this.buildingList = blist;
        //TODO Make list of buttons, on button click call GameScreen.setSelectedListBuilding
    }
}
