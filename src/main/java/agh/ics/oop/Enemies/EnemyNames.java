package agh.ics.oop.Enemies;

public enum EnemyNames {
    BASICENEMY,
    FLYINGENEMY;

    public int convert(){
        return switch(this){
            case BASICENEMY -> 1;
            case FLYINGENEMY -> 2;
        };
    }
}
