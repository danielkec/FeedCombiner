package cz.kec.wls.feedcombiner.datastore;

import cz.kec.wls.feedcombiner.model.CombinedFeed;
import java.util.List;

/**
 * DAO for persisting CombinedFeed
 *
 * @see CombinedFeed
 * @author Daniel Kec <daniel at kecovi.cz>
 * @sice Dec 4, 2014
 */
public interface CombinedFeedDao {

    /**
     * Returns all combined feeds saved in Feed Combiner
     *
     * @return all availaible combined feeds
     */
    public List<CombinedFeed> getAllCombinedFeeds();

    /**
     * Checks if CombinedFeed with same id/title is already persisted
     *
     * @param title
     * @return true if contains feed with same title
     */
    public boolean containsCombinedFeed(String title);

    /**
     * Returns CombineFeed by it's title/name, name is used as indentifier.
     *
     * @param name CombinedFeed name to be used as a key
     * @return CombinedFeed with specefied name or null if nothing is found
     */
    public CombinedFeed getCombinedFeedByName(String name);

    /**
     * Add CombinedFeed to the datastore.
     *
     * @param combinedFeed to be added to InMemoryDataStore
     * @see InMemoryDataStore
     */
    public void createCombinedFeed(CombinedFeed combinedFeed);

    /**
     * Updates persisted combined feed. If there isn't already persisted
     * combined feed does nothing and returns false
     *
     * @param combinedFeed updated combined feed
     * @return true if updated / false if there wasn't same combined feed to
     * update
     */
    public boolean updateCombinedFeed(CombinedFeed combinedFeed);

    /**
     * Removes persisted combine feed. Or return false if there is nothing to
     * remove.
     *
     * @param title id of combine feed to be removed
     * @return true on succesfull removal
     */
    public boolean deleteCombinedFeed(String title);

    /**
     * Removes all persisted combine feeds. Or return false if there is nothing to
     * remove.
     *
     * @return true on succesfull removal
     */
    public boolean deleteAllCombinedFeeds();
}
