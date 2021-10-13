package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

// modify so it fits OOP standards - professor

public class Bullet extends Movable {
    private int bulletSpeed = 6;
    private Rectangle hitbox;
    public final static int DAMAGE = 10;


    public Bullet(int x, int y, int angle, BufferedImage img) {
        super(x,y,5,5, angle, img, GameObjects.BULLET);
        this.hitbox = new Rectangle(x,y,img.getWidth(), img.getHeight());
    }

    //speed of bullets must differ from tank so they are not drawn on top of one another
    protected void moveForwards() {
        oldX = x;
        oldY = y;
        vx = (int) Math.round(bulletSpeed * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(bulletSpeed * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        this.hitBox.setLocation(x,y);
    }

    public void update(){
        moveForwards();
    }

}
