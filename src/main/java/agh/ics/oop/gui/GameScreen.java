package agh.ics.oop.gui;


import agh.ics.oop.Interfaces.SelectionObserver;
import agh.ics.oop.Proejctiles.HomingProjectile;
import agh.ics.oop.Proejctiles.HomingProjectileTestClass;
import agh.ics.oop.Proejctiles.NormalProjectile;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.Building;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GameScreen {

    public Canvas canvas;
    public GraphicsContext gc;

    final int width;
    final int height;
    final int boxWidth = 10;

    CanvasElement elementUnderCursor;

    CanvasElement[][] elements;

    ArrayList<SelectionObserver> observers;


    private Building selectedListBuilding = null; //if not null, place on mouseClick (if possible) the building on current cursor element and set back to null
    private Building selectedExistingBuilding; //change on canvas click when selectedListBuilding is null

    public GameScreen(int width, int height){
        this.canvas = new Canvas(width,height);
        this.width = width;
        this.height = height;
        this.gc = canvas.getGraphicsContext2D();




        this.elements = new CanvasElement[width][height];
        try {
            Image defaultImage = new Image(new FileInputStream("src/main/resources/blueRect.png"));
            Image defaultBlack = new Image(new FileInputStream("src/main/resources/blackRect.png"));
            Image cursorImg = new Image(new FileInputStream("src/main/resources/yellowRect.png"));

            for (int i = 0; i < width/boxWidth; i++) {
                for (int j = 0; j < height/boxWidth; j++) {
                    CanvasElement temp = new CanvasElement(defaultImage, cursorImg, i, j);
                    CanvasElement temp1 = new CanvasElement(defaultBlack, cursorImg,i, j);
                    if(i%2==0 || j%2 == 0){
                        elements[i][j] = temp;
                    }
                    else{
                        elements[i][j] = temp1;
                    }

                }
            }
            this.elementUnderCursor = elements[0][0];
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.canvas.setOnMouseMoved(e -> {

            double mouseX = e.getX();
            double mouseY = e.getY();

            int arrayIndexX = (int) (mouseX/boxWidth);
            int arrayIndexY = (int) (mouseY/boxWidth);


            if(this.elementUnderCursor != elements[arrayIndexX][arrayIndexY]){
                this.elementUnderCursor.revert();
                this.elementUnderCursor = elements[arrayIndexX][arrayIndexY];
                this.elementUnderCursor.highlight();
            }

            //test
            this.p2.updateTarget(new Vector(mouseX, mouseY));


        });

        this.canvas.setOnMouseClicked(e -> {
            System.out.println(this.elementUnderCursor.xIndex + "   " + this.elementUnderCursor.yIndex);
        });
    }

    public void addObserver(SelectionObserver o){
        this.observers.add(o);
    }

    public void notifySelectionChange(){
        for(SelectionObserver o: this.observers){
            o.updateSelected(selectedExistingBuilding);
        }
    }
    public void setSelectedListBuilding(Building b){
        this.selectedListBuilding = b;
    }

    public void updateInfoPane(){
        //TODO
    }

    HealthBar h1 = new HealthBar();
    NormalProjectile p1 = new NormalProjectile(new Vector(100,100),2,new Vector(500,500));
    HomingProjectileTestClass p2 = new HomingProjectileTestClass(new Vector(100,100),4);

    public void run(){
            for (int i = 0; i < 60; i++) {
                for (int j = 0; j < 60; j++) {
                    elements[i][j].draw(this.gc);


                }
            }

            //test
            this.h1.draw(this.gc);
            this.h1.reportHealthChange(this.h1.currentPercentage-0.0025);

            this.p1.move();
            this.p1.draw(this.gc);

            this.p2.move();
            this.p2.draw(this.gc);
            if(this.h1.currentPercentage<=0){
                this.h1.reportHealthChange(1.0);
            }
        }

}
