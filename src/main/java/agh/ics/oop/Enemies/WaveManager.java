package agh.ics.oop.Enemies;

import agh.ics.oop.Interfaces.EnemyKilledObserver;
import agh.ics.oop.Interfaces.WaveStateObserver;
import agh.ics.oop.gui.GameScreen;

import java.util.ArrayList;
import java.util.Timer;

public class WaveManager implements EnemyKilledObserver {
    int currentWaveNumber;
    GameScreen gameScreen;
    EnemySpawner spawner;

    Integer remainingAlive;

    ArrayList<WaveStateObserver> observers = new ArrayList<>();

    double initialWaitTime = 500;

    public int currentlyKilled = 0;
    public int totalToSpawn = 0;

    Timer waveTimer;

    public int waveNumber = 1;

    public WaveManager(GameScreen gs){
        this.currentWaveNumber=0;
        this.gameScreen=gs;
        this.spawner = new EnemySpawner(gs.gameEngine);
    }

    int getTotalEnemyCountThisWave(){
        if(this.currentWaveNumber%4 == 0){
            return this.waveNumber/4;
        }
        else{
            return (int)Math.sqrt(Math.pow(this.currentWaveNumber,1.54)*50);
        }

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
        System.out.println("spawning: "+this.totalToSpawn +" enemeies");
        changeWaveStates();
        this.waveNumber ++;
        this.waveTimer = new Timer();

        WaveType type;

        if(this.currentWaveNumber %4 == 0){
            type = WaveType.BossWave;
        }
        else{
            type = WaveType.NormalWave;
        }

        this.waveTimer.scheduleAtFixedRate(new WaveSpawner(this.totalToSpawn,this.spawner, type, this.waveNumber),10L,500L);
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
