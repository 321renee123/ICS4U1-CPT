package main;
import java.awt.*;
import character.voicelines.*;


public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font courier_1;

    public UI(GamePanel gp) {
        this.gp = gp;
        courier_1 = new Font("Courier", Font.PLAIN, 30);    
    } 


    public void update() {

    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(courier_1);
        g2.setColor(Color.red);

        if (gp.gameState == gp.playing) {
            displayFightText();
        } else if (gp.gameState == gp.dialogue) {


        }
    }

    public void displayFightText() {
        if (gp.determineCollision() > 0) {
            g2.drawString("Press [F] or to fight or [B] to befriend",140,700);
        }
    }

    public void displayDialogue() {
        int x = gp.displayedTile*2;
        int y = gp.displayedTile/2;
        int width = gp.width - (gp.displayedTile*4);
        int height = gp.displayedTile*3;

        
        Color c = new Color(0,0,0);
        g2.setColor(c);
        g2.drawRoundRect(x,y,width,height,35,35);
        
    }



    
}
