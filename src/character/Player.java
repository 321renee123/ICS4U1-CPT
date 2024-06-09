package character;
import main.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Character{
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 480;
        y = 352;
        speed = 4;
    }

    public void getPlayerImage() {
        sprite = getImage("/res/player/player");
    }

    public void update() {
        if (keyH.upPressed == true) {
            y -= speed;
        } 
        else if (keyH.downPressed == true) {
            y += speed;
        } 
        else if (keyH.leftPressed == true) {
            x -= speed;
        } 
        else if (keyH.rightPressed == true) {
            x += speed;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = sprite;
        g2.drawImage(image, x, y, gp.displayedTile, gp.displayedTile, null); 
    }

    

    


}
