package main;

import javax.swing.JFrame;
import javax.swing.*;

public class App {


    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame();
       
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); // window is not resizable
        frame.setTitle("Game Test");

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.pack(); // sets frame to fit screen layout in GamePanel class
        
        frame.setLayout(null);

        JLabel bg = new JLabel("",new ImageIcon("C:\\\\Users\\\\renee\\\\workspace\\\\ICS4U1-CPT\\\\src\\\\res\\\\background\\\\bg2.jpg"),JLabel.CENTER);
        bg.setSize(gamePanel.width,gamePanel.height);
        bg.setBounds(0,0,1024,768);
        
        frame.add(bg);

        frame.setLocationRelativeTo(null); // window appears at the center of the screen
        frame.setVisible(true); // window appears

        gamePanel.startGameThread();


    }
}
