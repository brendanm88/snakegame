import java.awt.*;
/*
 * Food.java also extends GameObj to create a food that is placed at random (by 
 * Board.java) on the board as a goal for the snake to eat. When the head of the snake collides
 * with the current food object, the snake grows by one cell and continues its movement. Foods have 
 * a color and a size field (size is final), and extend the constructor of a gameObj and can never
 * have a velocity. They also override the GameObj draw method for drawing on the board.
 */

public class Food extends GameObj {
    public static final int SIZE = 20;

    private Color color;

    public Food(int boardWidth, int boardHeight, int initX, int initY, Color color) {
        super(0, 0, initX, initY, SIZE, SIZE, boardWidth, boardHeight);

        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }
}
