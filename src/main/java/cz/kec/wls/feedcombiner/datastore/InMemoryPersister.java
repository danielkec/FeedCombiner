package cz.kec.wls.feedcombiner.datastore;

/**
 * Is able to persist InMemoryDataStore in a way dependent on implmentation.
 *
 * @see InMemoryDataStore
 * @author Daniel Kec <daniel at kecovi.cz>
 * @sice Dec 8, 2014
 */
public interface InMemoryPersister {

    /**
     * Persist InMemoryDataStore.
     */
    void persistDefaultInMemoryStore();

    /**
     * Load InMemoryDataStore from persisted state.
     */
    void loadPersistedInMemoryStore();
}
