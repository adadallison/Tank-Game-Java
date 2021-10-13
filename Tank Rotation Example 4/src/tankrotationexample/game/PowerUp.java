package tankrotationexample.game;

import java.awt.image.BufferedImage;

public class PowerUp extends Stationary {
    public static enum PowerUps {
        MUSHROOM, STAR, COIN
    }

    protected PowerUps powerUpType;

    public PowerUp(int x, int y, BufferedImage img, PowerUps powerUpType){
        super(x,y,0,img, GameObjects.POWER_UP);
        this.powerUpType = powerUpType;
    }
}

