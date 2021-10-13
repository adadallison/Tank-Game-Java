package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Wall extends Stationary {
    public Wall(int x, int y, int angle, BufferedImage img, GameObjects type) {
        super(x,y,angle,img,type);
    }
}
