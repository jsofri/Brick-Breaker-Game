package brickbreaker.shapes;


import java.util.List;
import brickbreaker.basics.CollisionInfo;


/**
 * This class includes all methods and data of Line object.
 * Line object is made of 2 Point objects.
 *
 * @author: Yehonatan Sofri
 */
public class Line {


    /**
     * EPSILON - relevant for comparing numbers of type double.
     */
    private static final double EPSILON = 0.01;


    /**
     * p1 and p2 hold the value of the points that create the line.
     */
    private Point start;
    private Point end;


    /**
     * constructor of class - initialize a line.
     *
     * @param p1 Point - starting point of the line
     * @param p2  Point - ending point of the line
     */
    public Line(Point p1, Point p2) {
        this.start = p1;
        this.end = p2;
    }

    /**
     * constructor of class - initialize a line.
     *
     * @param x1 x coordinate of point 1
     * @param y1 y coordinate of point 1
     * @param x2 x coordinate of point 2
     * @param y2 y coordinate of point 2
     */
    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * this method returns the length of the line using Point class.
     *
     * @return double - length of line
     */
    public double length() {
        return this.start.distance(end);
    }

    /**
     * this method returns the middle point of the line.
     *
     * @return Point - middle point of line
     */
    public Point middle() {
        double avgX = ((this.start.getX() + this.end.getX()) / 2);
        double avgY = ((this.start.getY() + this.end.getY()) / 2);

        return new Point(avgX, avgY);
    }

    /**
     * this method returns the first point of the line (start).
     *
     * @return Point - starting point of line
     */
    public Point start() {
        return this.start;
    }

    /**
     * this method returns the second point of the line (end).
     *
     * @return Point - ending point of line
     */
    public Point end() {
        return this.end;
    }

    /**
     * method check if 2 lines are equal by comparing their lengths.
     *
     * @param other the second line to compare this line
     * @return boolean - true if lines are equal, false otherwise
     */
    public boolean equals(Line other) {

        double l1 = this.length();
        double l2 = other.length();
        double deltaLength = l2 - l1;

        if (deltaLength <= EPSILON && deltaLength >= EPSILON) {
            return true;
        }
        return false;
    }

    /**
     * method check if line is intersecting with another line.
     * this method is using a few helper methods.
     *
     * @param other Line object, 'this' Line is compared to 'other'
     * @return boolean, depending on the result
     */
    public boolean isIntersecting(Line other) {
        Point intersectionPoint = this.intersectionWith(other);

        //happens when there's no intersection point between lines.
        if (intersectionPoint == null) {
            return false;
        }
        return true;
    }

    /**
     * this method check if lines intersect and returns the intersection point.
     *
     * @param other the Line to check if there's an intersection with
     * @return point of intersection/ null if lines do not intersect
     */
    public Point intersectionWith(Line other) {
        return this.intersectionPoint(other);
    }

    /**
     * this method finds the intersection point of 2 lines, by graph equations.
     *
     * @param other other line to compare to 'this' line
     * @return point of intersection/ null if no intersection point
     */
    private Point intersectionPoint(Line other) {

        /*
         * m and n are slope - intercept form of line in two dimensions
         * initializing variables for compilation reasons
         */
        double m1 = 0;
        double n1 = 0;
        double m2 = 0;
        double n2 = 0;
        double deltaM, deltaN;
        int    exceptionCounter = 0;
        Point  intersectionPoint;

        //exception happans when there's no incline - line/s are diagonal
        try {
            m1 = this.findIncline();
            n1 = this.findN(m1);
        } catch (RuntimeException ex) {

            //1 - meaning there's a problem with incline of 'this' line
            exceptionCounter = 1;
        }
        try {
            m2 = other.findIncline();
            n2 = other.findN(m2);
        } catch (RuntimeException ex) {

            //counter >= 2 - meaning there's a problem with incline of 'other'
            exceptionCounter += 2;
        }
        if (exceptionCounter != 0) {
            return this.handleException(other, exceptionCounter);
        }

        // following code is meant to actually find the intersection point.
        deltaM = m1 - m2;

        //if incline of lines is similar
        if ((deltaM <= EPSILON) && (deltaM >= -EPSILON)) {
            deltaN = n1 - n2;
            return this.checkParallelLines(deltaN, other);
        }
        intersectionPoint = calculateIntersection(m1, n1, m2, n2);
        if (this.isPointInLine(intersectionPoint)
                && other.isPointInLine(intersectionPoint)) {
            return intersectionPoint;
        }

        //intersection point is not on both lines
        return null;
    }

    /**
     * this method is a helper for intersectionPoint().
     * it is called when at least one line is diagonal to x axis.
     *
     * @param other the second line to calculate of
     * @param flag int in range [1,3], to mark the problem
     * @return Point of intersection/ null if there's no intersection
     */
    private Point handleException(Line other, int flag) {
        Point p;

        /*
         *flag is 1 = 'this' line is vertical
         *flag is 2 = 'other' line is vertical
         *flag is 3 = both lines are vertical
         */
        if (flag == 1) {
            p = handleExceptionHelper(this, other);
        } else if (flag == 2) {
            p = handleExceptionHelper(other, this);
        } else {
            double x1 = this.start().getX();
            double x2 = other.start().getX();

            return this.checkParallelLines(x2 - x1, other);
        }
        if (this.isPointInLine(p) && other.isPointInLine(p)) {
            return p;
        }
        return null;
    }

