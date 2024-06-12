package main;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import character.*;
import minigames.*;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETUP
    final int actualTile = 16; // 16x16 tile, 8 bit?
    final int scale = 4; // scale tile by 4
    public final int displayedTile = actualTile*scale; // 64x64 tile
    final int col = 16;
    final int row = 12;
    public final int width = displayedTile * col;
    public final int height = displayedTile * row;

    // SYSTEM SETUP
    int FPS = 60;
    Thread gameThread;
    KeyHandler keyH = new KeyHandler(this);
    public UI ui = new UI(this, keyH);
    boolean gameOver;


    // CHARACTER SETUP
    Player player = new Player(this,keyH);
    OpponentOne opp1 = new OpponentOne(this);
    OpponentTwo opp2 = new OpponentTwo(this);
    OpponentThree opp3 = new OpponentThree(this);
    OpponentFour opp4 = new OpponentFour(this);
    public ArrayList<Integer> completed = new ArrayList<Integer>();
    PolygonGuessingGame pgg = new PolygonGuessingGame(this, keyH);
    FlagGuessingGame fgg = new FlagGuessingGame(this,keyH);
    ArithmeticGame ag = new ArithmeticGame(this,keyH);
    SynonymFindingGame sfg = new SynonymFindingGame(this,keyH);



    // GAME STATE
    public final int intro = 1;
    public final int playing = 2;
    public final int dialogue = 3; 
    public final int minigame = 4;
    public final int outro = 5;
    public int gameState;




    public GamePanel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);
        this.setOpaque(false);
        this.setDoubleBuffered(true); // improve rendering
        this.addKeyListener(keyH);
        this.setFocusable(true);

        gameOver = false;
        gameState = intro;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); 
    }

    public void run() {
        double drawInterval =  1000000000/FPS; // 0.016666.... seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) { // when thread is running

            // UPDATE: update info like change in character position
            update();

            // DRAW: draw the screen with updated info 
            repaint();


            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                if (remainingTime <0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    } 

    

    public void update() {
        if (gameState == playing) {
            player.update();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g); // access java built in paintComponent method

        Graphics2D g2 = (Graphics2D)g;


        opp1.draw(g2);
        opp2.draw(g2);
        opp3.draw(g2);
        opp4.draw(g2);
        player.draw(g2);
        ui.draw(g2);
        pgg.draw(g2);
        fgg.draw(g2);
        ag.draw(g2);
        sfg.draw(g2);
        

        g2.dispose(); // save memory
    }

    public int determineCollision() {
        int oppNum = 0;
        if (player.x <= 130 && player.y<=120) {
            oppNum = 1;
        } else if (player.x >= 805 && player.y>= 520) {
            oppNum = 2;
        } else if (player.x >= 805 && player.y<=140) {
            oppNum = 3;
        } else if (player.x<=120 && player.y>=500 ) {
            oppNum = 4;
        }
        return oppNum;
    }
    
    public boolean hasFought() {
        boolean fought = false;
        for (int element : completed) {
            if (determineCollision() == element) {
                fought = true;
                return fought;
            }
        }
        return fought;
    }
}