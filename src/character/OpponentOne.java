package character;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

/**
 * OpponentOne subclass
 * MeowMeow the cat's class
 */
public class OpponentOne extends Character {

    /* constructor
     * pre: none
     * post: opponent one set up complete, sets location of opponent, opponnet image, and game panel object
     */
    public OpponentOne(GamePanel gp) {
        super(gp);

        setDefaultValues();
        getOpponentImage();     
        
    }

    /* location of opponent four is set
     * pre: none
     * post: opponent one x and y set
     */
    public void setDefaultValues() {
        x = 100;
        y= 100;
    }


    /* opponent image set
     * pre: none
     * post: opponent one image gotten
     */
    public void getOpponentImage() {
        sprite = getImage("/res/characters/opponentOne");
    }

    /* draws opponent
     * pre: none
     * post: opponent image is drawn
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = sprite;
        g2.drawImage(image, x, y, gp.displayedTile, gp.displayedTile, null);
    }

}
