package cz.kec.wls.feedcombiner.feeds;

import com.rometools.rome.feed.synd.SyndEntry;
import java.util.Comparator;

/**
 * 
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since 3.12.2014
 */
public class EntryComparator implements Comparator<SyndEntry>{

    public int compare(SyndEntry o1, SyndEntry o2) {
        if(o1.getPublishedDate()!=null && o1.getPublishedDate()!=null){
            if (o1.getPublishedDate().after(o2.getPublishedDate())) {
                return 1;
            }
            return -1;
        }
        if(o1.getUpdatedDate()!=null && o1.getUpdatedDate()!=null){
            if (o1.getUpdatedDate().after(o2.getUpdatedDate())) {
                return 1;
            }
            return -1;
        }
        return 0;
    }

}
