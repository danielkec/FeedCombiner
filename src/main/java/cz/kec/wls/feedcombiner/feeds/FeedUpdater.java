package cz.kec.wls.feedcombiner.feeds;

import com.rometools.rome.feed.synd.SyndFeed;
import cz.kec.wls.feedcombiner.datastore.CombinedFeedDao;
import cz.kec.wls.feedcombiner.datastore.DaoFactory;
import cz.kec.wls.feedcombiner.datastore.InMemoryDataStore;
import cz.kec.wls.feedcombiner.model.CombinedFeed;
import java.util.List;

/**
 * Updates all combined feeds in the InMemoryDataStore.
 *
 * @see InMemoryDataStore
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 9, 2014
 */
public abstract class FeedUpdater {

    /**
     * Updates all combined feeds in the InMemoryDataStore.
     *
     * @see InMemoryDataStore
     */
    public static void updateAll() {
        CombinedFeedDao combinedFeedDao = DaoFactory.getCombinedFeedDao();
        List<CombinedFeed> allCombinedFeeds = combinedFeedDao.getAllCombinedFeeds();
        for (CombinedFeed combinedFeed : allCombinedFeeds) {
            FeedCollector feedCollector = new FeedCollector(combinedFeed.getUris());
            List<SyndFeed> syndFeeds = feedCollector.collect();
            FeedMixer feedMixer = new FeedMixer(syndFeeds);
            feedMixer.mix(combinedFeed);
            DaoFactory.getCombinedFeedDao().updateCombinedFeed(combinedFeed);
        }
    }
}
