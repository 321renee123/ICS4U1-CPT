/* 
* Main.java
* Name: Renee Xu
* Date: June 12 2024
* A program that allows user to interact with monsters and play educational minigames
*/

package main;

import javax.swing.*;
import java.net.URL;

/*
* App class (main class)
*/
public class App {


    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame();
       

        // FRAME SETTINGS
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); 
        frame.setTitle("TBD");


        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel); // adds object to frame
        frame.pack(); // sets frame to fit screen layout in GamePanel class
        
        frame.setLayout(null); // position components absolutely

        // ADD BACKGROUND
        URL bg_url = App.class.getResource("/res/background.jpg");
        JLabel bg = new JLabel("",new ImageIcon(bg_url),JLabel.CENTER);
        bg.setSize(gamePanel.width,gamePanel.height); // sizes bg to fit the screen
        bg.setBounds(0,0,gamePanel.width,gamePanel.height); // positioning
        
        frame.add(bg); // adds bg to frame

        frame.setLocationRelativeTo(null); // window appears at the center of the screen
        frame.setVisible(true); // window appears

        gamePanel.startGameThread(); // game is run


    }
}
