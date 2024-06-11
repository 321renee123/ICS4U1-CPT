package minigames;

import java.util.ArrayList;
import java.awt.*;
import main.*;

public abstract class Minigame {

    public abstract void update();
    public abstract void draw(Graphics2D g2);
    GamePanel gp;
    public int x,y;

    public Minigame(GamePanel gp) {
        this.gp = gp;
    }
    

    public boolean checkForRepeats(ArrayList<String> list, String line) {
        boolean repeated = false;
        for (String element: list) {
            if (line == element) {
                repeated = true;
            }
        }    
        return repeated;
    }

    public void drawBackground(Graphics2D g2) {
        x = gp.displayedTile*2;
        y= gp.displayedTile*2;
        int width = gp.width - (gp.displayedTile*4);
        int height = gp.displayedTile*8;
        Color c = new Color(0,0,0,255);

        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        
    }
    
}
