import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

/** 
 *  Testing for all of the game classes, separated by comment lines
 */
public class GameTest {

    private static JLabel status = new JLabel("Running...");
    /*** WriteBoard.java Testing ****************************************************************/

    @Test
    public void testWriteGetBoard() {
        int[][] arr = {{1, 2, 3}, {1, 2, 3}, {1, 2, 3}};
        WriteBoard.writeBoardToFile(arr, "files/testBoard.txt");
        int[][] result = WriteBoard.getBoard("files/testBoard.txt", 3, 3);
        assertArrayEquals(result, arr);
    }
    
    @Test
    public void testWriteGetScores() {
        List<Score> scrs = new ArrayList<Score>();
        Score playerOne = new Score(5, 1);
        Score playerTwo = new Score(8, 2);
        scrs.add(playerOne);
        scrs.add(playerTwo);
        WriteBoard.writeScoresToFile(scrs, "files/testBoard.txt");
        List<Score> result = WriteBoard.getScores("files/testBoard.txt");
        assertEquals(result, scrs);
        assertTrue(result.contains(playerOne));
        assertTrue(result.contains(playerTwo));
    }
    
    @Test
    public void testNullFile() {
        assertThrows(IllegalArgumentException.class, () -> {
            WriteBoard.getBoard(null, 2, 2);
        }); 
        assertThrows(IllegalArgumentException.class, () -> {
            WriteBoard.getScores(null);
        });  
    }
    
    @Test
    public void testBadFile() {
        assertThrows(IllegalArgumentException.class, () -> {
            WriteBoard.getBoard("aaa", 2, 2);
        }); 
        assertThrows(IllegalArgumentException.class, () -> {
            WriteBoard.getScores("aaa");
        });  
    }
    
    /*** Food.java Testing ****************************************************************/
    //NONE, JUST EXTENDS GAMEOBJ, WAS TOLD IT IS NOT EXPECTED TO TEST THIS
    
    
    /*** Score.java Testing ****************************************************************/
    @Test
    public void testEquals() {
        Score sOne = new Score(5, 1);
        Score sTwo = new Score(5, 1);
        assertTrue(sOne.equals(sTwo));
    }
    
    @Test
    public void testHash() {
        Score sOne = new Score(5, 1);
        assertEquals(1, sOne.hashCode());
    }
    
    @Test
    public void testGetScore() {
        Score sOne = new Score(5, 1);
        assertEquals(5, sOne.getScore());
        int score = sOne.getScore();
        score = 6;
        assertEquals(5, sOne.getScore());
    }
    
    @Test
    public void testName() {
        Score sOne = new Score(5, 1);
        assertEquals("Player 1", sOne.getName());
        String name = sOne.getName();
        name = "bob";
        assertEquals("Player 1", sOne.getName());
    }
    
    @Test
    public void testNum() {
        Score sOne = new Score(5, 1);
        assertEquals(1, sOne.getNum());
        int num = sOne.getNum();
        num = 2;
        assertEquals(1, sOne.getNum());
    }
    
    @Test
    public void testCompareTo() {
        Score sOne = new Score(5, 1);
        Score sTwo = new Score(7, 2);      
        assertEquals(-2, sOne.compareTo(sTwo));
    }
    
    /*** Cell.java Testing ****************************************************************/
    @Test
    public void testColor() {
        Cell c = new Cell(0, 0, 0, 0, 0, 0, Color.BLACK);     
        assertEquals(Color.BLACK, c.getColor());
    }
    
    @Test
    public void testObjGetters() {
        Cell c = new Cell(1, 2, 3, 4, 5, 6, Color.BLACK);     
        assertEquals(1, c.getVx());
        assertEquals(2, c.getVy());
        assertEquals(3, c.getPx());
        assertEquals(4, c.getPy());
        assertEquals(20, c.getWidth());
        assertEquals(20, c.getHeight());
    }
    
    /*** Snake.java Testing ****************************************************************/
    @Test
    public void testGrow() {
        Cell c = new Cell(1, 0, 40, 40, 5, 6, Color.BLACK);
        Snake s = new Snake(c);
        s.grow();
        List<Cell> cells = s.getCells();
        Cell cOne = new Cell(1, 0, 20, 40, 5, 6, Color.BLACK);
        assertEquals(2, s.getLength());
        assertEquals(cOne.getPx(), cells.get(1).getPx());
        assertEquals(cOne.getPy(), cells.get(1).getPy());
        assertEquals(cOne.getVx(), cells.get(1).getVx());
        assertEquals(cOne.getVy(), cells.get(1).getVy());
    }
    
