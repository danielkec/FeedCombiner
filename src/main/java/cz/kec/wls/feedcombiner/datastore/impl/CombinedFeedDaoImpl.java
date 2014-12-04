package cz.kec.wls.feedcombiner.datastore.impl;

import cz.kec.wls.feedcombiner.datastore.CombinedFeedDao;
import cz.kec.wls.feedcombiner.datastore.InMemoryDataStore;
import cz.kec.wls.feedcombiner.model.CombinedFeed;
import java.util.List;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 4, 2014
 */
public class CombinedFeedDaoImpl implements CombinedFeedDao{
    private final InMemoryDataStore inMemoryDataStore;

    public CombinedFeedDaoImpl(InMemoryDataStore inMemoryDataStore) {
        this.inMemoryDataStore = inMemoryDataStore;
    }
    
    @Override
    public List<CombinedFeed> getAllCombinedFeeds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createCombinedFeed(CombinedFeed combinedFeed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateCombinedFeed(CombinedFeed combinedFeed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteCombinedFeed(CombinedFeed combinedFeed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
