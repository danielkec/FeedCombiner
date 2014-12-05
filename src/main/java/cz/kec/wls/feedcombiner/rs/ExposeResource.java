package cz.kec.wls.feedcombiner.rs;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import cz.kec.wls.feedcombiner.feeds.FeedCollector;
import cz.kec.wls.feedcombiner.feeds.FeedMixer;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Exposes the contents of a combined feed in HTML, JSON and ATOM format
 * @author Daniel Kec <daniel at kecovi.cz>
 */
@Path("expose")
public class ExposeResource {
    @GET
    @Produces("application/atom+xml")
    public String getCombinedATOM(){
        try {
            SyndFeed feed = new SyndFeedImpl();
            feed.setFeedType("atom_1.0");
            feed.setTitle("Muj prvni feed!");
            FeedCollector feedCollector = new FeedCollector(new URI[]{
            URI.create("http://www.reddit.com/r/worldnews/.rss"),
            URI.create("http://www.reddit.com/r/java/.rss"),
            URI.create("http://www.buzzfeed.com/index.xml"),
            URI.create("http://feeds.dzone.com/javalobby/frontpage"),
            URI.create("http://feeds.delicious.com/v2/rss/OracleTechnologyNetwork/otnheadlines")});
            List<SyndFeed> wireFeeds = feedCollector.collect();
            FeedMixer feedMixer = new FeedMixer(wireFeeds);
            feedMixer.mix(feed);
            SyndFeedOutput output = new SyndFeedOutput();   
            String response = output.outputString(feed,true);
            return response;
            
            //return "";
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ExposeResource.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        } catch (FeedException ex) {
            Logger.getLogger(ExposeResource.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        } catch (Exception ex) {
            Logger.getLogger(ExposeResource.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        } catch (Error ex) {
            Logger.getLogger(ExposeResource.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
    }
}
