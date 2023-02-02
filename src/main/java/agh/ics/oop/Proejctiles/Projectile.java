package agh.ics.oop.Proejctiles;

import agh.ics.oop.Enemy;
import agh.ics.oop.Hitboxes.RectangularHitbox;
import agh.ics.oop.Interfaces.ProjectileObserver;
import agh.ics.oop.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;

public abstract class Projectile {

    public Vector position;
    double velocity;
    RectangularHitbox hitbox;

    ImageView sprite;

    ProjectileObserver pobs;

    protected Projectile(Vector position, double velocity){
        this.position = position;
        this.velocity = velocity;
        this.hitbox = new RectangularHitbox(position, 4);
    }

    public abstract void move();

    public abstract void hit(Enemy collided);

    public void draw(GraphicsContext gc){
        gc.drawImage(this.sprite.getImage(),this.hitbox.upperLeft.getX(), this.hitbox.upperLeft.getY());
    }

    public void setObserver(ProjectileObserver o){
        this.pobs = o;
    }

    @Override
    public String toString(){
        return "( " + this.position.toString() + "   " + this.position.getXindex() + " " + this.position.getYindex();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
