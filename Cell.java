
import java.awt.*;

/*
 * A Cell is an object that extends GameObj, and is used to make up the links of the snake in
 * Board.java. THe Cell inherits all of the methods of a GameObj, and overrides draw to be 
 * drawn when the game is run, and also features a getColor() method to return the color of the
 * cell to be used to create the color of the snake when first created
 */
public class Cell extends GameObj {
    public static final int SIZE = 20;
    public static final int INIT_POS_X = 40;
    public static final int INIT_POS_Y = 40;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;

    private Color color;
    
    

    /**
    * Note that, because we don't need to do anything special when constructing a Square, we simply
    * use the superclass constructor called with the correct parameters.
    */
    public Cell(int xVel, int yVel, int xPos, int yPos, int boardW, int boardH, Color color) {
        super(xVel, yVel, xPos, yPos, SIZE, SIZE, boardW, boardH);
        this.color = color;
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }

    public Color getColor() {
        return this.color;
    }

}
