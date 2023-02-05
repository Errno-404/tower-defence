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


    public GameScreen() {
        double canvasSize = Constants.canvasSize;
        this.canvas = new Canvas(canvasSize, canvasSize);
        this.gc = canvas.getGraphicsContext2D();


        this.elements = new CanvasElement[(int) canvasSize][(int) canvasSize];
        try {

            // Grafika trawy - podłoża

            Image lighterGrass = new Image(new FileInputStream("src/main/resources/lighterGrass.png"),
                    Constants.tileSize, Constants.canvasSize, true, false);
            Image darkerGrass = new Image(new FileInputStream("src/main/resources/darkerGrass.png"),
                    Constants.tileSize, Constants.tileSize, true, false);


//            Image defaultImage = new Image(new FileInputStream("src/main/resources/blueRect.png"));
//            Image defaultBlack = new Image(new FileInputStream("src/main/resources/blackRect.png"));


            // Grafika kursora ?

            Image cursorImg = new Image(new FileInputStream("src/main/resources/yellowRect.png"));


            // Dodawanie odpowiedniego elementu do narysowania

            for (int i = 0; i < Constants.numberOfTiles; i++) {
                for (int j = 0; j < Constants.numberOfTiles; j++) {
                    CanvasElement temp = new CanvasElement(lighterGrass, cursorImg, i, j);
                    CanvasElement temp1 = new CanvasElement(darkerGrass, cursorImg, i, j);
                    if ((i + j) % 2 == 0) {
                        elements[i][j] = temp;
                    } else {
                        elements[i][j] = temp1;
                    }

                }
            }
            this.elementUnderCursor = elements[0][0];
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        // Dodanie silnika do ekranu
        this.gameEngine = new GameEngine(this);
        Random rand = new Random();


//        for(int i = 0;i<1000;i++){
//            this.gameEngine.addProjectile(false);
//        }

//        for (int i = 0; i < 1000; i++) {
//            this.gameEngine.addProjectile(true);
//        }

        for(int i = 0;i<1000;i++){
            this.gameEngine.addProjectile(true);
        }

        try {
            for (int i = 0; i < 5; i++) {
                this.gameEngine.addEnemy(new BasicEnemy(rand.nextDouble(0,600), rand.nextDouble(0,600),this.gameEngine.gameMap));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // co to jest?
//        try {
//            for (int i = 0; i < 5; i++) {
//                this.gameEngine.addEnemy(new BasicEnemy(rand.nextDouble(0, 600), rand.nextDouble(0, 600), this.gameEngine.gameMap));
//            }
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }


        // onMouse Events
        this.canvas.setOnMouseMoved(e -> {

            double mouseX = e.getX();
            double mouseY = e.getY();

            int arrayIndexX = (int) (mouseX / Constants.tileSize);
            int arrayIndexY = (int) (mouseY / Constants.tileSize);


            if (this.elementUnderCursor != elements[arrayIndexX][arrayIndexY]) {
                this.elementUnderCursor.revert();
                this.elementUnderCursor = elements[arrayIndexX][arrayIndexY];
                this.elementUnderCursor.highlight();
            }

            // TODO ??
            if (this.selectedBuildingSquare != null) {
                this.selectedBuildingSquare.move(arrayIndexX, arrayIndexY);
            }

            //test
            this.gameEngine.friendlyProjectiles.forEach((Projectile p) -> {
                if (p instanceof HomingProjectileTestClass p1) {
                    p1.updateTarget(new Vector(mouseX, mouseY));
                }

            });

            this.gameEngine.enemyProjectiles.forEach((Projectile p) -> {
                if (p instanceof HomingProjectileTestClass p1) {
                    p1.updateTarget(new Vector(mouseX, mouseY));
                }

            });


        });

        this.canvas.setOnMouseClicked(e -> {
            //test
            int currX = this.elementUnderCursor.xIndex;
            int currY = this.elementUnderCursor.yIndex;
            if (selectedListBuildingID == 0 && this.gameEngine.gameMap.castleCentre == null) {
                setSelectedListBuilding(1);

            } else if (selectedListBuildingID == 0) {
                setSelectedListBuilding(2);
            } else if (this.selectedBuildingSquare.validPosition) {
                placeSelectedListBuilding(BuildingFactory.getBuildingById(this.selectedListBuildingID, currX, currY, this, gameEngine));
                this.gameEngine.enemyProjectiles.forEach((Projectile p) -> {
                    if (p instanceof HomingProjectileTestClass p1) {
                        p1.updateTarget(new Vector(rand.nextDouble(0, 600), rand.nextDouble(0, 600)));
                    }
                });
            }


//            System.out.println(this.elementUnderCursor.xIndex + "   " + this.elementUnderCursor.yIndex + "    " + this.elementUnderCursor.boxCentre);
            //System.out.println("projectiles at " + currX + " " + currY + "  " + this.gameEngine.gameMap.map[this.elementUnderCursor.xIndex][this.elementUnderCursor.yIndex].enemyProjectileList.size());
//            System.out.println("fval: " + this.gameEngine.friendlyProjectiles.size() + " " + this.gameEngine.friendlyProjectilesToRemove.size()
//                    + " " + this.gameEngine.enemies.size() + " " + this.gameEngine.deadEnemies.size() + " " + this.gameEngine.gameMap.sumProj());
            //test
            //this.castle.destroyBuilding();
        });
    }

    public void addObserver(SelectionObserver o) {
        this.observers.add(o);
    }

    public void notifySelectionChange() {
        for (SelectionObserver o : this.observers) {
            o.updateSelected(selectedExistingBuilding);
        }
    }

    public void setSelectedListBuilding(Integer id) {
        if (this.gameEngine.gameMap.castleCentre != null && id == 1) {
            return;
        }
        this.selectedListBuildingID = id;
        this.selectedBuildingSquare = BuildingSquareFactory.newSquare(id, this.gameEngine.gameMap);
    }

    public void placeSelectedListBuilding(Building b) {
        this.gameEngine.addBuilding(b);
        this.selectedListBuildingID = 0;
        this.selectedBuildingSquare.remove();
        this.selectedBuildingSquare = null;
    }

    public void updateInfoPane() {
        //TODO
    }

    HealthBar h1 = new HealthBar();



    // ========================================= Główna metoda ==============================================

    public void run() {

        // pętle rysują wszystkie kafelki

        for (int i = 0; i < Constants.numberOfTiles; i++) {
            for (int j = 0; j < Constants.numberOfTiles; j++) {
                elements[i][j].draw(this.gc);


            }
        }

            this.gameEngine.clearEnemiesInTowers();

            this.gameEngine.defensiveBuildings.forEach(Building::drawHealthBar);
            this.gameEngine.towers.forEach(Building::drawHealthBar);
            //this.gameEngine.enemies.forEach(Enemy::drawOnCanvas);

            this.gameEngine.moveProjectiles();
            this.gameEngine.friendlyProjectiles.forEach((Projectile p) -> p.draw(this.gc));
            this.gameEngine.enemyProjectiles.forEach((Projectile p) -> p.draw(this.gc));
            this.gameEngine.checkCollisions();

            this.gameEngine.removeRemainingProjectiles();


            this.gameEngine.enemies.forEach(Enemy::move);
            this.gameEngine.enemies.forEach((Enemy e) -> {
                e.draw(this.gc);
            });

            this.gameEngine.addEnemiesToTowers();
            //test
            this.h1.drawTest(this.gc);
            this.h1.reportHealthChange(this.h1.currentPercentage-0.0025);

        // rysowanie delikatnych linii siatki
//        gc.setFill(Color.LIGHTGRAY);
//        gc.setLineWidth(0.05);
//        for (int x = 0; x <= Constants.CanvasWidth; x += Constants.tileWidth) {
//            gc.strokeLine(x, 0, x, Constants.CanvasWidth);
//        }
//        for (int y = 0; y <= Constants.CanvasWidth; y += Constants.tileWidth) {
//            gc.strokeLine(0, y, Constants.CanvasWidth, y);
//        }


//            this.gameEngine.defensiveBuildings.forEach(Building::drawHealthBar);
//            this.gameEngine.activeTowers.forEach(Building::drawHealthBar);
//            this.gameEngine.waitingTowers.forEach(Building::drawHealthBar);
//            //this.gameEngine.enemies.forEach(Enemy::drawOnCanvas);
//
//            this.gameEngine.moveProjectiles();
//            this.gameEngine.friendlyProjectiles.forEach((Projectile p) -> p.draw(this.gc));
//            this.gameEngine.enemyProjectiles.forEach((Projectile p) -> p.draw(this.gc));
//            this.gameEngine.checkCollisions();
//
//
//            this.gameEngine.enemies.forEach(Enemy::move);
//            this.gameEngine.enemies.forEach((Enemy e) -> {
//                e.draw(this.gc);
//            });
//            //test
//            this.h1.drawTest(this.gc);
//            this.h1.reportHealthChange(this.h1.currentPercentage-0.0025);
//
//
//            this.gameEngine.defensiveBuildings.forEach((DefensiveBuilding b) -> {
//                //b.reduceHealth(0.1);
//                if(b.getCurrentHealth() <= 0){
//                    this.gameEngine.destroyedBuildings.add(b);
//                }
//            });
//            this.gameEngine.defensiveBuildings.removeAll(this.gameEngine.destroyedBuildings);
//            this.gameEngine.destroyedBuildings.forEach(Building::destroyBuilding);
//            this.gameEngine.destroyedBuildings.clear(); //zmienic usuwanie zniszczonych budynkow na funkcje w GameEngine
//
//
//            if(this.h1.currentPercentage<=0){
//                this.h1.reportHealthChange(1.0);
//            }
//        }

    }
}
