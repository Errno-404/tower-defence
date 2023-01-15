package agh.ics.oop.gui;

import agh.ics.oop.Interfaces.HealthChangeObserver;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class HealthBar implements HealthChangeObserver {
    static double healthBarSizeX = 25;
    static double healthBarSizeY = 15;

    double currentPercentage = 1;
    Color healtColor = Color.LIGHTGREEN;

    static double changePointLow = 0.1;
    static double changePointHigh = 0.75;

    static Color highHealth = Color.LIMEGREEN;
    static Color lowHealth = Color.RED;

    public HealthBar(){
    }

    public static Color lerp(Color c1, Color c2, double t){
        double r = ((1 - t)*c1.getRed() + c2.getRed()*t);
        double g = (Math.round((1 - t)*c1.getGreen()) + c2.getGreen()*t);
        double b =  ((1 - t)*c1.getBlue() + c2.getBlue()*t);
        return Color.color(r,g,b);
    }

    public static double inverseLerp(double a,double b, double v){
        if(v<=a){
            return 0.0;
        }
        else if(v>=b){
            return 1.0;
        }
        return ((v-a)/(b-a));
    }

    public static Color remap(double iMin, double iMax, Color oMin, Color oMax, double v){
        double t = inverseLerp(iMin, iMax, v);
        return lerp(oMin,oMax,t);
    }
    @Override
    public void reportHealthChange(double f){
        this.currentPercentage = f;
        this.healtColor = remap(changePointLow, changePointHigh, lowHealth, highHealth,f);
    }



    //testFunctions
    public void draw(GraphicsContext gc){

        gc.setFill(Color.BLACK);
        gc.fillRect(303,303, healthBarSizeX, healthBarSizeY);
        gc.setFill(this.healtColor);
        double sizeX = this.currentPercentage*healthBarSizeX;
        gc.fillRect(303,303,sizeX, healthBarSizeY);

        gc.setFill(null);
    }
}
