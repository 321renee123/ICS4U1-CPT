package character;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

// Higher/lower number game opponent
// MeowMeow the cat

public class OpponentOne extends Character {

    public boolean completedRun;
    public int livesLeft;
    

    public OpponentOne(GamePanel gp) {
        super(gp);

        setDefaultValues();
        getOpponentImage();     

        completedRun = false;
        
    }

    public void setDefaultValues() {
        x = 100;
        y= 100;
        speed = 4;
    }

    public void getOpponentImage() {
        sprite = getImage("/res/opponents/opponentOne");
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = sprite;
        g2.drawImage(image, x, y, gp.displayedTile, gp.displayedTile, null);
    }

    public void runMinigame() {
        
    }
}
