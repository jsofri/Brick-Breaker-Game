package brickbreaker.basics;


/**
 * A task is something that needs to happen.
 * or something that we can run() and return a value.
 *
 * @param <T> type of Object that implementing methods will get
 */
public interface Task<T> {

    /**
     * this method has a different implementation for each object.
     * it's always inside an anonymous class.
     *
     * @return type of object
     */
    T run();
}
