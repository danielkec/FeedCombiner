package cz.kec.wls.feedcombiner.model;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 4, 2014
 */
public class CombinedFeed implements Serializable{
    private final String name;
    private String description;
    private List<URI> uris = new ArrayList<URI>();

    public CombinedFeed(String name, String description, List<URI> uris) {
        this.name = name;
        this.description = description;
        this.uris = uris;
    }

    public CombinedFeed(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<URI> getUris() {
        return uris;
    }

    public void setUris(List<URI> uris) {
        this.uris = uris;
    }
    
}
