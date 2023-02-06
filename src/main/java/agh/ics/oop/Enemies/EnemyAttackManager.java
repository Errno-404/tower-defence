package agh.ics.oop.Enemies;

import java.util.TimerTask;

public class EnemyAttackManager extends TimerTask {
    private Enemy enemy;

    public EnemyAttackManager(Enemy e){
        this.enemy = e;
    }

    @Override
    public void run() {
        if(this.enemy.canAttack()){
            this.enemy.attack();

        }
    }
}
