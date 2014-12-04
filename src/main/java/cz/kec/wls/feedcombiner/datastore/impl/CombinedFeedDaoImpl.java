package cz.kec.wls.feedcombiner.datastore.impl;

import cz.kec.wls.feedcombiner.datastore.CombinedFeedDao;
import cz.kec.wls.feedcombiner.datastore.InMemoryDataStore;
import cz.kec.wls.feedcombiner.model.CombinedFeed;
import java.util.ArrayList;
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
        //because there is no keyset in the InMemoryDataStore
        InMemoryKeySet inMemoryKeySet = 
                this.inMemoryDataStore.get(InMemoryKeySet.KEYSET_KEY, InMemoryKeySet.class);
        if(inMemoryKeySet==null){
            this.inMemoryDataStore.put(InMemoryKeySet.KEYSET_KEY,new InMemoryKeySet());
        }
    }
    
    @Override
    public List<CombinedFeed> getAllCombinedFeeds() {
        InMemoryKeySet keySet = this.inMemoryDataStore.get(InMemoryKeySet.KEYSET_KEY,InMemoryKeySet.class);
        ArrayList<CombinedFeed> feedList = new ArrayList<CombinedFeed>();
        for (String key : keySet) {
            feedList.add(this.inMemoryDataStore.get(key,CombinedFeed.class));
        }
        return feedList;
    }
    @Override
    public synchronized void createCombinedFeed(CombinedFeed combinedFeed) {
        this.inMemoryDataStore.put(combinedFeed.getName(), combinedFeed);
        InMemoryKeySet keySet = this.inMemoryDataStore.get(InMemoryKeySet.KEYSET_KEY, InMemoryKeySet.class);
        keySet.add(combinedFeed.getName());
        this.inMemoryDataStore.put(InMemoryKeySet.KEYSET_KEY, keySet);
        
    }

    @Override
    public void updateCombinedFeed(CombinedFeed combinedFeed) {
        this.createCombinedFeed(combinedFeed);
    }

    @Override
    public synchronized void deleteCombinedFeed(CombinedFeed combinedFeed) {
        this.inMemoryDataStore.put(combinedFeed.getName(), null);
        InMemoryKeySet keySet = this.inMemoryDataStore.get(InMemoryKeySet.KEYSET_KEY, InMemoryKeySet.class);
        keySet.remove(combinedFeed.getName());
        this.inMemoryDataStore.put(InMemoryKeySet.KEYSET_KEY, keySet);
    }

}
