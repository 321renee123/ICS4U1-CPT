package character;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

/**
 * OpponentThree subclass
 * Pollynomial's class
 */
public class OpponentThree extends Character {


    /* constructor
     * pre: none
     * post: opponent three set up complete, sets location of opponent, opponnet image, and game panel object
     */
    public OpponentThree(GamePanel gp) {
        super(gp);

        setDefaultValues();
        getOpponentImage();     

    }

    /* location of opponent four is set
     * pre: none
     * post: opponent three x and y set
     */
    public void setDefaultValues() {
        x = 860;
        y = 100;
    }

    /* opponent image set
     * pre: none
     * post: opponent three image gotten
     */
    public void getOpponentImage() {
        sprite = getImage("/res/characters/opponentThree");
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