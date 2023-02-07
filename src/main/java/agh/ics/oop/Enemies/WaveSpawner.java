package agh.ics.oop.Enemies;

import java.util.TimerTask;

public class WaveSpawner extends TimerTask {

    int totalToSpawn;
    int spawnedSoFar = 0;
    EnemySpawner spawner;
    WaveType type;
    int waveNumber;



    public WaveSpawner(int totalToSpawn, EnemySpawner spawner, WaveType type, int waveNumber){
        this.totalToSpawn = totalToSpawn;
        this.spawner = spawner;
        this.type = type;
        this.waveNumber = waveNumber;

    }

    @Override
    public void run() {
        if(spawnedSoFar < totalToSpawn){
            spawner.spawnSingularEnemy(type, this.waveNumber);
            this.spawnedSoFar++;
        }
    }
}
