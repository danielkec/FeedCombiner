package cz.kec.wls.feedcombiner.exceptions;

/**
 * Exception raised when wrong update interval is used.
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 9, 2014
 */
public class WrongIntervalException extends Exception {

    /**
     * Creates new exception refering about wrong refresh interval is used.
     *
     * @param max max posible interval value
     * @param min min posible interval value
     * @param actual actual value which has been used
     */
    public WrongIntervalException(int max, int min, int actual) {
        super("Refresh inteval is " + actual + "sec but must be within " + min + "sec to " + max + "sec");
    }

}
