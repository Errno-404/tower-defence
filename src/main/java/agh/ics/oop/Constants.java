package agh.ics.oop;

import agh.ics.oop.buildings.BuildingsName;

import java.util.HashMap;

public final class Constants {
    public final static double tileSize = 10;

    public final static double canvasSize = 600;

    public final static int numberOfTiles = (int) (Constants.canvasSize /Constants.tileSize);





    // ============================================ Basic Tower constants ==============================================

    public static final double basicAttackStrength = 150.0;
    public static final double basicProjectileSpeed = 10.0 ;










    public final static HashMap<BuildingsName, Integer[]> buildingSizes = new HashMap<>() {{
        put(BuildingsName.CASTLE, new Integer[]{3,3,1,1});
        put(BuildingsName.TOWER, new Integer[]{2,2,1,1});
        put(BuildingsName.WALL, new Integer[]{5, 1, 1, 1});
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
