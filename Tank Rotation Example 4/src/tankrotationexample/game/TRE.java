/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankrotationexample.game;


import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static javax.imageio.ImageIO.read;

/**
 * @author anthony-pc
 */

// Runnable allows class to be threaded object
public class TRE extends JPanel implements Runnable {

    public static BufferedImage bulletImage; // there is a better way of doing this - prof
    static long tickCount = 0;
    ArrayList<Stationary> stationaryObjects;
    private BufferedImage world;
    private Image backgroundImg;
    private Tank t1;
    private Tank t2;
    private final Launcher lf;
    private long tick = 0;


    public TRE(Launcher lf) {
        this.lf = lf;
    }

    @Override
    public void run() {
        try {
            this.resetGame();
            while (true) {
                this.tick++;
                this.t1.update(); // calls update() in Tank class
                this.t2.update();

                //Checks all  TANK collisions with Stationary Objects
                for (int i = 0; i < stationaryObjects.size(); i++) {
                    Collision.check(t1, stationaryObjects.get(i));
                    Collision.check(t2, stationaryObjects.get(i));
                }

                //Checks all BULLETS collision with Stationary Objects
                int numT1Bullets = t1.getBullets().size();
                int numT2Bullets = t2.getBullets().size();
                for (int i = 0; i<Math.max(numT1Bullets, numT2Bullets); i++) {
                    for (int s = 0; s < stationaryObjects.size(); s++) {
                        if (i<numT1Bullets)
                            Collision.check(t1.getBullets().get(i), stationaryObjects.get(s));
                        if (i<numT2Bullets)
                            Collision.check(t2.getBullets().get(i), stationaryObjects.get(s));

                        if (!stationaryObjects.get(s).isShown()) { //once an object is shot at and isShow() os false, the object will no longer be drawn
                            stationaryObjects.remove(s);
                        }
                    }

                    //Check t1's bullets and t2 collision
                    if (i<numT1Bullets)
                        Collision.check(t1.getBullets().get(i), t2);
                    if (i<numT2Bullets)
                        Collision.check(t2.getBullets().get(i), t1);
                }

                //Check Tanks collision
                Collision.check(t1, t2);
                Collision.check(t2, t1);

                this.repaint();   // redraw game
                Thread.sleep(1000 / 200); //sleep for a few milliseconds
                tickCount++;

                /**
                 * Ends the game.
                 */
                if (t1.isDead()) {
                    //Display that t1 won
                    this.lf.setFrame("end2");
                    return;
                }
                else if (t2.isDead()) {
                    //Display that t2 won
                    this.lf.setFrame("end1");
                    return;
                }

            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame() {
        this.tick = 0;
        this.t1.setX(200);
        this.t1.setY(300);

        this.t2.setX(900);
        this.t2.setY(300);
    }


    /*public void endGame1() {
        this.lf.setFrame("end1");
    }
    public void endGame2() {
        this.lf.setFrame("end2");
    }*/


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {
        this.world = new BufferedImage(GameConstants.GAME_SCREEN_WIDTH,
                GameConstants.GAME_SCREEN_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        BufferedImage t1img = null;
        BufferedImage t2img = null;
        BufferedImage breakWall = null;
        BufferedImage unbreakWall = null;
        BufferedImage powerUp1 = null;
        BufferedImage powerUp2 = null;
        BufferedImage powerUp3 = null;
        stationaryObjects = new ArrayList<>();

        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */
            t1img = read(TRE.class.getClassLoader().getResource("tank1.png"));
            t2img = read((TRE.class.getClassLoader().getResource("tank2.png")));
            unbreakWall = read(TRE.class.getClassLoader().getResource("break.png"));
            breakWall = read(TRE.class.getClassLoader().getResource("unbreak.png"));
            powerUp1 = read(TRE.class.getClassLoader().getResource("powerup1.png"));
            powerUp2 = read(TRE.class.getClassLoader().getResource("powerup2.png"));
            powerUp3 = read(TRE.class.getClassLoader().getResource("powerup3.png"));
            //TRE.bulletImage = read(TRE.class.getClassLoader().getResource("bullet.png")); // better way of doing this - prof

            //Reading Game Txt. Map:
            InputStreamReader isr = new InputStreamReader(TRE.class.getClassLoader().getResourceAsStream("maps/map1"));
            BufferedReader mapReader = new BufferedReader(isr);

            String row = mapReader.readLine();
            if (row == null) {
                throw new IOException("no data in file");
            }
            String[] mapInfo = row.split("\t"); // map is separated by tabs
            int numCols = Integer.parseInt(mapInfo[0]);
            int numRows = Integer.parseInt(mapInfo[1]);

            //23 x 24 = rows x cols: always has to be 1 # lower than the actual rows and cols
            for (int curRow = 0; curRow < numRows; curRow++) {
                row = mapReader.readLine(); // new row we just read
                mapInfo = row.split("\t");
                for (int curCol = 0; curCol < numCols; curCol++) { // for every column in the row
                    switch (mapInfo[curCol]) {
                        case "1":
                            PowerUp pUp1 = new PowerUp(curCol * GameConstants.WALL_SIZE
                                    , curRow * GameConstants.WALL_SIZE, powerUp1, PowerUp.PowerUps.MUSHROOM);
                            this.stationaryObjects.add(pUp1);
                            break;
                        case "2": // if we see a 2 we make a breakable wall
                            BreakWall br = new BreakWall(curCol * GameConstants.WALL_SIZE
                                    , curRow * GameConstants.WALL_SIZE, breakWall); //curCol*30 switched curRow*30 to change rotation of map
                            this.stationaryObjects.add(br);
                            break;
                        case "3": // if we see a 3 we make a unbreakable wall
                        case "9": // if we see a 9 we make an unbreakable wall border
                            UnBreakWall ubr = new UnBreakWall(curCol * GameConstants.WALL_SIZE
                                    , curRow * GameConstants.WALL_SIZE, unbreakWall);
                            this.stationaryObjects.add(ubr);
                            break;
                        case "4":
                            PowerUp pUp2 = new PowerUp(curCol * GameConstants.WALL_SIZE,
                                    curRow * GameConstants.WALL_SIZE, powerUp2, PowerUp.PowerUps.STAR);
                            this.stationaryObjects.add(pUp2);
                            break;
                        case "5":
                            PowerUp pUp3 = new PowerUp(curCol * GameConstants.WALL_SIZE,
                                    curRow * GameConstants.WALL_SIZE, powerUp3, PowerUp.PowerUps.COIN);
                            this.stationaryObjects.add(pUp3);
                            break;
                    }
                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        //Initialize Tanks:
        t1 = new Tank(200, 300, 0, 0, 0, t1img, 1);
        t2 = new Tank(900, 300, 0, 0, 0, t2img, 2);
        // create tank  control objects
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_F);
        this.setBackground(Color.WHITE);
        // add event listeners to J frame getting a reference to Tank Control class
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

       //Background Image:
       /*try {
            backgroundImg = ImageIO.read(TRE.class.getClassLoader().getResource("background2.png"));
        } catch (IOException e) {
            System.out.println("failed");
        }*/

        //g2.drawImage(world, 0, 0, null);

        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.black); // redraws the floor aka black bkgrnd everytime tanks update so there is no trail
        buffer.fillRect(0, 0, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT);

        buffer.drawImage(backgroundImg, 0, 0, null);

        //Drawing Stationary Objects aka PowerUps & Walls:
        this.stationaryObjects.forEach(stationaryObjects -> stationaryObjects.drawImage(buffer)); //For each stationary object created, draw stationary object
        /*for (Stationary stationaryObject : this.stationaryObjects) {
            stationaryObject.drawImage(buffer);
        }*/

        //Drawing Tank:
        this.t1.drawImage(buffer); // tank 1
        this.t2.drawImage(buffer); // tank 2


        //Split Screen:
        int x1Start;
        int x2Start;


        //Tank 1 Split Screen X:
        //1st scenario: when the tanks x coordinate is less than 1/4 of the GAME_SCREEN_WIDTH (x value)
        if (t1.getX() < GameConstants.GAME_SCREEN_WIDTH/4) {
            x1Start = 0; //our perspectives begins at the top left corner which is the origin
        }
        //2nd scenario: when the tanks x coordinate is greater than 3/4 of the GAME_SCREEN_WIDTH/4 (x value)
        else if (t1.getX() > 3*GameConstants.GAME_SCREEN_WIDTH/4) {
            x1Start = GameConstants.GAME_SCREEN_WIDTH/2 -5; //our perspectives begins at the middle of the screen
        }
        //2nd scenario: when the tanks x coordinate is not less than 1/4 of the GAME_SCREEN_WIDTH OR greater than 3/4 of the GAME_SCREEN_WIDTH/4
        else {
            x1Start = t1.getX() - GameConstants.GAME_SCREEN_WIDTH/ 4;
        }


        //Tank 2 Split Screen:
        //1st scenario: when the tanks x coordinate is less than 1/4 of the GAME_SCREEN_WIDTH (x value)
        if (t2.getX() < GameConstants.GAME_SCREEN_WIDTH /4) {
            x2Start = 0; //our perspectives begins at the top left corner which is the origin
        }
        //2nd scenario: when the tanks x coordinate is greater than 3/4 of the GAME_SCREEN_WIDTH/4 (x value)
        else if (t2.getX() > 3*GameConstants.GAME_SCREEN_WIDTH /4) {
            x2Start = GameConstants.GAME_SCREEN_WIDTH /2 ; //our perspectives begins at the middle of the screen
        }
        //3rd scenario: when the tanks x coordinate is not less than 1/4 of the GAME_SCREEN_WIDTH OR greater than 3/4 of the GAME_SCREEN_WIDTH/4
        else {
            x2Start = t2.getX() - GameConstants.GAME_SCREEN_WIDTH/ 4;
        }


        //Split Screen Drawing:
        BufferedImage leftHalf = world.getSubimage(x1Start,0,GameConstants.GAME_SCREEN_WIDTH/2, GameConstants.STATS_START);
        BufferedImage rightHalf = world.getSubimage(x2Start,0,GameConstants.GAME_SCREEN_WIDTH/2, GameConstants.STATS_START);
        g2.drawImage(leftHalf,0,0,null); // draw buffered image to screen
        g2.drawImage(rightHalf,GameConstants.GAME_SCREEN_WIDTH/2 +4,0,null); // draw buffered image to screen

        //Stats Drawing:
        BufferedImage stats = world.getSubimage(0,GameConstants.STATS_START,GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT - GameConstants.STATS_START); //SUPPOSED to split screen?
        g2.drawImage(stats,0,GameConstants.STATS_START,null); // draw buffered image to screen

        //Mini Map:
        g2.scale(.20, .20);
        BufferedImage map = world.getSubimage(0, 0, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT);
        g2.drawImage(map, 2000, 2885, 1500, 1050, null); // position of mini map
    }

}