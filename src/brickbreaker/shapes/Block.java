package brickbreaker.shapes;


import biuoop.DrawSurface;
import brickbreaker.animation.GameLevel;
import brickbreaker.basics.Ass7Game;
import brickbreaker.basics.Collidable;
import brickbreaker.basics.Velocity;
import brickbreaker.io.ColorFiller;
import brickbreaker.io.Filler;
import brickbreaker.sprites.Sprite;
import brickbreaker.listeners.HitNotifier;
import brickbreaker.listeners.HitListener;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import static brickbreaker.animation.GameLevel.EDGE_BLOCK_COLOR;


/**
 * This class contains all methods and data of a Block object.
 * A Block is a rectangle that can be drawn to a DrawSurface.
 * also is a coolidable - meaning objects in game might collide with it.
 *
 * @author Yehonatan Sofri
 */
public class Block implements Collidable, Sprite, HitNotifier {


    /**
     * settings for a block.
     */
    public static final int    TEXT_SIZE = 14;
    public static final double ROUNDER = 0.5;      //relevant to casting to int
    public static final int    BLOCK_THICKNESS = 30;


    /**
     * object's fields.
     * number is a counter of the collisions of the instance.
     * text is a String of number. if it isn't positive - String contain 'X'.
     */
    private Rectangle            rectangle;
    private int                  number;
    private int                  maxNumber;
    private Color                stroke;
    private List<HitListener>    hitListeners;
    private Map<Integer, Filler> fillers;


    /**
     * constructor for edges blocks.
     *
     * @param upperLeft Point of the upper left corner of the object
     * @param width instance's rectangle width
     * @param height instance's rectangle height
     * @param color java.awt.Color of object
     */
    public Block(Point upperLeft, double width, double height, Color color) {
        this.number = 0;
        this.maxNumber = 0;
        this.hitListeners = null;
        this.stroke = null;
        this.setRectangle(upperLeft, width, height);
        this.setFiller(color, upperLeft, (int) (width + ROUNDER),
                       (int) (height + ROUNDER));
    }

    /**
     * constructor for death Block.
     */
    public Block() {
        this(new Point(0, Ass7Game.HEIGHT), Ass7Game.WIDTH,
                Block.BLOCK_THICKNESS, EDGE_BLOCK_COLOR);
        this.hitListeners = new ArrayList<>();
    }
    /**
     * constructor of a regular block.
     *
     * @param x int, x coordinate of uper left point of block
     * @param y int, y coordinate of uper left point of block
     * @param width int - width of block
     * @param height int - height of block
     * @param hitPoints int - number of hit points of block
     * @param stroke Color of block stroke
     * @param fillers Map of Fillers for block, each key is current hit points
     */
    public Block(int x, int y, int width, int height, int hitPoints,
                 Color stroke, Map<Integer, Filler> fillers) {
        this.setRectangle(new Point(x, y), width, height);
        this.number = hitPoints;
        this.maxNumber = hitPoints;
        this.stroke = stroke;
        this.fillers = fillers;
        this.hitListeners = new ArrayList<>();
    }

    /**
     * method set a color filler for block.
     *
     * @param color Color object
     * @param upperLeft starting Point of rectangle
     * @param width int - width of block
     * @param height int - width of rectangle
     */
    private void setFiller(Color color, Point upperLeft, int width,
                           int height) {
        ColorFiller colorFiller;
        int x = (int) (upperLeft.getX() + ROUNDER);
        int y = (int) (upperLeft.getY() + ROUNDER);

        colorFiller = new ColorFiller(color);
        colorFiller.setPosition(x, y, width, height);

        this.fillers = new TreeMap<>();
        this.fillers.put(0, colorFiller);
    }

    /**
     * a setter to the instance's number. the default value defined in Class.
     */

    public void setNumber() {
        this.number = this.maxNumber;
    }

    /**
     * a getter to the instance's number.
     *
     * @return int - instance's number.
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * a getter to instance's hitListeners list.
     *
     * @return List of HitListeners
     */
    private List<HitListener> getHitListeners() {
        return this.hitListeners;
    }

