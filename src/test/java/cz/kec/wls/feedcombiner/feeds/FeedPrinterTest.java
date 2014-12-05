package cz.kec.wls.feedcombiner.feeds;

import cz.kec.wls.feedcombiner.view.FeedPrinter;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import cz.kec.wls.feedcombiner.model.CombinedFeed;
import java.net.URI;
import java.util.Date;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 */
public class FeedPrinterTest {
    /**
     * Test of printToHTML method, of class FeedPrinter.
     */
//    @Test
//    public void testPrintToHTML() {
//        System.out.println("printToHTML");
//        FeedPrinter instance = null;
//        String expResult = "";
//        String result = instance.printToHTML();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of printToJSON method, of class FeedPrinter.
//     */
//    @Test
//    public void testPrintToJSON() {
//        System.out.println("printToJSON");
//        FeedPrinter instance = null;
//        String expResult = "";
//        String result = instance.printToJSON();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of printToATOM method, of class FeedPrinter.
     */
    @Test
    public void testPrintToATOM() throws Exception {
//        System.out.println("printToATOM");
//        FeedPrinter instance = null;
//        String expResult = "";
//        String result = instance.printToATOM();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
        CombinedFeed combinedFeed = new CombinedFeed("test", "sssss");
        SyndFeed feedAdapter = new SyndFeedAdapter(combinedFeed);

        FeedCollector feedCollector = new FeedCollector(new URI[]{
            URI.create("http://www.reddit.com/r/worldnews/.rss")
//            URI.create("http://www.reddit.com/r/java/.rss"),
//            URI.create("http://www.buzzfeed.com/index.xml"),
//            URI.create("http://feeds.dzone.com/javalobby/frontpage"),
//            URI.create("http://feeds.delicious.com/v2/rss/OracleTechnologyNetwork/otnheadlines")
        });
            List<SyndFeed> wireFeeds = feedCollector.collect();
            FeedMixer feedMixer = new FeedMixer(wireFeeds);
            feedMixer.mix(feedAdapter);

        FeedPrinter feedPrinter = new FeedPrinter(combinedFeed);
        System.out.println(feedPrinter.printToATOM());
        System.out.println(feedPrinter.printToJSON());
        System.out.println(feedPrinter.printToHTML());

    }

}
