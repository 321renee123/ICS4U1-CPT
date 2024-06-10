package minigames;
import java.awt.event.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

/**
 * PolygonGuessingGame.java
 * A simple polygon guessing game where the user must guess the number of sides
 * of a randomly generated polygon displayed on the screen.
 */
public class PolygonGuessingGame extends JFrame {
    private JPanel drawingPanel;  // Panel to display the polygon
    private JTextField guessField;  // Text field for user input
    private JButton submitButton;  // Button to submit the guess
    private ArrayList<String> questions;  // List to store questions from a file (currently unused)
    private Random random;  // Random number generator
    private int sides;  // Number of sides of the current polygon

    /**
     * Constructor for PolygonGuessingGame
     * pre: none
     * post: GUI components are set up and the game is initialized
     */
    public PolygonGuessingGame() {
        setTitle("Polygon Guessing Game");  // Set the window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close application on window close
        setLayout(new BorderLayout());  // Use BorderLayout for the frame

        // Initialize the drawing panel
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int[] xPoints = new int[sides];  // X coordinates of the polygon vertices
                int[] yPoints = new int[sides];  // Y coordinates of the polygon vertices
                int radius = 50;  // Radius of the polygon
                int centerX = getWidth() / 2;  // Center X coordinate of the panel
                int centerY = getHeight() / 2;  // Center Y coordinate of the panel

                // Calculate the vertices of the polygon
                for (int i = 0; i < sides; i++) {
                    double angle = 2 * Math.PI / sides * i;  // Angle for the current vertex
                    xPoints[i] = (int) (centerX + radius * Math.cos(angle));
                    yPoints[i] = (int) (centerY + radius * Math.sin(angle));
                }

                // Draw the polygon
                g2d.drawPolygon(xPoints, yPoints, sides);
            }
        };

        drawingPanel.setPreferredSize(new Dimension(200, 200));  // Set preferred size of the drawing panel
        add(drawingPanel, BorderLayout.CENTER);  // Add the drawing panel to the center of the frame

        // Initialize the input panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        guessField = new JTextField(10);  // Text field for user input with a width of 10 columns
        submitButton = new JButton("Submit");  // Button to submit the guess
        submitButton.addActionListener(new SubmitButtonListener(this));  // Add action listener to the button
        inputPanel.add(new JLabel("How many sides does this polygon have?"));  // Add label to the input panel
        inputPanel.add(guessField);  // Add text field to the input panel
        inputPanel.add(submitButton);  // Add button to the input panel
        add(inputPanel, BorderLayout.SOUTH);  // Add the input panel to the bottom of the frame

        questions = new ArrayList<>();  // Initialize the list of questions
        random = new Random();  // Initialize the random number generator
        loadQuestionsFromFile("questions.txt");  // Load questions from the specified file (currently unused)

        displayRandomPolygon();  // Display the first random polygon
        pack();  // Pack the components within the frame
        setLocationRelativeTo(null);  // Center the frame on the screen
        setVisible(true);  // Make the frame visible
    }

    /**
     * Loads questions from a specified file into the questions list.
     * pre: The argument has to be a valid String
     * post: Questions are loaded into the list
     */
    private void loadQuestionsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                questions.add(line.trim());  // Add each line as a question to the list
            }
        } catch (IOException e) {
            System.err.println("Error reading questions from file: " + e.getMessage());  // Print error message if file reading fails
        }
    }

    /**
     * Displays a random polygon by setting the number of sides to a random value between 3 and 10, and then repainting the drawing panel.
     * pre: none
     * post: A new random polygon is displayed
     */
    private void displayRandomPolygon() {
        sides = random.nextInt(8) + 3;  // Random number between 3 and 10
        drawingPanel.repaint();  // Repaint the drawing panel to display the new polygon
    }

    /**
     * SubmitButtonListener class handles the event when the submit button is clicked.
     */
    private class SubmitButtonListener implements ActionListener {
        private PolygonGuessingGame game;

        public SubmitButtonListener(PolygonGuessingGame game) {
            this.game = game;
        }

        @Override
        /**
         * Handles the submit button click event.
         * pre: none
         * post: The guess is checked and the appropriate message is displayed
         */
        public void actionPerformed(ActionEvent e) {
            String guess = game.guessField.getText().trim();  // Get the user's guess from the text field
            if (!guess.isEmpty()) {
                try {
                    int userGuess = Integer.parseInt(guess);  // Parse the guess to an integer
                    checkGuess(userGuess);  // Check if the guess is correct
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(game,
                            "Please enter a valid number.", "Invalid Guess", JOptionPane.ERROR_MESSAGE);  // Show error message if input is not a valid number
                }
            }
        }

        /**
         * Checks the user's guess against the actual number of sides of the polygon.
         * pre: none
         * post: The guess is checked and the appropriate message is displayed
         */
        private void checkGuess(int userGuess) {
            if (userGuess == game.sides) {
                JOptionPane.showMessageDialog(game,
                        "Congratulations! Your guess is correct!", "Correct Guess", JOptionPane.INFORMATION_MESSAGE);  // Show success message if the guess is correct
            } else {
                JOptionPane.showMessageDialog(game,
                        "Sorry, the correct answer is " + game.sides, "Incorrect Guess", JOptionPane.ERROR_MESSAGE);  // Show error message if the guess is incorrect
            }
            // No need to display a new polygon or reset the input field
        }
    }

    /**
     * Runs the Polygon Guessing Game.
     * pre: none
     * post: The Polygon Guessing Game window is displayed
     */
    public static void run() {
        SwingUtilities.invokeLater(PolygonGuessingGame::new);  // Run the game on the Event Dispatch Thread
    }
}
