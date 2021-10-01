

/*
 * A score is an object that doesn't extend GameObj, and contains a points integer, a 
 * String name, and an integer playNumber. When constructed the name is created based on the 
 * playNumber of the object, and points are an input integer score. The object class also implements
 * comparable so that the scores can be sorted in ascending/descending order for usage in the 
 * high score methods in Board.java and Game.java. There are also getters to return score, name, and
 * playNumber of a specific Score. Scores are in a list of high scores in the board object.
 */
public class Score implements Comparable<Score> {
    // make a new score object to store high scores
    private int points;
    private String name;
    private int playNumber;
    
    public Score(int score, int playNum) {
        this.points = score;
        this.playNumber = playNum;
        this.name = "Player " + playNum;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        
        Score score = (Score) obj;
        
        return (score.name.equals(this.name) && score.points == this.points && 
                score.playNumber == this.playNumber);
    }
    
    @Override
    public int hashCode() {
        return this.playNumber;
    }
    
    public int getScore() {
        return this.points;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getNum() {
        return this.playNumber;
    }
    
    @Override
    public int compareTo(Score s) {
        String n1 = "" + this.points;
        String n2 = "" + s.getScore();
        return n1.compareTo(n2);
    }
    
    
}
