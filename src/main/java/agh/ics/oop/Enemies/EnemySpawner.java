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
        this.factory = new EnemyFactory(this.gameEngine);
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

    public void spawnSingularEnemy(WaveType type, int waveNumber) {
        Random rand = new Random();
        int side;
        int pos;

        side = rand.nextInt(4);
        pos = rand.nextInt(Constants.numberOfTiles);
        switch (type){
            case BossWave -> {
                Enemy enemyToAdd = switch (side) {
                    case 0 -> this.factory.getNewBossEnemy(0, pos * Constants.tileSize, 2000, 5);
                    case 1 -> this.factory.getNewBossEnemy(pos * Constants.tileSize, 0, 2000, 5);
                    case 2 -> this.factory.getNewBossEnemy((Constants.numberOfTiles - 1) * Constants.tileSize,
                            pos * Constants.tileSize, 2000,5);
                    case 3 -> this.factory.getNewBossEnemy(pos * Constants.tileSize,
                            (Constants.numberOfTiles - 1) * Constants.tileSize, 2000,5);
                    default -> throw new IllegalStateException("Unexpected value: " + side);
                };
                enemyToAdd.multiplyHealth(healthInterpolator(waveNumber));
                this.gameEngine.addEnemy(enemyToAdd);
            }
            case NormalWave -> {
                EnemyNames randResult = EnemyNames.getRandomEnemy();
                Enemy enemyToAdd = switch(side){
                    case 0 -> this.factory.getDefaultEnemyByName(0, pos * Constants.tileSize, randResult);
                    case 1 -> this.factory.getDefaultEnemyByName(pos * Constants.tileSize, 0, randResult);
                    case 2 -> this.factory.getDefaultEnemyByName((Constants.numberOfTiles - 1) * Constants.tileSize,
                            pos * Constants.tileSize, randResult);
                    case 3 -> this.factory.getDefaultEnemyByName(pos * Constants.tileSize,
                            (Constants.numberOfTiles - 1) * Constants.tileSize, randResult);
                    default -> throw new IllegalStateException("Unexpected value: " + side);
                };
                enemyToAdd.multiplyHealth(this.healthInterpolator(waveNumber));
                this.gameEngine.addEnemy(enemyToAdd);

            }
        }


    }

    private double healthInterpolator(int waveNumber){
        if(waveNumber <= 1){
            return 1.0;
        }
        else if(waveNumber >= 50){
            return 3.0;
        }
        else{
            double dWave = (double)waveNumber;
            double t = (dWave - 1.0)/(50.0 - dWave);

            return t*(50.0 - 1.0) + 1.0;
        }
    }
}
