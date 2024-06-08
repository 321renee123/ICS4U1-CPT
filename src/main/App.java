package main;

import javax.swing.JFrame;
import javax.swing.*;
import java.net.URL;

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

        URL bg_url = App.class.getResource("/res/background/bg2.jpg");

        JLabel bg = new JLabel("",new ImageIcon(bg_url),JLabel.CENTER);
        bg.setSize(gamePanel.width,gamePanel.height);
        bg.setBounds(0,0,1024,768);
        
        frame.add(bg);

        frame.setLocationRelativeTo(null); // window appears at the center of the screen
        frame.setVisible(true); // window appears

        gamePanel.startGameThread();


    }
}