    @Test
    public void testIsFullFalse() {
        Cell c = new Cell(1, 0, 40, 40, 5, 6, Color.BLACK);
        Snake s = new Snake(c);
        s.grow();
        assertFalse(s.isFull());
        
    }
    
    @Test
    public void testIsFullTrue() {
        Cell c = new Cell(1, 0, 40, 40, 5, 6, Color.BLACK);
        Snake s = new Snake(c);
        s.grow();
        s.grow();
        s.grow();
        s.grow();
        s.grow();
        s.grow();
        s.grow();
        s.grow();
        s.grow();
        assertTrue(s.isFull());
    }
    
    @Test
    public void testHitSelfTrue() {
        Cell c = new Cell(1, 0, 40, 40, 5, 6, Color.BLACK);
        Snake s = new Snake(c);
        s.grow();
        s.grow();
        s.getCells().get(2).setPx(40);
        assertTrue(s.hitSelf());
    }
    
    @Test
    public void testHitSelfFalse() {
        Cell c = new Cell(1, 0, 40, 40, 5, 6, Color.BLACK);
        Snake s = new Snake(c);
        assertFalse(s.hitSelf());
    }
    
    @Test
    public void testGetLengthMore() {
        Cell c = new Cell(1, 0, 40, 40, 5, 6, Color.BLACK);
        Snake s = new Snake(c);
        s.grow();
        s.grow();
        s.grow();
        s.grow();
        s.grow();
        s.grow();
        s.grow();
        s.grow();
        s.grow();
        assertTrue(s.isFull());
        assertEquals(10, s.getLength());
        int l = s.getLength();
        l = 6;
        assertEquals(10, s.getLength());

    }
    
    @Test
    public void testHeadOnly() {
        Cell c = new Cell(1, 0, 40, 40, 5, 6, Color.BLACK);
        Snake s = new Snake(c);
        assertFalse(s.isFull());
        assertEquals(1, s.getLength());
        int l = s.getLength();
        l = 6;
        assertEquals(1, s.getLength());

    }
    
    @Test
    public void testGetHead() {
        Cell c = new Cell(1, 0, 40, 40, 5, 6, Color.BLACK);
        Snake s = new Snake(c);
        Cell h = s.getHead();

        assertEquals(h.getPx(), c.getPx());
        assertEquals(h.getPy(), c.getPy());
        assertEquals(h.getVx(), c.getVx());
        assertEquals(h.getVy(), c.getVy());
        h = new Cell(0, 0, 0, 0, 0, 0, Color.BLACK);
        assertEquals(s.getHead().getPx(), c.getPx());
        assertEquals(s.getHead().getPy(), c.getPy());
        assertEquals(s.getHead().getVx(), c.getVx());
        assertEquals(s.getHead().getVy(), c.getVy());
    }
    
    @Test
    public void testGetCellsOne() {
        Cell c = new Cell(1, 0, 40, 40, 5, 6, Color.BLACK);
        Snake s = new Snake(c);
        List<Cell> cells = s.getCells();

        assertEquals(1, cells.size());
        assertTrue(cells.contains(c));
        cells = new ArrayList<Cell>();
        assertEquals(1, s.getCells().size());
        assertTrue(s.getCells().contains(c));
    }
    
    @Test
    public void testGetCellsMore() {
        Cell c = new Cell(1, 0, 40, 40, 5, 6, Color.BLACK);
        Snake s = new Snake(c);
        s.grow();
        List<Cell> cells = s.getCells();

        assertEquals(2, cells.size());
        assertTrue(cells.contains(c));
        cells = new ArrayList<Cell>();
        assertEquals(2, s.getCells().size());
        assertTrue(s.getCells().contains(c));
    }
    
    @Test
    public void testMoveMultiple() {
        Cell c = new Cell(20, 0, 40, 40, 600, 400, Color.BLACK);
        Snake s = new Snake(c);
        s.grow();
        s.move();
        List<Cell> cells = s.getCells();
        assertEquals(60, cells.get(0).getPx());
        assertEquals(40, cells.get(0).getPy());
        assertEquals(40, cells.get(1).getPx());
        assertEquals(40, cells.get(1).getPy());
        
    }
    
