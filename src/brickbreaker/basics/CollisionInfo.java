package brickbreaker.basics;


import brickbreaker.shapes.Point;


/**
 * This class contain data about a collision in game.
 * Each instance will hold information about where and who collided in a game.
 */
public class CollisionInfo {


    /**
     * object's field - the object that the ball collided with and where.
     */
    private Point      collisionPoint;
    private Collidable collidableObject;


    /**
     * a constructor.the  data is written only once in the beginning.
     *
     * @param point Point of collision
     * @param collidable the object that the ball collided with
     */
    public CollisionInfo(Point point, Collidable collidable) {
        this.collisionPoint = point;
        this.collidableObject = collidable;
    }

    /**
     * method returns the instance collision point.
     *
     * @return a Point object - coordinates of the collision point.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * method returns the collidable field in the instance.
     * this is an object implementing collidable interface.
     *
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collidableObject;
    }
}
