package minigames;

import main.*;
import java.awt.*;
import java.util.*;
import java.awt.image.BufferedImage;

/**
 * FlagGuessingGame subclass
 */
public class FlagGuessingGame extends Minigame {

    // GAME
    private String[] flags = {"america","canada","india","france","ireland","italy","japan", "south korea", "uk"};
    private ArrayList<String> usedFlags = new ArrayList<String>();
    private ArrayList<String> usedQs = new ArrayList<String>();
    private String correctAns;
    private String opt1, opt2, opt3;
    private int opty;
    private String userAns;
    private int numCorrect;
    private String result;
    private BufferedImage flag;

    private KeyHandler keyH;
    private Random rand = new Random();

    /* constructor
     * pre: none
     * post: sets up minigame and acquires first question
     */
    public FlagGuessingGame(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        getQuestion();
        opty = gp.displayedTile*8;
    }

    /* Draws minigame
     * pre: none
     * post: draw minigame
     */
    public void draw(Graphics2D g2) {
        if (gp.gameState == gp.minigame && gp.determineCollision() == 2) { // if it is minigame time and you are colliding with opponent two
            showQuestion(g2);
        }
    }


    /* generates random flag to ask about
     * pre: none
     * post: generates random flag
     */
    private void getRandomFlag() {
        do {
            correctAns = flags[rand.nextInt(8)];
        } while (checkForRepeats(usedQs, correctAns) == true); // ensures no question answered correctly is used twice
        flag = getImage("/res/minigame_assets/" + correctAns); // set image of flag
        usedFlags.add(correctAns); // add to mc options ArrayList
    }
    
    /* Generate other multiple choice options
     * pre: none
     * post: options generated and added to array
     */
    private void generateOptions() {
        String option;
        for (int i = 0 ; i < 2; i++) { // generate two other ooptions
            do {
                option = flags[rand.nextInt(8)];
            } while(super.checkForRepeats(usedFlags,option) == true); // ensures options do not repeat
            usedFlags.add(option); // add to array of options
        }
        Collections.shuffle(usedFlags); // mixes up array
    }


    /* independent variables set for multiple choice options
     * pre: none
     * post: variables set
     */
    private void getMultipleChoice() {
        // set variables opt1, opt2, and opt3 based on now randomized ArrayList of mc options
        opt1 = usedFlags.get(0);
        opt2 = usedFlags.get(1);
        opt3 = usedFlags.get(2); 
    }

    /* Runs all three setup question methods
     * pre: none
     * post: All three methods are run
     */
    private void getQuestion() {
        getRandomFlag();
        generateOptions();
        getMultipleChoice();
    }

    /* Displays question and allows user to answer
     * pre: none
     * post: Question is displayed, user can answer and new question will be generated
     */
    private void showQuestion(Graphics2D g2) {
        super.drawBackground(g2); // background is drawn

        // question is drawn
        g2.setColor(Color.white);
        g2.setFont(new Font("Courier", Font.PLAIN, 30));
        g2.drawString("Type the number associated with", super.x+ gp.displayedTile,super.y+gp.displayedTile);
        g2.drawString("your answer", super.x+ gp.displayedTile,super.y+gp.displayedTile + 30);
        g2.drawString("Name the country:",super.x + gp.displayedTile, super.y+gp.displayedTile + 70);
        g2.drawImage(flag, 420,280,gp.displayedTile*3,gp.displayedTile*3, null);

        // options are displayed
        g2.setFont(new Font("Courier", Font.PLAIN, 20));
        g2.setColor(Color.white);
        g2.drawString("1 - " + opt1.toUpperCase(), opt1x,opty);
        g2.drawString("2 - " + opt2.toUpperCase(), opt2x, opty);
        g2.drawString("3 - " + opt3.toUpperCase(), opt3x, opty);

        getResult(); // result is acquired

        if (userAns!= null && userAns.length()!=0) { // if user has answered
            // result is displayed
            g2.setColor(Color.white);
            g2.setFont(new Font("Courier", Font.PLAIN, 30));
            g2.drawString(result, gp.displayedTile*3-10,gp.displayedTile*9);
            g2.drawString("Press [ENTER] to proceed",gp.displayedTile*3-10,gp.displayedTile*9+30);

            
            if (keyH.nextPressed == true && numCorrect == 6 && result.equals("Correct!")) { // if user pressed to go next and they have answered 7 questions correctly
                keyH.nextPressed = false; 
                keyH.userAns = 0;
                super.changeGameState(); // change game state
            } else if (keyH.nextPressed == true){ // if user presses to go next but they have not answered 7 questions correctly
                // reset
                keyH.nextPressed = false;
                userAns = null;
                keyH.userAns = 0;
                usedFlags.clear();

                if (result.equals("Correct!")) { // if user answered correctly
                    numCorrect += 1;
                    usedQs.add(correctAns); // ensures correctly answered question is not repeated
                }

                getQuestion(); // generate new question
            } 
        }
    }

    /* Sets result string as either correct or incorrect depending on user inpuit
     * pre: none
     * post: result string set
     */
    private void getResult() {
        switch (keyH.userAns) { // gets user's answer and sets as corresponding mc option
            case 1:
                userAns = opt1;
                break;
            case 2:
                userAns = opt2;
                break;
            case 3:
                userAns = opt3;
                break;
            default: // user has not answered
                userAns = null;
                break;
        }
        if (userAns!=null && userAns.equals(correctAns)) { // user answered correctly
            result = "Correct!";
        } else { // user did not answer correctly
            result = "Incorrect.";
        }
    }
}
