package agh.ics.oop.gui;


import agh.ics.oop.buildings.Building;
import agh.ics.oop.maps.GameMap;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

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

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
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

    public void run(){
            for (int i = 0; i < 60; i++) {
                for (int j = 0; j < 60; j++) {
                    elements[i][j].draw(this.gc);
                }
            }
        }
}
