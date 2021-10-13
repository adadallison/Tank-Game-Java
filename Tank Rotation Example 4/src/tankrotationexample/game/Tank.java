package tankrotationexample.game;



import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static javax.imageio.ImageIO.read;

/**
 *
 * @author anthony-pc
 */
public class Tank extends Movable {
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
    private ArrayList<Bullet> bullets;
    private BufferedImage bulletImg;
    private PlayerStats stats;
    private boolean bulletProof;
    private long bulletProofTime;
    private boolean isDead;
    private int player;
    private final int BULLETPROOF_TIME = 3000; //time player is bulletproof when obtaining a 'star' power-up

    Tank(int x, int y, int vx, int vy, int angle, BufferedImage img, int player) {
        super(x,y,vx,vy,angle,img, GameObjects.TANK);
        this.bullets = new ArrayList<>();
        this.player = player;
        this.stats = new PlayerStats(this);
        this.bulletProof = false;
        this.bulletProofTime = 0;
        this.isDead = false;
        try {
            bulletImg = read(Tank.class.getClassLoader().getResource("bullet.png"));
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }


    void wasShot() {
        if (!bulletProof) { //if tank is not bulletproof then it can be shot at
            //calls damage to be done in stats class
            if (stats.damage(Bullet.DAMAGE)) {
                isDead = true;
            }
        }
    }



    boolean isDead() {
        return isDead;
    }

    boolean isBulletProof() {
        return bulletProof;
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPressed() {
        this.ShootPressed = true;
    }

    //Accessed in TRE
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public int getPlayerNum() {
        return player;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleShootPressed() {
        this.ShootPressed = false;
    }

    //Creating bullet that is to be fired and then adding it to bullet ArrayList
    private void fire() {
        Bullet bulletFired = new Bullet(x+5, y+5, angle, bulletImg);
        bullets.add(bulletFired);
    }


    public void applyPowerUp(PowerUp pU) {
        if(pU.isShown()) {
            switch (pU.powerUpType) {
                case MUSHROOM: //allows player to obtain extra lives, with the limit being 5 lives
                    stats.addLife();
                    break;
                case STAR: //allows player to be bulletproof for a set time
                    bulletProof = true;
                    bulletProofTime = BULLETPROOF_TIME;
                    break;
                case COIN: //boosts players health by increment of 10
                    stats.addHealth();
                    break;
            }
        }
    }

    //Updates Tanks:
    void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.ShootPressed && TRE.
                tickCount%20 == 0) {
            fire();
        }

        //Decrements time tank is able to use the obtained POWER-UP:
        if(this.bulletProof) {
            bulletProofTime--; //as game updates the time a player is bulletproof decreases
            if (bulletProofTime <= 0) {
                bulletProof = false;
            }
        }

        //Bullets fired:
        //bullets.forEach(bullet -> bullet.update());
        for (int i=0; i<bullets.size(); i++) {
            if (bullets.get(i).isShown()) { //if bullet is shown then
                bullets.get(i).update(); //moves bullets forward when shooting
            }
            else {
                bullets.remove(i); //if bullet is not shown then the bullet will be removed from arraylist
            }
        }
    }

    void drawImage(Graphics g) {
        super.drawImage(g);
        Graphics2D g2d = (Graphics2D) g;

        //Draws Bulletproof Tank Shield:
        if (bulletProof) {
            g2d.setColor(Color.BLUE);
            g2d.drawOval(x - 9, y -7, 49, 49);
        }

        //Draws Bullets each Tank fires():
        //this.bullets.forEach(bullets -> bullets.drawImage(g));
        for (int i=0; i<bullets.size(); i++) {
            bullets.get(i).drawImage(g);
        }

        stats.drawStats(g);
    }
}
