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

    /**
     * Store new data or replace existing data stored under a {@code key} with the new
     * data.
     *
     * The method throws an exception if the {@code key} is {@code null}.
     *
     * @param key  a unique identifier of the location where the data should be
     *             stored or replaced. Must not be {@code null}.
     * @param data new data to be stored under the given {@code key}. May be {@code null},
     *             in which case the data will be removed from the store.
     * @return {@code null} if there are no data stored under the given {@code key}
     *          or any replaced data previously stored under the {@code key}.
     *
     * @throws NullPointerException     in case the {@code key} is {@code null}.
     */
    @Override
    public <T extends Serializable> Serializable put(String key, T data) {
        if(key==null){
            throw new NullPointerException("Key is null.");
        }
        if(data == null){
            return (T)this.concurrentHashMap.remove(key);
        }
        return (T)this.concurrentHashMap.put(key, data);
    }

    /**
     * Retrieve data from the data store.
     *
     * The method retrieves the data stored under a unique {@code key} from the data store
     * and returns the data cast into the Java type specified by {@code type} parameter.
     *
     * @param key  a unique identifier of the location from which the stored data should be
     *             retrieved. Must not be {@code null}.
     * @param type expected Java type of the data being retrieved. Must not be {@code null}.
     * @return retrieved data or {@code null} if there are no data stored under the given
     *         {@code key}.
     *
     * @throws NullPointerException in case the {@code key} or {@code type} is {@code null}.
     * @throws ClassCastException   in case the data stored under the given {@code key}
     *                              cannot be cast to the Java type represented by
     *                              {@code type} parameter.
     */
    @Override
    public <T extends Serializable> T get(String key, Class<T> type) {
        if(key == null){
            throw new NullPointerException("Key is null.");
        }
        if(type == null){
            throw new NullPointerException("Type is null.");
        }
        Object object = this.concurrentHashMap.get(key);
        if(type.isInstance(object)){
            return (T)object;
        }
        throw new ClassCastException("Stored value is "+object.getClass().getName()+" not "+type.getName());
    }

    /**
     * Save current content of the data store to an output stream.
     *
     * The operation is guaranteed to produce a consistent snapshot of the data store
     * inner state.
     *
     * @param out output stream where the content of the data store is saved.
     *            The method does not close the stream.
     *
     * @throws IOException in case the save operation failed.
     */
    @Override
    public void save(OutputStream out) throws IOException {
        try{
            FSTObjectOutput fstOut = new FSTObjectOutput(out);
            fstOut.writeObject(this.concurrentHashMap);
            fstOut.close();
        }catch(Exception e){
            throw new IOException(e);
        }
    }

    /**
     * Load content of the data store from an input stream.
     *
     * Any content previously stored in the data store will be discarded and replaced
     * with the content loaded from the input stream.
     *
     * @param in input stream from which the content of the data store is loaded.
     *           The method does not close the stream.
     *
     * @throws IOException in case the load operation failed.
     */
    @Override
    public void load(InputStream in) throws IOException {
        try {
            FSTObjectInput fstIn = new FSTObjectInput(in);
            this.concurrentHashMap = (ConcurrentHashMap<String, Serializable>) fstIn.readObject();
            fstIn.close();
        } catch (ClassNotFoundException ex) {
            throw new IOException("There is a problem with the inputstream structure.",ex);
        }
    }

}
