package brickbreaker.shapes;


import biuoop.DrawSurface;
import brickbreaker.sprites.Sprite;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;


/**
 * This class contains all methods and data of a Line object.
 *
 * @author Yehonatan Sofri
 */
public class Rectangle implements Sprite {


    /**
     * epsilon is relevant to compare double numbers.
     */
    private static final double EPSILON = 0.01;
    private static final double INT_ROUNDER = 0.5;


    /**
     * object's fields.
     */
    private Point  upperLeft;
    private double width;
    private double height;
    private Color  color;


    /**
     * Constructor.
     *
     * @param upLeft Point of the upper left corner of rectangle
     * @param newWidth double - rectangle's width
     * @param newHeight double - rectangle's height
     */
    public Rectangle(Point upLeft, double newWidth, double newHeight) {
        this.upperLeft = upLeft;
        this.width = newWidth;
        this.height = newHeight;
    }

    /**
     * Constructor for colored rectangles.
     *
     * @param upLeft Point of the upper left corner of rectangle
     * @param newWidth double - rectangle's width
     * @param newHeight double - rectangle's height
     * @param color color of the rectangle
     */
    public Rectangle(Point upLeft, double newWidth, double newHeight,
                     Color color) {
        this(upLeft, newWidth, newHeight);
        this.color = color;
    }

    /**
     * method draw the object on a DrawSurface object.
     *
     * @param ds DrawSurface object to draw on this rectangle
     * @param newColor java.awt.Color - the color to draw
     */
    public void drawOn(DrawSurface ds, java.awt.Color newColor) {
        this.color = newColor;
        this.drawOn(ds);
    }

    /**
     * method draw the sprite to the screen.
     *
     * @param d DrawSurface object to draw on
     */
    public void drawOn(DrawSurface d) {
        int       x = (int) (this.getUpperLeft().getX() + INT_ROUNDER);
        int       y = (int) (this.getUpperLeft().getY() + INT_ROUNDER);
        int       thisWidth = (int) this.getWidth();
        int       thisHeight = (int) this.getHeight();

        d.setColor(color);
        d.fillRectangle(x, y, thisWidth, thisHeight);
        d.setColor(Color.BLACK);
        d.drawRectangle(x, y, thisWidth, thisHeight);  //a black frame
    }

    /**
     * method check if input line is intersecting this rectangle.
     *
     * @param line the Line to check intersection with
     * @return List of Points - intersection points or null if no intersection
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        List<Point> list = new ArrayList<>();
        Point       tmpPoint;

        //intersection point with upperLine
        tmpPoint = this.getUpperLine().intersectionWith(line);
        this.addIntersectionPointToList(list, tmpPoint);

        //intersection point with rightLine
        tmpPoint = this.getRightLine().intersectionWith(line);
        this.addIntersectionPointToList(list, tmpPoint);

        //intersection point with lowerLine
        tmpPoint = this.getLowerLine().intersectionWith(line);
        this.addIntersectionPointToList(list, tmpPoint);

        //intersection point with leftLine
        tmpPoint = this.getLeftLine().intersectionWith(line);
        this.addIntersectionPointToList(list, tmpPoint);

        return list;
    }

    /**
     * method add a point to a list if point isn't null.
     *
     * @param list the List of points to add the point into
     * @param point Point object
     */
    private void addIntersectionPointToList(List<Point> list, Point point) {
        if (point != null) {
            list.add(point);
        }
    }

    /**
     * a getter to width.
     *
     * @return double - object's width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * a getter to height.
     *
     * @return double - object's height
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * a getter to upperLeft.
     *
     * @return Point - upper left corner of rectangle
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * a getter to upper right point of the rectangle.
     *
     * @return Point - upper right corner of rectangle
     */
    public Point getUpperRight() {
        double x = this.upperLeft.getX() + this.width;
        double y = this.upperLeft.getY();

        return new Point(x, y);
    }

    /**
     * a getter to lower right point of the rectangle.
     *
     * @return Point - lower right corner of rectangle
     */
    private Point getLowerRight() {
        double x = this.upperLeft.getX() + this.width;
        double y = this.upperLeft.getY() + this.height;

        return new Point(x, y);
    }

    /**
     * a getter to lower left point of the rectangle.
     *
     * @return Point - lower left corner of rectangle
     */
    private Point getLowerLeft() {
        double x = this.upperLeft.getX();
        double y = this.upperLeft.getY() + this.height;

        return new Point(x, y);
    }

    /**
     * a getter to the upper line of the rectangle.
     *
     * @return Line - upper line of rectangle
     */
    public Line getUpperLine() {
        return new Line(this.upperLeft, this.getUpperRight());
    }

    /**
     * a getter to the right line of the rectangle.
     *
     * @return Line - right line of rectangle
     */
    public Line getRightLine() {
        return new Line(this.getUpperRight(), this.getLowerRight());
    }

    /**
     * a getter to the left line of the rectangle.
     *
     * @return Line - left line of rectangle
     */
    public Line getLeftLine() {
        return new Line(this.upperLeft, this.getLowerLeft());
    }

    /**
     * a getter to the lower line of the rectangle.
     *
     * @return Line - lower line of rectangle
     */
    private Line getLowerLine() {
        return new Line(this.getLowerLeft(), this.getLowerRight());
    }

    /**
     * a helper method to check if an X coordinate is on object's frame.
     *
     * @param xCoordinate double - the x coordinate to compare object's lines
     * @return boolean - true if x coordinate is on object's line
     */
    public boolean xIsOnOutline(double xCoordinate) {
        if (this.onOutlineHelper(xCoordinate, this.getUpperLeft().getX())) {
            return true;
        }
        return onOutlineHelper(xCoordinate, this.getLowerRight().getX());
    }

    /**
     * a helper method to check if a Y coordinate is on object's frame.
     *
     * @param yCoordinate double - the y coordinate to compare object's lines
     * @return boolean - true if coordinate is on object's line
     */
    public boolean yIsOnOutline(double yCoordinate) {
        if (onOutlineHelper(yCoordinate, this.getUpperLeft().getY())) {
            return true;
        }
        return onOutlineHelper(yCoordinate, this.getLowerRight().getY());
    }

    /**
     * method is a helper to isOnOutLine() - check if 2 doubles are identical.
     *
     * @param num1 double - 1st coordinate
     * @param num2 double - 2nd coordinate
     * @return boolean - true if numbers are equal
     */
    private boolean onOutlineHelper(double num1, double num2) {
        if (((num1 - num2) >= -EPSILON) && (num1 - num2) <= EPSILON) {
            return true;
        }
        return false;
    }

    /**
     * part of Sprite interface - irrelevant.
     */
    public void timePassed() { }
}
