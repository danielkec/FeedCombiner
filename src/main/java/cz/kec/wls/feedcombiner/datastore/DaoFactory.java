package cz.kec.wls.feedcombiner.datastore;

import cz.kec.wls.feedcombiner.datastore.impl.CombinedFeedDaoImpl;
import cz.kec.wls.feedcombiner.datastore.impl.InMemoryDataStoreSingleton;
import cz.kec.wls.feedcombiner.datastore.impl.InMemoryPersisterImpl;

/**
 * Factory for all DAO objects used in the feedcombiner project
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 4, 2014
 */
public final class DaoFactory {
    //not instantiable
    private DaoFactory() {}

    /**
     * Returns implementation of dao for CombinedFeed
     * @return CombinedFeed dao
     */
    public static CombinedFeedDao getCombinedFeedDao(){
        //actual implementation of persistent CombinedFeeds
        return new CombinedFeedDaoImpl(InMemoryDataStoreSingleton.getInstance());
    }

    /**
     * Returns persister which can persist whole InMemoryDataStore
     * @see InMemoryDataStore
     * @return new implementation of the InMemoryPersister
     */
    public static InMemoryPersister getInMemoryPersister(){
        return new InMemoryPersisterImpl(InMemoryDataStoreSingleton.getInstance());
    }
}
