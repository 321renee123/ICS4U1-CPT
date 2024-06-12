package main;
import java.awt.event.*;

/**
 * KeyHandler class
 */
public class KeyHandler implements KeyListener{
    
    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed, nextPressed; // for player controls
    public int userAns; // for user answer to minigame questions

    /* constructor
     * pre: none
     * post: KeyHandler setup with gamepanel object
     */
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }


    /* Unused abstract method inherited from superclass
     * pre: none
     * post: none 
     */
    public void keyTyped(KeyEvent e) {
        // empty
    }


    /* abstract method keyPressed inherited from superclass, used to determined which keys are pressed
     * pre: none
     * post: public variables adjusted based on key that is pressed, diff keys function during diff game states
     */
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // during intro sequence, outro, or dialogue state
        if (gp.gameState == gp.intro || gp.gameState == gp.outro || gp.gameState == gp.dialogue) {
            if (code == KeyEvent.VK_ENTER) { // if enter is pressed
                nextPressed = true;
            }
        }


        // during player moving time (WASD controls)
        if (gp.gameState == gp.playing) {
            if(code == KeyEvent.VK_W) { // W pressed
                upPressed = true;
            }
            if(code == KeyEvent.VK_A) { // A pressed
                leftPressed = true;
            }
            if(code == KeyEvent.VK_S) { // S pressed
                downPressed = true;
            }
            if(code == KeyEvent.VK_D) { // D pressed
                rightPressed = true;
            }
            if(code == KeyEvent.VK_F) { // F pressed
                if (gp.determineCollision() > 0 && gp.hasFought() == false) { // user is interacting with an opponent they have not yet fought
                    gp.gameState = gp.dialogue; // game state changed to dialogue state, i.e. user is about to fight the opponent
                    gp.completed.add(gp.determineCollision()); // adds opponent to list of defeated opponents
                    
                }
            }
        } 


        // during minigames
        if (gp.gameState == gp.minigame) {
            if (code == KeyEvent.VK_1) { // 1 pressed
                userAns = 1;
            }
            if (code == KeyEvent.VK_2) { // 2 pressed
                userAns = 2;
            }
            if (code == KeyEvent.VK_3) { // 3 pressed
                userAns = 3;
            }
            if (code == KeyEvent.VK_ENTER) { // Enter pressed
                nextPressed = true;
            }
        }

    }

    /* Abstract method inherited from superclass that determines when a key is released
     * pre: none
     * post: none 
     */
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        // during player moving time (stops movement from WASD controls)
        if (gp.gameState == gp.playing) {
            if(code == KeyEvent.VK_W) { // W released
                upPressed = false;
            }
            if(code == KeyEvent.VK_A) { // A released
                leftPressed = false;
            }
            if(code == KeyEvent.VK_S) { // S released
                downPressed = false;
            }
            if(code == KeyEvent.VK_D) { // D released
                rightPressed = false;
            }
        }
    }
}
