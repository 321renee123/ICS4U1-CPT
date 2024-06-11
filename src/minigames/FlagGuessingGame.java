package minigames;

import main.*;
import java.awt.*;
import java.util.*;
import java.awt.image.BufferedImage;

public class FlagGuessingGame extends Minigame {

    private KeyHandler keyH;
    private Random rand = new Random();

    private String[] flags = {"america","canada","china","france","ireland","italy","japan", "south korea","uk","ukraine"};
    private ArrayList<String> usedFlags = new ArrayList<String>();
    private String correctAns;
    private String opt1, opt2, opt3;
    private int opt1x,opt2x,opt3x,opty;
    private String userAns;
    private int numCorrect;
    private String result;
    private BufferedImage flag;

    public FlagGuessingGame(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        getQuestion();
        opt1x = gp.displayedTile*3 ;
        opt2x = gp.displayedTile*7 ;
        opt3x = gp.displayedTile*11 -5;
        opty = gp.displayedTile*8;
    }


    public void draw(Graphics2D g2) {
        if (gp.gameState == gp.minigame && gp.determineCollision() == 2) {
            showQuestion(g2);
        }
    }

    private void getRandomFlag() {
        correctAns = flags[rand.nextInt(7)];
        flag = getImage("/res/minigame_assets/" + correctAns);
        usedFlags.add(correctAns);
    }
    
    private void generateOptions() {
        String option;
        for (int i = 0 ; i < 2; i++) {
            do {
                option = flags[rand.nextInt(7)];
            } while(super.checkForRepeats(usedFlags,option) == true);
            usedFlags.add(option);
        }
        Collections.shuffle(usedFlags);
    }

    private void getMultipleChoice() {
        opt1 = usedFlags.get(0);
        opt2 = usedFlags.get(1);
        opt3 = usedFlags.get(2); 
    }

    

    private void getQuestion() {
        getRandomFlag();
        generateOptions();
        getMultipleChoice();
    }

    private void showQuestion(Graphics2D g2) {
        super.drawBackground(g2);
        g2.setColor(Color.white);
        g2.setFont(new Font("Courier", Font.PLAIN, 30));
        g2.drawString("Name the country:",super.x + gp.displayedTile, super.y+gp.displayedTile);

        g2.drawImage(flag, 320,192,gp.displayedTile*3,gp.displayedTile*3, null);

        g2.setFont(new Font("Courier", Font.PLAIN, 20));
        g2.setColor(Color.white);
        g2.drawString("1 - " + opt1, opt1x,opty);
        g2.drawString("2 - " + opt2, opt2x, opty);
        g2.drawString("3 - " + opt3, opt3x, opty);

        getResult();

        if (userAns!= null && userAns.length()!=0) {
            g2.setFont(new Font("Courier",Font.PLAIN,30));
            g2.setColor(Color.white);
            g2.drawString(result, gp.displayedTile*3-10,gp.displayedTile*9);
            g2.drawString("Press [ENTER] to proceed",gp.displayedTile*3-10,gp.displayedTile*9+20);

            
            if (keyH.nextPressed == true && numCorrect < 4) {
                keyH.nextPressed = false;
                userAns = null;
                keyH.userAns = 0;
                usedFlags.clear();
                if (result.equals("Correct!")) {
                    numCorrect += 1;
                }
                getQuestion();

            } else if (keyH.nextPressed == true){
                keyH.nextPressed = false;
                keyH.userAns = 0;
                gp.gameState = gp.playing;
            } 
        }
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
