package cz.kec.wls.feedcombiner.feeds;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import cz.kec.wls.feedcombiner.model.CombinedFeed;
import java.util.List;

/**
 * Adapter for CombinedFeed to be used as ROME's SyndFeed.
 *
 * @see CombinedFeed
 * @see SyndFeed
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 5, 2014
 */
public class SyndFeedAdapter extends SyndFeedImpl {
    
    /** Wrapped CombinedFeed */
    private final CombinedFeed combinedFeed;

    /**
     * Contructs wrapper around CmbinedFeed, so it can be used as SyndFeed
     *
     * @see CombinedFeed
     * @see SyndFeed
     * @param combinedFeed to be wrapped
     */
    public SyndFeedAdapter(CombinedFeed combinedFeed) {
        super();
        this.combinedFeed = combinedFeed;
        super.setTitle(combinedFeed.getName());
        super.setDescription(combinedFeed.getDescription());
    }

    /**
     * Returns name of the wrapped combined feed
     * @return name of the combined feed
     */
    @Override
    public String getTitle() {
        return combinedFeed.getName();
    }

    /**
     * Returns description of the wrapped combined feed
     * @return description of combined feed
     */
    @Override
    public String getDescription() {
        return combinedFeed.getDescription();
    }

    @Override
    public List<SyndEntry> getEntries() {
        return combinedFeed.getEntries();
    }

    @Override
    public void setEntries(List<SyndEntry> entries) {
        combinedFeed.setEntries(entries);
    }

}
