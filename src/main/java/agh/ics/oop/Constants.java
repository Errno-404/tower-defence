package agh.ics.oop;

import java.util.HashMap;

public final class Constants {
    public final static double tileSize = 30;
//    public final static double tileWidth = 10;

//    public final static double CanvasWidth = 600;
    public final static double canvasSize = 600;

//    public final static int numberOfTiles = (int) (Constants.CanvasWidth/Constants.boxWidth);
    public final static int numberOfTiles = (int) (Constants.canvasSize /Constants.tileSize);

    public final static HashMap<Integer, String> buildingIDs = new HashMap<>() {{
        put(1, "Castle");
        put(2, "BasicTower");
    }

    };

    public final static HashMap<Integer, Integer[]> buildingSizes = new HashMap<>() {{
        put(1, new Integer[]{3,3,0,0});
        put(2, new Integer[]{2,2,0,0});
    }
    };

}
