package agh.ics.oop.gui;


import agh.ics.oop.Constants;
import agh.ics.oop.Enemies.BasicEnemy;
import agh.ics.oop.Enemies.Enemy;
import agh.ics.oop.Enemies.WaveManager;
import agh.ics.oop.GameEngine;
import agh.ics.oop.Interfaces.ShopSelectionObserver;
import agh.ics.oop.Attacks.Projectile;
import agh.ics.oop.Interfaces.UnitSelectionObserver;
import agh.ics.oop.Interfaces.WaveStateObserver;
import agh.ics.oop.buildings.*;
import agh.ics.oop.buildings.DefensiveBuildings.DefensiveBuilding;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameScreen implements ShopSelectionObserver, WaveStateObserver {

    public Canvas canvas;
    public GraphicsContext gc;

    CanvasElement elementUnderCursor;

    public CanvasElement[][] elements;

    ArrayList<ShopSelectionObserver> observers;

    public GameEngine gameEngine;

    public WaveManager waveManager;

    boolean isOver = false;
    public boolean isWaveStarted = false;


    private BuildingsName selectedListBuildingID = null; //if not null, place on mouseClick (if possible) the building on
    // current cursor element and set back to null
    private BuildingCreationSquare selectedBuildingSquare = null;
    private Building selectedExistingBuilding; //change on canvas click when selectedListBuilding is null


    private UnitSelectionObserver obs;

    public void addUnitSelectionObserver(UnitSelectionObserver o){
        this.obs = o;
    }




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

            Image cursorImg = new Image(new FileInputStream("src/main/resources/yellowRect2.png"));


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

        this.waveManager = new WaveManager(this);
        this.gameEngine.addEnemyKilledObserver(this.waveManager);

        this.waveManager.addObserver(this);

        // TODO zmienić na fale
        placeCastleOnMap();
        //spawnEnemiesOnEdges(20);
        //Random rand = new Random();


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
                int currX = this.elementUnderCursor.xIndex;
                int currY = this.elementUnderCursor.yIndex;

                if (this.selectedBuildingSquare!= null && this.selectedBuildingSquare.validPosition) {
                    placeSelectedListBuilding(BuildingFactory.getBuildingById(this.selectedListBuildingID, currX, currY, this, gameEngine));
                }

                Building building = this.gameEngine.gameMap.map[currX][currY].buildingID;
                if(building != null){
                    this.selectedExistingBuilding = building;

                if(!isWaveStarted){
                    this.updateInfoPane();
                    }
                else{
                    if(this.selectedExistingBuilding instanceof Castle){
                        this.selectedExistingBuilding = null;
                    }
                    else{
                        this.updateInfoPane();
                    }
                }

//                    this.waveWanager.startNewWave();

                }





            }
        });


    }

    public void endGame(){
        this.gameEngine.removeAllGameOver();
        this.isOver = true;
    }
    @Override
    public void changeWaveState(){
        this.isWaveStarted = !isWaveStarted;
        this.selectedExistingBuilding = null;
        updateInfoPane();
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
        this.gameEngine.removeDestroyedBuildings();
        this.gameEngine.removeRemainingProjectiles();


        this.gameEngine.enemies.forEach(Enemy::move);
        this.gameEngine.enemies.forEach((Enemy e) -> e.draw(this.gc));

        this.gameEngine.defensiveBuildings.forEach(DefensiveBuilding::drawHealthBar);


        this.gameEngine.towers.forEach(Building::drawHealthBar);

        this.gameEngine.addEnemiesToTowers();
        }
        else{
            this.gc.setFill(Color.BLACK);
            this.gc.fillRect(0,0,600,600);
        }

//        System.out.println("Run");

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
                    case 0 -> new BasicEnemy(0, pos * Constants.tileSize, 10,this.gameEngine);
                    case 1 -> new BasicEnemy(pos * Constants.tileSize, 0, 10,this.gameEngine);
                    case 2 -> new BasicEnemy((Constants.numberOfTiles - 1)*Constants.tileSize,
                            pos * Constants.tileSize, 10,this.gameEngine);
                    case 3 -> new BasicEnemy(pos * Constants.tileSize,
                            (Constants.numberOfTiles -1) * Constants.tileSize, 10,this.gameEngine);
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
        Shop.commitTransaction();
    }

    public void updateInfoPane() {
        this.obs.changeSelectedUnit(this.selectedExistingBuilding);
    }

    @Override
    public void updateSelected(BuildingsName o) {
        this.setSelectedListBuilding(o);
    }
}
