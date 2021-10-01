
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


/*
 * WriteBoard handles all of the FIle I/O for the project. It also catches
 * exceptions related to null files and IOExceptions and illegal arguments. Contains functionality 
 * to read and write representations of the board array as well as high scores.
 */
public class WriteBoard {
    
    // write the board to a file as a 2D integer array input from the board class
    public static void writeBoardToFile(int[][] boardArr, String filePath) {
        File file = Paths.get(filePath).toFile();
        BufferedWriter bw = null;

        FileWriter fw = null;
        try {
            fw = new FileWriter(file, false);
            bw = new BufferedWriter(fw);
            for (int i = 0; i < boardArr.length; i++) {
                for (int j = 0; j < boardArr[0].length; j++) {
                    String cell = boardArr[i][j] + " ";
                    bw.write(cell);
                }
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally { 
            try {
                bw.close();
            } catch (IOException eOne) {
                System.out.println("problem closing BufferedWriter");
            }
        }
    }
    
 // write the scores to a file as a pairs of values separated by spaces
    public static void writeScoresToFile(List<Score> scoreList, String filePath) {
        File file = Paths.get(filePath).toFile();
        BufferedWriter bw = null;

        FileWriter fw = null;
        try {
            fw = new FileWriter(file, false);
            bw = new BufferedWriter(fw);
            for (int i = 0; i < scoreList.size(); i++) {
                String name = "" + scoreList.get(i).getNum();
                String score = "" + scoreList.get(i).getScore();
                bw.write(name + " " + score);
                bw.newLine();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally { 
            try {
                bw.close();
            } catch (IOException eOne) {
                System.out.println("problem closing BufferedWriter");
            }
        }
    }
    
    
    // pull an integer array board from a file to be used to reinstantiate the board state later
    public static int[][] getBoard(String filePath, int height, int width) {
        int[][] savedBoard = new int[height][width];
        // check null string
        BufferedReader r;
        if (filePath == null) {
            System.out.println("no file specified");
            throw new IllegalArgumentException();
        }
        // created BR
        try {
            r = new BufferedReader(new FileReader(filePath)); 
            // check file exists
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < savedBoard.length; i++) {
            try {
                String line = r.readLine();
                if (line == null) {
                    try {
                        r.close();
                        // make sure can close reader
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                String[] split = line.split(" ");
                for (int j = 0; j < savedBoard[0].length; j++) {
                    savedBoard[i][j] = Integer.parseInt(split[j]);
                }
            } catch (IOException e) {           
                throw new NoSuchElementException();
            }
        }
        return savedBoard;
    }
    
    // pull scores from the file and place them in a list to be displayed by high score window
    public static List<Score> getScores(String filePath) {
        List<Score> savedScores = new ArrayList<Score>();
        // check null string
        BufferedReader r;
        if (filePath == null) {
            System.out.println("no file specified");
            throw new IllegalArgumentException();
        }
        // created BR
        try {
            r = new BufferedReader(new FileReader(filePath)); 
            // check file exists
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            throw new IllegalArgumentException();
        }
        try {
            String line = r.readLine();
            while (line != null) {
                String[] split = line.split(" ");
                Score s = new Score(Integer.parseInt(split[1]), Integer.parseInt(split[0]));
                savedScores.add(s);
                line = r.readLine();
            }
            if (line == null) {
                try {
                    r.close();
                    // make sure can close reader
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
        } catch (IOException e) {  
            throw new NoSuchElementException();
        } finally {
            try {
                r.close();
            } catch (IOException eOne) {
                System.out.println("problem closing BufferedReader");
            }
        }

        return savedScores;
    }
    
    // testing board in main
    /*public static void main(String[] args) {
        int[][] test = {{1, 2, 3}, {1, 2, 3}, {1, 2, 3}};
        
        writeBoardToFile(test, "files/testBoard.txt");
        int[][] saved = getBoard("files/testBoard.txt", 3, 3);
        for (int i = 0; i < saved.length; i++) {
            for (int j = 0; j < saved[0].length; j++) {
                System.out.print(saved[i][j] + " ");
            }
            System.out.println();
        }
        
    }*/
    
}
