package agh.ics.oop.Enemies;

import agh.ics.oop.Interfaces.EnemyKilledObserver;
import agh.ics.oop.Interfaces.WaveStateObserver;
import agh.ics.oop.gui.GameScreen;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class WaveWanager implements EnemyKilledObserver {
    int currentWaveNumber;
    GameScreen gameScreen;
    EnemySpawner spawner;

    Integer remainingAlive;

    ArrayList<WaveStateObserver> observers = new ArrayList<>();

    double initialWaitTime = 500;

    int currentlyKilled = 0;
    int totalToSpawn = 0;

    Timer waveTimer;

    public WaveWanager(GameScreen gs){
        this.currentWaveNumber=0;
        this.gameScreen=gs;
        this.spawner = new EnemySpawner(gs.gameEngine);
    }

    int getTotalEnemyCountThisWave(){
        return (int)Math.sqrt(this.currentWaveNumber*50);
    }

    public void addObserver(WaveStateObserver o){
        this.observers.add(o);
    }

    private void changeWaveStates(){
        this.observers.forEach((WaveStateObserver::changeWaveState));
    }

    private void resetWaveParameters(){
        this.currentlyKilled=0;
    }

    public void startNewWave(){
        System.out.println("starting new wave");
        this.currentWaveNumber++;
        this.totalToSpawn = getTotalEnemyCountThisWave();
        changeWaveStates();
        this.waveTimer = new Timer();
        this.waveTimer.scheduleAtFixedRate(new WaveSpawner(this.totalToSpawn,this.spawner),10L,500L);
    }


    @Override
    public void addGold(Integer n) {
        this.currentlyKilled++;
        if(currentlyKilled>=this.totalToSpawn){
            this.waveTimer.cancel();
            changeWaveStates();
            resetWaveParameters();
        }
    }
}
