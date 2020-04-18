package brickbreaker.io;


import biuoop.DrawSurface;
import java.awt.Image;


/**
 * this class implements filler, it's purpose is to be drawn on DrawSurfaces.
 * Image filler is a sprite that draw an image on a DrawSurface.
 */
public class ImageFiller implements Filler {


    /**
     * fields of object.
     */
    private Image image;
    private int   xCor;
    private int   yCor;


    /**
     * constructor.
     *
     * @param image an image object to add to the instance
     */
    public ImageFiller(Image image) {
        this.image = image;
    }

    @Override
    public void setPosition(int x, int y, int width, int height) {
        this.xCor = x;
        this.yCor = y;
    }

    @Override
    public Filler clone() {
        return new ImageFiller(this.image);
    }

    @Override
    public void drawOn(DrawSurface ds) {
        ds.drawImage(this.xCor, this.yCor, this.image);
    }

    @Override
    public void timePassed() { }
}
