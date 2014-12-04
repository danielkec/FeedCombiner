package cz.kec.wls.feedcombiner.feeds;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.rometools.rome.io.impl.FeedParsers;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.http.HTTPException;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since 3.12.2014
 */
public class FeedCollector {
    private final URI[] uris;

    public FeedCollector(URI[] uris) {
        this.uris = uris;        
    }
    
    public List<SyndFeed> collect(){
        FeedParsers feedParsers = new FeedParsers();
        ArrayList<SyndFeed> syndEntryList = new ArrayList<SyndFeed>();
        for (URI uri : uris) {
            try {
                //Document jdom = XMLUtils.toJDOM(HttpUtils.get(uri.toURL()));
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed inFeed = input.build(new XmlReader(uri.toURL()));
                syndEntryList.add(inFeed);
            } catch (HTTPException e) {
                Logger.getLogger(FeedCollector.class.getName()).log(Level.SEVERE, "Suplemented URI " + uri + " is not reachable.", e);
            } catch (Exception e) {
                Logger.getLogger(FeedCollector.class.getName()).log(Level.SEVERE, "There is a problem when connecting URI " + uri + "", e);
            }
        }
        return syndEntryList;
    }
    
    
    
}
