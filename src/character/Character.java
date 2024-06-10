package character;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Character {
    GamePanel gp;
    public int x,y;
    public int speed;
    
    public BufferedImage sprite;

    public Character(GamePanel gp) {
        this.gp = gp;
    }

    public BufferedImage getImage(String imagePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            return image;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }


}
