package main;
import java.awt.event.*;

public class KeyHandler implements KeyListener{
    
    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean nextPressed;
    public int userAns;
    public boolean answered = false;

    

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();


        if (gp.gameState == gp.intro || gp.gameState == gp.outro) {
            if (code == KeyEvent.VK_ENTER) {
                nextPressed = true;
            }
        }
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
            if(code == KeyEvent.VK_F) {
                if (gp.determineCollision() > 0 && gp.hasFought() == false) {
                    gp.gameState = gp.dialogue;
                    gp.completed.add(gp.determineCollision());
                    
                }
            }
        } 
        if (gp.gameState == gp.dialogue) {
            if (code == KeyEvent.VK_ENTER) {
                nextPressed = true;
            }
        }
        if (gp.gameState == gp.minigame) {
            if (code == KeyEvent.VK_1) {
                userAns = 1;
                answered = true;
            }
            if (code == KeyEvent.VK_2) {
                userAns = 2;
                answered = true;
            }
            if (code == KeyEvent.VK_3) {
                userAns = 3;
                answered = true;
            }
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

        if (gp.gameState == gp.minigame) {
            if (code == KeyEvent.VK_ENTER) {
                nextPressed = false;
            }
        }
    }
}