    @Test
    public void testMoveOnlyHead() {
        Cell c = new Cell(20, 0, 40, 40, 600, 400, Color.BLACK);
        Snake s = new Snake(c);
        s.move();
        assertEquals(60, s.getHead().getPx());
        assertEquals(40, s.getHead().getPy());
    }
    
    
    /*** Board.java Testing ****************************************************************/
    @Test
    public void testReset() {
        Board board = new Board(status);
        board.reset();
        Score sOne = new Score(1, 1);
        board.addScore(sOne);
        board.getSnake().getHead().setVx(20);
        board.tick();
        board.reset();
        Snake s = board.getSnake();
        List<Score> scores = board.getScoreList();
        assertEquals(40, s.getHead().getPx());
        assertEquals(40, s.getHead().getPy());
        assertTrue(scores.contains(sOne));
        assertEquals(2, board.getPlayNum());
    }
    
    @Test
    public void testGetPlayNumFirst() {
        Board board = new Board(status);
        board.reset();
        int num = board.getPlayNum();
        num = 5;
        assertEquals(1, board.getPlayNum());
        
    }
    
    @Test
    public void testGetPlayNumMore() {
        Board board = new Board(status);
        board.reset();
        board.reset();
        board.reset();
        int num = board.getPlayNum();
        num = 5;
        assertEquals(3, board.getPlayNum());
        
    }
    
    @Test
    public void testGetSnakeInitial() {
        Board board = new Board(status);
        board.reset();
        Snake s = board.getSnake();
        Cell head = board.getSnake().getHead();
        assertEquals(0, head.getVx());
        assertEquals(0, head.getVy());
        assertEquals(40, head.getPx());
        assertEquals(40, head.getPy());

    }
    
    @Test
    public void testGetSnakeEncap() {
        Board board = new Board(status);
        board.reset();
        Snake s = board.getSnake();
        Cell c = new Cell(0, 0, 0, 0, 0, 0, Color.BLACK);
        s = new Snake(c);
        Cell head = board.getSnake().getHead();
        assertEquals(0, head.getVx());
        assertEquals(0, head.getVy());
        assertEquals(40, head.getPx());
        assertEquals(40, head.getPy());
    }
    
    @Test
    public void testGetScoreListInitial() {
        Board board = new Board(status);
        board.reset();
        List<Score> scrs = board.getScoreList();
        assertTrue(scrs.isEmpty());
    }
    
    @Test
    public void testGetScoreListAdded() {
        Board board = new Board(status);
        board.reset();
        board.addScore(new Score(100, 1));
        board.addScore(new Score(300, 2));
        board.addScore(new Score(200, 3));
        board.addScore(new Score(700, 4));

        List<Score> scrs = board.getScoreList();
        assertEquals(4, scrs.size());
        Score s = new Score(100, 1);
        assertEquals(s, scrs.get(0));
        assertEquals(new Score(700, 4), scrs.get(3));
    }
    
    @Test
    public void testGetScoreListEncap() {
        Board board = new Board(status);
        board.reset();
        board.addScore(new Score(100, 1));
        board.addScore(new Score(300, 2));
        board.addScore(new Score(200, 3));
        board.addScore(new Score(700, 4));

        List<Score> scrs = board.getScoreList();
        scrs = new ArrayList<Score>();
        assertTrue(scrs.isEmpty());
        
        Score s = new Score(100, 1);
        assertEquals(s, board.getScoreList().get(0));
        assertEquals(new Score(700, 4), board.getScoreList().get(3));
    }
    
    @Test
    public void testGetFood() {
        Board board = new Board(status);
        board.reset();
        board.placeFood(50, 50);
        Food f = board.getFood();
        assertEquals(50, f.getPx());
        assertEquals(50, f.getPy());
        assertEquals(0, f.getVx());
        assertEquals(0, f.getVy());

    }
    
    @Test
    public void testGetFoodEncap() {
        Board board = new Board(status);
        board.reset();
        board.placeFood(50, 50);
        Food f = board.getFood();
        f = new Food(0, 0, 0, 0, Color.BLACK);
        assertEquals(50, board.getFood().getPx());
        assertEquals(50, board.getFood().getPy());
        assertEquals(0, board.getFood().getVx());
        assertEquals(0, board.getFood().getVy());
    }
    
    @Test
    public void testNewFood() {
        Board board = new Board(status);
        board.reset();
        board.newFood();
        Food f = board.getFood();
        assertEquals(20, f.getHeight());
        assertEquals(20, f.getWidth());
        assertEquals(0, f.getVx());
        assertEquals(0, f.getVy());
    }
    
    @Test
    public void testNewFoodEncap() {
        Board board = new Board(status);
        board.reset();
        board.newFood();
        Food f = board.getFood();
        f = new Food(0, 0, 0, 0, Color.BLACK);
        assertEquals(20, board.getFood().getHeight());
        assertEquals(20, board.getFood().getWidth());
        assertEquals(0, board.getFood().getVx());
        assertEquals(0, board.getFood().getVy());
    }
    
