package brickbreaker.shapes;


import biuoop.DrawSurface;
import java.awt.Color;
import java.util.Random;
import brickbreaker.animation.GameLevel;
import brickbreaker.basics.CollisionInfo;
import brickbreaker.basics.GameEnvironment;
import brickbreaker.basics.Velocity;
import brickbreaker.sprites.Sprite;


/**
 * This class contains all methods and data of a Ball object.
 *
 * @author Yehonatan Sofri
 */
public class Ball implements Sprite {


    /**
     * finals are relevant to set a ball's velocity.
     */
    private static final int    BIG_BALL = 50;
    private static final int    DEGREES = 360;
    private static final int    SLOW_BALL = 2;
    private static final double INTERSECTION = 8;
    private static final double INCLINE = -0.15;

    /**
     * to convert a double to int.
     */
    private static final double INT_ROUNDER = 0.5;

    /**
     * default sizes of a ball in game.
     */
    public static final int             BALL_RADIUS = 5;
    public static final double          BALL_SPEED = 7.4;
    private static final java.awt.Color BALL_COLOR = Color.white;


    /**
     * object's fields.
     */
    private Point           center;
    private int             radius;
    private java.awt.Color  color;
    private Velocity        velocity;
    private GameEnvironment environment;


    /**
     * constuctor.
     *
     * @param cent Point of the ball center
     * @param gameEnvironment GameEnvironment object, contain collidables
     */
    public Ball(Point cent, GameEnvironment gameEnvironment) {
        this.center = cent;
        this.radius = BALL_RADIUS;
        this.environment = gameEnvironment;
        this.color = BALL_COLOR;
    }

    /**
     * constructor.
     *
     * @param cent Point of the ball center
     * @param r int - radius of the ball
     * @param c color of the ball (java.awt.Color)
     */
    public Ball(Point cent, int r, java.awt.Color c) {
        this.center = cent;
        this.radius = r;
        this.color  = c;
    }

    /**
     * constructor.
     *
     * @param cent Point of the ball center
     * @param r int - radius of the ball
     * @param c color of the ball (java.awt.Color)
     * @param ge GameEnvironment object - contain game's collidables
     */
    public Ball(Point cent, int r, java.awt.Color c, GameEnvironment ge) {
        this(cent, r, c);
        this.setEnvironment(ge);
    }

    /**
     * constructor.
     *
     * @param point Point of the ball center
     * @param color color of the ball (java.awt.Color)
     * @param ge GameEnvironment object - contain game's collidables
     */
    public Ball(Point point, java.awt.Color color, GameEnvironment ge) {
        this(point, BALL_RADIUS, color, ge);
    }

    /**
     * constructor.
     *
     * @param x int, x coordinate of the center
     * @param y int, y coordinate of the center
     * @param r int, radius of the ball
     * @param c color of the ball
     */
    public Ball(int x, int y, int r, java.awt.Color c) {
        this(new Point(x, y), r, c);
    }

    /**
     * this method returns the x coordinate of the ball's center.
     *
     * @return int - x coordinate of center
     */
    public int getX() {
        return (int) (this.center.getX() + INT_ROUNDER);
    }

    /**
     * this method returns the y coordinate of the ball's center.
     *
     * @return int - y coordinate of center
     */
    public int getY() {
        return (int) (this.center.getY() + INT_ROUNDER);
    }

    /**
     * this method returns the size (radius) of the object.
     *
     * @return int - radius of the ball
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * this method set the velocity of the object.
     *
     * @param v a Velocity object to set
     */
    public void setVelocity(Velocity v) {
        this.setVelocity(v.getDx(), v.getDy());
    }

    /**
     * this method set the velocity of the object, by creating a velocity
     * object and call to second setVelocity method.
     *
     * @param dx double, the delta x axis of the velocity
     * @param dy double, the delta y axis of the velocity
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * method set ball's velocity proportionally to it's radius.
     */
    public void setVelocity() {
        Random rand = new Random();
        int    angle =   rand.nextInt(DEGREES);
        int    r =  this.getSize();
        int    proportionalV;

        //default of big balls
        if (r > BIG_BALL) {
            proportionalV = SLOW_BALL;
        } else {
            proportionalV = (int) ((INCLINE * r) + INTERSECTION);
        }
        this.velocity = Velocity.fromAngleAndSpeed((double) angle,
                                                   proportionalV);
    }

