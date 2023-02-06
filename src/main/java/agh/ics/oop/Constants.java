package agh.ics.oop;

import agh.ics.oop.buildings.BuildingsName;

import java.util.HashMap;

public final class Constants {
    public final static double tileSize = 10;

    public final static double canvasSize = 600;

    public final static int numberOfTiles = (int) (Constants.canvasSize /Constants.tileSize);


    public final static HashMap<Integer, Integer[]> buildingSizes = new HashMap<>() {{
        put(BuildingsName.CASTLE.convert(), new Integer[]{3,3,1,1});
        put(BuildingsName.TOWER.convert(), new Integer[]{2,2,1,1});
        put(BuildingsName.WALL.convert(), new Integer[]{1, 1, 1, 1});
    }
    };

    private static Integer[] getMap1RowEven(){
        Integer[] ret = new Integer[Constants.numberOfTiles];


        boolean isDark = true;
        for(int i = 0; i<Constants.numberOfTiles;i++){
            if(i%6==0) {
                isDark = !isDark;
            }

        }
        return ret;
    }

    public final static Integer[][] mapBackgroundData = new Integer[][] {

    };

}
