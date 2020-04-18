package brickbreaker.shapes;


import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;
import brickbreaker.animation.GameLevel;
import brickbreaker.basics.Ass7Game;
import brickbreaker.basics.Collidable;
import brickbreaker.basics.Velocity;
import brickbreaker.sprites.Sprite;


/**
 * This class contains all methods and data of a Paddle object.
 * A Paddle is a rectangle that can be collided to and drawn to a DrawSurface.
 * Also, Paddle is moving and controlled by user in the game.
 *
 * @author Yehonatan Sofri
 */
public class Paddle implements Sprite, Collidable {


    /**
     * parameters for sizes and dynamics with a ball's collision.
     */
    private static final int            PADDLE_HEIGHT = 15;
    private static final int            COLLISION_HEIGHT = 1;
    private static final java.awt.Color PADDLE_COLOR = Color.ORANGE;
    private static final int            FIRST_FIFTH = -30;
    private static final int            SECOND_FIFTH = -60;
    private static final int            FOURTH_FIFTH = 60;
    private static final int            FIFTH_FIFTH = 20;
    private static final int            LEFT = 1;
    private static final int            RIGHT = 2;


    /**
     * object's fields.
     * collision rectangle is a thin rectangle on the upper line of the paddle.
     */
    private KeyboardSensor keyboard;
    private Rectangle      rectangle;
    private java.awt.Color color;
    private int            speed;
    private int            width;


    /**
     * constructor.
     * @param newKeyboard a gui KeyboardSensor object - relevant for moving
     *                    Paddle
     * @param paddleWidth int width of the paddle
     * @param speed int speed of the paddle on the screenc
     */
    public Paddle(KeyboardSensor newKeyboard, int paddleWidth, int speed) {
        this.width = paddleWidth;
        this.keyboard = newKeyboard;
        this.color = PADDLE_COLOR;
        this.speed = speed;
        this.center(Ass7Game.WIDTH, Ass7Game.HEIGHT);
    }

    /**
     * set this rectangle and collision rectangle - smaller one on upper line.
     *
     * @param upperLeft Point of upper left corner of the rectangle
     */
    private void setRectangles(Point upperLeft) {
        int thisWidth = this.getWidth();

        this.rectangle = new Rectangle(upperLeft, thisWidth, PADDLE_HEIGHT);
    }

    /**
     * @return Rectangle shape of object
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * @return a Rectangle object of the paddle's shape
     */
    public Rectangle getRectangle() {
        return this.rectangle;
    }

    /**
     * @return int, width of the paddle
     */
    public int getWidth() {
        return this.width;
    }
    /**
     * @return java.awt.Color of object
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * method is called after a user click left - move this object to the left.
     */
    public void moveLeft() {
        if (this.canMove(LEFT)) {
            Point newUpperLeft = this.getCollisionRectangle().getUpperLeft();
            newUpperLeft = new Point((newUpperLeft.getX() - this.speed),
                    newUpperLeft.getY());

            this.setRectangles(newUpperLeft);
        }
    }

    /**
     * method's called after a user click left - move this object to the right.
     */
    public void moveRight() {
        if (this.canMove(RIGHT)) {
            Point newUpperLeft = this.getCollisionRectangle().getUpperLeft();
            newUpperLeft = new Point((newUpperLeft.getX()  + this.speed),
                    newUpperLeft.getY());

            this.setRectangles(newUpperLeft);
        }
    }

    /**
     * method check if paddle can move farther and return a boolean.
     *
     * @param side int - side that player want the paddle to move
     * @return boolean - true if it can move
     */
    private boolean canMove(int side) {
        double xcor;

        if (side == LEFT) {
            xcor = this.rectangle.getLeftLine().start().getX();

            if (xcor >= Block.BLOCK_THICKNESS) {
                return true;
            }

            return false;
        } else if (side == RIGHT) {
            xcor = this.rectangle.getRightLine().start().getX();

            if (xcor <= Ass7Game.WIDTH - Block.BLOCK_THICKNESS) {
                return true;
            }
        }

        return false;
    }

