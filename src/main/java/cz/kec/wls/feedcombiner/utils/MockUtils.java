package cz.kec.wls.feedcombiner.utils;

import com.rometools.rome.feed.synd.SyndFeed;
import cz.kec.wls.feedcombiner.datastore.CombinedFeedDao;
import cz.kec.wls.feedcombiner.datastore.DaoFactory;
import cz.kec.wls.feedcombiner.feeds.FeedCollector;
import cz.kec.wls.feedcombiner.feeds.FeedMixer;
import cz.kec.wls.feedcombiner.model.CombinedFeed;
import java.net.URI;
import java.util.List;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since 14:15:26 6.12.2014
 */
public class MockUtils {

    private MockUtils() {
    }

    public static void createMockFeeds() {
        CombinedFeed combinedFeed = new CombinedFeed("All in one", "All aggregated to one mighty feed.");
        combinedFeed.getUris().add(URI.create("http://www.reddit.com/r/worldnews/.rss"));
        combinedFeed.getUris().add(URI.create("http://www.buzzfeed.com/index.xml"));
        combinedFeed.getUris().add(URI.create("http://marek.potociar.net/feed/"));
        CombinedFeedDao combinedFeedDao = DaoFactory.getCombinedFeedDao();
        combinedFeedDao.createCombinedFeed(combinedFeed);

        CombinedFeed combinedFeed2 = new CombinedFeed("BuzzFeed and Zive.cz", "BuzzFeed mixed with Zive");
        combinedFeed2.getUris().add(URI.create("http://www.zive.cz/rss/sc-47/"));
        combinedFeed2.getUris().add(URI.create("http://www.buzzfeed.com/index.xml"));
        combinedFeedDao.createCombinedFeed(combinedFeed2);
    }

    public static void mockSync() {
        CombinedFeedDao combinedFeedDao = DaoFactory.getCombinedFeedDao();
        List<CombinedFeed> allCombinedFeeds = combinedFeedDao.getAllCombinedFeeds();
        for (CombinedFeed combinedFeed : allCombinedFeeds) {
            FeedCollector feedCollector = new FeedCollector(combinedFeed.getUris());
            List<SyndFeed> syndFeeds = feedCollector.collect();
            FeedMixer feedMixer = new FeedMixer(syndFeeds);
            feedMixer.mix(combinedFeed);
        }
    }
}
