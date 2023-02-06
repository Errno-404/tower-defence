package agh.ics.oop.buildings;

public enum BuildingsName {
    CASTLE,
    TOWER,
    WALL;

    public int convert(){
        return switch (this){

            case CASTLE -> 0;
            case TOWER -> 1;
            case WALL -> 2;
        };
    }
}