    /**
     * part of Sprite interface. check if user pressed a key to move object.
     */
    public void timePassed() {

        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        } else if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }
    }

    /**
     * method draw object on DrawSurface object.
     * method is delegated to rectangle class.
     *
     * @param d the DrawSurface object to be drawn on.
     */
    public void drawOn(DrawSurface d) {
        this.getRectangle().drawOn(d, this.getColor());
    }

    /**
     * method is called when an object collided with this Paddle.
     * using helpers, method returns velocity as requested in assignment 3.
     *
     * @param collisionPoint Point of collision
     * @param currentVelocity Velocity of the object that was collided with
     * @param hitter the ball that hitts this paddle
     * @return Velocity, depending on the relative position of the collision
     */
    public Velocity hit(Ball hitter, Point collisionPoint,
                        Velocity currentVelocity) {
        int thisWidth = this.getWidth();
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        double thisSpeed = Math.sqrt((dx * dx) + (dy * dy));
        double distance = collisionPoint.getX()
                - this.getCollisionRectangle().getUpperLeft().getX();

        if (dy > 0) {

            //if collisionPoint is in the middle of the paddle
            if ((distance > (thisWidth * 0.4))
                    && (distance <= (thisWidth * 0.6))) {
                return new Velocity(currentVelocity.getDx(), -dy);
            } else {
                if (distance <= thisWidth * 0.2) {
                    return this.firstAndSecondFifths(FIRST_FIFTH, thisSpeed);
                } else if (distance <= thisWidth * 0.4) {
                    return this.firstAndSecondFifths(SECOND_FIFTH, thisSpeed);
                } else if (distance <= thisWidth * 0.8) {
                    return this.fourthAndFifthFifths(FOURTH_FIFTH, thisSpeed);
                } else {
                    return this.fourthAndFifthFifths(FIFTH_FIFTH, thisSpeed);
                }
            }

          //ball is under the upper line of the paddle - make it bounce through
        } else {
            return new Velocity(dx, -dy);
        }
    }

    /**
     * method get an angle and a speed and return velocity accordingly.
     * the formula's good for both fifths. only difference is the angle.
     *
     * @param degrees a double in [0,360)
     * @param velocity double - length of velocity vector
     * @return Velocity according to the angle
     */
    private Velocity firstAndSecondFifths(double degrees, double velocity) {
        double theta = Math.toRadians(degrees);
        double dx = -(velocity * (Math.cos(theta)));
        double dy = velocity * (Math.sin(theta));

        return new Velocity(dx, dy);
    }

    /**
     * method get an angle and a speed and return velocity accordingly.
     * the formula's good for both fifths. only difference is the angle.
     *
     * @param degrees a double in [0,360)
     * @param velocity double - length of velocity vector
     * @return Velocity according to the angle
     */
    private Velocity fourthAndFifthFifths(double degrees, double velocity) {
        double theta = Math.toRadians(degrees);
        double dx = velocity * Math.cos(theta);
        double dy = -(velocity * Math.sin(theta));

        return new Velocity(dx, dy);
    }

    /**
     * method add this paddle to a game object.
     *
     * @param g the GameLevel object to add this into
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * method remove this paddle from a game object.
     *
     * @param g the GameLevel object to remove the paddle from
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
        g.removeCollidable(this);
    }

    /**
     * method center the paddle in the middle of the screen.
     *
     * @param screenWidth int, width of screen
     * @param screenHeight int. height of screen
     */
    public void center(int screenWidth, int screenHeight) {
        Point upperLeft = new Point((screenWidth / 2) - (this.getWidth() / 2),
                screenHeight - PADDLE_HEIGHT);

        this.setRectangles(upperLeft);
    }
}
