package brickbreaker.basics;


import brickbreaker.shapes.Ball;
import brickbreaker.shapes.Point;
import brickbreaker.shapes.Rectangle;


/**
 * this interface is used by objects that can be collided with.
 * everything that we can collide into is rectangular.
 */
public interface Collidable {

    /**
     * a getter to the instance's collision shape.
     * @return the "collision shape" of the object
     */
    Rectangle getCollisionRectangle();

    /**
     * method notify the object that we collided with 2 parameters.
     * the collision point and the given velocity of the object.
     *
     * @param collisionPoint A Point object that indicates the collision point
     * @param currentVelocity the Velocity of the object we collided with
     * @param hitter the ball that hitted the collidable object
     * @return Velocity expected after the hit (based on object's hit logic)
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
