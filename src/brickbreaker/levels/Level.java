package brickbreaker.levels;


import brickbreaker.basics.Velocity;
import brickbreaker.io.Filler;
import brickbreaker.shapes.Block;
import brickbreaker.shapes.Point;
import brickbreaker.sprites.Sprite;
import java.util.List;


/**
 * Level is a base class that holds data and implements LevelInformation.
 * by using it we can define levels from a file.
 */
public class Level implements LevelInformation {


    /**
     * fields of class.
     */
    private Filler           background;
    private List<Block>      blocks;
    private List<Velocity>   velocities;
    private List<Point>      placesOfBalls;
    private String           levelName;
    private int              paddleSpeed;
    private int              paddleWidth;


    /**
     * constructor.
     *
     * @param background Filler
     * @param blocks List of Block
     * @param velocities List of Velocity
     * @param placesOfBalls int - number of balls
     * @param levelName String - name of level
     * @param paddleSpeed int - speed of paddle
     * @param paddleWidth int - width of paddle
     */
    public Level(Filler background, List<Block> blocks,
                 List<Velocity> velocities, List<Point> placesOfBalls,
                 String levelName, int paddleSpeed, int paddleWidth) {
        this.background = background;
        this.blocks = blocks;
        this.velocities = velocities;
        this.placesOfBalls = placesOfBalls;
        this.levelName = levelName;
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
    }

    @Override
    public int numberOfBalls() {
        return this.velocities.size();
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return this.velocities;
    }

    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }

    @Override
    public String levelName() {
        return this.levelName;
    }

    @Override
    public Sprite getBackground() {
        return this.background;
    }

    @Override
    public List<Block> blocks() {
        return this.blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blocks().size();
    }

    @Override
    public List<Point> getPointsOfBalls() {
        return this.placesOfBalls;
    }
}
