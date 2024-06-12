package minigames;

import main.*;
import java.awt.*;
import java.util.*;

public class ArithmeticGame extends Minigame{

    KeyHandler keyH;

    Random rand = new Random();
    private ArrayList<Integer> usedNums = new ArrayList<Integer>();
    private int correctAns;
    private int operation;
    private int num1,num2;
    private String equation;
    private int opt1, opt2, opt3;
    private int opt1x,opt2x,opt3x,opty;
    private int userAns = -1;
    private int numCorrect;
    private String result;
    

    public ArithmeticGame(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        opt1x = gp.displayedTile*3 ;
        opt2x = gp.displayedTile*7 ;
        opt3x = gp.displayedTile*11 -5;
        opty = gp.displayedTile*8;

        getQuestion();
    }

    public void draw(Graphics2D g2) {
        if (gp.gameState == gp.minigame && gp.determineCollision() == 3) {
            showQuestion(g2);
        }
    }

    private void getEquation() {
        operation = rand.nextInt(3);
        switch(operation) {
            case 0:
                num1 = rand.nextInt(10);
                num2 = rand.nextInt(10);
                correctAns = num1 + num2;
                equation = num1 + " + " + num2 + " = ?";
                break;

            case 1:
                num1 = rand.nextInt(10);
                num2 = num1 + rand.nextInt(10);
                correctAns = num2 - num1;
                equation = num2 + " - " + num1 + " = ?";
                break;

            case 2:
                num1 = rand.nextInt(10);
                num2 = rand.nextInt(10);
                correctAns = num1*num2;
                equation = num1 + " x " + num2 + " = ?";
                break;

            case 3:
                num1 = rand.nextInt(10);
                num2 = num1*rand.nextInt(10);
                correctAns = num2/num1;
                equation = num2 + " / " + num1 + " = ?";
                break;
        }
        usedNums.add(correctAns);
    }

    private void generateOptions() {
        int option;
        for (int i = 0; i<2; i++) {
            do {
                option = rand.nextInt(20);
            } while (super.checkForRepeats(usedNums,option));
            usedNums.add(option);
        }
        Collections.shuffle(usedNums);
    }

    private void getMultipleChoice() {
        opt1 = usedNums.get(0);
        opt2 = usedNums.get(1);
        opt3 = usedNums.get(2);
    }

    private void getQuestion() {
        getEquation();
        generateOptions();
        getMultipleChoice();
    }

    private void showQuestion(Graphics2D g2) {
        super.drawBackground(g2);
        g2.setColor(Color.white);
        g2.setFont(new Font("Courier", Font.PLAIN, 30));
        g2.drawString("Type the number associated with", super.x+ gp.displayedTile,super.y+gp.displayedTile);
        g2.drawString("your answer", super.x+ gp.displayedTile,super.y+gp.displayedTile + 30);
        g2.drawString(equation, super.x + gp.displayedTile,super.y + (gp.displayedTile*3));

        g2.setFont(new Font("Courier", Font.PLAIN, 30));
        g2.setColor(Color.white);
        g2.drawString("1) " + opt1, opt1x,opty);
        g2.drawString("2) " + opt2, opt2x, opty);
        g2.drawString("3) " + opt3, opt3x, opty);

        getResult();

        if (userAns != -1) {
            g2.setColor(Color.white);
            g2.setFont(new Font("Courier", Font.PLAIN, 30));
            g2.drawString(result, gp.displayedTile*3-10, gp.displayedTile*9);
            g2.drawString("Press [ENTER] to proceed",gp.displayedTile*3-10,gp.displayedTile*9+30);

            if (keyH.nextPressed == true && numCorrect == 6 && result.equals("Correct!")) {
                keyH.nextPressed = false;
                keyH.userAns = 0;
                super.changeGameState();

            } else if (keyH.nextPressed == true){
                keyH.nextPressed = false;
                userAns = -1;
                keyH.userAns = 0;
                usedNums.clear();
                if (result.equals("Correct!")) {
                    numCorrect += 1;
                }
                getQuestion();
            } 
            
        }
 
    }

    private void getResult() {
        switch(keyH.userAns) {
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
                userAns = -1;
                break;
        }
        if (userAns != -1 && userAns == correctAns) {
            result = "Correct!";
        } else {
            result = "Incorrect. Correct answer: " + correctAns;
        }
    }

    
}
