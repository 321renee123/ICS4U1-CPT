package character;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

// Unscramble words game opponent
// Stinky the skunk

public class OpponentTwo extends Character {


    public boolean completedRun;

    public OpponentTwo(GamePanel gp) {
        super(gp);

        setDefaultValues();
        getOpponentImage();    
        
        completedRun = false; 
    }

    public void setDefaultValues() {
        x = 860;
        y= 604;
        speed = 4;
    }

    public void getOpponentImage() {
        sprite = getImage("/res/opponents/opponentTwo");
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = sprite;
        g2.drawImage(image, x, y, gp.displayedTile, gp.displayedTile, null);
    }

}