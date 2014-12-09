package cz.kec.wls.feedcombiner.rs;

import cz.kec.wls.feedcombiner.datastore.CombinedFeedDao;
import cz.kec.wls.feedcombiner.datastore.DaoFactory;
import cz.kec.wls.feedcombiner.model.CombinedFeed;
import cz.kec.wls.feedcombiner.utils.JSONUtils;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RESTful resource through which you can manage (create, delete, update) a
 * combined feeds.
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 6, 2014
 */
@Path("manage")
public class ManageResource {

    private final Logger LOG = LoggerFactory.getLogger(ManageResource.class);

    /**
     * Returns all combined feed in the json format.
     *
     * @return all combined feeds in InMemoryDataStore
     */
    @GET
    @Produces("application/json")
    public String getAllCombinedFeeds() {
        CombinedFeedDao combinedFeedDao = DaoFactory.getCombinedFeedDao();
        List<CombinedFeed> combinedFeeds = combinedFeedDao.getAllCombinedFeeds();
        return JSONUtils.toJSON(combinedFeeds);
    }

    /**
     * Creates new combined feed based on suplied params.
     * 
     * @param title title of new combined feed, works as id, must be unique
     * @param description description of newly created combined feed
     * @param urls feed sources of new combined feed
     * @return 
     */
    @GET
    @Path("create")
    public Response createCombinedFeed(
            @QueryParam("title") String title,
            @QueryParam("description") String description,
            @QueryParam("urls") List<String> urls) {
        LOG.info("entering createCombinedFeed title: {} desc: {} urls: {}", title, description, urls);
        try {
            CombinedFeed combinedFeed = new CombinedFeed(title, description);
            for (String url : urls) {
                combinedFeed.getUris().add(URI.create(url));
            }
            CombinedFeedDao combinedFeedDao = DaoFactory.getCombinedFeedDao();
            combinedFeedDao.createCombinedFeed(combinedFeed);
            return Response.ok().build();
        } catch (Exception e) {
            LOG.error("Exception during creation of the conbined feed.", e);
            return Response.serverError().build();
        } catch (Error e) {
            LOG.error("Error during creation of the conbined feed.", e);
            return Response.serverError().build();
        }
    }

    @GET
    @Path("update")
    @Produces("application/json")
    public Response updateCombinedFeed(
            @QueryParam("title") String title,
            @QueryParam("description") String description,
            @QueryParam("urls") List<String> urls) {
        LOG.info("entering updateCombinedFeed title: {} desc: {} urls: {}", title, description, urls);
        try {
            CombinedFeed combinedFeed = new CombinedFeed(title, description);
            for (String url : urls) {
                combinedFeed.getUris().add(URI.create(url));
            }
            CombinedFeedDao combinedFeedDao = DaoFactory.getCombinedFeedDao();
            combinedFeedDao.updateCombinedFeed(combinedFeed);
            return Response.ok().build();
        } catch (Exception e) {
            LOG.error("Exception during update of the conbined feed.", e);
            return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (Error e) {
            LOG.error("Error during update of the conbined feed.", e);
            return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("delete")
    @Produces("application/json")
    public String deleteCombinedFeed(@QueryParam("title") String title) {
        LOG.info("entering deleteCombinedFeed title: {}", title);
        try {
            CombinedFeedDao combinedFeedDao = DaoFactory.getCombinedFeedDao();
            boolean succesfullyDeleted = combinedFeedDao.deleteCombinedFeed(title);
            Map<String, Boolean> map = new HashMap<String, Boolean>(1);
            map.put("deleted", succesfullyDeleted);
            String result = JSONUtils.toJSON(map);
            LOG.info("response " + result);
            return result;
        } catch (Exception e) {
            LOG.error("Exception during creation of the conbined feed.", e);
            return e.getMessage();
        } catch (Error e) {
            LOG.error("Error during creation of the conbined feed.", e);
            return e.getMessage();
        }
    }
}
