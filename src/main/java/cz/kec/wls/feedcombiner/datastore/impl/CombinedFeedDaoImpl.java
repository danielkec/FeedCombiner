package cz.kec.wls.feedcombiner.datastore.impl;

import cz.kec.wls.feedcombiner.datastore.CombinedFeedDao;
import cz.kec.wls.feedcombiner.datastore.InMemoryDataStore;
import cz.kec.wls.feedcombiner.model.CombinedFeed;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of CombinedFeedDao. Because there is no keyset in the
 * InMemoryDataStore, is used InMemoryKeySet which is persisted same way as all
 * combined feeds.
 *
 * @see CombinedFeedDao
 * @see InMemoryKeySet
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 4, 2014
 */
public class CombinedFeedDaoImpl implements CombinedFeedDao {

    /**
     * InMemoryDataStore to be used by this dao
     */
    private final InMemoryDataStore inMemoryDataStore;

    /**
     * Creates new CombinedFeedDao implementation. With its own keyset above
     * InMemoryStore.
     *
     * @param inMemoryDataStore
     */
    public CombinedFeedDaoImpl(InMemoryDataStore inMemoryDataStore) {
        this.inMemoryDataStore = inMemoryDataStore;
        //because there is no keyset in the InMemoryDataStore
        getKeySet();
    }

    /**
     * Returns all combined feeds saved in Feed Combiner
     *
     * @return all availaible combined feeds
     */
    @Override
    public List<CombinedFeed> getAllCombinedFeeds() {
        InMemoryKeySet keySet = getKeySet();
        ArrayList<CombinedFeed> feedList = new ArrayList<CombinedFeed>();
        for (String key : keySet) {
            feedList.add(this.inMemoryDataStore.get(key, CombinedFeed.class));
        }
        return feedList;
    }

    /**
     * Checks if CombinedFeed with same id/title is already persisted
     *
     * @param title
     * @return true if contains feed with same title
     */
    @Override
    public boolean containsCombinedFeed(String title) {
        //reserved title/id for keyset
        if (InMemoryKeySet.KEYSET_KEY.equals(title)) {
            return true;
        }
        InMemoryKeySet keySet = getKeySet();
        return keySet.contains(title);
    }

    /**
     * Add CombinedFeed to the datastore.
     *
     * @param combinedFeed to be added to InMemoryDataStore
     * @see InMemoryDataStore
     */
    @Override
    public synchronized void createCombinedFeed(CombinedFeed combinedFeed) {
        this.inMemoryDataStore.put(combinedFeed.getName(), combinedFeed);
        InMemoryKeySet keySet = getKeySet();
        keySet.add(combinedFeed.getName());
    }

    /**
     * Updates persisted combined feed. If there isn't already persisted
     * combined feed does nothing and returns false
     *
     * @param combinedFeed updated combined feed
     * @return true if updated / false if there wasn't same combined feed to
     * update
     */
    @Override
    public synchronized boolean updateCombinedFeed(CombinedFeed combinedFeed) {
        if (containsCombinedFeed(combinedFeed.getName())) {
            this.createCombinedFeed(combinedFeed);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes persisted combine feed. Or return false if there is nothing to
     * remove.
     *
     * @param title id of combine feed to be removed
     * @return true on succesfull removal
     */
    @Override
    public synchronized boolean deleteCombinedFeed(String title) {
        InMemoryKeySet keySet = getKeySet();
        // return false if there is no feed with suplemented title/id
        if (!keySet.contains(title)) {
            return false;
        }
        this.inMemoryDataStore.put(title, null);
        keySet.remove(title);
        return true;
    }

    /**
     * Returns CombineFeed by it's title/name, name is used as indentifier.
     *
     * @param name CombinedFeed name to be used as a key
     * @return CombinedFeed with specefied name or null if nothing is found
     */
    @Override
    public CombinedFeed getCombinedFeedByName(String name) {
        return this.inMemoryDataStore.get(name, CombinedFeed.class);
    }

    /**
     * Gets InMemoryKeySet or creates new one if doesn't exist yet.
     *
     * @return KeySet from InMemoryDataStore
     * @see InMemoryDataStore
     */
    private InMemoryKeySet getKeySet() {
        InMemoryKeySet inMemoryKeySet
                = this.inMemoryDataStore.get(InMemoryKeySet.KEYSET_KEY, InMemoryKeySet.class);
        if (inMemoryKeySet == null) {
            this.inMemoryDataStore.put(InMemoryKeySet.KEYSET_KEY, new InMemoryKeySet());
        }
        return this.inMemoryDataStore.get(InMemoryKeySet.KEYSET_KEY, InMemoryKeySet.class);
    }
}
