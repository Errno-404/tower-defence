package agh.ics.oop.buildings.AttackingBuildings;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.Attacks.NormalProjectile;
import agh.ics.oop.Constants;
import agh.ics.oop.Enemies.Enemy;
import agh.ics.oop.Vector;

public class AttackFactory{

    public static Attack BasicTowerAttack(Vector target, Vector startingPosition, double attackPower){
        return new NormalProjectile(startingPosition,Constants.basicProjectileSpeed, attackPower,target);
    }
}
