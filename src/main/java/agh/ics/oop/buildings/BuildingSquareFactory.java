package agh.ics.oop.buildings;

import agh.ics.oop.Constants;
import agh.ics.oop.maps.GameMap;

public class BuildingSquareFactory {

    public static BuildingCreationSquare newSquare(Integer id, GameMap map){
        Integer[] params = Constants.buildingSizes.get(id);
        return new BuildingCreationSquare(params[0], params[1], params[2], params[3], map);
    }
}
