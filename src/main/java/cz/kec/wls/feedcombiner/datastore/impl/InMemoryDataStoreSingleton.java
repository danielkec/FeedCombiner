package cz.kec.wls.feedcombiner.datastore.impl;

import cz.kec.wls.feedcombiner.datastore.InMemoryDataStore;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

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
 * @since Dec 3, 2014
 */
public class InMemoryDataStoreSingleton implements InMemoryDataStore{
    /**
     * Thread safe map for storing in memory
     */
    private ConcurrentHashMap<String, Serializable> concurrentHashMap;
    /**
     * Initialization on Demand Holder.
     * makes singleton thread safe
     */
    private static class LazyHolder {
        private static final InMemoryDataStoreSingleton INSTANCE = new InMemoryDataStoreSingleton();
    }

    private InMemoryDataStoreSingleton() {
        this.concurrentHashMap = new ConcurrentHashMap<String,Serializable>(128);
    }

    /**
     * Returns instance of thread safe singleton InMemoryDateStore implementation
     * @return singleton instance of InMemoryDateStore implementation
     */
    public static InMemoryDataStoreSingleton getInstance(){
        return LazyHolder.INSTANCE;
    }

    @Override
    public <T extends Serializable> Serializable put(String key, T data) {
        if(data == null){
            return (T)this.concurrentHashMap.remove(key);
        }
        return (T)this.concurrentHashMap.put(key, data);
    }

    @Override
    public <T extends Serializable> T get(String key, Class<T> type) {
        return (T)this.concurrentHashMap.get(key);
    }

    @Override
    public void save(OutputStream outputStream) throws IOException {
        FSTObjectOutput out = new FSTObjectOutput(outputStream);
        out.writeObject(this.concurrentHashMap);
        out.close();
    }

    @Override
    public void load(InputStream inputStream) throws IOException {
        try {
            FSTObjectInput in = new FSTObjectInput(inputStream);
            this.concurrentHashMap = (ConcurrentHashMap<String, Serializable>) in.readObject();
            in.close();
        } catch (ClassNotFoundException ex) {
            throw new IOException("There is a problem with the inputstream structure.",ex);
        }
    }

}
