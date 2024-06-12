package character;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.GamePanel;



/**
 * OpponentFour subclass
 * MegaGon the dinosaur's class
 */

public class OpponentFour extends Character {


    /* constructor
     * pre: none
     * post: opponent four set up complete, sets location of opponent, opponnet image, and game panel object
     */
    public OpponentFour(GamePanel gp) {
        super(gp);

        setDefaultValues();
        getOpponentImage();   
        
    }

    /* location of opponent four is set
     * pre: none
     * post: opponent four x and y set
     */
    public void setDefaultValues() {
        x = 100;
        y= 604;
    }

    /* opponent image set
     * pre: none
     * post: opponent four image gotten
     */
    public void getOpponentImage() {
        sprite = getImage("/res/characters/opponentFour");
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
