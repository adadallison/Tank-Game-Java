package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakWall extends Wall{

    public BreakWall(int x, int y, BufferedImage img){
        super(x,y,0,img, GameObjects.BREAKABLE_WALL);
    }
}
