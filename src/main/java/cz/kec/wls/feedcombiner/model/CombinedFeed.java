package cz.kec.wls.feedcombiner.model;

import com.rometools.rome.feed.synd.SyndEntry;
import java.io.Serializable;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing feed which is mixing several others feeds entries.
 * Sources of all original feeds are represented by uris.
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 4, 2014
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CombinedFeed implements Serializable {

    private final String name;
    private final String encodedName;
    private String description;
    private List<URI> uris = new ArrayList<URI>();
    @XmlTransient//dont need entries in the overview
    private List<SyndEntry> entries = new ArrayList<SyndEntry>();

    /**
     * Create new combined feed.
     *
     * @param name unique name of the combined feed, works as id
     * @param description short description of the combined feed
     * @param uris feed sources(RSS or ATOM)
     */
    public CombinedFeed(String name, String description, List<URI> uris) {
        this.name = name;
        this.encodedName = URLEncoder.encode(name);
        this.description = description;
        this.uris = uris;
    }

    /**
     * Create new combined feed with no sources(uri).
     *
     * @param name unique name of the combined feed, works as id
     * @param description short description of the combined feed
     */
    public CombinedFeed(String name, String description) {
        this.name = name;
        this.encodedName = URLEncoder.encode(name);
        this.description = description;
    }

    /**
     * Name of this combined feed, have to be unique
     *
     * @return the name(identificator)
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a short description of the combined feed
     *
     * @return description of the combined feed
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set a short description of the combined feed
     *
     * @param description description of the combined feed
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sources of all original feeds are represented by their uris.
     *
     * @return a list of java.net.URI
     */
    public List<URI> getUris() {
        return uris;
    }

    /**
     * Sources of all original feeds are represented by their uris.
     *
     * @param uris a list of java.net.URI
     */
    public void setUris(List<URI> uris) {
        this.uris = uris;
    }

    public List<SyndEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<SyndEntry> entries) {
        this.entries = entries;
    }
}
