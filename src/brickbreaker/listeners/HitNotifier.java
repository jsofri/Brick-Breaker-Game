package brickbreaker.listeners;


/**
 * objects that implement this send notifications when they are being hit.
 */
public interface HitNotifier {


    /**
     * add a HitListener object as a listener to hit events.
     *
     * @param hl HitListener object
     */
        void addHitListener(HitListener hl);

    /**
     * remove a HitListener object from a list of listeners to hit events.
     *
     * @param hl HitListener object
     */
        void removeHitListener(HitListener hl);

}
