package agh.ics.oop.gui;


import agh.ics.oop.Constants;
import agh.ics.oop.Enemies.BasicEnemy;
import agh.ics.oop.Enemies.Enemy;
import agh.ics.oop.GameEngine;
import agh.ics.oop.Interfaces.SelectionObserver;
import agh.ics.oop.Attacks.HomingProjectileTestClass;
import agh.ics.oop.Attacks.Projectile;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.*;
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


    private int selectedListBuildingID = 0; //if not null, place on mouseClick (if possible) the building on current cursor element and set back to null
    private BuildingCreationSquare selectedBuildingSquare = null;
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
        for(int i = 0;i<1;i++){
            this.gameEngine.addProjectile(false);
        }

        try {
            for (int i = 0; i < 5; i++) {
                this.gameEngine.addEnemy(new BasicEnemy(300,300,this.gameEngine.gameMap));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


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

            if(this.selectedBuildingSquare != null){
                this.selectedBuildingSquare.move(arrayIndexX, arrayIndexY);
            }

            //test
            this.gameEngine.enemyProjectiles.forEach((Projectile p) -> {
                if(p instanceof HomingProjectileTestClass p1){
                    p1.updateTarget(new Vector(mouseX, mouseY));
                }

            });



        });

        this.canvas.setOnMouseClicked(e -> {
           //test
            int currX = this.elementUnderCursor.xIndex;
            int currY = this.elementUnderCursor.yIndex;
            if(selectedListBuildingID == 0){
                setSelectedListBuilding(1);

            }
            else if(this.selectedBuildingSquare.validPosition){
                placeSelectedListBuilding(BuildingFactory.getBuildingById(this.selectedListBuildingID,currX, currY,this));
                this.gameEngine.enemyProjectiles.forEach((Projectile p) -> {
                    if (p instanceof HomingProjectileTestClass p1){
                        p1.updateTarget(new Vector(rand.nextDouble(0,600), rand.nextDouble(0,600)));
                    }
                });
            }


            System.out.println(this.elementUnderCursor.xIndex + "   " + this.elementUnderCursor.yIndex + "    " + this.elementUnderCursor.boxCentre);
            //System.out.println("projectiles at " + currX + " " + currY + "  " + this.gameEngine.gameMap.map[this.elementUnderCursor.xIndex][this.elementUnderCursor.yIndex].enemyProjectileList.size());
            System.out.println("fval: " +this.gameEngine.gameMap.map[this.elementUnderCursor.xIndex][this.elementUnderCursor.yIndex].mapWeightValue);
            //test
            //this.castle.destroyBuilding();
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
    public void setSelectedListBuilding(Integer id){
        if(this.gameEngine.gameMap.castleCentre != null && id == 1){
            return;
        }
        this.selectedListBuildingID = id;
        this.selectedBuildingSquare = BuildingSquareFactory.newSquare(id, this.gameEngine.gameMap);
    }

    public void placeSelectedListBuilding(Building b){
        this.gameEngine.addBuilding(b);
        this.selectedListBuildingID = 0;
        this.selectedBuildingSquare.remove();
        this.selectedBuildingSquare = null;
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


            this.gameEngine.defensiveBuildings.forEach(Building::drawHealthBar);
            this.gameEngine.activeTowers.forEach(Building::drawHealthBar);
            this.gameEngine.waitingTowers.forEach(Building::drawHealthBar);
            //this.gameEngine.enemies.forEach(Enemy::drawOnCanvas);

            this.gameEngine.moveProjectiles();
            this.gameEngine.friendlyProjectiles.forEach((Projectile p) -> p.draw(this.gc));
            this.gameEngine.enemyProjectiles.forEach((Projectile p) -> p.draw(this.gc));
            this.gameEngine.checkCollisions();


            this.gameEngine.enemies.forEach(Enemy::move);
            this.gameEngine.enemies.forEach((Enemy e) -> {
                e.draw(this.gc);
            });
            //test
            this.h1.drawTest(this.gc);
            this.h1.reportHealthChange(this.h1.currentPercentage-0.0025);


            this.gameEngine.defensiveBuildings.forEach((DefensiveBuilding b) -> {
                //b.reduceHealth(0.1);
                if(b.getCurrentHealth() <= 0){
                    this.gameEngine.destroyedBuildings.add(b);
                }
            });
            this.gameEngine.defensiveBuildings.removeAll(this.gameEngine.destroyedBuildings);
            this.gameEngine.destroyedBuildings.forEach(Building::destroyBuilding);
            this.gameEngine.destroyedBuildings.clear(); //zmienic usuwanie zniszczonych budynkow na funkcje w GameEngine


            if(this.h1.currentPercentage<=0){
                this.h1.reportHealthChange(1.0);
            }
        }

}
