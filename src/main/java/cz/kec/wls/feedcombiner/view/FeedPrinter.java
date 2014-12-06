package cz.kec.wls.feedcombiner.view;

import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import cz.kec.wls.feedcombiner.feeds.SyndFeedAdapter;
import cz.kec.wls.feedcombiner.model.CombinedFeed;
import cz.kec.wls.feedcombiner.utils.JSONUtils;
import cz.kec.wls.feedcombiner.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Prints combined feed to string in desired format.
 *
 * @see CombinedFeed
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 5, 2014
 */
public class FeedPrinter {
    private Logger LOG = LoggerFactory.getLogger(FeedPrinter.class);

    public static final String FEED_TYPE_ATOM10 = "atom_1.0";

    private final CombinedFeed feed;

    /**
     * Creates new printer for suplemented feed
     * @param feed feed to be printed
     */
    public FeedPrinter(CombinedFeed feed) {
        this.feed = feed;
    }

    public String printToHTML(){
        return XMLUtils.transform(printToATOM(), FeedPrinter.class.getResourceAsStream("atomTohtml.xsl"));
    }
    public String printToJSON(){
        return JSONUtils.toJSON(this.feed);
    }
    public String printToATOM(){
        return printToString(FEED_TYPE_ATOM10);
    }

    private String printToString(String outputType){
        try {
            SyndFeedAdapter sfeed = new SyndFeedAdapter(this.feed);
            sfeed.setFeedType(outputType);
            SyndFeedOutput output = new SyndFeedOutput();
            return output.outputString(sfeed);
        } catch (FeedException ex) {
            LOG.error("Error when printing feed to string with type {}",outputType, ex);
            throw new RuntimeException("Error when printing feed to string");
        }
    }

}
