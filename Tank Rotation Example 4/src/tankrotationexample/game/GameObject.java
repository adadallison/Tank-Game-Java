package tankrotationexample.game;
import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    public static enum GameObjects {
        TANK, BULLET, UNBREAKABLE_WALL, BREAKABLE_WALL, POWER_UP
    }

    protected int x;
    protected int y;
    protected int angle;
    protected BufferedImage img;
    protected GameObjects type;
    protected Rectangle hitBox;
    protected boolean show;


    public GameObject (int x, int y, int angle, BufferedImage img, GameObjects type) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.angle = angle;
        this.hitBox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
        this.type = type;
        this.show = true;
    }

    void setX(int x){
        this.x = x;
    }

    void setY(int y) {
        this. y = y;
    }

    public boolean isShown() {
        return show;
    }

    public void setShow(boolean s) {
        this.show = s;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public BufferedImage getImg() { return img; }



    void drawImage(Graphics g) {
        if (this.show) {
            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
            rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.img, rotation, null);
            //Hitbox
            //g2d.setColor(Color.CYAN);
            //g2d.drawRect(x, y, this.img.getWidth(), this.img.getHeight());
            //g2d.drawString("Test", 500, 500);
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }
}
