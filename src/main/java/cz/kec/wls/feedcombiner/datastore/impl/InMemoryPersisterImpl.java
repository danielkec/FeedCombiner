package cz.kec.wls.feedcombiner.datastore.impl;

import cz.kec.wls.feedcombiner.datastore.InMemoryDataStore;
import cz.kec.wls.feedcombiner.datastore.InMemoryPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of InMemoryPersister able to persist InMemoryDataStore, in a
 * file.
 *
 * @see InMemoryDataStore
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 8, 2014
 */
public class InMemoryPersisterImpl implements InMemoryPersister {
    private final Logger LOG = LoggerFactory.getLogger(InMemoryPersisterImpl.class);
    private final InMemoryDataStore inMemoryDataStore;

    /**
     * Creates persister ready to persist.
     * @param inMemoryDataStore
     */
    public InMemoryPersisterImpl(InMemoryDataStore inMemoryDataStore) {
        this.inMemoryDataStore = inMemoryDataStore;
    }

    /**
     * Persist InMemoryDatastore to the file.
     */
    @Override
    public void persistDefaultInMemoryStore() {
        LOG.info("Persisting datastore");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Loads InMemoryDatastore from file.
     */
    @Override
    public void loadPersistedInMemoryStore() {
        LOG.info("Loading prsisted datastore");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
