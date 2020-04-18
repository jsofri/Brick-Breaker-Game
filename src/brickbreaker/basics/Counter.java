package brickbreaker.basics;


/**
 * This class is a counter - a number can be increased or decreased.
 */
public class Counter {


    /**
     * this is the value of the counter.
     */
    private int counter;

    /**
     * constructor - creates a counter with the value 0, use other constructor.
     */
    public Counter() {
        this(0);
    }

    /**
     * constructor.
     *
     * @param number the number to set for the counter
     */
    public Counter(int number) {
        this.counter = number;
    }

    /**
     * add number to current count.
     *
     * @param number int - the number to add
     */
    public void increase(int number) {
        this.counter += number;
    }

    /**
     * add 1 to count using polymorphism.
     */
    public void increase() {
        this.increase(1);
    }

    /**
     * subtract number from current count.
     *
     * @param number int - the number to substract
     */
    public void decrease(int number) {
        this.counter -= number;
    }

    /**
     * decrease 1 from count using polymorphism.
     */
    public void decrease() {
        this.decrease(1);
    }
    /**
     * @return int - current value of counter
     */
    public int getValue() {
        return this.counter;
    }
}
