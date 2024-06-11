package minigames;

import main.*;
import java.awt.*;
import java.util.*;
import java.awt.image.BufferedImage;

public class SynonymFindingGame extends Minigame{

    KeyHandler keyH;
    private String[] words = {"america","canada","china","france","ireland","italy","japan", "south korea","uk","ukraine"};
    private ArrayList<String> usedWords = new ArrayList<String>();
    private String correctAns;
    private String opt1, opt2, opt3;
    private int opt1x,opt2x,opt3x,opty;
    private String userAns;
    private int numCorrect;
    private String result;
    private BufferedImage flag;


    public SynonymFindingGame(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;


        opt1x = gp.displayedTile*3 ;
        opt2x = gp.displayedTile*7 ;
        opt3x = gp.displayedTile*11 -5;
        opty = gp.displayedTile*8;
    }

    public void draw(Graphics2D g2) {
        if (gp.gameState == gp.minigame && gp.determineCollision() == 1) {

        }
    }





    
}
