package cz.kec.wls.feedcombiner.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 9, 2014
 */
public class DateUtils {

    private DateUtils() {//not instantiable
    }

    
    public static String humanReadableDuration(Date from,Date to){
            StringBuilder sb = new StringBuilder();
            if(from==null)return "";
            if(to  ==null)return "";
            long millis = to.getTime()-from.getTime();
            
            long hours   = TimeUnit.MILLISECONDS.toHours(millis);
            millis=millis-TimeUnit.HOURS.toMillis(hours);
            
            long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
            millis=millis-TimeUnit.MINUTES.toMillis(minutes);
            
            long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
            millis=millis-TimeUnit.SECONDS.toMillis(seconds);
                    
            if(hours  !=0)sb.append(hours  ).append("h ");
            if(minutes!=0)sb.append(minutes).append("m ");
            if(seconds!=0)sb.append(seconds).append("s ");
            if(millis !=0)sb.append(millis ).append("ms ");
            return sb.toString();
        }
    
}
