package agh.ics.oop;

import java.util.HashMap;

public final class Constants {
    public final static double boxWidth = 10;
    public final static double boxHeight = 10;

    public final static double CanvasWidth = 600;
    public final static double CanvasHeight = 600;

    public final static int boxNoWidth = (int) (Constants.CanvasWidth/Constants.boxWidth);
    public final static int boxNoHeight = (int) (Constants.CanvasHeight/Constants.boxHeight);

    public final static HashMap<Integer, String> buildingIDs = new HashMap<>() {{
        put(1, "Castle");
    }

    };

    public final static HashMap<Integer, Integer[]> buildingSizes = new HashMap<>() {{
        put(1, new Integer[]{3,3,0,0});
    }
    };

}
