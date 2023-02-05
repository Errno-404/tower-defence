package agh.ics.oop.Attacks;

import agh.ics.oop.Hitboxes.RectangularHitbox;
import agh.ics.oop.Interfaces.Hittable;
import agh.ics.oop.Interfaces.ProjectileObserver;
import agh.ics.oop.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;

public abstract class Projectile extends Attack {

    public Vector position;
    double velocity;
    RectangularHitbox hitbox;

    ImageView sprite;

    ProjectileObserver pobs;

    protected Projectile(Vector position, double velocity, double str){
        super(str);
        this.position = position;
        this.velocity = velocity;
        this.hitbox = new RectangularHitbox(position, 4);
    }

    protected Projectile(Vector position, double velocity){
        super(50);
        this.position = position;
        this.velocity = velocity;
        this.hitbox = new RectangularHitbox(position, 3);
    }

    public abstract void move();

    public abstract void hit(Hittable h);

    public void draw(GraphicsContext gc){
        gc.drawImage(this.sprite.getImage(),this.hitbox.upperLeft.getX(), this.hitbox.upperLeft.getY());
    }

    public void setObserver(ProjectileObserver o){
        this.pobs = o;
    }

    public RectangularHitbox getHitbox(){
        return this.hitbox;
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
