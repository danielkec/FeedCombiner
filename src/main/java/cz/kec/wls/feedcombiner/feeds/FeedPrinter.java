package cz.kec.wls.feedcombiner.feeds;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.SyndFeedOutput;
import cz.kec.wls.feedcombiner.model.CombinedFeed;
import java.net.URI;
import java.util.List;

/**
 * Prints combined feed to string in desired format.
 * 
 * @see CombinedFeed
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 5, 2014
 */
public class FeedPrinter {
    private final CombinedFeed feed;

    /**
     * Creates new printer for suplemented feed
     * @param feed feed to be printed
     */
    public FeedPrinter(CombinedFeed feed) {
        this.feed = feed;
    }

    public String printToHTML(){
        return null;
    }
    public String printToJSON(){
        return null;
    }
    public String printToATOM(){
        SyndFeed feed = new SyndFeedImpl();
            feed.setFeedType("atom_1.0");
            feed.setTitle("Muj prvni feed!");
            FeedCollector feedCollector = new FeedCollector(new URI[]{
            List<SyndFeed> wireFeeds = feedCollector.collect();
            FeedMixer feedMixer = new FeedMixer(wireFeeds);
            feedMixer.mix(feed);
            SyndFeedOutput output = new SyndFeedOutput();
    }
    
}
    