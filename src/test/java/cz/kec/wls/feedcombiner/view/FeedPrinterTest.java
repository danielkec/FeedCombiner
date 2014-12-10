package cz.kec.wls.feedcombiner.view;

import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndContentImpl;
import cz.kec.wls.feedcombiner.model.CombinedFeed;
import cz.kec.wls.feedcombiner.utils.JSONUtils;
import cz.kec.wls.feedcombiner.utils.XMLUtils;
import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.w3c.dom.Document;
import org.w3c.tidy.Node;
import org.w3c.tidy.Tidy;
import org.w3c.tidy.TidyMessage;
import org.w3c.tidy.TidyMessageListener;

/**
 * Tisting validity of printed feeds in various formats
 * 
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since 10 Dec 2014
 */
public class FeedPrinterTest {

    private CombinedFeed savedCombinedFeed;

    @Before
    public void setUp() {
        this.savedCombinedFeed = new CombinedFeed("test", "test popis");
        List<URI> uris = savedCombinedFeed.getUris();
        uris.add(URI.create("http://www.reddit.com/r/worldnews/.rss"));
        uris.add(URI.create("http://www.reddit.com/r/java/.rss"));
        uris.add(URI.create("http://www.buzzfeed.com/index.xml"));
        uris.add(URI.create("http://feeds.dzone.com/javalobby/frontpage"));
        uris.add(URI.create("http://feeds.delicious.com/v2/rss/OracleTechnologyNetwork/otnheadlines"));

        SyndEntry entry = new SyndEntryImpl();
        entry.setAuthor("James Gosling");
        entry.setTitle("Duke story");
        SyndContentImpl syndContentImpl = new SyndContentImpl();
        syndContentImpl.setValue("description");
        entry.setDescription(syndContentImpl);

        savedCombinedFeed.getEntries().add(entry);
    }

    /**
     * Test of printToHTML method, of class FeedPrinter.
     */
    @Test
    public void testPrintToHTML() throws UnsupportedEncodingException {
        System.out.println("printToHTML");
        FeedPrinter instance = new FeedPrinter(this.savedCombinedFeed);
        String result = instance.printToHTML();
        //System.out.println(result);
        ByteArrayInputStream bais = new ByteArrayInputStream(result.getBytes("UTF-8"));
        Tidy tidy = new Tidy();
        tidy.setXHTML(false);
        tidy.setOnlyErrors(true);
        tidy.setErrout(new PrintWriter(System.err));
        tidy.setMessageListener(new TidyMessageListener() {
            @Override 
            public void messageReceived(TidyMessage message) {
                TidyMessage.Level level = TidyMessage.Level.fromCode(message.getErrorCode());
                String msg = "HTML is not tidy: "+message.getErrorCode()+"-"+message.getMessage()+" on line: "+message.getLine()+" col: "+message.getColumn();
                if(TidyMessage.Level.ERROR.equals(level)){
                    fail(msg);
                }else if(TidyMessage.Level.WARNING.equals(level)){
                    fail(msg);
                }else if(TidyMessage.Level.INFO.equals(level)){
                    System.out.println(msg);
                }
            }
        });
        tidy.parse(bais,System.out);
    }

    /**
     * Test of printToJSON method, of class FeedPrinter.
     */
    @Test
    public void testPrintToJSON() {
        System.out.println("printToJSON");
        FeedPrinter instance = new FeedPrinter(this.savedCombinedFeed);
        String result = instance.printToJSON();
        ProcessingReport reporter = JSONUtils.validateJSON(result, FeedPrinterTest.class.getResourceAsStream("atomJsonSchema.json"));
        if (!reporter.isSuccess()) {
            for (ProcessingMessage report : reporter) {
                System.out.println(report.toString());
            }
            fail("JSON form of CombinedFeed is not valid ");
        }
    }

    /**
     * Test of printToATOM method, of class FeedPrinter.
     */
    @Test
    public void testPrintToATOM() {
        System.out.println("printToATOM");
        FeedPrinter instance = new FeedPrinter(this.savedCombinedFeed);
        String result = instance.printToATOM();
        Document dom = XMLUtils.parseNSAware(result);
        XMLUtils.removeAllNodesOfNS(dom, "http://purl.org/dc/elements/1.1/");//atom feed from ROME lib are poluted by this ns
        result = XMLUtils.serializeNSAware(dom);
        //System.out.println(result);
        boolean validationResult = XMLUtils.validate(result, FeedPrinterTest.class.getResourceAsStream("atom.xsd"));
        assertTrue(validationResult);
    }

}
