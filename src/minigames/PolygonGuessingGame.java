package minigames;

import java.util.*;
import java.awt.*;
import main.*;


/**
 * PolygonGuessingGame subclass
 */
public class PolygonGuessingGame extends Minigame{

    // GAME ASSETS
    private String[] polygons = {"triangle","quadrilateral","pentagon","hexagon","heptagon","octagon","nonagon","decagon"};
    private ArrayList<String> usedPolygons = new ArrayList<String>();
    private ArrayList<String> usedQs = new ArrayList<String>();
    private int sides;
    private int[] xPoints, yPoints;
    private String correctAns;
    private String opt1, opt2, opt3;
    private int opty;
    private String userAns;
    private int numCorrect;
    private String result;

    private KeyHandler keyH;
    private Random rand = new Random();

    /* constructor
     * pre: none
     * post: sets up minigame and acquires first question
     */
    public PolygonGuessingGame(GamePanel gp, KeyHandler keyH) {
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
        if (gp.gameState == gp.minigame && gp.determineCollision() == 4) { // if it is minigame time and you are colliding with opponent four
            showQuestion(g2);
        }
    }

    /* generates random polygon to ask about
     * pre: none
     * post: generates random polygon
     */
    private void getRandomPolygon() {
        do {
            sides = rand.nextInt(8) + 3; // generates amount of sides

            xPoints = new int[sides];  // X coordinates of the polygon vertices
            yPoints = new int[sides];  // Y coordinates of the polygon vertices
            int radius = 50;  // Radius of the polygon
            int centerX = gp.width / 2;  // Center X coordinate of the panel
            int centerY = gp.height / 2;  // Center Y coordinate of the panel
    
            // Calculate the vertices of the polygon
            for (int i = 0; i < sides; i++) {
                double angle = 2 * Math.PI / sides * i;  // Angle for the current vertex
                xPoints[i] = (int) (centerX + radius * Math.cos(angle));
                yPoints[i] = (int) (centerY + radius * Math.sin(angle));
            }
            correctAns = polygons[sides-3]; // sets this polygon as the correct answer
        } while (checkForRepeats(usedQs,correctAns) == true); // ensures that a question is not used twice if answered correctly
        usedPolygons.add(correctAns); // add this polygon as one of the multiple choice options
    }

    /* Generate other multiple choice options
     * pre: none
     * post: options generated and added to array
     */
    private void generateOptions() {
        String option;
        for (int i = 0 ; i < 2; i++) { // generate two other options
            do {
                option = polygons[rand.nextInt(7)];
            } while(super.checkForRepeats(usedPolygons,option) == true); // ensures options do not repeat
            usedPolygons.add(option); // add to the array of options
        }
        Collections.shuffle(usedPolygons); // mixes up array
    }

    /* independent variables set for multiple choice options
     * pre: none
     * post: variables set
     */
    private void createMultipleChoice() {
        // set variables opt1, opt2, and opt3 based on now randomized ArrayList of mc options
        opt1 = usedPolygons.get(0); 
        opt2 = usedPolygons.get(1);
        opt3 = usedPolygons.get(2);
    }

    /* Runs all three setup question methods
     * pre: none
     * post: All three methods are run
     */
    private void getQuestion() {
        getRandomPolygon();
        generateOptions();
        createMultipleChoice();
    }

    /* Displays question and allows user to answer
     * pre: none
     * post: Question is displayed, user can answer and new question will be generated
     */
    private void showQuestion(Graphics2D g2) {
        super.drawBackground(g2); // background is drawn
        
        // question is drawn
        g2.setColor(Color.white);
        g2.setFont(new Font("Courier",Font.PLAIN,30));
        g2.drawString("Type the number associated with", super.x+ gp.displayedTile,super.y+gp.displayedTile);
        g2.drawString("your answer", super.x+ gp.displayedTile,super.y+gp.displayedTile + 30);
        g2.drawString("Name the polygon:", super.x+gp.displayedTile, super.y+gp.displayedTile+70);
        g2.setColor(new Color(255,255,255,255));
        g2.fillPolygon(xPoints, yPoints, sides);

        // options are displayed
        g2.setFont(new Font("Courier",Font.PLAIN,20));
        g2.drawString("1 - " + opt1, opt1x, opty);
        g2.drawString("2 - " + opt2, opt2x, opty);
        g2.drawString("3 - " + opt3, opt3x, opty);
        
        getResult(); // result is acquired

        if (userAns!=null && userAns.length()!=0) { // if user has answered
            // result is displayed
            g2.setColor(Color.white);
            g2.setFont(new Font("Courier", Font.PLAIN, 30));
            g2.drawString(result, gp.displayedTile*3 - 10, gp.displayedTile*9);
            g2.drawString("Press [ENTER] to proceed", gp.displayedTile*3 - 10, gp.displayedTile*9 + 30);

            if (keyH.nextPressed == true && numCorrect  == 6 && result.equals("Correct!")) { // if user presses to go next and they have answered seven questions correctly
                keyH.nextPressed = false; 
                keyH.userAns = 0;
                super.changeGameState(); // change game state
            } else if (keyH.nextPressed == true){ // if user presses to go next but they have not answered 7 questions correctly
                // reset
                keyH.nextPressed = false;
                userAns = null;
                keyH.userAns = 0;
                usedPolygons.clear();

                if (result.equals("Correct!")) { // if user answered correctly
                    numCorrect += 1;
                    usedQs.add(correctAns); // ensure question does not repeat
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
            default: // user did not answer
                userAns = null;
                break;
        }
        if (userAns!=null && userAns.equals(correctAns)) { // if user answered correctly
            result = "Correct!";
        } else { // if user answered incorrectly
            result = "Incorrect.";
        }
    }
}