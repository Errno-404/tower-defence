package agh.ics.oop.Enemies;

import agh.ics.oop.Attacks.StandardMeleeAttack;
import agh.ics.oop.maps.GameMap;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class EnemyFactory {
    GameMap map;

    public EnemyFactory(GameMap map){
        this.map=map;
    }

    public Enemy getNewBasicEnemy(double x, double y, double attack){
        try {
            return new BasicEnemy(x, y, attack, this.map);
        }catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public Enemy getNewFlyingEnemy(double posx, double posy,double hp, double attack){
        try {
            return new FlyingEnemy(posx, posy, hp, new StandardMeleeAttack(attack), this.map, new Image(new FileInputStream("src/main/resources/flyingEnemy.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
