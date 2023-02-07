package agh.ics.oop.buildings.AttackingBuildings;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.Attacks.NormalProjectile;
import agh.ics.oop.Constants;
import agh.ics.oop.Enemies.Enemy;
import agh.ics.oop.Vector;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AttackFactory{

    public static Attack BasicTowerAttack(Vector target, Vector startingPosition, double attackPower){
        try {
            return new NormalProjectile(startingPosition, Constants.basicProjectileSpeed, attackPower, target,new Image(new FileInputStream("src/main/resources/yellowRect.png")));
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }

    }

    public static Attack BasicEnemyAttack(Vector target, Vector startingPosition, double attackPower){
        try {
            return new NormalProjectile(startingPosition, Constants.basicProjectileSpeed, attackPower, target, new Image(new FileInputStream("src/main/resources/EnemyProjectile.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Attack BasicBossAttack(Vector target, Vector startingPosition, double attackPower){
        try {
            return new NormalProjectile(startingPosition, Constants.basicProjectileSpeed, attackPower, target, new Image(new FileInputStream("src/main/resources/BossProjectile.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Attack SpreadShootingTowerAttack(Vector target, Vector startingPosition, double attackPower){
        try {
            return new NormalProjectile(startingPosition, Constants.basicProjectileSpeed, attackPower, target, new Image(new FileInputStream("src/main/resources/yellowRect.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
