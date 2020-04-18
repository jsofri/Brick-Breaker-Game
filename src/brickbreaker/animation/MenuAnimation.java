package brickbreaker.animation;


import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import brickbreaker.basics.Ass7Game;
import brickbreaker.basics.Menu;
import brickbreaker.basics.Task;
import brickbreaker.sprites.MenuBackground;
import brickbreaker.sprites.Sprite;
import java.util.Map;
import java.util.TreeMap;


/**
 * this method is a template for running a menu animation.
 */
public class MenuAnimation implements Menu<Task> {


    /**
     * data for printing text on screen.
     */
    static final int    X_OF_OPTION = 150;
    static final int    Y_OF_OPTION = 150;
    static final int    OPTION_SIZE = 30;
    static final String OPTION1 = "Press -";
    static final String OPTION2 = "- Key To ";


    /**
     * fields of object.
     */
    private Map<String, Task>   taskMap;
    private Map<String, String> stringMap;
    private KeyboardSensor      keyboardSensor;
    private Task                task;
    private boolean             stop;
    private boolean             isAlreadyPressed;
    private Sprite              backGround;


    /**
     * constructor.
     *
     * @param keyboardSensor KeyboardSensor object
     */
    public MenuAnimation(KeyboardSensor keyboardSensor) {
        this.taskMap = new TreeMap<>();
        this.stringMap = new TreeMap<>();
        this.keyboardSensor = keyboardSensor;
        this.stop = false;
        this.isAlreadyPressed = true;
    }

    @Override
    public void addSelection(String key, String message, Task tas) {
        this.taskMap.put(key, tas);
        this.stringMap.put(key, message);
    }

    @Override
    public Task getStatus() {
        return this.task;
    }

    @Override
    public void doOneFrame(DrawSurface ds) {
        this.drawMenu(ds);
        this.checkIfPressed();
    }

    @Override
    public void addSubMenu(String key, String message, Menu<Task> subMenu) { }

    /**
     * method draw the menu on a DrawSurface object, using a foreach loop.
     *
     * @param ds DrawSurface object to draw onto
     */
    private void drawMenu(DrawSurface ds) {
        this.backGround.drawOn(ds);
        this.drawText(ds);
    }

    /**
     * method draw text of menu on input DrawSurface.
     *
     * @param ds DrawSurface object to draw onto
     */
    private void drawText(DrawSurface ds) {
        ds.setColor(Ass7Game.TEXT_COLOR);
        int i = 1;

        for (String option : this.stringMap.keySet()) {
            ds.drawText(X_OF_OPTION, Y_OF_OPTION + (100 * i),
                    OPTION1 + option + OPTION2 + this.stringMap.get(option),
                    OPTION_SIZE);

            i++;
        }
    }

    /**
     * this method run on the map of keys and check if a map key is pressed.
     */
    private void checkIfPressed() {
        for (String key : this.taskMap.keySet()) {
            if (this.keyboardSensor.isPressed(key)) {
                if (!isAlreadyPressed) {
                    this.stop = true;
                    this.task = this.taskMap.get(key);
                }
            } else {
                this.isAlreadyPressed = false;
            }
        }
    }

    @Override
    public void mainMenu(boolean bool) {
        if (bool) {
            this.backGround = new MenuBackground(true);
        } else {
            this.backGround = new MenuBackground(false);
        }

    }

    @Override
    public boolean shouldStop() {
        if (this.stop) {
            this.stop = false;
            return true;
        }

        return false;
    }
}
