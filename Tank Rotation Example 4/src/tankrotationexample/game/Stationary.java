package tankrotationexample.game;

import java.awt.image.BufferedImage;

//consists of POWER-UPS and WALLS
public abstract class Stationary extends GameObject {
    public Stationary(int x, int y, int angle, BufferedImage img, GameObjects type) {
        super(x,y,angle,img, type);
    }
}