    @Test
    public void testPlaceFood() {
        Board board = new Board(status);
        board.reset();
        board.placeFood(100, 100);
        Food f = board.getFood();
        assertEquals(20, f.getHeight());
        assertEquals(20, f.getWidth());
        assertEquals(100, f.getPx());
        assertEquals(100, f.getPy());
        assertEquals(0, f.getVx());
        assertEquals(0, f.getVy());
    }
    
    @Test
    public void testPlaceFoodEncap() {
        Board board = new Board(status);
        board.reset();
        board.placeFood(100, 100);
        Food f = board.getFood();
        f = new Food(0, 0, 0, 0, Color.BLACK);
        assertEquals(20, board.getFood().getHeight());
        assertEquals(20, board.getFood().getWidth());
        assertEquals(100, board.getFood().getPx());
        assertEquals(100, board.getFood().getPy());
        assertEquals(0, board.getFood().getVx());
        assertEquals(0, board.getFood().getVy());
    }
    
    @Test
    public void testAddScoreNull() {
        Board board = new Board(status);
        board.reset();
        board.addScore(null);
        List<Score> scrs = board.getScoreList();
        assertTrue(scrs.isEmpty());
    }
    
    @Test
    public void testAddScoreOne() {
        Board board = new Board(status);
        board.reset();
        board.addScore(new Score(100, 1));
        List<Score> scrs = board.getScoreList();
        assertEquals(1, scrs.size());
        Score s = new Score(100, 1);
        assertEquals(s, scrs.get(0));
    }
    
    @Test
    public void testAddScoreMult() {
        Board board = new Board(status);
        board.reset();
        board.addScore(new Score(100, 1));
        board.addScore(new Score(300, 2));
        board.addScore(new Score(200, 3));
        board.addScore(new Score(700, 4));

        List<Score> scrs = board.getScoreList();
        assertEquals(4, scrs.size());
        Score s = new Score(100, 1);
        assertEquals(s, scrs.get(0));
        assertEquals(new Score(700, 4), scrs.get(3));
        
    }
    
    @Test
    public void testAddScoreMultRepeated() {
        Board board = new Board(status);
        board.reset();
        board.addScore(new Score(300, 1));
        board.addScore(new Score(300, 2));
        board.addScore(new Score(300, 3));
        board.addScore(new Score(300, 4));

        List<Score> scrs = board.getScoreList();
        assertEquals(4, scrs.size());
        assertEquals(300, scrs.get(0).getScore());
        assertEquals(1, scrs.get(0).getNum());
        assertEquals(300, scrs.get(3).getScore());
        assertEquals(4, scrs.get(3).getNum());
    }
    
    @Test
    public void testPause() {
        Board board = new Board(status);
        board.reset();
        board.addScore(new Score(300, 1));
        board.pause();
        List<Score> scores = WriteBoard.getScores("files/Score_Save.txt");
        assertTrue(scores.contains(new Score(300, 1)));
        int[][] arr = WriteBoard.getBoard("files/Board_Save.txt", 20, 30);
        assertEquals(0, arr[2][2]); // snake starts as 0 when saved unless moved
    }
    
    @Test
    public void testSetSnake() {
        Board board = new Board(status);
        board.reset();
        board.setSnake(new Snake(new Cell(20, Cell.INIT_VEL_Y, 0, 
                Cell.INIT_POS_Y, Board.BOARD_WIDTH, Board.BOARD_HEIGHT, 
                Color.BLACK)));
        Snake s = board.getSnake();
        assertEquals(20, s.getHead().getVx());
        assertEquals(0, s.getHead().getPx());
        assertEquals(0, s.getHead().getVy());
        assertEquals(40, s.getHead().getPy());
        
    }
    
    @Test
    public void testPauseMoved() {
        Board board = new Board(status);
        board.reset();
        board.setSnake(new Snake(new Cell(20, Cell.INIT_VEL_Y, Cell.INIT_POS_X, 
                Cell.INIT_POS_Y, Board.BOARD_WIDTH, Board.BOARD_HEIGHT, 
                Color.BLACK)));
        board.placeFood(20, 20);
        board.tick();
        board.addScore(new Score(300, 1));
        board.pause();
        List<Score> scores = WriteBoard.getScores("files/Score_Save.txt");
        assertTrue(scores.contains(new Score(300, 1)));
        int[][] arr = WriteBoard.getBoard("files/Board_Save.txt", 20, 30);
        assertEquals(4, arr[2][3]); // snake moving right is represented as a 4.
        assertEquals(2, arr[1][1]);

    }
    
