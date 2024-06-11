package character;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


import main.GamePanel;

// polygon side counting game character
// MegaGon the dinosaur

public class OpponentFour extends Character {

    // PolygonGuessing polygonGuessing = new PolygonGuessing();
    public boolean completedRun;
    private int score;


    public OpponentFour(GamePanel gp) {
        super(gp);

        setDefaultValues();
        getOpponentImage();   
        
        completedRun = false;
        score = 0;
    }

    public void setDefaultValues() {
        x = 100;
        y= 604;
        speed = 4;
    }

    public void getOpponentImage() {
        sprite = getImage("/res/opponents/opponentFour");
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = sprite;
        g2.drawImage(image, x, y, gp.displayedTile, gp.displayedTile, null);
    }

    public static void runMinigame() {
    }
}
