package cz.kec.wls.feedcombiner.utils;

import cz.kec.wls.feedcombiner.datastore.CombinedFeedDao;
import cz.kec.wls.feedcombiner.datastore.DaoFactory;
import cz.kec.wls.feedcombiner.model.CombinedFeed;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates mock data, so a new instance of feed combiner is not so empty.
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since 14:15:26 6.12.2014
 */
public abstract class MockUtils {

    private static final Logger LOG = LoggerFactory.getLogger(MockUtils.class);

    public static void createMockFeeds() {
        CombinedFeedDao combinedFeedDao = DaoFactory.getCombinedFeedDao();
        //check if there are real feeds in the storage
        if (!combinedFeedDao.getAllCombinedFeeds().isEmpty()) {
            return;//thera are some persisted feeds in the storage
        }

        LOG.info("There are no combine feeds in the storage. Let's add some mocks.");

        CombinedFeed combinedFeed = new CombinedFeed("All in one", "All aggregated to one mighty feed.");
        combinedFeed.getUris().add(URI.create("http://www.reddit.com/r/worldnews/.rss"));
        combinedFeed.getUris().add(URI.create("http://www.buzzfeed.com/index.xml"));
        combinedFeed.getUris().add(URI.create("http://marek.potociar.net/feed/"));
        combinedFeedDao.createCombinedFeed(combinedFeed);

        CombinedFeed combinedFeed2 = new CombinedFeed("BuzzFeed and Zive.cz", "BuzzFeed mixed with Zive");
        combinedFeed2.getUris().add(URI.create("http://www.zive.cz/rss/sc-47/"));
        combinedFeed2.getUris().add(URI.create("http://www.buzzfeed.com/index.xml"));
        combinedFeedDao.createCombinedFeed(combinedFeed2);
    }
}
