package cz.kec.wls.feedcombiner.datastore;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @sice Dec 8, 2014
 */
public interface InMemoryPersister {
    void persistDefaultInMemoryStore();
    void loadPersistedInMemoryStore();
}
