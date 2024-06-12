package character;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import main.GamePanel;


/**
 * Character abstract superclass
 */
public abstract class Character {
    GamePanel gp;

    // character location
    public int x,y;
    
    public BufferedImage sprite;

    /* constructor
     * pre: none
     * post: character set up with game panel object
     */
    public Character(GamePanel gp) {
        this.gp = gp;
    }
    
    // abstract method for drawing character 
    public abstract void draw(Graphics2D g2);

    /* Get image from image path
     * pre: none
     * post: BufferedImage for the character image returned
     */
    public BufferedImage getImage(String imagePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png")); // set image from imagePath
            return image;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }


}
