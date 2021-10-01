
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/*
 * this is the main game object. When created it contains a status, as well as a 
 * default snake object that always starts in the same location and is made up of only a head cell
 * object. A single food is also randomly placed on the board as the first food that the snake 
 * should try to eat. The class contains fields representing the width and height of the board
 * array for writing to a file, board width and height in pixels, initial snake velocity, 
 * a boolean for if the game is being played, a JLabel status, a Snake and FOod object, an
 * integer for the current play number, a list of high scores, strings for save files for the board
 * and scores, and finally an interval for the timer that the game relies on. There are methods
 * created to update the game as it should for correct play functionality
 */
@SuppressWarnings("serial")
public class Board extends JPanel {
    
    public static final int NUM_WIDTH = 30;
    public static final int NUM_HEIGHT = 20;
    public static final int BOARD_WIDTH = 600;
    public static final int BOARD_HEIGHT = 400;
    public static final int SNAKE_VELOCITY = 20;
    private boolean playing = false; // whether the game is running 
    private JLabel status; // Current status text, i.e. "Running..."
    
    
    // TESTABLE COMPONENTS:
    // the state of the game logic
    private Snake snake;
    private Food food;
    private int playNumber = 0;
    private List<Score> highScores = new ArrayList<Score>();
    
    
    private static final String SAVE_FILE = "files/Board_Save.txt";
    private static final String SCORE_FILE = "files/Score_Save.txt";
    
    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 150;
    
