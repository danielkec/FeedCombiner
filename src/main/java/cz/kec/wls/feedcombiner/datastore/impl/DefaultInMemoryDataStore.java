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
public class DefaultInMemoryDataStore implements InMemoryDataStore{

    @Override
    public <T extends Serializable> Serializable put(String key, T data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T extends Serializable> T get(String key, Class<T> type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save(OutputStream out) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void load(InputStream in) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
