package character;
import main.*;
import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * Player subclass
 */
public class Player extends Character{

    KeyHandler keyH;
    private int speed;

    /* setup player with game panel object, key handler object, location + speed, and image
     * pre: none
     * post: player set up completed
     */
    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    /* player default location and speed is set
     * pre: none
     * post: player default x and y and speed set
     */
    public void setDefaultValues() {
        x = 480;
        y = 352;
        speed = 4;
    }

    /* player image set
     * pre: none
     * post: player image gotten
     */
    public void getPlayerImage() {
        sprite = getImage("/res/characters/player");
    }

    /* player position changed when keys are pressed
     * pre: none
     * post: image position changes by speed when controls are pressed
     */
    public void update() {
        if (keyH.upPressed == true) { // W pressed
            y -= speed; // go up
        } 
        else if (keyH.downPressed == true) { // s pressed
            y += speed; // go down
        } 
        else if (keyH.leftPressed == true) { // D pressed
            x -= speed; // go right
        } 
        else if (keyH.rightPressed == true) { // A pressed 
            x += speed; // go left
        }
    }
    
    /* draws player
     * pre: none
     * post: player image is drawn
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = sprite;
        g2.drawImage(image, x, y, gp.displayedTile, gp.displayedTile, null); 
    }
    


    

    


}
