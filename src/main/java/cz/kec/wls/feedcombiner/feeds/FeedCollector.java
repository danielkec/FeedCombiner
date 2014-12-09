package cz.kec.wls.feedcombiner.feeds;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.http.HTTPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fetches feeds from suplemented uris.
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since 3.12.2014
 */
public class FeedCollector {

    private final Logger LOG = LoggerFactory.getLogger(FeedCollector.class);

    private final List<URI> uris;

    /**
     * Creates new collector prepered to collect feeds from suplemented uris.
     *
     * @param uris to fetche feeds from
     */
    public FeedCollector(List<URI> uris) {
        this.uris = uris;
    }

    /**
     * Fetches all feeds it can from the uris it has been constructed with.
     *
     * @return List of SyndFeeda
     * @see SyndFeed
     */
    public List<SyndFeed> collect() {
        ArrayList<SyndFeed> syndEntryList = new ArrayList<SyndFeed>();
        for (URI uri : uris) {
            try {
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed inFeed = input.build(new XmlReader(uri.toURL()));
                syndEntryList.add(inFeed);
            } catch (HTTPException e) {
                LOG.warn("Suplemented URI " + uri + " is not reachable.", e);
            } catch (IOException e) {
                LOG.warn("There is a problem when connecting URI " + uri + "", e);
            } catch (IllegalArgumentException e) {
                LOG.warn("There is a problem when connecting URI " + uri + "", e);
            } catch (FeedException e) {
                LOG.warn("There is a problem when connecting URI " + uri + "", e);
            }
        }
        return syndEntryList;
    }

}
