package cz.kec.wls.feedcombiner.feeds;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since 3.12.2014
 */
public class FeedMixer {
    private final List<SyndFeed> feedList;

    public FeedMixer(List<SyndFeed> feedList) {
        this.feedList = feedList;
    }

    public SyndFeed mix(SyndFeed f){   
        ArrayList<SyndEntry> entryList = new ArrayList<SyndEntry>();
        for (SyndFeed wirefeed : feedList) {
            entryList.addAll(wirefeed.getEntries());
        }
        sortByDate(entryList);
        f.setEntries(entryList);
        return f;
    }
    
    private void sortByDate(List<SyndEntry> entryList){
        Collections.sort(entryList,new EntryComparator());
    }
}
