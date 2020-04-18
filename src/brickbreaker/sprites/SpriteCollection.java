package brickbreaker.sprites;


import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;


/**
 * This class holds a list that contain all Sprite objects in a game.
 * that allows to perform actions on it's various objects in the list.
 */
public class SpriteCollection {


    /**
     * this list contain all the sprites objects in this instance.
     */
    private List<Sprite> spriteList;

    /**
     * a constructor - create a list.
     */
    public SpriteCollection() {
        this.spriteList = new ArrayList<>();
    }

    /**
     * method add a new sprite to the list.
     *
     * @param s the Sprite to add
     */
    public void addSprite(Sprite s) {
        this.getSpriteList().add(s);
    }

    /**
     * method remove a sprite from it's list.
     *
     * @param s the Sprite to remove
     */
    public void removeSprite(Sprite s) {
        this.getSpriteList().remove(s);
    }

    /**
     * method call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        List<Sprite> copyOfSprites = new ArrayList<>(this.getSpriteList());

        for (Sprite sprite : copyOfSprites) {
            sprite.timePassed();
        }
    }

    /**
     * method call drawOn(d) on all sprites.
     *
     * @param d the drawsurface to draw onto
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite : this.getSpriteList()) {
            sprite.drawOn(d);
        }
    }

    /**
     * @return this instance List of Sprites
     */
    private List<Sprite> getSpriteList() {
        return this.spriteList;
    }
}
