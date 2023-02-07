package agh.ics.oop.Enemies;

import java.util.Random;

public enum EnemyNames {
    BASICENEMY,
    FLYINGENEMY,
    SHOOTINGENEMY;

    public int convert(){
        return switch(this){
            case BASICENEMY -> 1;
            case FLYINGENEMY -> 2;
            case SHOOTINGENEMY -> 3;
        };
    }

    public static EnemyNames getRandomEnemy(){
        Random rand = new Random();
        int n = rand.nextInt(0,3);
        return switch (n){
            case 0 -> BASICENEMY;
            case 1 -> FLYINGENEMY;
            case 2 -> SHOOTINGENEMY;
            default -> throw new IllegalStateException("Unexpected value: " + n);
        };
    }
}
