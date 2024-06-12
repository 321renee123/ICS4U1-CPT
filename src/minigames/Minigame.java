package minigames;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import main.*;


/**
 * Minigame abstract super class
 */
public abstract class Minigame {

    GamePanel gp;

    public int x,y; // background location 
    public int opt1x,opt2x,opt3x; // multiple choice options location

    // abstract method to draw
    public abstract void draw(Graphics2D g2);


    /* constructor
     * pre: none
     * post: sets up minigame with location as well as multiple choice question location and game panel object
     */
    public Minigame(GamePanel gp) {
        this.gp = gp;
        opt1x = gp.displayedTile*3;
        opt2x = gp.displayedTile*7-10;
        opt3x = gp.displayedTile*11-20;
    }

    /* checks for repeats of a given line in an array list of strings
     * pre: none
     * post: return boolean of whether or not a string is repeated
     */
    public boolean checkForRepeats(ArrayList<String> list, String line) {
        boolean repeated = false;
        for (String element: list) { // traverse array
            if (line == element) { // if line is present 
                repeated = true; // element already in array list
            }
        }    
        return repeated;
    }

    /* checks for repeats of a given line in an array list of integers
     * pre: none
     * post: return boolean of whether or not an integer is repeated
     */
    public boolean checkForRepeats(ArrayList<Integer> list, int line) {
        boolean repeated = false;
        for (int element: list) { // traverse array list
            if (line == element) { // if integer is present
                repeated = true; // element is already in array list
            }
        }    
        return repeated;
    }

    /* Draws background
     * pre: none
     * post: background is drawn
     */
    public void drawBackground(Graphics2D g2) {

        // size, location, color settings
        x = gp.displayedTile*2;
        y= gp.displayedTile*2;
        int width = gp.width - (gp.displayedTile*4);
        int height = gp.displayedTile*8;
        Color c = new Color(0,0,0,255);
        g2.setColor(c);

        // draw rectangle
        g2.fillRoundRect(x,y,width,height,35,35);

        
    }

    /* gets image based on image path
     * pre: none
     * post: buffered image is returned
     */
    public BufferedImage getImage(String imagePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".jpg"));
            return image;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /* changes game state if all 4 monsters are defeated
     * pre: none
     * post: game state changed to either outro or playing
     */
    public void changeGameState() {
        if (gp.completed.size() == 4) { // all monsters defeated
            gp.gameState = gp.outro; // game state changed to outro
        } else {
            gp.gameState = gp.playing; // game state changed to playing
        }
    }

    
}
