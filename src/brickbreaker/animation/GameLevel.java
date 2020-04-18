package brickbreaker.animation;


import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import brickbreaker.basics.Ass7Game;
import brickbreaker.basics.Collidable;
import brickbreaker.basics.Counter;
import brickbreaker.basics.GameEnvironment;
import brickbreaker.basics.Velocity;
import brickbreaker.levels.LevelInformation;
import brickbreaker.listeners.BallRemover;
import brickbreaker.listeners.BlockRemover;
import brickbreaker.listeners.ScoreTrackingListener;
import brickbreaker.shapes.Ball;
import brickbreaker.shapes.Block;
import brickbreaker.shapes.Paddle;
import brickbreaker.shapes.Point;
import brickbreaker.sprites.Sprite;
import brickbreaker.sprites.LevelName;
import brickbreaker.sprites.LiveIndicator;
import brickbreaker.sprites.ScoreIndicator;
import brickbreaker.sprites.SpriteCollection;
import java.awt.Color;
import java.util.List;


/**
 * This class holds all data and method in order to make a brick breaker game.
 * meaning this class can initialize a game environment and run it.
 */
public class GameLevel implements Animation {


    /**
     * parameters for balls in game.
     */
    public static final int END_LEVEL_SCORE = 100;

    /**
     * parameters for edges blocks in game.
     */
    public static final int    SCORE_BOARD_THICKNESS = 20;
    public static final Color  EDGE_BLOCK_COLOR = Color.gray;


    /**
     * object's fields.
     */
    private SpriteCollection      sprites;
    private GameEnvironment       environment;
    private GUI                   gui;
    private KeyboardSensor        keyboard;
    private Paddle                paddle;
    private Counter               blockCounter;
    private Counter               ballCounter;
    private Counter               score;
    private Counter               numberOfLives;
    private BlockRemover          blockRemover;
    private ScoreTrackingListener scoreTrackingListener;
    private LiveIndicator         liveIndicator;
    private AnimationRunner       runner;
    private boolean               running;
    private LevelInformation      levelInformation;
    private LevelName             levelName;


    /**
     * constructor.
     *
     * @param levelInformation LevelInformation object to run it's animation
     * @param ks KeyboardSensor to control the game
     * @param stl ScoreTrackingListener to add to objects
     * @param liveIndicator LiveIndicator - contain number of lives left
     * @param gui GUI window that the game will be in
     * @param animationRunner AnimationRunner object to run LevelInformation
     */
    public GameLevel(LevelInformation levelInformation, KeyboardSensor ks,
                     ScoreTrackingListener stl, LiveIndicator liveIndicator,
                     GUI gui, AnimationRunner animationRunner) {
        this.environment = new GameEnvironment();
        this.gui = gui;
        this.keyboard = ks;
        this.runner = animationRunner;
        this.levelInformation = levelInformation;
        this.blockCounter = new Counter();
        this.ballCounter = new Counter();
        this.score = stl.getCurrentScore();
        this.blockRemover = new BlockRemover(this, this.getBlockCounter());
        this.scoreTrackingListener = stl;
        this.liveIndicator = liveIndicator;
        this.levelName = new LevelName(this);
        this.numberOfLives = liveIndicator.getLives();

        this.initializeGameLevelSpritesCollection();
    }

    /**
     * a helper to createGameSprites - add sprites of this class to sprites.
     */
    private void initializeGameLevelSpritesCollection() {
        this.sprites = new SpriteCollection();

        this.addSprite(this.getLevelInformation().getBackground());
        this.setEdgesBlocks();
        this.addSprite(new ScoreIndicator(this.getScore()));
        this.addSprite(this.getLiveIndicator());
        this.addSprite(this.levelName);
    }

    /**
     * method add a collidable object to instance's GameEnvironment field.
     *
     * @param c a Collidable object to add to GameEnvironment field
     */
    public void addCollidable(Collidable c) {
        this.getEnvironment().addCollidable(c);
    }

    /**
     * method remove a collidable object from instance's GameEnvironment field.
     *
     * @param c a Collidable object to remove from GameEnvironment field
     */
    public void removeCollidable(Collidable c) {
        this.getEnvironment().removeCollidable(c);
    }

    /**
     * method add a sprite object to instance's SpriteCollection field.
     *
     * @param s a Sprite object to add to this SpriteCollection field
     */
    public void addSprite(Sprite s) {
        this.getSprites().addSprite(s);
    }

    /**
     * method remove a sprite object from instance's SpriteCollection field.
     *
     * @param s a Sprite object to remove from this SpriteCollection field
     */
    public void removeSprite(Sprite s) {
        this.getSprites().removeSprite(s);
    }

    /**
     * @return this instance GameEnvironment
     */
    public GameEnvironment getEnvironment() {
        return this.environment;
    }

    /**
     * @return this instance SpriteCollection
     */
    private SpriteCollection getSprites() {
        return this.sprites;
    }

    /**
     * @return this instance gui objects
     */
    public GUI getGui() {
        return this.gui;
    }

    /**
     * @return this instance Counter of balls
     */
    public Counter getBallCounter() {
        return this.ballCounter;
    }

    /**
     * @return this instance Counter of remaining blocks
     */
    public Counter getBlockCounter() {
        return this.blockCounter;
    }

