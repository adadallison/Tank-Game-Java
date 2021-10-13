package tankrotationexample.menus;


import tankrotationexample.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class StartMenuPanel extends JPanel {

    private BufferedImage menuBackground;
    private JButton start; //Start button on Start Panel
    private JButton exit; // Exit button on Start Panel
    private Launcher lf;

    public StartMenuPanel(Launcher lf) {
        this.lf = lf;
        try {
            menuBackground = ImageIO.read(this.getClass().getClassLoader().getResource("title.png"));
        } catch (IOException e) {
            System.out.println("Error cant read menu background");
            e.printStackTrace();
            System.exit(-3);
        }

        this.setBackground(Color.BLACK);
        this.setLayout(null);


        // Button Creation:
        start = new JButton("Start"); // JButton start = new JButton("Start), this is the start button
        start.setBackground(Color.GREEN);
        start.setOpaque(true);
        start.setFont(new Font("Monospaced", Font.BOLD ,24));
        start.setBounds(175,300,150,50);
        // this actionListener listens for an event to be called so code can be triggered
        start.addActionListener((actionEvent -> {
            this.lf.setFrame("game");
        }));


        exit = new JButton("Exit");
        exit.setBackground(Color.RED);
        exit.setOpaque(true);
        exit.setSize(new Dimension(200,100));
        exit.setFont(new Font("Monospaced", Font.BOLD ,24));
        exit.setBounds(175,400,150,50);
        exit.addActionListener((actionEvent -> {
            this.lf.closeGame();
        }));


        this.add(start);
        this.add(exit);

    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground,0,0,null);
    }
}
