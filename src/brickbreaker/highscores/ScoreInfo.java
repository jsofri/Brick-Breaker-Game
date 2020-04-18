package brickbreaker.highscores;


import java.io.Serializable;


/**
 * Each instance of this type consists data about high score - name and score.
 */
public class ScoreInfo implements Comparable<ScoreInfo>, Serializable {


    /**
     * fields of object.
     */
    private String name;
    private int    score;


    /**
     * constructor.
     *
     * @param name String, the name of the score maker
     * @param score int that represent the score
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * getter.
     *
     * @return String - name of highscore maker
     */
    public String getName() {
        return this.name;
    }

    /**
     * getter.
     *
     * @return int - value of score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * method compare ScoreInfo using the value of score.
     *
     * @param other ScoreInfo object to compare
     * @return int - according to compareTo logic
     */
    public int compareTo(ScoreInfo other) {
        return other.getScore() - this.getScore();
    }
}
