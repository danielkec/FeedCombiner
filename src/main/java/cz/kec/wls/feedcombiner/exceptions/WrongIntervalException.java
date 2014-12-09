package cz.kec.wls.feedcombiner.exceptions;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 9, 2014
 */
public class WrongIntervalException extends Exception{

    public WrongIntervalException(int max, int min,int actual) {
        super("Refresh inteval is "+actual+"sec but must be within "+min+"sec to "+max+"sec");        
    }

}
