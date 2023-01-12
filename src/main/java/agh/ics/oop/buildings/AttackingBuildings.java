package agh.ics.oop.buildings;

import agh.ics.oop.Attack;
import agh.ics.oop.Vector;

public abstract class AttackingBuildings extends Buildings {

    //TODO TOWERS
    // pytanie, czy ta klasa jest nam potrzebna w ogóle? mają być 3 rodzaje wieżyczek, ale wystarczy tworzyć wtedy
    // obiekty o 3 różnych zestawach parametrów, chyba że chcemy dodać różne klasy tych wieżyczek, np. te co
    // strzelają, te co wpływają jakoś na mapę (np. trzęsienie ziemi) lub takie, które spawnują rycerzy.
    // ja bym zostawił tę klasę jako możliwość rozszerzenia i zdefiniował w niej podstawowe parametry, wspólne dla
    // takich klas wieżyczek - zdrowie, obrona, pozycja itd.
    //


    protected Attack attack;

    protected AttackingBuildings(int width, int height, Vector position, int health, Attack attack) {
        super(width, height, position, health);
        this.attack = attack;
    }


}
