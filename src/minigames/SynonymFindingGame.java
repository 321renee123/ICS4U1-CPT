package minigames;

import main.*;
import java.awt.*;
import java.util.*;

public class SynonymFindingGame extends Minigame{

    KeyHandler keyH;
    private String[] words = {"assist","brave","calm","delicious","eager","famous","gigantic","happy","idea","kind","lazy","mend","neat","old","polite","quick","rich","tiny","enraged","dangerous"};
    private String[] synonyms = {"help","courageous","tranquil","scrumptious","enthusiastic","renowned","huge","joyous","concept","benevolent","idle","repair","tidy","ancient","courteous","rapid","wealthy","miniscule","furious","perilous"};
    private ArrayList<String> usedWords = new ArrayList<String>();

    private int index;
    private String word;
    private String correctAns;
    private String opt1, opt2, opt3;
    private String userAns;
    private int numCorrect;
    private String result;


    private int opt1x,opt2x,opt3x,opty;

    Random rand = new Random();


    public SynonymFindingGame(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        opt1x = gp.displayedTile*3 ;
        opt2x = gp.displayedTile*7 ;
        opt3x = gp.displayedTile*11 -5;
        opty = gp.displayedTile*7;
        getQuestion();
    }

    public void draw(Graphics2D g2) {
        if (gp.gameState == gp.minigame && gp.determineCollision() == 1) {
            showQuestion(g2);
        }
    }

    private void getWord() {
        index = rand.nextInt(19);
        word = words[index];
        correctAns = synonyms[index];

        usedWords.add(correctAns);
    }

    private void generateOptions() {
        String option;
        for (int i = 0 ; i < 2; i++) {
            do {
                option =synonyms[rand.nextInt(7)];
            } while(super.checkForRepeats(usedWords,option) == true);
            usedWords.add(option);
        }
        Collections.shuffle(usedWords);
    }

    private void getMultipleChoice() {
        opt1 = usedWords.get(0);
        opt2 = usedWords.get(1);
        opt3 = usedWords.get(2);
    }

    private void getQuestion() {
        getWord();
        generateOptions();
        getMultipleChoice();
    }

    private void showQuestion(Graphics2D g2) {
        super.drawBackground(g2);
        g2.setColor(Color.white);
        g2.setFont(new Font("Courier", Font.PLAIN, 30));
        g2.drawString("Type the number associated with", super.x+ gp.displayedTile,super.y+gp.displayedTile);
        g2.drawString("your answer", super.x+ gp.displayedTile,super.y+gp.displayedTile + 30);
        g2.drawString("Find the synonym for:",super.x + gp.displayedTile, super.y+gp.displayedTile + 70);

        g2.drawString(word.toUpperCase(),super.x + gp.displayedTile, super.y+gp.displayedTile + 120);
        g2.setFont(new Font("Courier",Font.PLAIN,20));
        g2.drawString("1 - " + opt1.toUpperCase(), opt1x, opty);
        g2.drawString("2 - " + opt2.toUpperCase(), opt2x, opty);
        g2.drawString("3 - " + opt3.toUpperCase(), opt3x, opty);

        getResult();

        if (userAns!=null && userAns.length()!=0) {
            g2.setColor(Color.white);
            g2.setFont(new Font("Courier", Font.PLAIN, 30));
            g2.drawString(result, gp.displayedTile*3-10,gp.displayedTile*8);
            g2.drawString("Press [ENTER] to proceed",gp.displayedTile*3-10,gp.displayedTile*8+30);

            if (keyH.nextPressed == true && numCorrect < 5) {
                keyH.nextPressed = false;
                keyH.userAns = 0;
                userAns = null;
                usedWords.clear();
                if (result.equals("Correct!")) {
                    numCorrect += 1;
                }
                getQuestion();
            } else if (keyH.nextPressed == true) {
                keyH.nextPressed = false;
                keyH.userAns = 0;
                super.changeGameState();
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





