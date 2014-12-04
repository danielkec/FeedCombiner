package cz.kec.wls.feedcombiner.rs;

import cz.kec.wls.feedcombiner.model.CombinedFeed;
import cz.kec.wls.feedcombiner.utils.JSONUtils;
import java.net.URI;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * RESTful resource through which you can manage (create, delete) a combined feed
 * @author Daniel Kec <daniel at kecovi.cz>
 */
@Path("manage")
public class ManageResource {
    @GET
    @Produces("application/json")
    public String getAllCombinedFeeds(){
        return "sss";
    }
    @GET
    @Path("create")
    @Produces("application/json")
    @Consumes("application/json")
    public String createCombinedFeedString(){
        try {
            CombinedFeed combinedFeed = new CombinedFeed("Ahoj", "svete");
            List<URI> uris = combinedFeed.getUris();
            uris.add(URI.create("http://www.reddit.com/r/worldnews/.rss"));
            uris.add(URI.create("http://www.reddit.com/r/java/.rss"));
            uris.add(URI.create("http://www.buzzfeed.com/index.xml"));
            uris.add(URI.create("http://feeds.dzone.com/javalobby/frontpage"));
            uris.add(URI.create("http://feeds.delicious.com/v2/rss/OracleTechnologyNetwork/otnheadlines"));
            return JSONUtils.toJSON(combinedFeed);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (Error e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
