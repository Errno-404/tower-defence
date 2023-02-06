package agh.ics.oop.Enemies;

import agh.ics.oop.Constants;
import agh.ics.oop.GameEngine;

import java.io.FileNotFoundException;
import java.util.Random;

public class EnemySpawner {

    public GameEngine gameEngine;
    EnemyFactory factory;

    public EnemySpawner(GameEngine ge) {
        this.gameEngine = ge;
        this.factory = new EnemyFactory(this.gameEngine.gameMap);
    }


    private void spawnEnemiesOnEdges(int countOfEnemies) {
        Random rand = new Random();
        int side;
        int pos;
        for (int i = 0; i < countOfEnemies; i++) {
            side = rand.nextInt(4);
            pos = rand.nextInt(Constants.numberOfTiles);

            this.gameEngine.addEnemy(switch (side) {
                case 0 -> this.factory.getNewBasicEnemy(0, pos * Constants.tileSize, 10);
                case 1 -> this.factory.getNewBasicEnemy(pos * Constants.tileSize, 0, 10);
                case 2 -> this.factory.getNewBasicEnemy((Constants.numberOfTiles - 1) * Constants.tileSize,
                        pos * Constants.tileSize, 10);
                case 3 -> this.factory.getNewBasicEnemy(pos * Constants.tileSize,
                        (Constants.numberOfTiles - 1) * Constants.tileSize, 10);
                default -> throw new IllegalStateException("Unexpected value: " + side);
            });

        }

    }

    public void spawnSingularEnemy() {
        Random rand = new Random();
        int side;
        int pos;

        side = rand.nextInt(4);
        pos = rand.nextInt(Constants.numberOfTiles);
        try {
            this.gameEngine.addEnemy(switch (side) {
                case 0 -> this.factory.getNewFlyingEnemy(0, pos * Constants.tileSize, 100, 10);
                case 1 -> new BasicEnemy(pos * Constants.tileSize, 0, 10, this.gameEngine.gameMap);
                case 2 -> new BasicEnemy((Constants.numberOfTiles - 1) * Constants.tileSize,
                        pos * Constants.tileSize, 10, this.gameEngine.gameMap);
                case 3 -> new BasicEnemy(pos * Constants.tileSize,
                        (Constants.numberOfTiles - 1) * Constants.tileSize, 10, this.gameEngine.gameMap);
                default -> throw new IllegalStateException("Unexpected value: " + side);
            });
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
