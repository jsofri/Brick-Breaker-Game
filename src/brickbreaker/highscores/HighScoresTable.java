package brickbreaker.highscores;


import brickbreaker.basics.Ass7Game;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;



/**
 * this class include I/O methods and holds data about in-game highscores.
 */
public class HighScoresTable implements Serializable {


    /**
     * the list and the maximum size of the object.
     */
    private List<ScoreInfo> scoreInfoList;
    private int             size;


    /**
     * constructor - create an empty high-scores table with the specified size.
     * The size means that the table holds up to size top scores.
     *
     * @param size int, maximum size of the list
     */
    public HighScoresTable(int size) {
        this.scoreInfoList = new ArrayList<>();
        this.size = size;
    }

    /**
     * add a high-score to the list.
     *
     * @param score ScoreInfo object to add to the object's list.
     */
    public void add(ScoreInfo score) {
        int rank = this.getRank(score.getScore());

        if (rank <= this.size) {
            if (this.scoreInfoList.size() == this.size) {

                //remove the smallest high score from the list
                this.getHighScores().remove(this.size - 1);
            }

            this.scoreInfoList.add(score);
        }
    }

    /**
     * methos return table's size.
     *
     * @return int - size of table
     */
    public int size() {
        return this.size;
    }

    /**
     * method return a sorted list in descending scores order.
     *
     * @return List of ScoreInfo
     */
    public List<ScoreInfo> getHighScores() {
        Collections.sort(this.scoreInfoList);

        return this.scoreInfoList;
    }

    /**
     * method check the rank of the input score in relation to the score list.
     *
     * @param score int, score of a player
     * @return int, where the input score rank will be on the list if added.
     */
    public int getRank(int score) {
        List<ScoreInfo> list = this.getHighScores();

        for (ScoreInfo node : list) {
            if (score > node.getScore()) {
                return this.scoreInfoList.indexOf(node) + 1;
            }
        }

        return list.size() + 1;
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.scoreInfoList.clear();
    }

    /**
     * load the table data of the object from a file, using deserialization.
     *
     * @param fileName File object to load the data from
     * @throws IOException might come from problems with loading from the file
     */
    public void load(File fileName) throws IOException {
        try {
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);

            this.clear();
            this.scoreInfoList = (ArrayList<ScoreInfo>) in.readObject();
            in.close();
            file.close();
        } catch (ClassNotFoundException ex) {
            if (fileName.getName().equals(Ass7Game.FILE_NAME)) {
                   this.save(fileName);
                   this.load(fileName);
            } else {
                System.out.println(ex.toString());
            }
        }
    }

    /**
     * save the table data of the object in a file, using serialization.
     *
     * @param fileName File object to save the data into
     * @throws IOException might come from problems with write to a file
     */
    public void save(File fileName) throws IOException {

        //false for appending - we want to overwrite
        FileOutputStream   file = new FileOutputStream(fileName, false);
        ObjectOutputStream out = new ObjectOutputStream(file);

        out.writeObject(this.getHighScores());
        out.close();
        file.close();
    }

    /**
     * Read a table from file and return a matching HighScoreTable object.
     * might return an empty table if something's wrong with loading it.
     *
     * @param fileName File object to load the data from
     * @return a HighScoreTable object that contain the data in the file.
     */
    public static HighScoresTable loadFromFile(File fileName) {
        HighScoresTable highScoresTable
                = new HighScoresTable(Ass7Game.SIZE_OF_TABLE);

        try {
            highScoresTable.load(fileName);
            highScoresTable.resetSize();
        } catch (IOException ioException) {
            System.out.println(ioException.toString());
        }

        return highScoresTable;
    }

    /**
     * a helper that sets the capacity of the list to be it's current size.
     */
    private void resetSize() {
        if (this.scoreInfoList.size() > 0) {
            this.size = this.scoreInfoList.size();
        }
    }
}
