package brickbreaker.basics;

import brickbreaker.shapes.Point;


/**
 * This class contains all methods and data of Velocity object.
 * The object is made of 2 doubles that represent the vector's coordinates.
 * Relevant for task 3.2
 *
 * @author Yehonatan Sofri
 */
public class Velocity {


    /**
     * dx + dy create the vector of the object.
     */
    private double dx;
    private double dy;


    /**
     * constructor - set the fields of the object.
     *
     * @param dX velocity in x axis
     * @param dY velocity in y axis
     */
    public Velocity(double dX, double dY) {
        this.dx = dX;
        this.dy = dY;
    }

    /**
     * a static method to create velocity using speed and angle.
     * method extract dx, dy and use the constructor to create new instance.
     *
     * @param angle a double that represent angle of vector (360 degrees)
     * @param speed double - length of vector
     * @return the created Velocity object
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = speed * (Math.cos(Math.toRadians(angle)));
        double dy = speed * (Math.sin(Math.toRadians(angle)));

        return new Velocity(dx, dy);
    }

    /**
     * this method return the velocity in the x axis.
     *
     * @return double - horizontal velocity
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * this methos return the velocity in the y axis.
     *
     * @return double - vertical velocity
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * this method change the point of input to a new point.
     * the new point's location is based on the object's velocity.
     *
     * @param p the point that is needed to be modified
     * @return Point object, after updating the input point.
     */
    public Point applyToPoint(Point p) {
        double newX = p.getX() + dx;
        double newY = p.getY() + dy;

        return new Point(newX, newY);
    }
}
