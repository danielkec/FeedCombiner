package cz.kec.wls.feedcombiner.datastore;

import cz.kec.wls.feedcombiner.datastore.impl.CombinedFeedDaoImpl;
import cz.kec.wls.feedcombiner.datastore.impl.InMemoryDataStoreSingleton;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 4, 2014
 */
public final class DaoFactory {
    //not instantiable
    private DaoFactory() {}
    
    public static CombinedFeedDao getCombinedFeedDao(){
        return new CombinedFeedDaoImpl(InMemoryDataStoreSingleton.getInstance());
    }
}
