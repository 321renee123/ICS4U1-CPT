package main;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * UI class (essentially responsible for displaying text outside of minigames as well as intro + outro sequence)
 */
public class UI {

    // set up file reading function to get dialogue
    FileReader in;
    BufferedReader read;

    GamePanel gp;
    KeyHandler keyH;

    // counters to see which line of dialogue, which slide of intro/outro sequence should be displayed
    int lineCounter = 0;
    int introCounter = 0;
    int outroCounter = 0;

    
    BufferedImage slide; // stores intro/outro slide images

    /* Constructor
     * pre: none
     * post: UI set up with game panel and key handler obejcts
     */
    public UI(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
    } 


    /* Displays different stuff depending on game state
     * pre: none
     * post: Text or dialogue or intro/outro sequence is drawn
     */
    public void draw(Graphics2D g2) {

        // intro game state
        if (gp.gameState == gp.intro) {
            displayIntro(g2);
        }

        // player moving game state
        if (gp.gameState == gp.playing) {
            displayFightText(g2);
            displayOpponentsAndInstruction(g2);
        } 

        // dialogue state
        if (gp.gameState == gp.dialogue) {
            displayDialogue(g2);
        }

        // outro state
        if (gp.gameState == gp.outro) {
            displayOutro(g2);
        }

        // end
        if (gp.gameState == gp.end) {
            displayEnd(g2);
        }
    }

    /* Displays intro sequence
     * pre: none
     * post: Intro sequence drawn
     */
    private void displayIntro(Graphics2D g2) {

        try {
            slide = ImageIO.read(UI.class.getResource("/res/intro/intro" + introCounter + ".png")); // sets image to the current slide
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2.drawImage(slide,0,0,gp.width,gp.height,null); // image drawn

        if (keyH.nextPressed == true) { // if user presses to go to the next slide
            keyH.nextPressed = false;
            if (introCounter < 7) { // if the intro sequence is not completed yet
                introCounter += 1; // go to next slide
            } else { // if intro sequence is completed
                gp.gameState = gp.playing; // change game state to player moving 
            }

        }
    }

    /* Displays outro sequence
     * pre: none
     * post: outro sequence drawn
     */
    private void displayOutro(Graphics2D g2) {

        try {
            slide = ImageIO.read(UI.class.getResource("/res/outro/outro" + outroCounter + ".png")); // sets image to current outro slide
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2.drawImage(slide,0,0,gp.width,gp.height,null); // draws slide

        if (keyH.nextPressed == true) { // if user presses to go to next slide
            keyH.nextPressed = false;
            if (outroCounter < 6) { // if outro sequence not yet completed 
                outroCounter += 1; // next slide
            } else { // if outro sequence is completed
                gp.gameState = gp.end; // change game state to end
            }
        }
    }

    /* Unchanging end screen displayed
     * pre: none
     * post: end screen drawn
     */
    private void displayEnd(Graphics2D g2) {

        try {
            slide = ImageIO.read(UI.class.getResource("/res/end.png")); // image set as end screen
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2.drawImage(slide,0,0,gp.width,gp.height,null); // end screen drawn
    }

    /* Writes the amount of opponents fought and a small hint at the beginning
     * pre: none
     * post: Text drawn
     */
    private void displayOpponentsAndInstruction(Graphics2D g2) {
        g2.setFont(new Font("Courier", Font.BOLD, 30));
        g2.setColor(new Color(255,255,255));

        g2.drawString(gp.completed.size() + "/4 opponents defeated",315,60); // opponent counter displayed
        if (gp.completed.size() == 0) { // at the very beginning of first player moving state
            g2.drawString("Try approaching a monster (WASD)",240,90); // hint displayed
        }
    }


    /* Lets user know to fight an opponent if opponent is approached
     * pre: none
     * post: Text is drawn
     */
    private void displayFightText(Graphics2D g2) {
        g2.setFont(new Font("Courier", Font.BOLD, 30));
        g2.setColor(new Color(255,255,255));

        if (gp.determineCollision() > 0) { // if user is near an opponent 
            if (gp.hasFought() == false) { // if the opponent has not yet been fought
                g2.drawString("Press [F] to fight",350,700); // draw text telling usre to fight
            }
        }
    }


    /* Create ArrayList of voicelines from the voicelines file of a given opponent
     * pre: none
     * post: ArrayList returned
     */
    private ArrayList<String> getVoicelines() {
        ArrayList<String> allLines = new ArrayList<String>();
        String line;

        try {
            // File reading tools setup
            in = new FileReader(new File("src/character/voicelines/opp" + gp.determineCollision() + ".txt")); 
            read = new BufferedReader(in);

            while((line = read.readLine()) != null) {
                allLines.add(line); // add all lines in file to ArrayList
            }
            return allLines;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return allLines;
    }


    /* Creates ArrayList for each individual line of the total voicelines array list, with each index in this ArrayList being displayed on individual lines, since Java Swing does not allow for line breaks to be drawn 
     * pre: none
     * post: ArrayList returns
     */
    private ArrayList<String> getBrokenLine(String line) {
        ArrayList<String> lineComponents = new ArrayList<String>();
        char character;
        String component = "";
        boolean endsOnLetter = false;

        for (int i = 0; i < line.trim().length();i++) {
            character = line.charAt(i); // sets character as character in the line
            if (character == '|') { // if you come across a line break
                lineComponents.add(component); // add previous words as a single string in the ArrayList
                component = ""; // reset line
                endsOnLetter = false;
            } else {
                component += character; // adds character to line
                endsOnLetter = true;
            }
        }
        if (endsOnLetter == true) {
            lineComponents.add(component); // adds final line to ArrayList
            component = "";
        }
        return lineComponents;
    }


    /* Draws dialogue 
     * pre: none
     * post: Dialogue is drawn
     */
    private void displayDialogue(Graphics2D g2) {
        // size and location of dialogue box
        int x = gp.displayedTile*2; 
        int y = gp.displayedTile/2;
        int width = gp.width - (gp.displayedTile*4);
        int height = gp.displayedTile*3;

        g2.setColor(new Color(0,0,0,190));
        g2.fillRoundRect(x,y,width,height,35,35);

        g2.setFont(new Font("Courier", Font.BOLD, 40));
        g2.setColor(Color.white);
        g2.drawString("Press [ENTER] to proceed",220,700);


        g2.setFont(new Font("Courier", Font.PLAIN, 20));
        g2.setColor(Color.white);

        // dialogue text location
        int textx = gp.displayedTile*2 + 20;
        int texty = gp.displayedTile;

        if (lineCounter < 6) { // before all lines have been said
            for (String line : getBrokenLine(getVoicelines().get(lineCounter))) { 
                g2.drawString(line, textx, texty); // displays dialogue lines
                texty += 25; // creates line break
            }
    
            if (keyH.nextPressed == true) { // move on to next line
                keyH.nextPressed = false;
                lineCounter +=1;
            }
        } else { // all lines have been said
            gp.gameState = gp.minigame; // change game state to minigame, minigame is starting
            lineCounter = 0;
        }     
    }



    
}
