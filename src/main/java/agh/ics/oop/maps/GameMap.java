package agh.ics.oop.maps;

import agh.ics.oop.Vector;
import agh.ics.oop.buildings.Building;
import agh.ics.oop.gui.GameScreen;

import java.util.HashMap;


public abstract class GameMap {
    protected Vector lowerLeft;
    protected Vector upperRight;
    protected double cellWidth;


    GameScreen gameScreen;


    protected int[][] freePlaces;


    // TODO struktura danych odpowiednia dla metody canPlace
    HashMap<Vector, Building> towers = new HashMap<>();
    HashMap<Vector, Building> otherBuildings = new HashMap<>();
    HashMap<Vector, Building> enemies = new HashMap<>();


    protected GameMap(int width, int height, double cellWidth) {
        // width - ile pól ma mapa w szerokości
        // height - ile pól ma mapa w długości
        // cellWidth - jaka jest szerokość pola

        this.freePlaces = new int[width][height];
        this.lowerLeft = new Vector(0, 0);
        this.upperRight = new Vector(cellWidth * width, cellWidth * height);
        this.cellWidth = cellWidth;
    }

    public boolean canPlace(Vector vector, int width, int height) {
        Vector upperRight = vector.addVector(new Vector(width * this.cellWidth, height * this.cellWidth));
        if (vector.follows(this.lowerLeft) && upperRight.precedes(this.upperRight)) {
            int i = (int) (vector.getX() / this.cellWidth);
            int j = (int) (vector.getY() / this.cellWidth);
            int k = 1 + (int) (upperRight.getX() / this.cellWidth);
            int l = 1 + (int) (upperRight.getY() / this.cellWidth);


            for (int a = i; a < k; a++) {
                for (int b = j; b < l; b++) {
                    if (freePlaces[a][b] != -1) return false;
                }
            }

            return true;
        }
        else throw new IllegalArgumentException("Vector: " + vector.toString() + " is out of bounds");

    }

    //TODO może dodać jakiś interfejs typu IMapElement, żeby móc jedną metodą dodawać przeszkody i budynki?
    public void place(Building building) {
        Vector pos = building.getPosition();
        int width = building.getWidth();
        int height = building.getHeight();
        if (canPlace(pos, width, height)) {
            System.out.println("There is some free space here :>");
        }

        //TODO w zależności od struktury danych
    }

}
