package main;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import character.*;
import minigames.*;

/*
 * GamePanel Class (subclass of JPanel)
 */
public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETUP
    final int actualTile = 16; // 16x16 tile
    final int scale = 4; // scale tile by 4
    public final int displayedTile = actualTile*scale; // 64x64 actual size
    final int col = 16; // number of tiles per column
    final int row = 12; // number of tiles per row
    public final int width = displayedTile * col; // screen width is acquired by multiplying tile size and number of columns
    public final int height = displayedTile * row; // height is acquried by multiplying tile size and number of rows

    // SYSTEM SETUP
    int FPS = 60;
    Thread gameThread;
    KeyHandler keyH = new KeyHandler(this);
    public ArrayList<Integer> completed = new ArrayList<Integer>();


    // CHARACTER SETUP
    private Player player = new Player(this,keyH);
    private OpponentOne opp1 = new OpponentOne(this);
    private OpponentTwo opp2 = new OpponentTwo(this);
    private OpponentThree opp3 = new OpponentThree(this);
    private OpponentFour opp4 = new OpponentFour(this);

    // TEXT SETUP
    private UI ui = new UI(this, keyH);

    // MINIGAME SETUP 
    private PolygonGuessingGame pgg = new PolygonGuessingGame(this, keyH);
    private FlagGuessingGame fgg = new FlagGuessingGame(this,keyH);
    private ArithmeticGame ag = new ArithmeticGame(this,keyH);
    private SynonymFindingGame sfg = new SynonymFindingGame(this,keyH);



    // GAME STATE
    public final int intro = 1; // intro slides sequence
    public final int playing = 2; // player moving around time
    public final int dialogue = 3; // dialogue time
    public final int minigame = 4; // minigame time
    public final int outro = 5; // outro slides sequence
    public final int end = 6; // game is finished
    public int gameState;




    /* constructor
     * pre: none
     * post: GamePanel setup complete, game state set to intro
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);
        this.setOpaque(false);
        this.setDoubleBuffered(true); // improve rendering
        this.addKeyListener(keyH);
        this.setFocusable(true);

        gameState = intro;
    }


    /* starts game thread
     * pre: none
     * post: game thread initialized and started 
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); 
    }

    
    /* game loop using "sleep" method 
     * pre: none
     * post: game loop is running 
     */
    public void run() {
        double drawInterval =  1000000000/FPS; // 0.016666.... seconds, amount of time between the repaintings of the frame
        double nextDrawTime = System.nanoTime() + drawInterval; 

        while (gameThread != null) { // when thread is running

            // UPDATE: update info like change in character position
            update();

            // DRAW: draw the screen with updated info 
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime(); // amt of time the loop is paused for before updating and repainting
                remainingTime = remainingTime/1000000; // convert to milliseconds
                if (remainingTime <0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime); // short pause before screen repaints again

                nextDrawTime += drawInterval; // kind of like resetting the loop's time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    } 

    
    /* method that updates images
     * pre: none
     * post: images are updated before being repainted
     */
    public void update() {
        if (gameState == playing) {
            player.update(); // player is moved around
        }
    }

    /* method that repaints images
     * pre: none
     * post: images repainted 
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g); // access JPanel superclass paintComponent method

        Graphics2D g2 = (Graphics2D)g;

        // IMAGES (in order of layer at the bottom to layer at the top)
        opp1.draw(g2); // opponent one drawn
        opp2.draw(g2); // opponent two drawn
        opp3.draw(g2); // opponent three drawn
        opp4.draw(g2); // opponent four drawn
        player.draw(g2); // player drawn
        ui.draw(g2); // text drawn
        pgg.draw(g2); // polygon guessing game drawn
        fgg.draw(g2); // flag guessing game drawn
        ag.draw(g2); // arithmetic game drawn
        sfg.draw(g2); // synonym finding game drawn
        

        g2.dispose(); // save memory
    }

    /* Determines which opponent the user is currently in collision with 
     * pre: none
     * post: Returns integer associated with the opponent user is in collision with
     */
    public int determineCollision() {
        int oppNum = 0;

        if (player.x <= 130 && player.y<=120) { // player is near MeowMeow
            oppNum = 1;
        } else if (player.x >= 805 && player.y>= 520) { // player is near stinky
            oppNum = 2;
        } else if (player.x >= 805 && player.y<=140) { // player is near Pollynomial
            oppNum = 3; 
        } else if (player.x<=120 && player.y>=500 ) { // player is near MegaGon
            oppNum = 4;
        }

        return oppNum;
    }
    

    /* Determins whether or not an opponent has already been fought
     * pre: none
     * post: Returns true if opponent has been fought, returns false if not
     */
    public boolean hasFought() {
        boolean fought = false;
        for (int element : completed) { // traverses array of completed opponents
            if (determineCollision() == element) {
                fought = true;
                return fought;
            }
        }
        return fought;
    }

}