    /**
     * @return this instance counter of score
     */
    public Counter getScore() {
        return this.score;
    }

    /**
     * @return paddle of this instance.
     */
    private Paddle getPaddle() {
        return this.paddle;
    }

    /**
     * @return this instance counter of lives.
     */
    private Counter getNumberOfLives() {
        return this.numberOfLives;
    }

    /**
     * @return this instance BlockRemover
     */
    public BlockRemover getBlockRemover() {
        return this.blockRemover;
    }

    /**
     * @return this instance ScoreTrackingListener
     */
    public ScoreTrackingListener getSTL() {
        return this.scoreTrackingListener;
    }

    /**
     * @return this instance LiveIndicator
     */
    private LiveIndicator getLiveIndicator() {
        return this.liveIndicator;
    }

    /**
     * @return this instance levelInformation field
     */
    public LevelInformation getLevelInformation() {
        return this.levelInformation;
    }

    /**
     * this method set the blocks right near the edges of the gui window.
     */
    private void setEdgesBlocks() {
        this.setWideBlock();
        this.setTallBlocks(new Point(0, 0));
        this.setTallBlocks(new Point(Ass7Game.WIDTH - Block.BLOCK_THICKNESS,
                     0));
    }

    /**
     * this method is a helper - set the edge tall blocks of the instance.
     * get a point and from it, it knows to set a block in the right place.
     * the edges block are out of gui edges - at the frame of the gui.
     *
     * @param upperLeft Point of the upperLeft point of the block
     */
    private void setTallBlocks(Point upperLeft) {
        Block tallBlock = new Block(upperLeft, Block.BLOCK_THICKNESS,
                                    Ass7Game.HEIGHT, EDGE_BLOCK_COLOR);

        tallBlock.addToGame(this);
    }

    /**
     * this method is a helper - set the edge wide blocks of the instance.
     * there are 2 wide blocks to set - an upper and a lower.
     * lower one is the death ball. both blocks are out of gui.
     */
    private void setWideBlock() {
        Block upperBlock = new Block(new Point(), Ass7Game.WIDTH,
                                     (Block.BLOCK_THICKNESS
                                             + SCORE_BOARD_THICKNESS),
                                     EDGE_BLOCK_COLOR);
        Block downDeathBlock = new Block();
        upperBlock.addToGame(this);
        downDeathBlock.addToGame(this);
        downDeathBlock.addHitListener(new BallRemover(this));
    }

    /**
     * method initialize a new game: create Blocks.
     */
    public void initialize() {
        this.addBlocks();
    }

    /**
     * method add blocks to the game.
     */
    private void addBlocks() {
        List<Block> blockList = this.getLevelInformation().blocks();

        for (Block block : blockList) {
            block.addToGame(this);
            block.addHitListener(this.getSTL());
            block.addHitListener(this.getBlockRemover());
            this.getBlockCounter().increase();
        }
    }

    /**
     * method runs a loop, in each iteration a new ball is made.
     */
    private void setBalls() {
        int numOfBalls = this.getLevelInformation().numberOfBalls();
        List<Velocity> vl = this.getLevelInformation().initialBallVelocities();
        List<Point> pointList = this.getLevelInformation().getPointsOfBalls();
        Velocity ballSpeed;
        Point center;

        for (int i = 0; i < numOfBalls; i++) {
            center = pointList.get(i);
            ballSpeed = vl.get(i);
            this.addBall(center, ballSpeed);
        }
    }

    /**
     * method create a new ball and add it to the game.
     *
     * @param center Point of the ball's center
     * @param speed Velocity of the ball
     */
    private void addBall(Point center, Velocity speed) {
        Ball ball = new Ball(center, this.getEnvironment());

        ball.setVelocity(speed);
        ball.addToGame(this);
        this.getBallCounter().increase();
    }

    /**
     * this method set the paddle and it it to this instance.
     */
    private void setPaddle() {
        int paddleWidth = this.getLevelInformation().paddleWidth();
        int paddleSpeed = this.getLevelInformation().paddleSpeed();
        KeyboardSensor keyboardSensor = this.getGui().getKeyboardSensor();
        this.paddle = new Paddle(keyboardSensor, paddleWidth, paddleSpeed);

        this.getPaddle().addToGame(this);
    }

    /**
     * method run 1 turn of the game - set paddle and balls and runs animation.
     */
    public void playOneTurn() {
        this.setPaddle();
        this.setBalls();
        this.runner.run(new CountdownAnimation(2, 3, this.getSprites()));
        this.running = true;
        this.runner.run(this);
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.getSprites().drawAllOn(d);
        this.getSprites().notifyAllTimePassed();

        if (this.keyboard.isPressed("p")) {
            this.runner.run(new PauseScreen(this.keyboard));
        }

        if (this.getBlockCounter().getValue() == 0
                || this.getBallCounter().getValue() == 0) {
            this.endOfTurn();
            this.running = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * a helper to doOneFrame() - check ending conditions & processes.
     */
    private void endOfTurn() {
        if (this.getBlockCounter().getValue() == 0) {
            this.score.increase(END_LEVEL_SCORE);
        } else {
            this.getNumberOfLives().decrease();
        }
        this.paddle.removeFromGame(this);
    }
}
