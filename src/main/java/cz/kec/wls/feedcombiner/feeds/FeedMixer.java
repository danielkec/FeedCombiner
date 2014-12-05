package cz.kec.wls.feedcombiner.feeds;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility responsible for time ordered mixing of the source feeds to one 
 * combined feed.
 * 
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since 3.12.2014
 */
public class FeedMixer {
    private final List<SyndFeed> feedList;
    
    /**
     * Creates new feed mixer.
     * @param feedList a list of the source feeds to be mixed to one combined
     */
    public FeedMixer(List<SyndFeed> feedList) {
        this.feedList = feedList;
    }

    /**
     * Mixes all source feeds and returns one with combined entries
     * @param f combined feed to add entries to
     */
    public void mix(SyndFeed f){   
        ArrayList<SyndEntry> entryList = new ArrayList<SyndEntry>();
        for (SyndFeed feed : feedList) {
            entryList.addAll(feed.getEntries());
        }
        sortByDate(entryList);//sorting part
        f.setEntries(entryList);
    }
    
    /**
     * Sorting collection of all entries by EntryComparator
     * @see cz.kec.wls.feedcobiner.feeds.EntryComparator
     * @param entryList 
     */
    private void sortByDate(List<SyndEntry> entryList){
        Collections.sort(entryList,new EntryComparator());
    }
}
