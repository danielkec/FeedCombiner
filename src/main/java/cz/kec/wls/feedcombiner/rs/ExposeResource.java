package cz.kec.wls.feedcombiner.rs;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import cz.kec.wls.feedcombiner.datastore.CombinedFeedDao;
import cz.kec.wls.feedcombiner.datastore.DaoFactory;
import cz.kec.wls.feedcombiner.feeds.FeedCollector;
import cz.kec.wls.feedcombiner.feeds.FeedMixer;
import cz.kec.wls.feedcombiner.view.FeedPrinter;
import cz.kec.wls.feedcombiner.model.CombinedFeed;
import cz.kec.wls.feedcombiner.utils.XMLUtils;
import cz.kec.wls.feedcombiner.view.OverViewBean;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.xml.bind.MarshalException;
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
        try {
            OverViewBean overViewBean = new OverViewBean("Feed Combiner - overview");
            CombinedFeedDao combinedFeedDao = DaoFactory.getCombinedFeedDao();
            overViewBean.getCombinedFeedList().addAll(combinedFeedDao.getAllCombinedFeeds());
            String xmlString = overViewBean.marshal();
            return
            XMLUtils.transform(xmlString, OverViewBean.class.getResourceAsStream("overview.xsl"));
        } catch (MarshalException ex) {
            LOG.error("Error ", ex);
            return ex.getMessage();
        }
    }

    @GET
    @Path("/html/{feedName}")
    @Produces("text/html")
    public String getCombinedHTMLByName(@PathParam("feedName") String feedName){
        LOG.info("entering getCombinedHTMLByName with name ()"+feedName);
        try{
        return prepCombinedFeedPrinterByName(feedName).printToHTML();
         } catch (Exception ex) {
            LOG.error("Error ", ex);
            return ex.getMessage();
        } catch (Error ex) {
            LOG.error("Error ", ex);
            return ex.getMessage();
        }
    }
    @GET
    @Path("/json/{feedName}")
    @Produces("text/html")
    public String getCombinedJSONByName(@PathParam("feedName") String feedName){
        LOG.info("entering getCombinedJSONByName with name ()"+feedName);
        try{
        return prepCombinedFeedPrinterByName(feedName).printToJSON();
         } catch (Exception ex) {
            LOG.error("Error ", ex);
            return ex.getMessage();
        } catch (Error ex) {
            LOG.error("Error ", ex);
            return ex.getMessage();
        }
    }
    @GET
    @Path("/atom/{feedName}")
    @Produces("text/html")
    public String getCombinedATOMByName(@PathParam("feedName") String feedName){
        LOG.info("entering getCombinedATOMByName with name ()"+feedName);
        try{
        return prepCombinedFeedPrinterByName(feedName).printToATOM();
         } catch (Exception ex) {
            LOG.error("Error ", ex);
            return ex.getMessage();
        } catch (Error ex) {
            LOG.error("Error ", ex);
            return ex.getMessage();
        }
    }

    public FeedPrinter prepCombinedFeedPrinterByName(String feedName){
        CombinedFeedDao combinedFeedDao = DaoFactory.getCombinedFeedDao();
        CombinedFeed combinedFeed = combinedFeedDao.getCombinedFeedByName(feedName);
        return new FeedPrinter(combinedFeed);
    }

    @GET
    @Path("atom")
    @Produces("application/atom+xml")
    public String getCombinedATOM(){
        try {
            SyndFeed feed = new SyndFeedImpl();
            feed.setFeedType("atom_1.0");
            feed.setTitle("Muj prvni feed!");
            ArrayList<URI> uris = new ArrayList<URI>();
            uris.add(URI.create("http://www.reddit.com/r/worldnews/.rss"));
            uris.add(URI.create("http://www.reddit.com/r/java/.rss"));
            uris.add(URI.create("http://www.buzzfeed.com/index.xml"));
            uris.add(URI.create("http://feeds.dzone.com/javalobby/frontpage"));
            uris.add(URI.create("http://feeds.delicious.com/v2/rss/OracleTechnologyNetwork/otnheadlines"));

            FeedCollector feedCollector = new FeedCollector(uris);

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

//    @GET
//    @Path("html")
//    @Produces("text/html")
//    public String getCombinedHTML(){
//         try {
//        CombinedFeed combinedFeed = new CombinedFeed("test", "sssss");
//        SyndFeed feedAdapter = new SyndFeedAdapter(combinedFeed);
//
//        FeedCollector feedCollector = new FeedCollector(new URI[]{
//            URI.create("http://www.reddit.com/r/worldnews/.rss"),
//            URI.create("http://www.reddit.com/r/java/.rss")
////            URI.create("http://www.buzzfeed.com/index.xml"),
////            URI.create("http://feeds.dzone.com/javalobby/frontpage"),
////            URI.create("http://feeds.delicious.com/v2/rss/OracleTechnologyNetwork/otnheadlines")
//        });
//            List<SyndFeed> wireFeeds = feedCollector.collect();
//            FeedMixer feedMixer = new FeedMixer(wireFeeds);
//            feedMixer.mix(feedAdapter);
//
//        FeedPrinter feedPrinter = new FeedPrinter(combinedFeed);
//        return feedPrinter.printToHTML();
//         }catch(Exception e){
//             LOG.error("Error ", e);
//             return e.getMessage();
//         }
//    }
}
