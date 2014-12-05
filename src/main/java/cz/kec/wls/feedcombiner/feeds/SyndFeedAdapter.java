package cz.kec.wls.feedcombiner.feeds;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import cz.kec.wls.feedcombiner.model.CombinedFeed;
import java.util.List;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 5, 2014
 */
public class SyndFeedAdapter extends SyndFeedImpl{
    private final CombinedFeed combinedFeed;

    public SyndFeedAdapter(CombinedFeed combinedFeed) {
        super();
        this.combinedFeed = combinedFeed;
        super.setTitle(combinedFeed.getName());
        super.setDescription(combinedFeed.getDescription());
    }

    @Override
    public String getTitle() {
        return combinedFeed.getName();
    }

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
