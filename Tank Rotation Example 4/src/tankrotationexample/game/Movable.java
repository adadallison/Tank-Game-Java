package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.image.BufferedImage;

public abstract class Movable extends GameObject {
    protected int vx;
    protected int vy;
    protected int oldX;
    protected int oldY;
    private final double speed = 1.5; //speed of tank
    private final float ROTATIONSPEED = 1.63f;

    public Movable(int x, int y, int vx, int vy, int angle, BufferedImage img, GameObjects type) {
        super(x,y,angle,img, type);
        oldX = x;
        oldY = y;
        this.vx = vx;
        this.vy = vy;
    }

    protected void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }   //move to movable

    protected void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    protected void moveBackwards() {
        oldX = x;
        oldY = y;
        vx = (int) Math.round(speed * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(speed * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        this.hitBox.setLocation(x,y); //creating hitbox around each moveable
    }

    protected void moveForwards() {
        oldX = x;
        oldY = y;
        vx = (int) Math.round(speed * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(speed * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        this.hitBox.setLocation(x,y); //updates location of hitbox to match each oveables location
    }

    //Collision Scenarios amongst GameObjects:
    public void collideWith(GameObject other) {
        //Tank      vs      Tank
        //Bullet    vs      Tank
        //Bullet    vs      UnBreak
        //Bullet    vs      Break
        //Tank      vs      Power-up
        //Bullet    vs      Power-up (nothing)
        //System.out.println(this.getClass().toString() + " collided with " + other.getClass().toString());

        switch (this.type) {
            case TANK: // when TANK interacts with OTHER game object
                switch(other.type) {
                    case POWER_UP:
                        ((Tank)this).applyPowerUp((PowerUp)other); //calls method inside Tank class
                        other.setShow(false); //power up disappears once Tank comes in contact with it

                        break;
                    case TANK:
                    case BREAKABLE_WALL:
                    case UNBREAKABLE_WALL:
                        setToPreviousPosition();
                        break;
                }
                break;
            case BULLET:  //when BULLET interacts with OTHER game object
                switch(other.type) {
                    case TANK:
                        this.setShow(false);
                        ((Tank)other).wasShot(); //the other tank aka the tank being shot is now affected
                        break;
                    case POWER_UP:
                        break;
                    case UNBREAKABLE_WALL:
                        this.setShow(false);
                        break;
                    case BREAKABLE_WALL:
                        other.setShow(false);
                        this.setShow(false);
                        break;
                }
                break;
        }
    }

    private void setToPreviousPosition() {
        x = oldX;
        y = oldY;
    }


}