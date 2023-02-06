package agh.ics.oop.Enemies;

import java.util.TimerTask;

public class WaveSpawner extends TimerTask {

    int totalToSpawn;
    int spawnedSoFar = 0;
    EnemySpawner spawner;

    public WaveSpawner(int totalToSpawn, EnemySpawner spawner){
        this.totalToSpawn = totalToSpawn;
        this.spawner = spawner;

    }

    @Override
    public void run() {
        if(spawnedSoFar < totalToSpawn){
            spawner.spawnSingularEnemy();
            this.spawnedSoFar++;
        }
    }
}
