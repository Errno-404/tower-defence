package agh.ics.oop;

import agh.ics.oop.buildings.BuildingsName;

import java.util.HashMap;

public final class Constants {
    public final static double tileSize = 10;

    public final static double canvasSize = 600;

    public final static int numberOfTiles = (int) (Constants.canvasSize /Constants.tileSize);





    // ============================================ Basic Tower constants ==============================================

    public static final double basicProjectileSpeed = 10.0 ;



    public static final int basicTowerWidth = 2;
    public static final int basicTowerHeight = 2;

    public static final double basicTowerAttackSpeed = 500.0;
    public static final double basicTowerAttackStrength = 200.0;
    public static final String basicTowerImagePath = "src/main/resources/Tower1.png";


    public final static HashMap<BuildingsName, Integer[]> buildingSizes = new HashMap<>() {{
        put(BuildingsName.CASTLE, new Integer[]{3,3,1,1});
        put(BuildingsName.TOWER, new Integer[]{2,2,1,1});
        put(BuildingsName.HORIZONTALWALL, new Integer[]{5, 1, 1, 1});
        put(BuildingsName.VERTICALWALL, new Integer[]{1,5,1,1});
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
