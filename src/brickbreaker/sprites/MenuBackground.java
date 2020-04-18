package brickbreaker.sprites;


import biuoop.DrawSurface;
import brickbreaker.basics.Ass7Game;
import brickbreaker.io.BlockFromLine;
import brickbreaker.io.Filler;


/**
 * this class set background of menu, depending if it's main menu or not.
 */
public class MenuBackground implements Sprite {


    public static final String  IMAGE_OF_MENU
                                        = "image(background_images/menu.jpg)";
    private static final String TITLE
                                    = "image(background_images/arkanoid.png)";


    /**
     * fields of instance.
     */
    private Filler image;
    private Filler title;


    /**
     * constructor.
     * use delegation to make filler
     *
     * @param main boolean, true if this background is for main menu
     */
    public MenuBackground(boolean main) {
        this.image = BlockFromLine.getFill(IMAGE_OF_MENU);
        this.image.setPosition(0, 0, Ass7Game.WIDTH, Ass7Game.HEIGHT);
        if (main) {
            this.title = BlockFromLine.getFill(TITLE);
            this.title.setPosition(Ass7Game.WIDTH / 3, 30, Ass7Game.WIDTH,
                                   Ass7Game.HEIGHT);
        }
    }

    @Override
    public void drawOn(DrawSurface ds) {
        this.image.drawOn(ds);
        if (this.title != null) {
            this.title.drawOn(ds);
        }

    }

    @Override
    public void timePassed() { }
}
