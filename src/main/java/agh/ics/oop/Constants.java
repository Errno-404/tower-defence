package agh.ics.oop;

import java.util.HashMap;

public final class Constants {
    public final static double tileSize = 10;
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
        put(1, new Integer[]{3,3,1,1});
        put(2, new Integer[]{2,2,1,1});
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