    @Test
    public void testTickHitWall() {
        Board board = new Board(status);
        board.reset();
        board.setSnake(new Snake(new Cell(-20, Cell.INIT_VEL_Y, Cell.INIT_POS_X, 
                Cell.INIT_POS_Y, Board.BOARD_WIDTH, Board.BOARD_HEIGHT, 
                Color.BLACK)));
        board.tick();
        board.tick();
        assertTrue(board.getScoreList().contains(new Score(1, 1)));
        assertEquals(0, board.getSnake().getHead().getPx());
        assertEquals(40, board.getSnake().getHead().getPy());

        // tick does nothing bc no longer playing
        board.tick();
        assertEquals(0, board.getSnake().getHead().getPx());
        assertEquals(40, board.getSnake().getHead().getPy());
    }
    
    
    @Test
    public void testTickIsFull() {
        Board board = new Board(status);
        board.reset();
        board.setSnake(new Snake(new Cell(20, Cell.INIT_VEL_Y, Cell.INIT_POS_X, 
                Cell.INIT_POS_Y, Board.BOARD_WIDTH, Board.BOARD_HEIGHT, 
                Color.BLACK)));
        for (int i = 0; i < 11; i++) {
            board.tick();
            Snake s = board.getSnake();
            s.grow();
            board.setSnake(s);
        }
        
        assertTrue(board.getScoreList().contains(new Score(10, 1)));
        assertEquals(240, board.getSnake().getHead().getPx());
        assertEquals(40, board.getSnake().getHead().getPy());

        // tick does nothing bc no longer playing bc won game
        board.tick();
        assertEquals(240, board.getSnake().getHead().getPx());
        assertEquals(40, board.getSnake().getHead().getPy());

    }
    
    @Test
    public void testTickEat() {
        Board board = new Board(status);
        board.reset();
        board.setSnake(new Snake(new Cell(20, Cell.INIT_VEL_Y, Cell.INIT_POS_X, 
                Cell.INIT_POS_Y, Board.BOARD_WIDTH, Board.BOARD_HEIGHT, 
                Color.BLACK)));
        board.placeFood(60, 40);
        board.tick();
        
        assertEquals(60, board.getSnake().getHead().getPx());
        assertEquals(40, board.getSnake().getHead().getPy());
        assertEquals(2, board.getSnake().getLength());
        

    }
    
    @Test
    public void testResume() {
        Board board = new Board(status);
        board.reset();
        board.placeFood(20, 20);
        board.setSnake(new Snake(new Cell(Cell.INIT_VEL_X, Cell.INIT_VEL_Y, Cell.INIT_POS_X, 
                Cell.INIT_POS_Y, Board.BOARD_WIDTH, Board.BOARD_HEIGHT, 
                Color.BLACK)));
        board.addScore(new Score(300, 1));
        board.pause();
        board.resume();
        
        assertEquals(40, board.getSnake().getHead().getPx());
        assertEquals(40, board.getSnake().getHead().getPy());
        int[][] arr = WriteBoard.getBoard("files/Board_Save.txt", 20, 30);
        assertEquals(2, arr[1][1]);
        assertTrue(WriteBoard.getScores("files/Score_Save.txt").contains(new Score(300, 1)));
        
    }
    
    @Test
    public void testResumeMoved() {
        Board board = new Board(status);
        board.reset();
        board.placeFood(20, 20);
        board.setSnake(new Snake(new Cell(20, Cell.INIT_VEL_Y, Cell.INIT_POS_X, 
                Cell.INIT_POS_Y, Board.BOARD_WIDTH, Board.BOARD_HEIGHT, 
                Color.BLACK)));
        board.tick();
        board.addScore(new Score(300, 1));
        board.pause();
        board.resume();
        
        assertEquals(60, board.getSnake().getHead().getPx());
        assertEquals(40, board.getSnake().getHead().getPy());
        int[][] arr = WriteBoard.getBoard("files/Board_Save.txt", 20, 30);
        assertEquals(2, arr[1][1]);
        List<Score> scrs = WriteBoard.getScores("files/Score_Save.txt");
        assertTrue(scrs.contains(new Score(300, 1)));

    }
    
    
    /*** Game.java Testing ****************************************************************/
    // NO TESTING DONE FOR Game.java BECAUSE IT IS ALL GUI
    

}
