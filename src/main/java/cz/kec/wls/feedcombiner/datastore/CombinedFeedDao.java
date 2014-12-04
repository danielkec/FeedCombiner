package cz.kec.wls.feedcombiner.datastore;

import cz.kec.wls.feedcombiner.model.CombinedFeed;
import java.util.List;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @sice Dec 4, 2014
 */
public interface CombinedFeedDao {
       public List<CombinedFeed> getAllCombinedFeeds();
       public void createCombinedFeed(CombinedFeed combinedFeed);
       public void updateCombinedFeed(CombinedFeed combinedFeed);
       public void deleteCombinedFeed(CombinedFeed combinedFeed);
}
