package cz.kec.wls.feedcombiner.beans;

import java.util.Date;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 */
@Singleton
public class RefreshFeedsTimerBean {

    @Schedule(dayOfWeek = "*", month = "*", hour = "*", dayOfMonth = "*", year = "*", minute = "*", second = "5", persistent = false)    
    public void refreshFeedsTimer() {
        System.out.println("Timer event: " + new Date());
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
