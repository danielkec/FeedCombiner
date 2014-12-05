package cz.kec.wls.feedcombiner.view;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import cz.kec.wls.feedcombiner.feeds.SyndFeedAdapter;
import cz.kec.wls.feedcombiner.model.CombinedFeed;
import cz.kec.wls.feedcombiner.utils.JSONUtils;
import cz.kec.wls.feedcombiner.utils.XMLUtils;
import java.net.URI;
import java.util.List;

/**
 * Prints combined feed to string in desired format.
 *
 * @see CombinedFeed
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 5, 2014
 */
public class FeedPrinter {
    public static final String FEED_TYPE_ATOM10 = "atom_1.0";
    public static final String FEED_TYPE_JSON   = "atom_1.0";
    public static final String FEED_TYPE_HTML   = "atom_1.0";

    private final CombinedFeed feed;

    /**
     * Creates new printer for suplemented feed
     * @param feed feed to be printed
     */
    public FeedPrinter(CombinedFeed feed) {
        this.feed = feed;
    }

    public String printToHTML() throws FeedException{
        return XMLUtils.transform(printToATOM(), FeedPrinter.class.getResourceAsStream("atomTohtml.xsl"));
    }
    public String printToJSON(){
        return JSONUtils.toJSON(this.feed);
    }
    public String printToATOM() throws FeedException{
        return printToString(FEED_TYPE_ATOM10);
    }

    private String printToString(String outputType) throws FeedException{
        SyndFeedAdapter sfeed = new SyndFeedAdapter(this.feed);
        sfeed.setFeedType(outputType);
        SyndFeedOutput output = new SyndFeedOutput();
        return output.outputString(sfeed);
    }

}
