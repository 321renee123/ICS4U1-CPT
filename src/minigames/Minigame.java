package minigames;

import java.util.ArrayList;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import main.*;

public abstract class Minigame {

    GamePanel gp;
    public int x,y;

    public abstract void draw(Graphics2D g2);



    public Minigame(GamePanel gp) {
        this.gp = gp;
    }


    

    public boolean checkForRepeats(ArrayList<String> list, String line) {
        boolean repeated = false;
        for (String element: list) {
            if (line == element) {
                repeated = true;
            }
        }    
        return repeated;
    }

    public boolean checkForRepeats(ArrayList<Integer> list, int line) {
        boolean repeated = false;
        for (int element: list) {
            if (line == element) {
                repeated = true;
            }
        }    
        return repeated;
    }

    public void drawBackground(Graphics2D g2) {
        x = gp.displayedTile*2;
        y= gp.displayedTile*2;
        int width = gp.width - (gp.displayedTile*4);
        int height = gp.displayedTile*8;
        Color c = new Color(0,0,0,255);

        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        
    }

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
    
}
