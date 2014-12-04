package cz.kec.wls.feedcombiner.datastore.impl;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 4, 2014
 */
public class InMemoryKeySet extends CopyOnWriteArraySet<String> implements Serializable{
    public static final String KEYSET_KEY = "KEYSET_KEY";

    public InMemoryKeySet() {
       super();
    }
    
}
