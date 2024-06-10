package character;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

// addition, subtraction, multiplication, division game character
// 

public class OpponentThree extends Character {

    public boolean completedRun;


    public OpponentThree(GamePanel gp) {
        super(gp);

        setDefaultValues();
        getOpponentImage();     

        completedRun = false;
    }

    public void setDefaultValues() {
        x = 860;
        y = 100;
        speed = 4;
    }

    public void getOpponentImage() {
        sprite = getImage("/res/opponents/opponentThree");
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = sprite;
        g2.drawImage(image, x, y, gp.displayedTile, gp.displayedTile, null);
    }

    public void runMinigame() {

    }

}