package brickbreaker.levels;


import java.util.List;
import brickbreaker.basics.Velocity;
import brickbreaker.shapes.Block;
import brickbreaker.sprites.Sprite;
import brickbreaker.shapes.Point;


/**
 * This Inteface specifies the information required to fully describe a level.
 */
public interface LevelInformation {


    /**
     * @return int, number of balls in level
     */
    int numberOfBalls();

    /**
     * @return List with the initial velocity of each ball
     */
    List<Velocity> initialBallVelocities();

    /**
     * @return int, the speed of the paddle
     */
    int paddleSpeed();

    /**
     * @return int, width of paddle
     */
    int paddleWidth();

    /**
     * @return String - the level name
     */
    String levelName();

    /**
     * @return a Sprite with the background of the level
     */
    Sprite getBackground();

    /**
     * @return List of the Blocks that make up this level
     */
    List<Block> blocks();

    /**
     * @return int, number of blocks that should be removed so level is 'clear'
     */
    int numberOfBlocksToRemove();

    /**
     * @return List of Points of the balls in the level.
     */
    List<Point> getPointsOfBalls();
}
