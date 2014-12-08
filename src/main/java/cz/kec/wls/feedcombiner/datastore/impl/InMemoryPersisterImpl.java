package cz.kec.wls.feedcombiner.datastore.impl;

import cz.kec.wls.feedcombiner.datastore.InMemoryDataStore;
import cz.kec.wls.feedcombiner.datastore.InMemoryPersister;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 8, 2014
 */
public class InMemoryPersisterImpl implements InMemoryPersister{
    private final InMemoryDataStore inMemoryDataStore;
   
    public InMemoryPersisterImpl(InMemoryDataStore inMemoryDataStore) {
        this.inMemoryDataStore = inMemoryDataStore;
    }
    
    @Override
    public void persistDefaultInMemoryStore() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadPersistedInMemoryStore() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