    /**
     * this method returns the velocity of the object.
     *
     * @return Velocity of the object
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * a setter to GameEnvironment.
     * @param e object that holds data about collidables in a game
     */
    private void setEnvironment(GameEnvironment e) {
        this.environment = e;
    }

    /**
     * a getter to game environment.
     *
     * @return object's GameEnvironment
     */
    public GameEnvironment getEnvironment() {
        return this.environment;
    }

    /**
     * a setter to ball's center.
     *
     * @param point Point of the desired new center for object
     */
    private void setCenter(Point point) {
        this.center = point;
    }

    /**
     * method add instance to a GameLevel's object.
     *
     * @param gameLevel GameLevel object to add this ball into
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }

    /**
     * this method returns the color of the object.
     *
     * @return color of the ball (java.awt.Color)
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * method check if the ball hits an object, and change the ball's position.
     * if a collision occurred, 2 things happen: #1 ball's velocity will change.
     * #2 a recursion check that in next step there are no collisions.
     * that way we avoid getting in an object (e.g. if the ball hits a corner).
     * if there's no collision - ball continue at it's regular track.
     */
    public void moveOneStep() {

        Line          vector = this.makeVector();
        CollisionInfo collision = this.environment.getClosestCollision(vector);
        Point         collisionPoint;
        Velocity      newVelocity;

        if (collision != null) {
            collisionPoint = collision.collisionPoint();
            this.moveCenterToHitPoint(collisionPoint);
            newVelocity = collision.collisionObject().hit(this, collisionPoint,
                                                          this.velocity);
            this.setVelocity(newVelocity);

            //to check if next iteration there's also a collision, before step
            this.moveOneStep();
            return;
        }
        this.setCenter(this.getVelocity().applyToPoint(this.center));
    }

    /**
     * method create a line, where start point is ball's center.
     * the end point is the future point - depending on ball's velocity.
     *
     * @return Line object - ball's current vector
     */
    private Line makeVector() {
        double x2 =   this.center.getX() + this.velocity.getDx();
        double y2 =   this.center.getY() + this.velocity.getDy();
        Point  start = this.center;
        Point  end =   new Point(x2, y2);

        return new Line(start, end);
    }

    /**
     * this method is called when a ball collides with a collidable object.
     * the center is set near collision point, using similarity of triangles.
     *
     * @param collisionPoint Point of the collision of the ball with an object
     */
    private void moveCenterToHitPoint(Point collisionPoint) {
        double r = this.getSize();
        double x1 = this.center.getX();
        double y1 = this.center.getY();
        double x2 = collisionPoint.getX();
        double y2 = collisionPoint.getY();
        double d = this.center.distance(collisionPoint);

        //almost hit point - distance of r from the point, assume ball is small
        double proportion = ((d - (2 * r)) / d);
        double x = x1 + ((x2 - x1) * proportion);
        double y = y1 + ((y2 - y1) * proportion);
        setCenter(new Point(x, y));
    }

    /**
     * method draw a ball on a DrawSurface (using biuoop package).
     *
     * @param surface a DrawSurface object to print the ball on it
     */
    public void drawOn(DrawSurface surface) {
        int x = this.getX();
        int y = this.getY();
        int size = this.getSize();

        surface.setColor(this.color);
        surface.fillCircle(x, y, size);

        //outline of ball
        surface.setColor(Color.BLACK);
        surface.drawCircle(x, y, size);
    }

    /**
     * method remove this instance to a gameLevel Object.
     *
     * @param gameLevel GameLevel object to remove this block from
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
    }

    /**
     * Sprite's interface method.
     * if time passed - it is needed change the ball's center.
     */
    public void timePassed() {
        this.moveOneStep();
    }
}
