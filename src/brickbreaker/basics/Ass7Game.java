package brickbreaker.basics;

import biuoop.GUI;
import biuoop.KeyboardSensor;
import brickbreaker.animation.AnimationRunner;
import brickbreaker.highscores.HighScoresTable;
import brickbreaker.io.LevelData;
import brickbreaker.io.LevelSetsFileReader;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * This class is the main of the game of assignment 3.
 */
public class Ass7Game {

    /**
     * name of the File.
     */
    public static final String FILE_NAME = "highscores";


    /**
     * sizes of gui window.
     */
    public static final int    WIDTH = 800;
    public static final int    HEIGHT = 600;
    public static final int    SIZE_OF_TABLE = 5;
    public static final String DEFAULT = "resources/level_sets.txt";
    public static final Color  TEXT_COLOR = new Color(199, 250, 252);


    /**
     * fields of class.
     */
    private GUI                    gui;
    private GameFlow               gameFlow;
    private AnimationRunner        animationRunner;
    private KeyboardSensor         keyboardSensor;
    private List<LevelData>        levelSets;
    private HighScoresTable        highScoresTable;
    private File                   fileName;


    /**
     * constructor.
     */
    public Ass7Game() {
        this.gui = new GUI("Arkanoid - Yehonatan Sofri", WIDTH, HEIGHT);
        this.setFile();
        this.animationRunner = new AnimationRunner(this.gui);
        this.keyboardSensor = gui.getKeyboardSensor();
        this.gameFlow = new GameFlow(this.animationRunner, this.keyboardSensor,
                                     this.gui, this.highScoresTable,
                                     this.fileName);
    }

    /**
     * this method create a GameLevel instance, initialize and run it.
     */
    public void startAGame() {
        this.gameFlow.gameCycleLoop(this.levelSets);
        this.gui.close();
    }

    /**
     * method create a list of level information objects.
     *
     * @param args String[] arguments from user - levels to enter the list
     */
    private void setLevelsList(String[] args) {
        if (args.length == 0) {
            this.levelSets = LevelSetsFileReader.readLevelSetsFile(DEFAULT);
        } else {
            this.levelSets = LevelSetsFileReader.readLevelSetsFile(args[0]);
        }
    }

    /**
     * method set the file field of class.
     */
    private void setFile() {
            this.fileName = new File(FILE_NAME);
            this.highScoresTable = new HighScoresTable(SIZE_OF_TABLE);

            try {
                this.highScoresTable.load(this.fileName);
            } catch (IOException loadException) {
                try {
                    this.highScoresTable.save(this.fileName);
                } catch (IOException saveException) {
                    System.out.println(saveException.toString());
                }
            }
    }

    /**
     * main method.
     *
     * @param args arguments from user - not used
     */
    public static void main(String[] args) {
        Ass7Game game = new Ass7Game();
        game.setLevelsList(args);
        game.startAGame();
    }
}
