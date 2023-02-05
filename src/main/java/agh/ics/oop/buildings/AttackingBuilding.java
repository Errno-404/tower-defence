package agh.ics.oop.buildings;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.Vector;

public abstract class AttackingBuilding extends Building{

    //TODO TOWERS
    // pytanie, czy ta klasa jest nam potrzebna w ogóle? mają być 3 rodzaje wieżyczek, ale wystarczy tworzyć wtedy
    // obiekty o 3 różnych zestawach parametrów, chyba że chcemy dodać różne klasy tych wieżyczek, np. te co
    // strzelają, te co wpływają jakoś na mapę (np. trzęsienie ziemi) lub takie, które spawnują rycerzy.
    // ja bym zostawił tę klasę jako możliwość rozszerzenia i zdefiniował w niej podstawowe parametry, wspólne dla
    // takich klas wieżyczek - zdrowie, obrona, pozycja itd.
    //

    private double radius;
    protected Attack attack;

    protected AttackingBuilding(int width, int height, Vector position, int health, Attack attack) {
        super(width, height, position, health);
        this.attack = attack;
    }

    @Override
    public void getHit(Attack attack) {
        this.reduceHealth(attack.getStrength());
    }
}