    /**
     * a setter to the rectangle of the instance.
     *
     * @param upperLeft Point of the rectangle
     * @param width of the rectangle (double)
     * @param height of the rectangle (double)
     */
    private void setRectangle(Point upperLeft, double width, double height) {
        this.rectangle = new Rectangle(upperLeft, width, height);
    }

    /**
     * a getter to the shape of the instance.
     *
     * @return a Rectangle instance - the specific shape of the instance
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * method add this instance to a gameLevel Object.
     *
     * @param gameLevel GameLevel object to add this block into
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
        gameLevel.addCollidable(this);
    }

    /**
     * method remove this instance to a gameLevel Object.
     *
     * @param gameLevel GameLevel object to remove this block from
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
        gameLevel.removeCollidable(this);
    }

    /**
     * method draw instance's shape and number to a DrawSurface object.
     * the drawing operation itself is delegated to Rectangle class.
     *
     * @param ds DrawSurface object to draw Block into
     */
    public void drawOn(DrawSurface ds) {
        try {
            this.fillers.get(this.number).drawOn(ds);
        } catch (NullPointerException exception) {
            this.fillers.get(0).drawOn(ds);
        }

        this.drawStroke(ds);
    }

    /**
     * method draw strike of block on a draw surface object.
     *
     * @param ds DrawSurface object
     */
    private void drawStroke(DrawSurface ds) {
        if (this.stroke != null) {
            Point upperLeft = this.getCollisionRectangle().getUpperLeft();
            int       x = (int) (upperLeft.getX() + ROUNDER);
            int       y = (int) (upperLeft.getY() + ROUNDER);
            int       thisWidth
                              = (int) this.getCollisionRectangle().getWidth();
            int       thisHeight
                              = (int) this.getCollisionRectangle().getHeight();

            ds.setColor(this.stroke);
            ds.drawRectangle(x, y, thisWidth, thisHeight);
        }
    }

    /**
     * method is called when other object collides with this Block.
     * method change the velocity of the other object under physics rules.
     *
     * @param collisionPoint Point of collision on the block's rectangle
     * @param currentVelocity Velocity of the object that collided with this
     * @param hitter the ball that hitted the block
     * @return a Velocity object - new velocity for the object that collided.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity
                        currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        if (this.getCollisionRectangle().xIsOnOutline(collisionPoint.getX())) {
            dx = -dx;
        }
        if (this.getCollisionRectangle().yIsOnOutline(collisionPoint.getY())) {
            dy = -dy;
        }

        this.decreaseNumber();
        this.notifyHit(hitter);
        return new Velocity(dx, dy);
    }

    /**
     * this method is a helper to hit method - decrease 1 of instance's number.
     * numbers that are not positive are irrelevant.
     */
    private void decreaseNumber() {
        if (this.getNumber() > 0) {
            this.number -= 1;
        }
    }

    /**
     * method notifies all listeners of ball object about a hit event.
     *
     * @param hitter the Ball that hits this block
     */
    private void notifyHit(Ball hitter) {
        if (this.hitListeners != null) {

            // Make a copy of the hitListeners before iterating over them.
            List<HitListener> listeners
                    = new ArrayList<>(this.getHitListeners());

            // Notify all listeners about a hit event.
            for (HitListener hl : listeners) {
                hl.hitEvent(this, hitter);
            }
        }
    }

    /**
     * add a HitListener object to this hitListeners list.
     *
     * @param hl HitListener object
     */
    public void addHitListener(HitListener hl) {
        this.getHitListeners().add(hl);
    }

    /**
     * remove a HitListener object from this hitListeners list.
     *
     * @param hl HitListener object
     */
    public void removeHitListener(HitListener hl) {
        this.getHitListeners().remove(hl);
    }


    /**
     * method is part of Sprite interface - irrelevant for this assignment.
     */
    public void timePassed() { }
}
