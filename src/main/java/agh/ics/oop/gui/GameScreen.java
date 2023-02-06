package agh.ics.oop.gui;


import agh.ics.oop.Constants;
import agh.ics.oop.Enemies.BasicEnemy;
import agh.ics.oop.Enemies.Enemy;
import agh.ics.oop.GameEngine;
import agh.ics.oop.Interfaces.ShopSelectionObserver;
import agh.ics.oop.Attacks.HomingProjectileTestClass;
import agh.ics.oop.Attacks.Projectile;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen implements ShopSelectionObserver {

    public Canvas canvas;
    public GraphicsContext gc;

    CanvasElement elementUnderCursor;

    public CanvasElement[][] elements;

    ArrayList<ShopSelectionObserver> observers;

    public GameEngine gameEngine;

    boolean isOver = false;


    private BuildingsName selectedListBuildingID = null; //if not null, place on mouseClick (if possible) the building on
    // current cursor element and set back to null
    private BuildingCreationSquare selectedBuildingSquare = null;
    private Building selectedExistingBuilding; //change on canvas click when selectedListBuilding is null


    //test
    ArrayList<Projectile> projectiles = new ArrayList<>();


    // ================================================= Constructor ===================================================
    public GameScreen() {
        double canvasSize = Constants.canvasSize;
        this.canvas = new Canvas(canvasSize, canvasSize);
        this.gc = canvas.getGraphicsContext2D();

        this.elements = new CanvasElement[Constants.numberOfTiles + 1][Constants.numberOfTiles + 1];
        try {

            // Adding grass graphics

            Image lighterGrass = new Image(new FileInputStream("src/main/resources/lighterGrass.png"),
                    Constants.tileSize, Constants.canvasSize, true, false);
            Image darkerGrass = new Image(new FileInputStream("src/main/resources/darkerGrass.png"),
                    Constants.tileSize, Constants.tileSize, true, false);

            Image cursorImg = new Image(new FileInputStream("src/main/resources/yellowRect.png"));


            // Adding graphic to tiles

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

            // Setting cursor to upper left tile
            this.elementUnderCursor = elements[0][0];
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        // Adding gameEngine to screen
        this.gameEngine = new GameEngine(this);

        // TODO zmienić na fale
        placeCastleOnMap();
        spawnEnemiesOnEdges(20);
        Random rand = new Random();


        // graphical representation of cursor
        this.canvas.setOnMouseMoved(e -> {
            if(!isOver) {
                double mouseX = e.getX();
                double mouseY = e.getY();

                int arrayIndexX = (int) (mouseX / Constants.tileSize);
                int arrayIndexY = (int) (mouseY / Constants.tileSize);


                if (this.elementUnderCursor != elements[arrayIndexX][arrayIndexY]) {
                    this.elementUnderCursor.revert();
                    this.elementUnderCursor = elements[arrayIndexX][arrayIndexY];
                    this.elementUnderCursor.highlight();
                }


                if (this.selectedBuildingSquare != null) {
                    this.selectedBuildingSquare.move(arrayIndexX, arrayIndexY);
                }

            }
        });


        this.canvas.setOnMouseClicked(e -> {
            if (!isOver) {
                //test
                spawnEnemiesOnEdges(5);
                int currX = this.elementUnderCursor.xIndex;
                int currY = this.elementUnderCursor.yIndex;
                if (selectedListBuildingID == null) {
                    setSelectedListBuilding(BuildingsName.WALL);
                } else if (this.selectedBuildingSquare.validPosition) {
                    placeSelectedListBuilding(BuildingFactory.getBuildingById(this.selectedListBuildingID, currX, currY, this, gameEngine));
                    this.gameEngine.enemyProjectiles.forEach((Projectile p) -> {
                        if (p instanceof HomingProjectileTestClass p1) {
                            p1.updateTarget(new Vector(rand.nextDouble(0, 600), rand.nextDouble(0, 600)));
                        }
                    });
                }

            }
        });
    }

    public void endGame(){
        this.gameEngine.removeAllGameOver();
        this.run();
        this.isOver = true;
    }


    // ================================================= Main method ===================================================

    public void run() {

        // Drawing all tiles
        if(!isOver){
        for (int i = 0; i < Constants.numberOfTiles; i++) {
            for (int j = 0; j < Constants.numberOfTiles; j++) {
                elements[i][j].draw(this.gc);
            }
        }


        // single iteration which moves everything
        this.gameEngine.clearEnemiesInTowers();
        this.gameEngine.moveProjectiles();
        this.gameEngine.friendlyProjectiles.forEach((Projectile p) -> p.draw(this.gc));
        this.gameEngine.enemyProjectiles.forEach((Projectile p) -> p.draw(this.gc));
        this.gameEngine.checkCollisions();
        this.gameEngine.removeRemainingProjectiles();


        this.gameEngine.enemies.forEach(Enemy::move);
        this.gameEngine.enemies.forEach((Enemy e) -> e.draw(this.gc));

        this.gameEngine.defensiveBuildings.forEach(Building::drawHealthBar);
        this.gameEngine.towers.forEach(Building::drawHealthBar);

        this.gameEngine.addEnemiesToTowers();
        }
        else{
            this.gc.setFill(Color.BLACK);
            this.gc.fillRect(0,0,600,600);
        }


    }


    // Method places Castle in the middle of the map
    private void placeCastleOnMap() {
        this.setSelectedListBuilding(BuildingsName.CASTLE);

        Integer[] arr = Constants.buildingSizes.get(BuildingsName.CASTLE);
        int x = arr[0];
        int y = arr[1];

        int castleXPosition = (Constants.numberOfTiles - x) / 2;
        int castleYPosition = (Constants.numberOfTiles - y) / 2;

        this.placeSelectedListBuilding(BuildingFactory.getBuildingById(selectedListBuildingID, castleXPosition, castleYPosition, this, gameEngine));
    }


    // Method spawns Enemies on edges in random points, countOfEnemies says how many enemies should be spawned at once
    private void spawnEnemiesOnEdges(int countOfEnemies) {
        // TODO change hardcoded values


        Random rand = new Random();
        int side;
        int pos;
        try {
            for (int i = 0; i < countOfEnemies; i++) {
                side = rand.nextInt(4);
                pos = rand.nextInt(Constants.numberOfTiles);

                this.gameEngine.addEnemy(switch (side) {
                    case 0 -> new BasicEnemy(0, pos * Constants.tileSize, this.gameEngine.gameMap);
                    case 1 -> new BasicEnemy(pos * Constants.tileSize, 0, this.gameEngine.gameMap);
                    case 2 -> new BasicEnemy((Constants.numberOfTiles - 1)*Constants.tileSize,
                            pos * Constants.tileSize, this.gameEngine.gameMap);
                    case 3 -> new BasicEnemy(pos * Constants.tileSize,
                            (Constants.numberOfTiles -1) * Constants.tileSize, this.gameEngine.gameMap);
                    default -> throw new IllegalStateException("Unexpected value: " + side);
                });

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    // ========================================== Selecting buildings ==================================================


    public void addObserver(ShopSelectionObserver o) {
        this.observers.add(o);
    }

    /*public void notifySelectionChange() {
        for (ShopSelectionObserver o : this.observers) {
            o.updateSelected(selectedExistingBuilding);
        }
    }*/

    public void setSelectedListBuilding(BuildingsName id) {
        if (this.gameEngine.gameMap.castleCentre != null && id == BuildingsName.CASTLE) {
            return;
        }
        this.selectedListBuildingID = id;
        this.selectedBuildingSquare = BuildingSquareFactory.newSquare(id, this.gameEngine.gameMap);
    }

    public void placeSelectedListBuilding(Building b) {
        this.gameEngine.addBuilding(b);
        this.selectedListBuildingID = null;
        this.selectedBuildingSquare.remove();
        this.selectedBuildingSquare = null;
    }

    public void updateInfoPane() {
        //TODO
    }

    @Override
    public void updateSelected(BuildingsName o) {
        this.setSelectedListBuilding(o);
    }
}
