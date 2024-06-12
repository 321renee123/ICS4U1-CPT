package minigames;

import main.*;
import java.awt.*;
import java.util.*;

/**
 * ArithmeticGame subclass
 */
public class ArithmeticGame extends Minigame{

    // GAME ASSETS
    private ArrayList<Integer> usedNums = new ArrayList<Integer>();
    private int correctAns;
    private int operation;
    private int num1,num2;
    private String equation;
    private int opt1, opt2, opt3;
    private int opty;
    private int userAns = -1;
    private int numCorrect;
    private String result;

    KeyHandler keyH;
    Random rand = new Random();

    /* constructor
     * pre: none
     * post: sets up minigame and acquires first question
     */
    public ArithmeticGame(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        opty = gp.displayedTile*8;

        getQuestion();
    }

    /* Draws minigame
     * pre: none
     * post: draw minigame
     */
    public void draw(Graphics2D g2) {
        if (gp.gameState == gp.minigame && gp.determineCollision() == 3) {// if it is minigame time and you are colliding with opponent three
            showQuestion(g2);
        }
    }

    /* generates random equation to ask about
     * pre: none
     * post: generates random equation
     */
    private void getEquation() {
        operation = rand.nextInt(3);
        switch(operation) {
            case 0: // addition
                num1 = rand.nextInt(10);
                num2 = rand.nextInt(10);
                correctAns = num1 + num2;
                equation = num1 + " + " + num2 + " = ?";
                break;

            case 1: // subtraction
                num1 = rand.nextInt(10);
                num2 = num1 + rand.nextInt(10);
                correctAns = num2 - num1;
                equation = num2 + " - " + num1 + " = ?";
                break;

            case 2: // multiplication
                num1 = rand.nextInt(10);
                num2 = rand.nextInt(10);
                correctAns = num1*num2;
                equation = num1 + " x " + num2 + " = ?";
                break;

            case 3: // division
                num1 = rand.nextInt(10);
                num2 = num1*rand.nextInt(10);
                correctAns = num2/num1;
                equation = num2 + " / " + num1 + " = ?";
                break;
        }
        usedNums.add(correctAns); // add correct answer to the mc options
    }

    /* Generate other multiple choice options
     * pre: none
     * post: options generated and added to array
     */
    private void generateOptions() {
        int option;
        for (int i = 0; i<2; i++) {
            do {
                option = rand.nextInt(30);
            } while (super.checkForRepeats(usedNums,option)); // ensures no repeats in possible answers
            usedNums.add(option); // add to multiple choice options 
        }
        Collections.shuffle(usedNums); // mixes up array
    }

    /* independent variables set for multiple choice options
     * pre: none
     * post: variables set
     */
    private void getMultipleChoice() {
        // set variables opt1, opt2, and opt3 based on now randomized ArrayList of mc options
        opt1 = usedNums.get(0);
        opt2 = usedNums.get(1);
        opt3 = usedNums.get(2);
    }

    /* Runs all three setup question methods
     * pre: none
     * post: All three methods are run
     */
    private void getQuestion() {
        getEquation();
        generateOptions();
        getMultipleChoice();
    }

    /* Displays question and allows user to answer
     * pre: none
     * post: Question is displayed, user can answer and new question will be generated
     */
    private void showQuestion(Graphics2D g2) {
        super.drawBackground(g2); // draw background

        // draw question
        g2.setColor(Color.white);
        g2.setFont(new Font("Courier", Font.PLAIN, 30));
        g2.drawString("Type the number associated with", super.x+ gp.displayedTile,super.y+gp.displayedTile);
        g2.drawString("your answer", super.x+ gp.displayedTile,super.y+gp.displayedTile + 30);
        g2.drawString(equation, super.x + gp.displayedTile,super.y + (gp.displayedTile*3));

        // draw options
        g2.setFont(new Font("Courier", Font.PLAIN, 30));
        g2.setColor(Color.white);
        g2.drawString("1) " + opt1, opt1x,opty);
        g2.drawString("2) " + opt2, opt2x, opty);
        g2.drawString("3) " + opt3, opt3x, opty);

        getResult(); // acquire result string

        if (userAns != -1) { // if user has answered
            // display result
            g2.setColor(Color.white);
            g2.setFont(new Font("Courier", Font.PLAIN, 30));
            g2.drawString(result, gp.displayedTile*3-10, gp.displayedTile*9);
            g2.drawString("Press [ENTER] to proceed",gp.displayedTile*3-10,gp.displayedTile*9+30);


            if (keyH.nextPressed == true && numCorrect == 6 && result.equals("Correct!")) { // if user pressed to go next and they have answered 7 questions correctly
                keyH.nextPressed = false;
                keyH.userAns = 0;
                super.changeGameState(); // change game state

            } else if (keyH.nextPressed == true){ // if user pressed to go next but has not answered 7 questions correctly
                // reset
                keyH.nextPressed = false;
                userAns = -1;
                keyH.userAns = 0;
                usedNums.clear();

                if (result.equals("Correct!")) { // if user answered correctly
                    numCorrect += 1;
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
        switch(keyH.userAns) { // gets user's answer and sets as corresponding mc option
            case 1: 
                userAns = opt1;
                break;
            case 2:
                userAns = opt2;
                break;
            case 3:
                userAns = opt3;
                break;
            default: //user has not answered
                userAns = -1;
                break;
        }
        if (userAns != -1 && userAns == correctAns) { // user answered correctly
            result = "Correct!";
        } else { // user did not answer correctly
            result = "Incorrect. Correct answer: " + correctAns;
        }
    }

    
}
