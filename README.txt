=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: maddenb
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays: A 2D array is used to store the current state of the game board, i.e. the positions 
  of the current snake, the current food that is available for the snake to eat, and all of the
  other empty positions on the board. This is used so that when the game is paused, the entire board
  can be saved to a file, and then when the game is unpaused, the board's snake, food, movement, and
  all other fields can be reinstantiated from the 2D array in the file. Different integers are used
  to store the head in the 2D array to represent the direction the snake is moving, snake body cells
  are represented differently than the head, and foods are represented differently than any part of
  the snake, so that the model can recreate the game correctly/in the same way as it was paused. A
  2D array is justified here (as opposed to just stopping game motion/ticking) so that the current 
  game can be saved to a file and then RESUMED even after the game is closed and reopened, as the
  resume method of the model pulls the 2D array from the file. An integer 2D array is perfect for
  modeling the state of the board because it is very easy to denote different objects of the board
  with different integers, with each integer representing a different object. This makes it easy to
  write the 2D array to a file because integers can easily be separated by spaces and then read back
  into the game when the game is unpaused. Each integer representing a specific object and/or
  direction of movement is complex enough for the model but not unnecessarily complicated.

  2. Collections: I used a List<Cell> (specifically ArrayList<Cell>) to keep track of the 
  snake in my game, using a cell object, as described in the Cell.java class description 
  below. I had originally wanted to use a TreeMap to represent high scores in my game, but I moved 
  away from this so that I could get credit for File I/O with high scores (also realized this was 
  unnecessarily inefficient as noted in the feedback in my proposal on gradescope, as I didn't need 
  to store scores with a key, I just needed an ordered list). I chose an ArrayList for my snake so 
  that size was variable (could add cells to the snake an unlimited amount of times to grow it), and
  so that I could sort/order the list when new cells are added. Using a list of Cell objects allows 
  me to maintain a list of current snake cells and use the LAST one in the list when I add a new
  cell to the tail of the snake (use its positions/velocities) as well as maintaining certain
  requirements for the snake based on cells being either the head or part of the rest of the body.
  This is better than using a set because I need to be able to order the cells, and the only for 
  loops I use are for-each loops to perform an action on every element of the list. A Map would have
  been quite inefficient and unnecessary compared to a List, because cells don't need to be mapped
  by specific keys, and I don't need to access specific cells, I just need to maintain their order.

  3. File I/O: File I/O is used when the high scores of the game are saved to a file after every 
  loss or win. I had originally stated in my proposal that I was going to use file I/O to save game 
  state to a file (and I do), but I wanted to use that to get credit for 2D Arrays, so I chose to 
  also read and write high scores for the game. While I use a collection of high scores to 
  write/read with a file, I get want to get credit for collections from modeling the snake so that
  should be fine. I write the scores to the file using their playNumber and their actual score. 
  Because playNumber directly correlates to player name (i.e. player name is constructed as:
  "Player " + playNum), this means that I am storing both player name and player score in the file,
  so that should cover two pieces of state. The data is stored separated by spaces in the file,
  with a new line when a new score/name combo is added. When the game is won/lost, a new score is
  written to the file. Then, the high score data is read from the file when the player presses the
  "Highscore" button while the game is running. A new JFrame window pops up with the data that is
  read in from the file, and displays a list of high scores and player names using that data. The
  high scores are ordered from lowest to highest in the file (increasing order) as a result of 
  natural ordering of the collection, but they are displayed from highest to lowest in the JFrame
  for high scores when read in and displayed. If a player achieves a score that is already on the 
  list, but it is still considered a high score, the most recent "duplicate" score is displayed on 
  the list, and if multiple of the same score should all rightfully be listed on the list of high 
  scores, the most recent achievements of that score are listed first/higher. The file I/O also 
  catches and handles exceptions related to reading/writing high scores.

  4. Testable Component: While all of the classes provided are JUnit testable, the one I focused on
  was Board.java, as it contains many states that can be tested as the board model (and a specific
  instance of it in the game) is updated by a variety of user interactions that call different
  methods. There are many methods in Board.java that are designed to specifically modify or
  return only a single part of the model, so that changes to the game state behind the scenes can be
  easily tested. These methods are listed and tested under tests for Board.java in GameTest.java 
  (the last section). I tried to test many edge cases, but there are countless that would require a 
  large amount of tests so I don't think it's realistic to capture all of them. Also, the JUnit
  testing done is reliant on updates to the game model, and is no way dependent on GUI components.
  The way I did my testing was that I made a lot of methods in Board.java that were very easily 
  testable, or designed to be JUnit testable in that they only modified or returned a single piece
  of the game state. Then, after testing those method and making sure they were all working by 
  checking edge cases and such like for usual testing, I tested the four more complicated methods
  by making use of the simpler methods (as the four more complicated methods made use of those 
  simpler functions in the actual Board.java as well). The four more complex methods were .tick(), 
  .pause(), .resume(), and .reset(). This completed pretty comprehensive testing of the whole 
  Board.java, and made sure that I had designed many of the states of the game to be unit testable.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
  Board.java: this is the main game object. When created it contains a status, as well as a 
  default snake object that always starts in the same location and is made up of only a head cell
  object. A single food is also randomly placed on the board as the first food that the snake 
  should try to eat. The class contains fields representing the width and height of the board
  array for writing to a file, board width and height in pixels, initial snake velocity, 
  a boolean for if the game is being played, a JLabel status, a Snake and FOod object, an
  integer for the current play number, a list of high scores, strings for save files for the board
  and scores, and finally an interval for the timer that the game relies on. The reset method 
  resets the board/game to its initial status and increments the playNumber, there are getters and
  setters for most of the fields of the board (either for unit testing or for use in the model), 
  newFood() places a food at a random location on the board, addScore adds the current player score
  to the list of scores, pause pauses the game and writes the game state to a file, resume pulls
  the game state from a file and reinstantiates the board as it was before, paintComponent and 
  getPreferredSize are usd for drawing/setting up the game in Game.java, and finally, tick() moves
  the game objects as they should and checks for end/continue conditions in the game and then 
  repaints the board.
  
  Cell.java: Cell.java extends GameObj to create an object that makes up links/cells of the Snake
  object. The size of a cell is a final field and there are also final fields for initial position 
  and initial velocity. The cell class contains a draw and getColor method that redraw the cell and
  return its color, respectively.
  
  Food.java: Food.java also extends GameObj to create a food that is placed at random (by 
  Board.java) on the board as a goal for the snake to eat. When the head of the snake collides
  with the current food object, the snake grows by one cell and continues its movement. Foods have 
  a color and a size field (size is final), and extend the constructor of a gameObj and can never
  have a velocity. They also override the GameObj draw method for drawing on the board.
  
  Game.java: Game.java is where the Swing things are done - JLabels are created and put into
  the JFrame of the board, and buttons are also created to do different things. Reset, Pause, and 
  Resume all do their respective functions in Board.java (Game.Java creates a Board object when run)
  and Instructions opens a new window with instructions for how to play the game. High scores is a
  button that when pressed gets the current high scores of the game from the save file and 
  displays them in descending order. Game.java contains a main method that ONLY creates a new game
  and runs it as Swing requires.
  
  GameObj.java: given object class that was modified for my game. It is an abstract class that 
  has fields of px, py, width, height, vx, vy, maxX, maxY for each GameObj. The constructor takes 
  these as arguments and creates a new GameObj with them. Each GameObj also contains getters 
  for all of its fields, setters for positions and velocities, and methods to deal with movement and
  object collisions. There is also an abstract draw method. GameObj objects can check if they 
  currently intersect another object, if they will intersect one in the next move/timestep, and if
  they hit the wall of the current board. The move method updates the px and py of the current 
  object based on current vx and vy of the object.
  
  Score.java: A score is an object that doesn't extend GameObj, and contains a points integer, a 
  String name, and an integer playNumber. WHen constructed the name is created based on the 
  playNumber of the object, and points are an input integer score. The object class also overrides
  comparable so that the scores can be sorted in ascending/descending order for usage in the 
  high score methods in Board.java and Game.java. There are also getters to return score, name, and
  playNumber of a specific Score. Scores are in a list of high scores in the board object.
  
  Snake.java: Snake is the class that is the actual object that moves around the screen in the game.
  It has fields for length, a list of Cells, a color, and a head. The snake constructor takes in 
  a head specified in the Board.java file. When grow is called, the length of the snake grows and
  a new cell is placed at a position behind the last cell of the snake (depends on current snake
  velocity), and the cell is added to the internal snake list. isFull() is the win condition, or 
  checks if the length of the snake is 10 to win the game. hitSelf checks if the snake's head
  collided with any of its body elements, there are getters for each of the fields of the snake, and
  the move method moves the snake. There's also a draw method that draws all of the cells in the
  snake. The move method is interesting because it only actually moves the head, and then all of
  the cells behind the head are updated to the position of the cell that came before it, 
  achieving the "snake" movement where it looks like each of the cells is following the head of
  the snake.
  
  WriteBoard.java: WriteBoard handles all of the FIle I/O for the project. It also catches all
  exceptions related to null files and IOExceptions and illegal arguments. The write board method
  takes in a board array from the game board and a string filepath to write the 2D array to. The
  2D array that is this board representation is written using an integer array separated by spaces
  and adding a line for every row in the 2D array. The filepath is final in Board.java so we don't
  need to worry about null or bad files (no other file path is needed, though a user could
  hypothetically modify Board.java to add their own). Write Scores does a similar thing except it
  takes in a list of scores from the board.java and places them in pairs separated by spaces and
  incremented by a new line each time. THe first integer in the pair is the playNumber (related to
  playername, so this is how name is saved), and the second is the actual score. The getBoard method
  gets the 2D array that the board uses as a representation of its state to reinstantiate the game
  by taking in a filePath as well as two final integers from Board.java for the
  width and height of the array to pull from the file. File exceptions are caught and the reader
  splits the string of the line by spaces to read each integer individually. THen it puts them in an
  array and returns the entire 2D integer array. Finally, getScores takes in a filePath and pulls
  the highScores from it, placing them in a list of scores again. As the Score.java constructor
  takes in a playNumber and a score, this is what getScores uses to create each new Score object to
  place in the final list to return. Lines are read until there are no more scores left, and the
  List<Score> is returned.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  I had a little trouble because I was originally using a 2D array to represent the board state when
  saving the game, but aws also using File I/O for saving that 2D to a file and reading it back to
  resume the game. I didn't realize this did not count for both concepts, so I had to rework my
  planned use of concepts. I decided to get credit for File I/O by storing high scores in a file
  instead and using I/O to update and return/display them. I still used file I/O to store the 2D 
  array for the game state/board for pausing and unpausing, but that was to get credit for 2D
  Arrays. Overall, I had to separate the use of File I/O and 2D arrays into two different uses.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  I think my game is designed pretty well. All methods do a single thing related to game state or a
  specific field, and functionality of the game is well separated. Also, I created a variety of 
  classes to separate functionality so that not everything is done in Board.java or Game.java.
  Instead, the classes/objects feature different methods that perform different functions as needed
  for only those objects, so I think my game modularity is pretty good. The private state, as 
  tested in my JUnit tests, is well encapsulated. The only modifications that can be made are those
  explicitly coded/designated in all of the methods in each of the classes. All fields are private
  and methods that are only called within a class are private as well. I might refactor some of the
  playNumber counting and state changes within Board.java to make some of the methods less 
  repetitive. I might also move some methods form Board.java into a separate class for movement, or
  a separate class for pause/resume (and create respective objects for those) to separate concerns
  even more, but I think my current modularity is decent and makes pretty good sense the way I have
  it now. 



========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  None, just JavaDocs for different methods (for collections and such) as well 
  as Piazza posts. Also used code provided in the Mushroom of Doom example. Also
  used notes from CIS110 in generating a random number.
