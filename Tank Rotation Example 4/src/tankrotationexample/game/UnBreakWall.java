package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnBreakWall extends Wall{

    public UnBreakWall(int x, int y, BufferedImage img){
        super(x,y,0,img, GameObjects.UNBREAKABLE_WALL);
    }
}
