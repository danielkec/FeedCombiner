/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.kec.wls.feedcombiner.view;

import cz.kec.wls.feedcombiner.model.CombinedFeed;
import java.net.URI;
import java.util.List;
import javax.xml.bind.MarshalException;
import org.junit.Test;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 */
public class OverViewBeanTest {



    /**
     * Test of getTitle method, of class OverViewBean.
     */
    @Test
    public void testMarshall() throws MarshalException {
        OverViewBean overViewBean = new OverViewBean("Feed Combiner - overview");
        CombinedFeed combinedFeed = new CombinedFeed("feed1", "popis");
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
        String xmlString = overViewBean.marshal();
        System.out.println(xmlString);
    }


}
