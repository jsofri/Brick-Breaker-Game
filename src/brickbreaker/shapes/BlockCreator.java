package brickbreaker.shapes;


/**
 * an interface for objects that can create a block in a certain position.
 */
public interface BlockCreator {


    /**
     * method create a block at the specified location.
     * @param xpos int - x coordinate
     * @param ypos int - y coordinate
     * @return Block object, according to instance's settings
     */
    Block create(int xpos, int ypos);
}
