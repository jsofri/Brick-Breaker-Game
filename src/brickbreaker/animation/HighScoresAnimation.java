package brickbreaker.animation;


import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import brickbreaker.basics.Ass7Game;
import brickbreaker.highscores.ScoreInfo;
import brickbreaker.highscores.HighScoresTable;
import brickbreaker.sprites.MenuBackground;
import brickbreaker.sprites.Sprite;
import java.util.List;


/**
 * this class print a screen with highscores on it.
 */
public class HighScoresAnimation implements Animation {


    /**
     * details about printing a sentence on a DrawSurface object.
     */
    static final int    START_OF_X = 213;
    static final int    START_OF_Y = 60;
    static final int    Y_OF_NAME = 80;
    static final int    TEXT_SIZE = 52;
    static final int    SCORE_INFO_SIZE = 30;
    static final String HEADLINE = "HIGH-SCORES";
    static final int    NO_SCORE_X = 75;
    static final String NO_SCORE = "No Scores yet - Be the first!";


    /**
     * data of class.
     */
    private KeyPressStoppableAnimation kpsAnimation;
    private HighScoresTable            highScoresTable;
    private Sprite                     backGround;


    /**
     * Constructor.
     *
     * @param keyboardSensor KeyBoardSensor object
     * @param scores HighScoresTable object
     */
    public HighScoresAnimation(KeyboardSensor keyboardSensor,
                               HighScoresTable scores) {
        this.kpsAnimation = new KeyPressStoppableAnimation(keyboardSensor);
        this.highScoresTable = scores;
        this.backGround = new MenuBackground(false);
    }

    @Override
    public void doOneFrame(DrawSurface ds) {
        this.kpsAnimation.doOneFrame(ds);
        this.backGround.drawOn(ds);
        this.printText(ds);
    }

    /**
     * method runs a for loop that prints high scores on a DrawSurface object.
     *
     * @param drawSurface DrawSurface object to draw on
     */
    private void printText(DrawSurface drawSurface) {
        List<ScoreInfo> list = this.highScoresTable.getHighScores();

        drawSurface.setColor(Ass7Game.TEXT_COLOR);
        if (this.highScoresTable.getHighScores().size() == 0) {
            drawSurface.drawText(NO_SCORE_X, START_OF_Y, NO_SCORE, TEXT_SIZE);
        } else {
            drawSurface.drawText(START_OF_X, START_OF_Y, HEADLINE, TEXT_SIZE);

            for (int i = 0; i < list.size(); i++) {
                this.printHighScore(drawSurface, list.get(i),
                        Y_OF_NAME * (i + 2));
            }
        }
    }

    /**
     * method print name and score of a Score info on a DrawSurface object.
     *
     * @param drawSurface DrawSurface object to draw on
     * @param scoreInfo ScoreInfo object - holds data about a high score
     * @param yCoordinate coordinate in y axis to place the text
     */
    private void printHighScore(DrawSurface drawSurface, ScoreInfo scoreInfo,
                                   int yCoordinate) {
        int    thirdScreenWidth = drawSurface.getWidth() / 3;
        int    xOfName = thirdScreenWidth - 20;
        int    xOfHighScore = 2 * xOfName;
        String score = Integer.toString(scoreInfo.getScore());

        drawSurface.drawText(xOfName, yCoordinate, scoreInfo.getName(),
                             SCORE_INFO_SIZE);
        drawSurface.drawText(xOfHighScore, yCoordinate, score,
                             SCORE_INFO_SIZE);
    }

    @Override
    public boolean shouldStop() {
        return this.kpsAnimation.shouldStop();
    }
}
