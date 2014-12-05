package cz.kec.wls.feedcombiner.datastore.impl;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Thread safe key set.
 * Extension of CopyOnWriteArraySet, every time it updates, it copy its internal state.
 * This is normaly costly, but may be more efficient when mostly reading.
 * 
 * @see CopyOnWriteArraySet
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 4, 2014
 */
public class InMemoryKeySet extends CopyOnWriteArraySet<String> implements Serializable{
    /** Very improbable Combined Feed name */
    public static final String KEYSET_KEY = "KEYSET_KEY_MIICdgIBADANBgkqhkiG9w0BAQEFAASCAm";

    public InMemoryKeySet() {
       super();
    }
    
}
