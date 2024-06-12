package minigames;

import main.*;
import java.awt.*;
import java.util.*;

/**
 * SynonymFindingGame subclass
 */
public class SynonymFindingGame extends Minigame{

    // GAME ASSETS
    private String[] words = {"assist","brave","calm","delicious","eager","famous","gigantic","happy","idea","kind","lazy","mend","neat","old","polite","quick","rich","tiny","enraged","dangerous"};
    private String[] synonyms = {"help","courageous","tranquil","scrumptious","enthusiastic","renowned","huge","joyous","concept","benevolent","idle","repair","tidy","ancient","courteous","rapid","wealthy","miniscule","furious","perilous"};
    private ArrayList<String> usedWords = new ArrayList<String>();
    private ArrayList<String> usedQs = new ArrayList<String>();
    private int index;
    private String word;
    private String correctAns;
    private String opt1, opt2, opt3;
    private String userAns;
    private int numCorrect;
    private String result;
    private int opty;

    KeyHandler keyH;
    Random rand = new Random();

    /* constructor
     * pre: none
     * post: sets up minigame and acquires first question
     */
    public SynonymFindingGame(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        getQuestion();
        opty = gp.displayedTile*7;
    }

    /* Draws minigame
     * pre: none
     * post: draw minigame
     */
    public void draw(Graphics2D g2) {
        if (gp.gameState == gp.minigame && gp.determineCollision() == 1) {
            showQuestion(g2);
        }
    }

    /* generates random word to ask about
     * pre: none
     * post: generates random word from ArrayList
     */
    private void getWord() {
        do {
            index = rand.nextInt(19);
            word = words[index];
        } while (checkForRepeats(usedQs,word) == true); // ensures word is not reused if the word has already been part of a correctly answered q
        correctAns = synonyms[index]; // get synonym for that word and set as correct answer

        usedWords.add(correctAns); // add correct answer to mc options
    }

    /* Generate other multiple choice options
     * pre: none
     * post: options generated and added to array
     */
    private void generateOptions() {
        String option;
        for (int i = 0 ; i < 2; i++) { // generate two other options
            do {
                option =synonyms[rand.nextInt(7)];
            } while(super.checkForRepeats(usedWords,option) == true); // ensures options do not repeat
            usedWords.add(option); // add to array of options
        }
        Collections.shuffle(usedWords); // mixes up array
    }

    /* independent variables set for multiple choice options
     * pre: none
     * post: variables set
     */
    private void getMultipleChoice() {
        // set variables opt1, opt2, and opt3 based on now randomized ArrayList of mc options
        opt1 = usedWords.get(0);
        opt2 = usedWords.get(1);
        opt3 = usedWords.get(2);
    }

    /* Runs all three setup question methods
     * pre: none
     * post: All three methods are run
     */
    private void getQuestion() {
        getWord();
        generateOptions();
        getMultipleChoice();
    }

    private void showQuestion(Graphics2D g2) {
        super.drawBackground(g2); // draw background

        // draw question
        g2.setColor(Color.white);
        g2.setFont(new Font("Courier", Font.PLAIN, 30));
        g2.drawString("Type the number associated with", super.x+ gp.displayedTile,super.y+gp.displayedTile);
        g2.drawString("your answer", super.x+ gp.displayedTile,super.y+gp.displayedTile + 30);
        g2.drawString("Find the synonym for:",super.x + gp.displayedTile, super.y+gp.displayedTile + 70);
        g2.drawString(word.toUpperCase(),super.x + gp.displayedTile, super.y+gp.displayedTile + 120);
        
        // draw options
        g2.setFont(new Font("Courier",Font.PLAIN,20));
        g2.drawString("1 - " + opt1.toUpperCase(), opt1x, opty);
        g2.drawString("2 - " + opt2.toUpperCase(), opt2x, opty);
        g2.drawString("3 - " + opt3.toUpperCase(), opt3x, opty);

        getResult(); // result string acquired

        if (userAns!=null && userAns.length()!=0) { // if user has answered
            // draw result
            g2.setColor(Color.white);
            g2.setFont(new Font("Courier", Font.PLAIN, 30));
            g2.drawString(result, gp.displayedTile*3-10,gp.displayedTile*8);
            g2.drawString("Press [ENTER] to proceed",gp.displayedTile*3-10,gp.displayedTile*8+30);

            if (keyH.nextPressed == true && numCorrect == 6 && result.equals("Correct!")) { // if user pressed to go next and they have answered 7 questions correctly
                keyH.nextPressed = false;
                keyH.userAns = 0;
                super.changeGameState(); // change game state
            } else if (keyH.nextPressed == true) { // if user pressed to go next but has not answered 7 qs correct;y
                // reset
                keyH.nextPressed = false;
                keyH.userAns = 0;
                userAns = null;
                usedWords.clear();

                if (result.equals("Correct!")) { // if user answered correctly
                    numCorrect += 1;
                    usedQs.add(word); // ensures correctly answered q is not repeated
                }

                getQuestion(); //generate new question
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
        if (userAns!=null && userAns.equals(correctAns)) { // if user answered correctly
            result = "Correct!";
        } else { // if user answered in correctly
            result = "Incorrect.";
        }
    }

}





