package brickbreaker.basics;


import java.util.List;
import java.util.ArrayList;
import brickbreaker.shapes.Rectangle;
import brickbreaker.shapes.Point;
import brickbreaker.shapes.Line;


/**
 * This class holds a list that contain all collidable objects in a game.
 * this allows to perform actions on it's various objects in the list.
 *
 */
public class GameEnvironment {

    /**
     * this list contain all the collidable objects in this instance.
     */
    private List<Collidable> collidables;

    /**
     * constructor - sets a new list of collidables.
     */
    public GameEnvironment() {
        this.collidables  = new ArrayList<>();
    }

    /**
     * method add the given collidable to the environment.
     *
     * @param c a Collidable object to add to this list.
     */
    public void addCollidable(Collidable c) {
        this.getCollidables().add(c);
    }

    /**
     * method remove the given collidable to the environment.
     *
     * @param c a Collidable object to remove from this list.
     */
    public void removeCollidable(Collidable c) {
        this.getCollidables().remove(c);
    }

    /**
     * method get a line and run for each collidable in instanc'es list.
     * method check if line is intersecting (collides) any collidable.
     * eventually, method returns a CollisionInfo object of the closest point.
     * to the start of the line.
     *
     * @param trajectory a line - vector of a ball. start is center of the ball
     * @return closest CollisionInfo object to start of line. else return null.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        List<CollisionInfo> list = new ArrayList<>();
        Rectangle           tmpRect;
        Point               tmpPoint;

        for (int i = 0; i < this.collidables.size(); i++) {
            tmpRect = this.getCollidables().get(i).getCollisionRectangle();
            tmpPoint = trajectory.closestIntersectionToStartOfLine(tmpRect);

            if (tmpPoint != null) {
                list.add(new CollisionInfo(tmpPoint,
                                           this.getCollidables().get(i)));
            }
        }

        if (list.size() != 0) {
            return trajectory.findClosestPointFromList(list);
        }
        return null;
    }

    /**
     * @return the object's List of collidables
     */
    private List<Collidable> getCollidables() {
        return this.collidables;
    }
}
