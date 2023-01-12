package agh.ics.oop.maps;

import agh.ics.oop.Vector;
import agh.ics.oop.buildings.Building;

import java.util.HashMap;


public abstract class GameMap {
    protected int width;
    protected int height;


    // TODO struktura danych odpowiednia dla metody canPlace
    HashMap<Vector, Building> buildings = new HashMap<>();

    protected GameMap(int width, int height){
        this.height = height;
        this.width = width;
    }

    private boolean canPlace(Vector vector, int width, int height){
        //TODO jak sprawdzić czy obszar jest wolny?
        return true;
    }

    //TODO może dodać jakiś interfejs typu IMapElement, żeby móc jedną metodą dodawać przeszkody i budynki?
    public void place(Building building){
        Vector pos = building.getPosition();
        int width = building.getWidth();
        int height = building.getHeight();
        if(canPlace(pos, width,height)){
            System.out.println("There is some free space here :>");
        }

        //TODO w zależności od struktury danych
    }

}
