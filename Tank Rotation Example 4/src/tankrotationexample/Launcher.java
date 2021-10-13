package tankrotationexample;


import tankrotationexample.game.TRE;
import tankrotationexample.menus.EndGamePanel1;
import tankrotationexample.menus.EndGamePanel2;
import tankrotationexample.menus.StartMenuPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class                                                                                                                         Launcher {

    /*
     * Main panel in JFrame, the layout of this panel
     * will be card layout, this will allow us to switch
     * to sub-panels depending on game state.
     */
    private JPanel mainPanel;
    /*
     * start panel will be used to view the start menu. It will contain
     * two buttons start and exit.
     */
    private JPanel startPanel;
    /*
     * game panel is used to show our game to the screen. inside this panel
     * also contains the game loop. This is where out objects are updated and
     * redrawn. This panel will execute its game loop on a separate thread.
     * This is to ensure responsiveness of the GUI. It is also a bad practice to
     * run long running loops(or tasks) on Java Swing's main thread. This thread is
     * called the event dispatch thread.
     */
    private TRE gamePanel;
    /*
     * end panel is used to show the end game panel.  it will contain
     * two buttons restart and exit.
     */
    private JPanel endPanel1;
    private JPanel endPanel2;
    /*
     * JFrame used to store our main panel. We will also attach all event
     * listeners to this JFrame.
     */
    private JFrame jf;
    /*
     * CardLayout is used to manage our sub-panels. This is a layout manager
     * used for our game. It will be attached to the main panel.
     */
    private CardLayout cl;


    public Launcher(){
        this.jf = new JFrame();             // creating a new JFrame object
        this.jf.setTitle("Allison Adad - Tank Wars Game"); // setting the title of the JFrame window.
        this.jf.setLocationRelativeTo(null); // this will open the window in the center of the screen.
        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // when the GUI is closed, this will also shutdown the VM
    }

    //Instantiation of panels:
    private void initUIComponents(){
        this.mainPanel = new JPanel(); // create a new main panel: JPanel mainPanel = new JPanel();
        this.startPanel = new StartMenuPanel(this); // create a new start panel
        this.gamePanel = new TRE(this); // create a new GAME panel ?
        this.gamePanel.gameInitialize(); // initialize game, but DO NOT start game
        this.endPanel1 = new EndGamePanel1(this); // create a new end game pane;
        this.endPanel2 = new EndGamePanel2(this); // create a new end game pane;
        cl = new CardLayout(); // creating a new CardLayout Panel
        this.jf.setResizable(false); //make the JFrame not resizable
        this.mainPanel.setLayout(cl); // set the layout of the main panel to our card layout
        this.mainPanel.add(startPanel, "start"); //add the start panel to the main panel
        this.mainPanel.add(gamePanel, "game");   //add the game panel to the main panel
        this.mainPanel.add(endPanel1, "end1");    // add the end game panel to the main panel
        this.mainPanel.add(endPanel2, "end2");    // add the end game panel to the main panel

        this.jf.add(mainPanel); // add the main panel to the JFrame ('add' method is part of the JFrame toolkit), IF we uncomment this we will not see the Tank Wars Title Image?
        this.setFrame("start"); // set the current panel to start panel, IF we uncomment this we will not see the Tank Wars Title Image and its blank?

    }

    public void setFrame(String type){
        this.jf.setVisible(false); // hide the JFrame, if set to true the tanks do not move

        // this switch case is used for switching between panel sizes?
        switch(type){
            case "start":
                // set the size of the jFrame to the expected size for the start panel
                this.jf.setSize(GameConstants.START_MENU_SCREEN_WIDTH,GameConstants.START_MENU_SCREEN_HEIGHT);
                break;
            case "game":
                // set the size of the jFrame to the expected size for the game panel
                this.jf.setSize(GameConstants.GAME_SCREEN_WIDTH,GameConstants.GAME_SCREEN_HEIGHT);

                //start a new thread for the game to run. This will ensure our JFrame is responsive and
                // not stuck executing the game loop.
                (new Thread(this.gamePanel)).start();
                break;
            case "end1":
            case "end2":
                // set the size of the jFrame to the expected size for the end panel
                this.jf.setSize(GameConstants.END_MENU_SCREEN_WIDTH,GameConstants.END_MENU_SCREEN_HEIGHT);
                break;
        }
        this.cl.show(mainPanel, type); // change current panel shown on main panel to the panel denoted by type.
        this.jf.setVisible(true); // show the JFrame
    }


    public JFrame getJf() {
        return jf;
    }

    public void closeGame() {
        this.jf.dispatchEvent(new WindowEvent(this.jf, WindowEvent.WINDOW_CLOSING));
    }


    public static void main(String[] args) {
        Launcher launch = new Launcher();
        (new Launcher()).initUIComponents();
    }
}
