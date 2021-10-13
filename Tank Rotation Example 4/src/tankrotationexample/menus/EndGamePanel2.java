package tankrotationexample.menus;

import tankrotationexample.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EndGamePanel2 extends JPanel {

    private BufferedImage menuBackground;
    private JButton start;
    private JButton exit;
    private Launcher lf;

    public EndGamePanel2(Launcher lf) {
        this.lf = lf;
        try {
            menuBackground = ImageIO.read(this.getClass().getClassLoader().getResource("gameover2.png")); //the title for Tank Wars
        } catch (IOException e) {
            System.out.println("Error cant read menu background");
            e.printStackTrace();
            System.exit(-3);
        }

        this.setBackground(Color.BLACK);
        this.setLayout(null);

        //no option to restart game, only exit:

        /*start = new JButton("Restart Game");
        start.setFont(new Font("Monospaced", Font.PLAIN ,21));
        start.setBounds(138,290,200,47);
        start.addActionListener((actionEvent -> {
            this.lf.setFrame("game");
        }));*/


        exit = new JButton("Exit");
        exit.setFont(new Font("Monospaced", Font.PLAIN ,21));
        exit.setBounds(150,315,170,47);
        exit.addActionListener((actionEvent -> {
            this.lf.closeGame();
        }));


        //this.add(start);
        this.add(exit);

    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground,0,0,null);
    }
}
