package brickbreaker.basics;


import brickbreaker.animation.Animation;


/**
 * interface that is used for the Main menu operation of the game.
 *
 * @param <T> type of object that implementing classes will get when using it.
 */
public interface Menu<T> extends Animation {

    /**
     * add a selection to a menu.
     *
     * @param key String, the key to wait for
     * @param message String- line to print
     * @param returnVal return type of object
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * @return type depending on the menu and the selection of user
     */
    T getStatus();

    /**
     * add a sub menu.
     *
     * @param key String, key to press to get to the sub menu
     * @param message String -  line to print
     * @param subMenu Menu object to run when key is pressed
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);

    /**
     * helps to identify if menu is main menu or not.
     * this way we know what background we need.
     *
     * @param bool boolean - true if it is main menu
     */
    void mainMenu(boolean bool);
}
