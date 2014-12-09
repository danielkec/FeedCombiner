package cz.kec.wls.feedcombiner.utils;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * DateUtils tests
 * @author Daniel Kec <daniel at kecovi.cz>
 */
public class DateUtilsTest {
    
    public DateUtilsTest() {
    }

    /**
     * Test of humanReadableDuration method, of class DateUtils.
     */
    @Test
    public void humanReadableDuration() throws InterruptedException {
        System.out.println("humanReadableDuration");
        Date olderDate = new Date(1418138845192L);//3sec duration
        Date newerDate = new Date(1418138848192L);
        String expResult = "3s".trim();
        String result = DateUtils.humanReadableDuration(olderDate, newerDate).trim();
        System.out.println("Result should be "+expResult+ " and is " +result);
        assertEquals(expResult, result);
    }
    
}
