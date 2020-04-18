package brickbreaker.shapes;


/**
 * This class includes all methods and data of Point object.
 * Relevant for task 1.1.
 *
 * @author: Yehonatan Sofri
 */
public class Point {


    /**
     * DEFAULT - used when an instance is created without entering coordinates.
     * EPSILON helps comparing coordinates, by checking if the gap is tiny.
     */
    public static final int     DEFAULT = 0;
    private static final double EPSILON = 0.01;


    /**
     * x and y (type double) hold the value of the point's coordinates.
     */
    private double x;
    private double y;


    /**
     * constructor of class - initialize coordinates.
     *
     * @param newX double, the x coordinate of the point
     * @param newY double,the y coordinate of the point
     */
    public Point(double newX, double newY) {
        this.x = newX;
        this.y = newY;
    }

    /**
     * constructor, relevant for cases when there's no input.
     */
    public Point() {
        this(DEFAULT, DEFAULT);
    }

    /**
     * this method calculate the distance between 2 points.
     *
     * @param other the second point needed to calculate distance from this
     * @return the distance in a double number
     */
    public double distance(Point other) {

        //'tmp' variables are coordinates of other
        double tmpX = other.getX();
        double tmpY = other.getY();

        //return the distance - using the formula of distance.
        return Math.sqrt(((this.x - tmpX) * (this.x - tmpX))
                            + ((this.y - tmpY) * (this.y - tmpY)));
    }

    /**
     * this method check if 2 points are equal.
     *
     * @param other the point needed to be compared
     * @return boolean, depending on the results
     */
    public boolean equals(Point other) {
        double deltaX = other.x - this.x;
        double deltaY = other.y - this.y;

        //if state is using the gap of the coordinates to check similarity
        if (((deltaX <= EPSILON) && (deltaX >= -EPSILON))
                && ((deltaY <= EPSILON) && (deltaY >= EPSILON))) {
            return true;
        }

        return false;
    }

    /**
     * this method returns the x coordinate of the point.
     *
     * @return double - x coordinate of the point
     */
    public double getX() {
        return this.x;
    }

    /**
     * this method returns the y coordinate of the point.
     *
     * @return double - y coordinate of the point
     */
    public double getY() {
        return this.y;
    }
}
