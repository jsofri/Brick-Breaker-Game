package brickbreaker.levels;


import brickbreaker.basics.Velocity;
import brickbreaker.io.BlocksDefinitionReader;
import brickbreaker.io.BlocksFromSymbolsFactory;
import brickbreaker.io.Filler;
import brickbreaker.shapes.Block;
import brickbreaker.shapes.Point;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static brickbreaker.basics.Ass7Game.HEIGHT;
import static brickbreaker.basics.Ass7Game.WIDTH;


/**
 * this class helps to organize data when making a level.
 * contain mainly setters to fields of LevelInformation data.
 */
public class LevelSetter {


    /**
     * fields of class.
     */
    private int                      numberOfBalls;
    private int                      paddleSpeed;
    private int                      paddleWidth;
    private int                      numOfBlocks;
    private int                      rowHeight;
    private int                      blockStartX;
    private int                      blockStartY;
    private Filler                   background;
    private String                   nameOfblockDefinitions;
    private String                   levelName;
    private List<Velocity>           ballsVelocity;
    private List<Block>              blocks;
    private List<String>             blockRows;
    private List<Point>              pointsOfBalls;
    private BlocksFromSymbolsFactory blocksFactory;


    /**
     * constuctor.
     */
    public LevelSetter() {
        this.ballsVelocity = new ArrayList<>();
        this.blocks = new ArrayList<>();
        this.blockRows = new ArrayList<>();
        this.pointsOfBalls = new ArrayList<>();
    }

    /**
     * setter of number of balls.
     *
     * @param number int
     */
    public void setNumberOfBalls(int number) {
        this.numberOfBalls = number;
    }

    /**
     * setter of paddle speed.
     *
     * @param speed int
     */
    public void setPaddleSpeed(int speed) {
        this.paddleSpeed = speed;
    }

    /**
     * setter of paddle width.
     *
     * @param width int
     */
    public void setPaddleWidth(int width) {
        this.paddleWidth = width;
    }

    /**
     * setter of number of blocks in level.
     *
     * @param numberOfBlocks int
     */
    public void setNumOfBlocks(int numberOfBlocks) {
        this.numOfBlocks = numberOfBlocks;
    }

    /**
     * setter of level name.
     *
     * @param name String
     */
    public void setLevelName(String name) {
        this.levelName = name;
    }

    /**
     * setter of path to block definitions.
     *
     * @param definitions String
     */
    public void setBlockDefinitions(String definitions) {
        this.nameOfblockDefinitions = definitions;
    }

    /**
     * setter of height of rows.
     *
     * @param height int
     */
    public void setRowHeight(int height) {
        this.rowHeight = height;
    }

    /**
     * setter of starting position of block in x axis.
     *
     * @param x int
     */
    public void setBlockStartX(int x) {
        this.blockStartX = x;
    }

    /**
     * setter of starting position of block in y axis.
     *
     * @param y int
     */
    public void setBlockStartY(int y) {
        this.blockStartY = y;
    }

    /**
     * setter of background of level.
     *
     * @param back Filler object
     */
    public void setBackground(Filler back) {
        this.background = back;
        this.background.setPosition(0, 0, WIDTH, HEIGHT);
    }

    /**
     * add velocity to list of initial velocities of balls in level.
     *
     * @param speed int
     * @param angle int
     */
    public void addVelocity(int speed, int angle) {
        Velocity velocity = Velocity.fromAngleAndSpeed(angle, speed);

        this.ballsVelocity.add(velocity);
    }

    /**
     * getter of instace's ballsVelocity.
     *
     * @return List of Velocity
     */
    public List<Velocity> initialBallVelocities() {
        return this.ballsVelocity;
    }

    /**
     * add a string of a row of blocks to the instance's list.
     *
     * @param line String containing symbols of blocks
     */
    public void addStringOfRowOfBlocks(String line) {
        this.blockRows.add(line);
    }

    /**
     * method check that all data is set in order to create a new level.
     *
     * @return boolean - true if all is set
     */
    public boolean allIsSet() {
        if ((this.ballsVelocity.size() > 0) && (this.numberOfBalls > 0)
                && (this.paddleWidth > 0) && (this.paddleSpeed > 0)
                && (this.rowHeight > 0) && (this.blockStartY > 0)
                && (this.blockStartX >= 0) && (this.background != null)
                && (this.nameOfblockDefinitions != null)
                && (this.levelName != null) && (this.blockRows.size() > 0)
                && (this.blockRows.size() > 0)) {

            return true;
        }

        return false;
    }

    /**
     * method create blocks as defined in path to block definitions file.
     */
    public void createBlocks() {
        BufferedReader br;
        InputStream is;

        try {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(this.nameOfblockDefinitions);
            Reader reader = new InputStreamReader(is);
            br = new BufferedReader(reader);

        } catch (Exception exception) {
            throw new RuntimeException("Error in opening block definition "
                                        + this.nameOfblockDefinitions);
        }

        this.blocksFactory = BlocksDefinitionReader.fromReader(br);
        this.setBlocksList();
    }

    /**
     * method create a list of blocks according to the instance settings.
     */
    private void setBlocksList() {
        char   c;
        String line;
        String symbol;
        Block tmpBlock;

        for (int x, y = 0, j = 0; j < this.blockRows.size(); j++) {
            y = Block.BLOCK_THICKNESS + this.blockStartY
                    + (j * this.rowHeight);
            x = Block.BLOCK_THICKNESS + this.blockStartX;
            line = this.blockRows.get(j);

            for (int i = 0; i < line.length(); i++) {
                c = line.charAt(i);
                symbol = String.valueOf(c);

                if (this.blocksFactory.isSpaceSymbol(symbol)) {
                    x += this.blocksFactory.getSpaceWidth(symbol);
                } else if (this.blocksFactory.isBlockSymbol(symbol)) {
                    tmpBlock = this.blocksFactory.getBlock(symbol, x, y);
                    this.blocks.add(tmpBlock);
                    x += tmpBlock.getCollisionRectangle().getWidth();
                }
            }
        }
    }

    /**
     * method set a list of initial places of balls.
     */
    private void setPlacesOfBalls() {
        int space = WIDTH / 2;
        int y = 500;

        for (int i = 0, x = space; i < this.numberOfBalls; i++) {
            this.pointsOfBalls.add(new Point(x, y));
        }
    }

    /**
     * method return a level with all data of this instance.
     *
     * @return Level instance that match to instance's settings
     */
    public Level getLevel() {
        this.setPlacesOfBalls();
        return new Level(this.background, this.blocks, this.ballsVelocity,
                             this.pointsOfBalls, this.levelName,
                             this.paddleSpeed, this.paddleWidth);
    }
}
