
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/*
 * Snake is the class that is the actual object that moves around the screen in the game.
 * It has fields for length, a list of Cells, a color, and a head. The snake constructor takes in 
 * a head specified in the Board.java file. It also features methods to draw, move, and grow the 
 * snake so that it functions as a snake should in the game.
 */
public class Snake {
    
    private int length = 1;
    private List<Cell> snakeList; 
    private Color color;
    private Cell head;
    
    
    public Snake(Cell head) {
        this.head = head;
        this.snakeList = new ArrayList<Cell>();
        this.snakeList.add(head);
        this.color = head.getColor();
    }
    
    // make snake longer by adding cell
    public void grow() {
        this.length++;
        int newX = 0;
        int newY = 0;
        Cell last = snakeList.get(snakeList.size() - 1);
        if (last.getVx() == 0 && last.getVy() < 0) {
            newX = last.getPx();
            newY = last.getPy() + 20;
        } else if (last.getVx() == 0 && last.getVy() > 0) { 
            newX = last.getPx();
            newY = last.getPy() - 20;
        } else if (last.getVx() < 0 && last.getVy() == 0) {
            newX = last.getPx() + 20;
            newY = last.getPy();
        } else if (last.getVx() > 0 && last.getVy() == 0) {
            newX = last.getPx() - 20;
            newY = last.getPy();
        }
        

        snakeList.add(new Cell(last.getVx(), last.getVy(), newX, newY, Board.BOARD_WIDTH, 
                Board.BOARD_HEIGHT, this.color));
    }
    
    // win condition, snake is 10 cells long
    public boolean isFull() {
        return this.length == 10;
    }
    
    // checks if snake head hits itself
    public boolean hitSelf() { 
        for (Cell c : this.snakeList) {
            if (c != this.head && this.head.intersects(c)) {
                return true;
            }
        }
        return false;
    }
    
    public int getLength() {
        return this.length;
    }
    
    public Cell getHead() {
        return this.head;
    }
    
    
    public List<Cell> getCells() {
        return this.snakeList;
    }
    
    
    // moves the snake by updating positions of tail cells and moving head
    public void move() {
        // move each cell that's not the head to the position of the cell before it
        for (int i = this.length - 1; i >= 0; i--) {
            Cell c = this.snakeList.get(i);
            if (c != this.head) {
                int prevIndex = this.snakeList.indexOf(c) - 1;
                Cell prevCell = this.snakeList.get(prevIndex);
                c.setPx(prevCell.getPx());
                c.setPy(prevCell.getPy());
                c.setVx(prevCell.getVx());
                c.setVy(prevCell.getVy());
            }
        }
        // move only the head
        this.head.move();
    }
    
    
    public void draw(Graphics g) {
        g.setColor(this.color);
        for (Cell c : this.snakeList) {
            g.fillRect(c.getPx(), c.getPy(), c.getWidth(), c.getHeight());
        }
    }
}
