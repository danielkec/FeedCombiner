package cz.kec.wls.feedcombiner.view;

import cz.kec.wls.feedcombiner.model.CombinedFeed;
import cz.kec.wls.feedcombiner.utils.XMLUtils;
import java.net.URI;
import java.util.List;
import javax.xml.bind.MarshalException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test marshalling of class OverViewBean.
 * Validates output xml and tests bean integrity
 * 
 * @author Daniel Kec <daniel at kecovi.cz>
 */
public class OverViewBeanTest {



    /**
     * Test marshalling of class OverViewBean.
     * Validates output xml and tests bean integrity
     */
    @Test
    public void testMarshall() throws MarshalException {
        System.out.println("testMarshall");
        
        String ovbeanName       = "Feed Combiner - overview";
        String combinedFeedName = "feed1";
        String feedDescription  = "description of "+combinedFeedName;
        
        OverViewBean overViewBean = new OverViewBean(ovbeanName);
        CombinedFeed combinedFeed = new CombinedFeed(combinedFeedName, feedDescription);
        List<URI> uris = combinedFeed.getUris();
        uris.add(URI.create("http://www.reddit.com/r/worldnews/.rss"));
        uris.add(URI.create("http://www.reddit.com/r/java/.rss"));
        uris.add(URI.create("http://www.buzzfeed.com/index.xml"));
        uris.add(URI.create("http://feeds.dzone.com/javalobby/frontpage"));
        uris.add(URI.create("http://feeds.delicious.com/v2/rss/OracleTechnologyNetwork/otnheadlines"));
        overViewBean.getCombinedFeedList().add(combinedFeed);
        overViewBean.getCombinedFeedList().add(combinedFeed);
        overViewBean.getCombinedFeedList().add(combinedFeed);
        overViewBean.getCombinedFeedList().add(combinedFeed);
        
        Assert.assertEquals(ovbeanName,overViewBean.getTitle());
        Assert.assertEquals(combinedFeedName,overViewBean.getCombinedFeedList().get(0).getName());
        Assert.assertEquals(feedDescription,overViewBean.getCombinedFeedList().get(0).getDescription());
        
        String xmlString = overViewBean.marshal();
        boolean result = XMLUtils.validate(xmlString, OverViewBeanTest.class.getResourceAsStream("overview.xsd"));
        Assert.assertTrue(result);
    }


}
