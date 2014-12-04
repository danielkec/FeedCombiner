package cz.kec.wls.feedcombiner.datastore.impl;

import cz.kec.wls.feedcombiner.datastore.InMemoryDataStore;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Stores application server-side state. 
 * (e.g. combined feed details, fetched feed entries so there is no need to
 * always fetch external resources when a request for a combined feed is
 * being processed). Tries to come up with the most efficient implementation 
 * that will handle 1000 concurrent clients with the following expected 
 * usage pattern:
 * <li/>80% of data read operations
 * <li/>15% of new data store operations
 * <li/>5% of update data store operations
 * <li/>expected save:load operation ratio of 1:2
 * 
 * @author Daniel Kec <daniel at kecovi.cz>
 */
public class InMemoryDataStoreSingleton implements InMemoryDataStore{
    /**
     * Initialization on Demand Holder.
     * makes singleton thread safe
     */
    private static class LazyHolder {
        private static final InMemoryDataStoreSingleton INSTANCE = new InMemoryDataStoreSingleton();
    }
       
    private InMemoryDataStoreSingleton() {
    }
    
    public static InMemoryDataStoreSingleton getInstance(){
        return LazyHolder.INSTANCE;
    }
    
    @Override
    public synchronized <T extends Serializable> Serializable put(String key, T data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized <T extends Serializable> T get(String key, Class<T> type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized void save(OutputStream out) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized void load(InputStream in) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
