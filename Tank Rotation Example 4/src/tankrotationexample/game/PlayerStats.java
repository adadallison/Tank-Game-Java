package tankrotationexample.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class PlayerStats {
    private BufferedImage img;
    private int lifeCount;
    private int health;
    private int player;
    private Tank t;

    private final int INITIAL_LIFE_COUNT = 3;

    public PlayerStats(Tank t) {
        this.t = t;
        this.player = t.getPlayerNum();
        this.img = t.getImg();
        this.lifeCount = INITIAL_LIFE_COUNT;
        this.health = 100;
    }

    void drawStats(Graphics g) {
        int xStart = 0;
        if (player == 2) {
            xStart = 700;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, xStart + 70, 630, 70, 72, null); //draws each players stats box

        //Player #:
        g2d.setColor(Color.WHITE);
        Font playerFont = new Font("Monospaced", Font.PLAIN, 20);
        g2d.setFont(playerFont);
        g2d.drawString("PLAYER " + player, xStart + 53, 600);

        //Health Bar:
        g2d.drawRect(xStart + 225, 585, 110, 20);
        if (health >= 50)
            g2d.setColor(Color.GREEN);
        else if (health >= 25)
            g2d.setColor(Color.ORANGE);
        else
            g2d.setColor(Color.RED);
        g2d.fillRect(xStart + 230, 590, health, 10);

        //Life Count:
        g2d.setColor(Color.GREEN);
        for (int i = 0; i < lifeCount; i++) {
            g2d.fillOval(xStart + 230 + 15 * i, 610, 10, 10);
        }

        //Displays stats if tank is BulletProof:
        g2d.setColor(Color.BLUE);
        Font bulletPFont = new Font("Monospaced", Font.PLAIN, 18);
        g2d.setFont(bulletPFont);
        g2d.drawString("Bulletproof: ", xStart + 222, 710);

        if (t.isBulletProof()) {
            g2d.setColor(Color.WHITE);
            g2d.drawString("Yes", xStart + 360, 710);
        } else {
            g2d.setColor(Color.WHITE);
            g2d.drawString("No", xStart + 360, 710);

        }

        //Displays HealthCount:
        g2d.setColor(Color.YELLOW);
        Font healthFont = new Font("Monospaced", Font.PLAIN, 18);
        g2d.setFont(healthFont);
        g2d.drawString("Health: " + health, xStart + 222, 680);

        //Displays LifeCount:
        g2d.setColor(Color.RED);
        Font lifeFont = new Font("Monospaced", Font.PLAIN, 18);
        g2d.setFont(lifeFont);
        g2d.drawString("Lives: " + lifeCount + "/5", xStart + 222, 650);


    }

    //Health and Life Damage done to bullet being shot:
    public boolean damage(int damageDone) {
        boolean gameOver = false;
        health -= damageDone;

      /*  //if player 2 is being shot then they get more lives
        if (t.getPlayerNum() == 2 && t.hasFlowerPowerUp()) {
            lifeCount++;
        }
*/
        if (health <= 0) {
            if (lifeCount > 1) {
                lifeCount--;
                health = 100;
            } else {
                gameOver = true;
            }
        }
        return gameOver;

    }

    public void addLife() {
        // 5 lives is the maximum lives a player can have
        if (lifeCount < 5) {
            lifeCount++;
        }
    }


    public void addHealth () {
        //1 full health bar is equal to 100, this adds 10pts of health as long as bar is not filled up
        if (lifeCount <= 5) { //&& health < 100
                health = health + 10;
                //allows coin to increase life count if health bar is full
                if (health >= 100){ //new life with health starting from the beginning
                    lifeCount++;
                    health = 10;
                    if (lifeCount == 6) {
                        lifeCount = 5;
                        health = 100;
                    }
                    }
                }
            }
        }


