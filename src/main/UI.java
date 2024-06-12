package main;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

public class UI {

    FileReader in;
    BufferedReader read;
    String collisionOpp;


    GamePanel gp;
    KeyHandler keyH;
    Font courier_1;    
    int lineCounter = 0;
    int introCounter = 0;
    int outroCounter = 0;
    BufferedImage slide;

    public UI(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
    } 

    public void draw(Graphics2D g2) {

        if (gp.gameState == gp.intro) {
            displayIntro(g2);
        }

        if (gp.gameState == gp.playing) {
            displayFightText(g2);
            displayOpponentsAndInstruction(g2);

        } 
        
        if (gp.gameState == gp.dialogue) {
            displayDialogue(g2);
        }

        if (gp.gameState == gp.outro) {
            displayOutro(g2);
        }

    }

    private void displayIntro(Graphics2D g2) {
        try {
            slide = ImageIO.read(UI.class.getResource("/res/intro/intro" + String.valueOf(introCounter) + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(slide,0,0,gp.width,gp.height,null);
        if (keyH.nextPressed == true) {
            keyH.nextPressed = false;
            if (introCounter<7) {
                introCounter += 1;
            } else {
                gp.gameState = gp.playing;
            }

        }
    }

    private void displayOutro(Graphics2D g2) {
        try {
            slide = ImageIO.read(UI.class.getResource("/res/outro/outro" + String.valueOf(outroCounter) + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(slide,0,0,gp.width,gp.height,null);
        if (keyH.nextPressed == true) {
            keyH.nextPressed = false;
            if (outroCounter<6) {
                outroCounter += 1;
            } else {
                gp.gameState = gp.playing;
            }

        }
    }

    private void displayOpponentsAndInstruction(Graphics2D g2) {
        g2.setFont(new Font("Courier", Font.BOLD, 30));
        g2.setColor(Color.white);
        g2.drawString(gp.completed.size() + "/4 opponents defeated",315,60);
        if (gp.completed.size() == 0) {
            g2.drawString("Try approaching a monster (WASD)",240,90);
        }
    }

    private void displayFightText(Graphics2D g2) {
        g2.setFont(new Font("Courier", Font.BOLD, 30));
        g2.setColor(Color.white);
        if (gp.determineCollision() > 0) {
            if (gp.hasFought() == false) {
                g2.drawString("Press [F] to fight",350,700);
            }
        }
    }


    private ArrayList<String> getVoicelines() {
        ArrayList<String> allLines = new ArrayList<String>();
        String line;

        try {
            in = new FileReader(new File("src/character/voicelines/opp" + gp.determineCollision() + ".txt"));
            read = new BufferedReader(in);

            while((line = read.readLine()) != null) {
                allLines.add(line);
            }
            return allLines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allLines;
    }

    private ArrayList<String> getBrokenLine(String line) {
        ArrayList<String> lineComponents = new ArrayList<String>();
        char character;
        String component = "";
        boolean endsOnLetter = false;

        for (int i = 0; i < line.trim().length();i++) {
            character = line.charAt(i);
            if (character == '|') {
                lineComponents.add(component);
                component = "";
                endsOnLetter = false;
            } else {
                component += character;
                endsOnLetter = true;
            }
        }
        if (endsOnLetter == true) {
            lineComponents.add(component);
            component = "";
        }
        return lineComponents;
    }

    private void displayDialogue(Graphics2D g2) {
        int x = gp.displayedTile*2;
        int y = gp.displayedTile/2;
        int width = gp.width - (gp.displayedTile*4);
        int height = gp.displayedTile*3;
        g2.setFont(new Font("Courier", Font.PLAIN, 40));

        Color c = new Color(0,0,0,190);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);
        g2.drawString("Press [ENTER] to proceed",220,700);



        g2.setFont(new Font("Courier", Font.PLAIN, 20));
        g2.setColor(Color.white);
        int textx = gp.displayedTile*2 + 20;
        int texty = gp.displayedTile;

        if (lineCounter < 6) {
            for (String line : getBrokenLine(getVoicelines().get(lineCounter))) {
                g2.drawString(line, textx, texty);
                texty += 25;
            }
    
            if (keyH.nextPressed == true) {
                keyH.nextPressed = false;
                lineCounter +=1;
            }
        } else {
            gp.gameState = gp.minigame;
            lineCounter = 0;
        }

        
        
    }



    
}
