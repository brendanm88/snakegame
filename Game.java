
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {
        // Top-level frame in which game components live
        final JFrame frame = new JFrame("SNAAAAAAAKE");
        frame.setLocation(450, 250);
        
        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Score: 0, Running...");
        status_panel.add(status);
        
        

        // Main playing area
        final Board board = new Board(status);
        Border gray = BorderFactory.createLineBorder(Color.GRAY, 12);
        board.setBorder(gray);
        frame.add(board, BorderLayout.CENTER);
        
        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.reset();
            }
        });
        control_panel.add(reset);
        
        // pause button
        final JButton pause = new JButton("Pause/Save");
        pause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.pause();
            }
        });
        control_panel.add(pause);
        
        // resume button
        final JButton resume = new JButton("Resume/Load");
        resume.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.resume();
            }
        });
        control_panel.add(resume);
        
        // instructions frame/window
        final JFrame instr = new JFrame("Instructions");
        JPanel txtPanel = new JPanel();
        txtPanel.setLayout(new GridLayout(12, 1));
        
        JLabel overView = new JLabel("Overview of Snake:");
        txtPanel.add(overView);
        
        JLabel desc = new JLabel("Snake is a game where the player controls a growing snake that "
                + "eats food and dies when it");
        txtPanel.add(desc);
        
        JLabel descTwo = new JLabel("hits itself or the walls. Reach the goal length to win!");
        txtPanel.add(descTwo);
        
        JLabel space = new JLabel(" *************************************************************"
                + "************************************ ");
        txtPanel.add(space);
        
        JLabel title = new JLabel("How to play snake:");
        txtPanel.add(title);
        
        JLabel stepOne = new JLabel("Keys: press the arrow keys to change direction of the snake.");
        txtPanel.add(stepOne);
        
        JLabel stepTwo = new JLabel("Goal: eat pieces of food to grow the snake.");
        txtPanel.add(stepTwo);
        
        JLabel stepThree = new JLabel("Avoid: don't hit the sides of the board or your own snake's "
                                    + "body - you'll lose!");
        txtPanel.add(stepThree);
        
        JLabel stepFour = new JLabel("To win: grow the snake to 10 blocks long!");
        txtPanel.add(stepFour);
        
        JLabel stepFive = new JLabel("Hint: the resume/load button can ALWAYS be used to start the "
                + "snake at the last saved location!");
        txtPanel.add(stepFive);
        
        JLabel stepFiveContd = new JLabel("    (If you pause or save, however, 'resume' will only "
                + "pick up where your current game left off!)");
        txtPanel.add(stepFiveContd);
        
        JLabel stepSix = new JLabel("    (Note: high scores are only reset if you move the game "
                + "without loading the previous game first)");
        txtPanel.add(stepSix);
        
        instr.add(txtPanel);
        instr.setLocation(450, 450);
        instr.setSize(450, 450);
        
        
        // instructions button
        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.pause();
                instr.pack();
                instr.setVisible(true);
            }
        });
        control_panel.add(instructions);        
        
        // high scores button
        final JButton high_scores = new JButton("High Scores");
        high_scores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.pause();
                
                // high scores window created/updated when pressed button
                final JFrame scrs = new JFrame("High Scores");
                scrs.setLocation(625, 300);
                JPanel highScrs = updateScores(board);
                Border grayThin = BorderFactory.createLineBorder(Color.GRAY, 6);
                highScrs.setBorder(grayThin);
                scrs.add(highScrs, BorderLayout.CENTER);
                scrs.pack();
                scrs.setVisible(true);
                
            }
        });
        control_panel.add(high_scores);
 
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        board.reset();
    }
    
    // method takes in a board, updates high scores by taking them from the board object, and
    // places them in a JPanel that is returned and used to display high scores.
    public JPanel updateScores(Board board) {
        JPanel scorePanel = new JPanel(new GridLayout(6, 1));
        JLabel title = new JLabel();
        title.setText("High Scores! (Highest at bottom)");
        scorePanel.add(title);
        for (int i = board.getScoreList().size() - 1; i >= 0; i--) {
            Score s = board.getScoreList().get(i);
            String text = s.getName() + ": " + s.getScore();
            JLabel highScores = new JLabel();
            highScores.setText(text);
            scorePanel.add(highScores, BorderLayout.SOUTH);
        }


        return scorePanel;
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
        
    }
}