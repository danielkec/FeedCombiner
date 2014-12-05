package cz.kec.wls.feedcombiner.model;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing feed which is mixing several others feeds entries.
 * Sources of all original feeds are represented by uris.
 * 
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 4, 2014
 */
public class CombinedFeed implements Serializable{
    private final String name;
    private String description;
    private List<URI> uris = new ArrayList<URI>();

    /**
     * Create new combined feed.
     * @param name unique name of the combined feed, works as id
     * @param description short description of the combined feed
     * @param uris feed sources(RSS or ATOM)
     */
    public CombinedFeed(String name, String description, List<URI> uris) {
        this.name = name;
        this.description = description;
        this.uris = uris;
    }
    
    /**
     * Create new combined feed with no sources(uri).
     * @param name unique name of the combined feed, works as id
     * @param description short description of the combined feed
     */
    public CombinedFeed(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    /**
     * Name of this combined feed, have to be unique
     * @return the name(identificator)
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a short description of the combined feed
     * @return description of the combined feed
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set a short description of the combined feed
     * @param description description of the combined feed
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sources of all original feeds are represented by their uris.
     * @return a list of java.net.URI
     */
    public List<URI> getUris() {
        return uris;
    }

    /**
     * Sources of all original feeds are represented by their uris.
     * @param uris a list of java.net.URI
     */
    public void setUris(List<URI> uris) {
        this.uris = uris;
    }
    
}
