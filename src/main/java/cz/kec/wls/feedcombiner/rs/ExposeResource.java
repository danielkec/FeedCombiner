package cz.kec.wls.feedcombiner.rs;

import cz.kec.wls.feedcombiner.datastore.CombinedFeedDao;
import cz.kec.wls.feedcombiner.datastore.DaoFactory;
import cz.kec.wls.feedcombiner.view.FeedPrinter;
import cz.kec.wls.feedcombiner.model.CombinedFeed;
import cz.kec.wls.feedcombiner.utils.XMLUtils;
import cz.kec.wls.feedcombiner.view.OverViewBean;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.xml.bind.MarshalException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Exposes the contents of a combined feed in HTML, JSON and ATOM format.
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 5, 2014
 */
@Path("expose")
public class ExposeResource {

    private Logger LOG = LoggerFactory.getLogger(ExposeResource.class);

    /**
     * Rest endpoint returning html UI Overview of all combined feeds in the
     * InMemoryStore.
     *
     * @return HTML UI overview
     */
    @GET
    @Produces("text/html")
    public String getOverView() {
        try {
            OverViewBean overViewBean = new OverViewBean("Feed Combiner - overview");
            CombinedFeedDao combinedFeedDao = DaoFactory.getCombinedFeedDao();
            overViewBean.getCombinedFeedList().addAll(combinedFeedDao.getAllCombinedFeeds());
            String xmlString = overViewBean.marshal();
            return XMLUtils.transform(xmlString, OverViewBean.class.getResourceAsStream("overview.xsl"));
        } catch (MarshalException ex) {
            LOG.error("Exception during marchalling of the overview feed ", ex);
            return ex.getMessage();
        }
    }

  /**
     * Rest endpoint returning HTML format of combined feed based on supplied name.
     *
     * @param feedName Name of the combined to be returned in HTML format
     * @return requested combined feed in HTML format
     */
    @GET
    @Path("/html/{feedName}")
    @Produces("text/html")
    public String getCombinedHTMLByName(@PathParam("feedName") String feedName) {
        LOG.info("entering getCombinedHTMLByName with name {}", feedName);
        try {
            return prepCombinedFeedPrinterByName(feedName).printToHTML();
        } catch (Exception ex) {
            LOG.error("Exception when printing combined feed as HTML ", ex);
            return ex.getMessage();
        } catch (Error ex) {
            LOG.error("Error when printing combined feed as HTML", ex);
            return ex.getMessage();
        }
    }

  /**
     * Rest endpoint returning JSON format of combined feed based on supplied name.
     *
     * @param feedName Name of the combined to be returned in JSON format
     * @return requested combined feed in JSON format
     */
    @GET
    @Path("/json/{feedName}")
    @Produces("application/json")
    public String getCombinedJSONByName(@PathParam("feedName") String feedName) {
        LOG.info("entering getCombinedJSONByName with name {}", feedName);
        try {
            return prepCombinedFeedPrinterByName(feedName).printToJSON();
        } catch (Exception ex) {
            LOG.error("Exception when printing combined feed as JSON ", ex);
            return ex.getMessage();
        } catch (Error ex) {
            LOG.error("Error when printing combined feed as JSON", ex);
            return ex.getMessage();
        }
    }

    /**
     * Rest endpoint returning ATOM format of combined feed based on supplied name.
     *
     * @param feedName Name of the combined to be returned in ATOM format
     * @return requested combined feed in ATOM format
     */
    @GET
    @Path("/atom/{feedName}")
    @Produces("application/xml")
    public String getCombinedATOMByName(@PathParam("feedName") String feedName) {
        LOG.info("entering getCombinedATOMByName with name {}", feedName);
        try {
            return prepCombinedFeedPrinterByName(feedName).printToATOM();
        } catch (Exception ex) {
            LOG.error("Exception when printing combined feed as ATOM", ex);
            return ex.getMessage();
        } catch (Error ex) {
            LOG.error("Error when printing combined feed as ATOM", ex);
            return ex.getMessage();
        }
    }

    /**
     * Preparing FeedPrinter
     *
     * @param feedName name of the combined feed to be printed
     * @return printer capable to print combined feed to HTML/ATOM/JSON
     */
    private FeedPrinter prepCombinedFeedPrinterByName(String feedName) {
        CombinedFeedDao combinedFeedDao = DaoFactory.getCombinedFeedDao();
        CombinedFeed combinedFeed = combinedFeedDao.getCombinedFeedByName(feedName);
        return new FeedPrinter(combinedFeed);
    }

}