    Timer timer;

    
    public Board(JLabel status) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically with the given INTERVAL. We
        // register an ActionListener with this timer, whose actionPerformed() method is called each
        // time the timer triggers. We define a helper method called tick() that actually does
        // everything that should be done in a single timestep.
        this.timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key is pressed, by
        // changing the square's velocity accordingly. (The tick method below actually moves the
        // square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (playing) {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT && snake.getHead().getVx() == 0) {
                        snake.getHead().setVx(-SNAKE_VELOCITY);
                        snake.getHead().setVy(0);

                    } 
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT && snake.getHead().getVx() == 0) {
                        snake.getHead().setVx(SNAKE_VELOCITY);
                        snake.getHead().setVy(0);

                    } 
                    if (e.getKeyCode() == KeyEvent.VK_DOWN && snake.getHead().getVy() == 0) {
                        snake.getHead().setVx(0);
                        snake.getHead().setVy(SNAKE_VELOCITY);

                    } 
                    if (e.getKeyCode() == KeyEvent.VK_UP && snake.getHead().getVy() == 0) {
                        snake.getHead().setVx(0);
                        snake.getHead().setVy(-SNAKE_VELOCITY);

                    }
                }
            }
        });

        this.status = status;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        this.snake = new Snake(new Cell(Cell.INIT_VEL_X, Cell.INIT_VEL_Y, Cell.INIT_POS_X, 
                                        Cell.INIT_POS_Y, Board.BOARD_WIDTH, Board.BOARD_HEIGHT, 
                                        Color.BLACK));
        this.newFood();
        this.playNumber++;

        timer.start(); //START THE TIMER!

        playing = true;
        status.setText("Score: " + this.snake.getLength() + ", Running...");

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
        
    }
    
    public int getPlayNum() {
        return this.playNumber;
    }
    
    public void setSnake(Snake s) {
        this.snake = s;
    }
    
    public Snake getSnake() {
        return this.snake;
    }
    
    public List<Score> getScoreList() {
        return this.highScores;
    }
    
    public Food getFood() {
        return this.food;
    }
    
    // new food at random location on board
    public void newFood() {
        int foodX = ((int)(Math.random() * (Board.NUM_WIDTH - 2)) + 1) * 20;
        int foodY = ((int)(Math.random() * (Board.NUM_HEIGHT - 2)) + 1) * 20;
        food = new Food(BOARD_WIDTH, BOARD_HEIGHT, foodX, foodY, Color.BLACK);

    }
    
    // for junit testing
    public void placeFood(int x, int y) {
        this.food = new Food(BOARD_WIDTH, BOARD_HEIGHT, x, y, Color.BLACK);
    }
    
    
    // NO SCORE ADDED IF RESET BEFORE WIN/LOSS!
    // most recent person who got same high score as others is treated as highest**
    public void addScore(Score curr) {
        if (curr == null) {
            return;
        }
        highScores.add(curr);
        Collections.sort(highScores);
        if (highScores.size() > 5) {
            this.highScores = highScores.subList(1, 6);
        }
        WriteBoard.writeScoresToFile(highScores, SCORE_FILE);
    }

    // pauses the game and saves it to a file
    public void pause() {
        
        playing = false;
        timer.stop();
        status.setText("Paused");
        
        int[][] board = new int[NUM_HEIGHT][NUM_WIDTH];
        
        // store current food
        board[food.getPy() / 20][food.getPx() / 20] = 2;
        
        //3 is left, 4 is right, 5 is up, 6 is down
        Cell head = this.snake.getHead();
        int headX = head.getPx();
        int headY = head.getPy();
        if (head.getVx() == 0 && head.getVy() < 0) {
            board[headY / 20][headX / 20] = 5;

        } else if (head.getVx() == 0 && head.getVy() > 0) { 
            board[headY / 20][headX / 20] = 6;

        } else if (head.getVx() < 0 && head.getVy() == 0) {
            board[headY / 20][headX / 20] = 3;

        } else if (head.getVx() > 0 && head.getVy() == 0) {
            board[headY / 20][headX / 20] = 4;

        }
        
        // place non-head cells in array
        for (Cell c : this.snake.getCells()) {
            if (c != this.snake.getHead()) {
                board[c.getPy() / 20][c.getPx() / 20] = 1;
            }
        }
        
        WriteBoard.writeBoardToFile(board, SAVE_FILE);    
        WriteBoard.writeScoresToFile(this.highScores, SCORE_FILE);
        requestFocusInWindow();
    }
    
    
    // resumes game from file
    public void resume() {
        this.highScores = WriteBoard.getScores(SCORE_FILE);      
        int[][] board = WriteBoard.getBoard(SAVE_FILE, NUM_HEIGHT, NUM_WIDTH);
        
        Cell head = new Cell(0, 0, 40, 40, BOARD_WIDTH, BOARD_HEIGHT, Color.BLACK);
        // find head first
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                //3 is left, 4 is right, 5 is up, 6 is down
                if (board[i][j] == 3) {
                    head = new Cell(-SNAKE_VELOCITY, 0, j * 20, i * 20, BOARD_WIDTH, 
                            BOARD_HEIGHT, Color.BLACK);
                } else if (board[i][j] == 4) {
                    head = new Cell(SNAKE_VELOCITY, 0, j * 20, i * 20, BOARD_WIDTH, 
                            BOARD_HEIGHT, Color.BLACK);
                } else if (board[i][j] == 5) {
                    head = new Cell(0, -SNAKE_VELOCITY, j * 20, i * 20, BOARD_WIDTH, 
                            BOARD_HEIGHT, Color.BLACK);
                } else if (board[i][j] == 6) {
                    head = new Cell(0, SNAKE_VELOCITY, j * 20, i * 20, BOARD_WIDTH, 
                            BOARD_HEIGHT, Color.BLACK);
                }
                this.snake = new Snake(head);

            }
        }    
        
        // place the food and grow the snake to where it was before
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 2) {
                    placeFood(j * 20, i * 20);
                } else if (board[i][j] == 1) {
                    this.snake.grow();
                }
            }
        }      
        
        playing = true;
        timer.start();
        status.setText("Running...");

        
        requestFocusInWindow();
    }
    
    
    /**
     * This method is called every time the timer defined in the constructor triggers.
     */
    void tick() {
        if (playing) {
            // advance the snake in its current direction.
            snake.move();
            
            // check for the game end conditions
            if (snake.getHead().hitWall() || snake.hitSelf()) {
                this.addScore(new Score(this.snake.getLength(), this.playNumber));
                playing = false;
                status.setText("You lose!"); 
                
            // win condition - full length snake
            } else if (snake.isFull()) {
                this.addScore(new Score(this.snake.getLength(), this.playNumber));
                playing = false;
                status.setText("You win!");
                
            // grow snake condition
            } else if (snake.getHead().intersects(food)) {
                snake.grow();
                this.newFood();
                status.setText("Score: " + this.snake.getLength() + ", Running...");

            }

            // update the display
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        snake.draw(g);
        food.draw(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
    
    
}
