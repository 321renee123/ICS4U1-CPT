package main;
import java.awt.event.*;

public class KeyHandler implements KeyListener{
    
    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean nextPressed;
    

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.gameState == gp.playing) {
            if(code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if(code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if(code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if(code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if(code == KeyEvent.VK_F || code == KeyEvent.VK_B) {
                if (gp.determineCollision() > 0 && gp.gameState == gp.playing) {
                    gp.gameState = gp.dialogue;
                    System.out.println("Swtich occured");
                }
            }
        } 
        if (gp.gameState == gp.dialogue) {
            if (code == KeyEvent.VK_ENTER) {
                nextPressed = true;
            }

        }

    }

    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (gp.gameState == gp.playing) {
            if(code == KeyEvent.VK_W) {
                upPressed = false;
            }
            if(code == KeyEvent.VK_A) {
                leftPressed = false;
            }
            if(code == KeyEvent.VK_S) {
                downPressed = false;
            }
            if(code == KeyEvent.VK_D) {
                rightPressed = false;
            }
        }
        if (gp.gameState == gp.dialogue) {
            if (code == KeyEvent.VK_ENTER) {
                nextPressed = false;
            }

        }
    }
}
