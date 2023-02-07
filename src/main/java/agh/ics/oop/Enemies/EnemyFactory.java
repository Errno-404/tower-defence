package agh.ics.oop.Enemies;

import agh.ics.oop.Attacks.StandardMeleeAttack;
import agh.ics.oop.GameEngine;
import agh.ics.oop.maps.GameMap;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class EnemyFactory {
    GameMap map;
    GameEngine gameEngine;

    public EnemyFactory(GameEngine ge){
        this.map=ge.gameMap;
        this.gameEngine = ge;
    }

    public Enemy getNewBasicEnemy(double x, double y, double attack){
        try {
            return new BasicEnemy(x, y, attack, this.gameEngine);
        }catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public Enemy getNewFlyingEnemy(double posx, double posy,double hp, double attack){
        try {
            return new FlyingEnemy(posx, posy, hp, new StandardMeleeAttack(attack), this.gameEngine, new Image(new FileInputStream("src/main/resources/flyingEnemy.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Enemy getNewShootingEnemy(double posx, double posy, double hp, double attack){
        try {
            return new ShootingEnemy(posx, posy, hp, attack,15, this.gameEngine, new Image(new FileInputStream("src/main/resources/ShootingEnemy.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
