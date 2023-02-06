package agh.ics.oop.buildings.AttackingBuildings;

import java.util.TimerTask;

public class TowerAttackManager extends TimerTask {
    AttackingBuilding tower;

    public TowerAttackManager(AttackingBuilding a){
        this.tower=a;
    }

    @Override
    public void run() {
        this.tower.attack();
    }
}
