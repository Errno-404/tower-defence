package agh.ics.oop.buildings.AttackingBuildings;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.Attacks.NormalProjectile;
import agh.ics.oop.Constants;
import agh.ics.oop.Enemies.Enemy;
import agh.ics.oop.Vector;

public class AttackFactory{

    public static Attack BasicTowerAttack(Enemy target, Vector startingPosition){
        return new NormalProjectile(startingPosition,Constants.basicProjectileSpeed, Constants.basicAttackStrength,target.getHitbox().centre);
    }
}