    /**
     * method is a helper to handleException method.
     * finds the intersection of a diagonal line and an inclined line.
     *
     * @param vertical the vertical (diagonal) line segment
     * @param inclined the inclined line segment
     * @return Point of intersection of the infinite lines
     */
    private Point handleExceptionHelper(Line vertical, Line inclined) {
        double m = inclined.findIncline();
        double n = inclined.findN(m);
        double x = vertical.start().getX();

        return new Point(x, ((m * x) + n));
    }

    /**
     * method use simple linear equations to calculate a line incline.
     *
     * @return double if an incline exists, otherwise - runtime exception
     */
    private double findIncline() {
        Point   tmpPoint = this.start();
        double  x1 = tmpPoint.getX();
        double  y1 = tmpPoint.getY();
        tmpPoint = this.end();
        double  x2 = tmpPoint.getX();
        double  y2 = tmpPoint.getY();
        double  deltaX = x2 - x1;
        double  deltaY = y2 - y1;

        //check if line's function is constant / inclined/ diagonal
        if ((deltaX >= -EPSILON) && (deltaX <= EPSILON)) {
            throw new RuntimeException("Line is diagonal to x axis");
        } else if ((deltaY >= -EPSILON) && (deltaY <= EPSILON)) {
            return 0;
        } else {
            return (deltaY / deltaX);
        }
    }

    /**
     * method calculate and returns value of N (intersection with y axis).
     *
     * @param m double - the incline of the line
     * @return double - value of N
     */
    private double findN(double m) {
        Point   tmpPoint = this.start();
        double  x = tmpPoint.getX();
        double  y = tmpPoint.getY();

        return (y - (m * x));
    }

    /**
     * a helper method to check if a point is on a certain line's segment.
     *
     * @param point Point of intersection of this line and other line
     * @return boolean - true if line contains the input point
     */
    public boolean isPointInLine(Point point) {
        double h1 = point.distance(this.start());
        double h2 = point.distance(this.end());
        double delta = (h1 + h2 - this.length());

        //if delta is very small - point is probably on the line
        if (delta <= EPSILON) {
                return true;
        }
        return false;
    }

    /**
     * method get slope - intercept form of two lines.
     * the method returns a new point of the intersection point.
     * @param m1 double, incline of the first lint
     * @param n1 double, intersection with y axis of first line
     * @param m2 double, incline of the second lint
     * @param n2 double, intersection with y axis of second line
     * @return a Point object - intersection point of the lines
     */
    private Point calculateIntersection(double m1, double n1, double m2,
                                        double n2) {
        double x = ((n2 - n1) / (m1 - m2));
        double y = ((m1 * x) + n1);
        Point  p = new Point(x, y);

        return p;
    }

    /**
     * method check if 2 lines that are parallel intersect/ touch or not.
     *
     * @param delta double - delta of intersection of lines with axis.
     * @param other second line to compare to 'this' line
     * @return Point of intersection/ null if no such point
     */
    private Point checkParallelLines(double delta, Line other) {

        //if intersection of lines with the axis is similar
        if ((delta <= EPSILON) && (delta >= -EPSILON)) {
            if (this.isPointInLine(other.start())) {
                return other.start();
            } else if (this.isPointInLine(other.end())) {
                return other.end();
            }
        }

        //lines are parallel and not touching
        return null;
    }

    /**
     * method get a rectangle and calculate intersection points with line.
     *
     * @param rect Rectangle object
     * @return closest intersection Point to the start of line, otherwise null
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> list = rect.intersectionPoints(this);
        Point       closest = null;
        Point       tmpPoint;

        if (list.size() != 0) {
            closest = list.get(0);

            for (int i = 1; i < list.size(); i++) {
                tmpPoint = list.get(i);
                closest = this.closerToStart(tmpPoint, closest);
            }
        }
        return closest;
    }

    /**
     * method get a list of CollisionInfo and find the closest point to start.
     * method use closerToStart method to find closest Point.
     *
     * @param list a List of CollisionInfo objects
     * @return the CollisionInfo object of the closest point.
     */
    public CollisionInfo findClosestPointFromList(List<CollisionInfo> list) {
        CollisionInfo closest = null;
        Point         tmpPoint;

        if (list.size() > 0) {
            closest = list.get(0);

            for (int i = 1; i < list.size(); i++) {
                tmpPoint = this.closerToStart((list.get(i).collisionPoint()),
                        (closest.collisionPoint()));

                if (tmpPoint.equals(list.get(i).collisionPoint())) {
                    closest = list.get(i);
                }
            }
        }
        return closest;
    }

    /**
     * method get 2 points and return the point that's closer to start of line.
     * method create 2 lines, compare lengths and return the smaller.
     *
     * @param pointA Point 1 to compare
     * @param pointB Point 2 to compare
     * @return the closer Point to start of line
     */
    private Point closerToStart(Point pointA, Point pointB) {
        Line lineA = new Line(this.start(), pointA);
        Line lineB = new Line(this.start(), pointB);

        if (lineA.length() > lineB.length()) {
            return pointB;
        }
        return pointA;
    }
}
