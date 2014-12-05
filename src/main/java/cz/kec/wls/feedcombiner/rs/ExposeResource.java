package cz.kec.wls.feedcombiner.rs;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import cz.kec.wls.feedcombiner.feeds.FeedCollector;
import cz.kec.wls.feedcombiner.feeds.FeedMixer;
import cz.kec.wls.feedcombiner.view.FeedPrinter;
import cz.kec.wls.feedcombiner.feeds.SyndFeedAdapter;
import cz.kec.wls.feedcombiner.model.CombinedFeed;
import java.net.URI;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Exposes the contents of a combined feed in HTML, JSON and ATOM format
 * @author Daniel Kec <daniel at kecovi.cz>
 */
@Path("expose")
public class ExposeResource {
    private Logger LOG = LoggerFactory.getLogger(ExposeResource.class);

    @GET
    @Produces("text/html")
    public String getOverView(){
        return "Ahoj svete";
    }

    @GET
    @Path("atom")
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
            LOG.error("Error ", ex);
            return ex.getMessage();
        } catch (FeedException ex) {
            LOG.error("Error ", ex);
            return ex.getMessage();
        } catch (Exception ex) {
            LOG.error("Error ", ex);
            return ex.getMessage();
        } catch (Error ex) {
            LOG.error("Error ", ex);
            return ex.getMessage();
        }
    }

    @GET
    @Path("html")
    @Produces("text/html")
    public String getCombinedHTML(){
         try {
        CombinedFeed combinedFeed = new CombinedFeed("test", "sssss");
        SyndFeed feedAdapter = new SyndFeedAdapter(combinedFeed);

        FeedCollector feedCollector = new FeedCollector(new URI[]{
            URI.create("http://www.reddit.com/r/worldnews/.rss"),
            URI.create("http://www.reddit.com/r/java/.rss")
//            URI.create("http://www.buzzfeed.com/index.xml"),
//            URI.create("http://feeds.dzone.com/javalobby/frontpage"),
//            URI.create("http://feeds.delicious.com/v2/rss/OracleTechnologyNetwork/otnheadlines")
        });
            List<SyndFeed> wireFeeds = feedCollector.collect();
            FeedMixer feedMixer = new FeedMixer(wireFeeds);
            feedMixer.mix(feedAdapter);

        FeedPrinter feedPrinter = new FeedPrinter(combinedFeed);
        return feedPrinter.printToHTML();
         }catch(FeedException e){
             LOG.error("Error ", e);
             return e.getMessage();
         }
    }
}
