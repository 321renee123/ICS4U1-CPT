package minigames;

import java.util.*;
import java.awt.*;
import main.*;

public class PolygonGuessingGame extends Minigame{

    private String[] polygons = {"triangle","quadrilateral","pentagon","hexagon","heptagon","octogon","nonagon","decagon"};
    private ArrayList<String> usedPolygons = new ArrayList<String>();
    private int sides;

    private String correctAns;
    private int[] xPoints, yPoints;
    private String opt1, opt2, opt3;
    private int opt1x,opt2x,opt3x,opty;
    private String userAns;
    private int numCorrect;
    private String result;

    private KeyHandler keyH;
    private Random rand = new Random();

    public PolygonGuessingGame(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        getQuestion();
        opt1x = gp.displayedTile*3 ;
        opt2x = gp.displayedTile*7 ;
        opt3x = gp.displayedTile*11 -5;
        opty = gp.displayedTile*8;
    }


    public void draw(Graphics2D g2) {
        if (gp.gameState == gp.minigame && gp.determineCollision() == 4) {
            showQuestion(g2);

        }
    }

    private void getRandomPolygon() {
        sides = rand.nextInt(8) + 3;

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
        correctAns = polygons[sides-3];
        usedPolygons.add(correctAns);
    }

    private void generateOptions() {
        String option;
        for (int i = 0 ; i < 2; i++) {
            do {
                option = polygons[rand.nextInt(7)];
            } while(super.checkForRepeats(usedPolygons,option) == true);
            usedPolygons.add(option);
        }
        Collections.shuffle(usedPolygons);
    }

    private void createMultipleChoice() {
        opt1 = usedPolygons.get(0);
        opt2 = usedPolygons.get(1);
        opt3 = usedPolygons.get(2);
    }

    private void getQuestion() {
        getRandomPolygon();
        generateOptions();
        createMultipleChoice();
    }


    private void showQuestion(Graphics2D g2) {
        super.drawBackground(g2);
        g2.setColor(Color.white);
        g2.setFont(new Font("Courier",Font.PLAIN,30));
        g2.drawString("Type the number associated with", super.x+ gp.displayedTile,super.y+gp.displayedTile);
        g2.drawString("your answer", super.x+ gp.displayedTile,super.y+gp.displayedTile + 30);
        g2.drawString("Name the polygon:", super.x+gp.displayedTile, super.y+gp.displayedTile+70);

        g2.setColor(new Color(255,255,255,255));
        g2.fillPolygon(xPoints, yPoints, sides);

        g2.setFont(new Font("Courier",Font.PLAIN,20));
        g2.drawString("1 - " + opt1, opt1x, opty);
        g2.drawString("2 - " + opt2, opt2x, opty);
        g2.drawString("3 - " + opt3, opt3x, opty);
        getResult();

        if (userAns!=null && userAns.length()!=0) {
            g2.setColor(Color.white);
            g2.setFont(new Font("Courier", Font.PLAIN, 30));
            g2.drawString(result, gp.displayedTile*3 - 10, gp.displayedTile*9);
            g2.drawString("Press [ENTER] to proceed", gp.displayedTile*3 - 10, gp.displayedTile*9 + 30);

            if (keyH.nextPressed == true && numCorrect < 5) {
                keyH.nextPressed = false;
                userAns = null;
                keyH.userAns = 0;
                usedPolygons.clear();
                if (result.equals("Correct!")) {
                    numCorrect += 1;
                }
                getQuestion();

            } else if (keyH.nextPressed == true){
                keyH.nextPressed = false;
                keyH.userAns = 0;
                super.changeGameState();
            } 
        
        
        }

        

       // displayResult(g2);
    }

    private void getResult() {
        switch (keyH.userAns) {
            case 1:
                userAns = opt1;
                break;

            case 2:
                userAns = opt2;
                break;
            case 3:
                userAns = opt3;
                break;
            default:
                userAns = null;
                break;
        }
        if (userAns!=null && userAns.equals(correctAns)) {
            result = "Correct!";
        } else {
            result = "Incorrect. Correct answer: " + correctAns;
        }
    }
}