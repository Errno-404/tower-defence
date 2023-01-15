package agh.ics.oop.Proejctiles;

import agh.ics.oop.Attackers;
import agh.ics.oop.Hitboxes.RectangularHitbox;
import agh.ics.oop.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Projectile {

    Vector position;
    double velocity;
    RectangularHitbox hitbox;

    ImageView sprite;

    protected Projectile(Vector position, double velocity){
        this.position = position;
        this.velocity = velocity;
        this.hitbox = new RectangularHitbox(position, 4);
    }

    public abstract void move();

    public abstract void hit(Attackers collided);

    public void draw(GraphicsContext gc){
        gc.drawImage(this.sprite.getImage(),this.hitbox.upperLeft.getX(), this.hitbox.upperLeft.getY());
    }
}
