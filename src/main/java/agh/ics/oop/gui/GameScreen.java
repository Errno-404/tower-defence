package agh.ics.oop.gui;


import agh.ics.oop.Constants;
import agh.ics.oop.GameEngine;
import agh.ics.oop.Interfaces.SelectionObserver;
import agh.ics.oop.Proejctiles.HomingProjectile;
import agh.ics.oop.Proejctiles.HomingProjectileTestClass;
import agh.ics.oop.Proejctiles.NormalProjectile;
import agh.ics.oop.Proejctiles.Projectile;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.Building;
import agh.ics.oop.buildings.Castle;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen {

    public Canvas canvas;
    public GraphicsContext gc;

    CanvasElement elementUnderCursor;

    public CanvasElement[][] elements;

    ArrayList<SelectionObserver> observers;

    GameEngine gameEngine;


    private Building selectedListBuilding = null; //if not null, place on mouseClick (if possible) the building on current cursor element and set back to null
    private Building selectedExistingBuilding; //change on canvas click when selectedListBuilding is null


    //test
    ArrayList<Projectile> projectiles = new ArrayList<>();
    Castle castle;


    public GameScreen(){
        double width = Constants.CanvasWidth;
        double height = Constants.CanvasHeight;
        this.canvas = new Canvas(width,height);
        this.gc = canvas.getGraphicsContext2D();




        this.elements = new CanvasElement[(int) width][(int) height];
        try {
            Image defaultImage = new Image(new FileInputStream("src/main/resources/blueRect.png"));
            Image defaultBlack = new Image(new FileInputStream("src/main/resources/blackRect.png"));
            Image cursorImg = new Image(new FileInputStream("src/main/resources/yellowRect.png"));

            for (int i = 0; i < width/Constants.boxWidth; i++) {
                for (int j = 0; j < height/Constants.boxWidth; j++) {
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

        this.gameEngine = new GameEngine(this);
        Random rand = new Random();
        for(int i = 0;i<3500;i++){
            this.gameEngine.addProjectile();
        }

        //test
        try {
            this.castle = new Castle(3, 3, 2, 2, 5, new Image(new FileInputStream("src/main/resources/test.png")), this);
            this.gameEngine.addBuilding(castle);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //endtest

        this.canvas.setOnMouseMoved(e -> {

            double mouseX = e.getX();
            double mouseY = e.getY();

            int arrayIndexX = (int) (mouseX/Constants.boxWidth);
            int arrayIndexY = (int) (mouseY/Constants.boxWidth);


            if(this.elementUnderCursor != elements[arrayIndexX][arrayIndexY]){
                this.elementUnderCursor.revert();
                this.elementUnderCursor = elements[arrayIndexX][arrayIndexY];
                this.elementUnderCursor.highlight();
            }

            //test
            this.gameEngine.projectiles.forEach((Projectile p) -> {
                if(p instanceof HomingProjectileTestClass p1){
                    p1.updateTarget(new Vector(rand.nextDouble(0,600), rand.nextDouble(0,600)));
                }

            });



        });

        this.canvas.setOnMouseClicked(e -> {
            System.out.println(this.elementUnderCursor.xIndex + "   " + this.elementUnderCursor.yIndex + "    " + this.elementUnderCursor.boxCentre);

            //test
            this.castle.destroyBuilding();
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


    public void run(){
            for (int i = 0; i < 60; i++) {
                for (int j = 0; j < 60; j++) {
                    elements[i][j].draw(this.gc);


                }
            }

            //test
            this.h1.draw(this.gc);
            this.h1.reportHealthChange(this.h1.currentPercentage-0.0025);

            this.gameEngine.moveProjectiles();
            this.gameEngine.projectiles.forEach((Projectile p) -> p.draw(this.gc));

            if(this.h1.currentPercentage<=0){
                this.h1.reportHealthChange(1.0);
            }
        }

}
