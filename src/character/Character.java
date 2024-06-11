package character;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;

import main.GamePanel;

public abstract class Character {
    GamePanel gp;
    public int x,y;
    public int speed;
    
    public BufferedImage sprite;

    public Character(GamePanel gp) {
        this.gp = gp;
    }
    
    public abstract void draw(Graphics2D g2);

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
