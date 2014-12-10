package cz.kec.wls.feedcombiner.datastore.impl;

import cz.kec.wls.feedcombiner.datastore.DaoFactory;
import cz.kec.wls.feedcombiner.datastore.InMemoryDataStore;
import cz.kec.wls.feedcombiner.model.CombinedFeed;
import cz.kec.wls.feedcombiner.utils.JSONUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testing of InMemoryDataStore implementation.
 * 
 * @author Daniel Kec <daniel at kecovi.cz>
 */
public class InMemoryDataStoreSingletonTest {

    private static final String SAVED_IN_MEMORY_STORE =
"rO0ABXNyACZqYXZhLnV0aWwuY29uY3VycmVudC5Db25jdXJyZW50SGFzaE1hcGSZ3hKdhyk9AwAD\n" +
"SQALc2VnbWVudE1hc2tJAAxzZWdtZW50U2hpZnRbAAhzZWdtZW50c3QAMVtMamF2YS91dGlsL2Nv\n" +
"bmN1cnJlbnQvQ29uY3VycmVudEhhc2hNYXAkU2VnbWVudDt4cAAAAA8AAAAcdXIAMVtMamF2YS51\n" +
"dGlsLmNvbmN1cnJlbnQuQ29uY3VycmVudEhhc2hNYXAkU2VnbWVudDtSdz9BMps5dAIAAHhwAAAA\n" +
"EHNyAC5qYXZhLnV0aWwuY29uY3VycmVudC5Db25jdXJyZW50SGFzaE1hcCRTZWdtZW50HzZMkFiT\n" +
"KT0CAAFGAApsb2FkRmFjdG9yeHIAKGphdmEudXRpbC5jb25jdXJyZW50LmxvY2tzLlJlZW50cmFu\n" +
"dExvY2tmVagsLMhq6wIAAUwABHN5bmN0AC9MamF2YS91dGlsL2NvbmN1cnJlbnQvbG9ja3MvUmVl\n" +
"bnRyYW50TG9jayRTeW5jO3hwc3IANGphdmEudXRpbC5jb25jdXJyZW50LmxvY2tzLlJlZW50cmFu\n" +
"dExvY2skTm9uZmFpclN5bmNliDLnU3u/CwIAAHhyAC1qYXZhLnV0aWwuY29uY3VycmVudC5sb2Nr\n" +
"cy5SZWVudHJhbnRMb2NrJFN5bmO4HqKUqkRafAIAAHhyADVqYXZhLnV0aWwuY29uY3VycmVudC5s\n" +
"b2Nrcy5BYnN0cmFjdFF1ZXVlZFN5bmNocm9uaXplcmZVqEN1P1LjAgABSQAFc3RhdGV4cgA2amF2\n" +
"YS51dGlsLmNvbmN1cnJlbnQubG9ja3MuQWJzdHJhY3RPd25hYmxlU3luY2hyb25pemVyM9+vua1t\n" +
"b6kCAAB4cAAAAAA/QAAAc3EAfgAFc3EAfgAJAAAAAD9AAABzcQB+AAVzcQB+AAkAAAAAP0AAAHNx\n" +
"AH4ABXNxAH4ACQAAAAA/QAAAc3EAfgAFc3EAfgAJAAAAAD9AAABzcQB+AAVzcQB+AAkAAAAAP0AA\n" +
"AHNxAH4ABXNxAH4ACQAAAAA/QAAAc3EAfgAFc3EAfgAJAAAAAD9AAABzcQB+AAVzcQB+AAkAAAAA\n" +
"P0AAAHNxAH4ABXNxAH4ACQAAAAA/QAAAc3EAfgAFc3EAfgAJAAAAAD9AAABzcQB+AAVzcQB+AAkA\n" +
"AAAAP0AAAHNxAH4ABXNxAH4ACQAAAAA/QAAAc3EAfgAFc3EAfgAJAAAAAD9AAABzcQB+AAVzcQB+\n" +
"AAkAAAAAP0AAAHNxAH4ABXNxAH4ACQAAAAA/QAAAdAAtS0VZU0VUX0tFWV9NSUlDZGdJQkFEQU5C\n" +
"Z2txaGtpRzl3MEJBUUVGQUFTQ0Ftc3IANWN6LmtlYy53bHMuZmVlZGNvbWJpbmVyLmRhdGFzdG9y\n" +
"ZS5pbXBsLkluTWVtb3J5S2V5U2V0r6iitNzp+gUCAAB4cgAoamF2YS51dGlsLmNvbmN1cnJlbnQu\n" +
"Q29weU9uV3JpdGVBcnJheVNldEu90JKQFWnXAgABTAACYWx0ACtMamF2YS91dGlsL2NvbmN1cnJl\n" +
"bnQvQ29weU9uV3JpdGVBcnJheUxpc3Q7eHBzcgApamF2YS51dGlsLmNvbmN1cnJlbnQuQ29weU9u\n" +
"V3JpdGVBcnJheUxpc3R4XZ/VRquQwwMAAHhwdwQAAAABdAAEdGVzdHhxAH4AM3NyACpjei5rZWMu\n" +
"d2xzLmZlZWRjb21iaW5lci5tb2RlbC5Db21iaW5lZEZlZWTBcZ4mwcTzLQIABUwAC2Rlc2NyaXB0\n" +
"aW9udAASTGphdmEvbGFuZy9TdHJpbmc7TAALZW5jb2RlZE5hbWVxAH4ANUwAB2VudHJpZXN0ABBM\n" +
"amF2YS91dGlsL0xpc3Q7TAAEbmFtZXEAfgA1TAAEdXJpc3EAfgA2eHB0AAp0ZXN0IHBvcGlzcQB+\n" +
"ADNzcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAAAHcEAAAAAHhx\n" +
"AH4AM3NxAH4AOQAAAAV3BAAAAAVzcgAMamF2YS5uZXQuVVJJrAF4LkOeSasDAAFMAAZzdHJpbmdx\n" +
"AH4ANXhwdAAmaHR0cDovL3d3dy5yZWRkaXQuY29tL3Ivd29ybGRuZXdzLy5yc3N4c3EAfgA8dAAh\n" +
"aHR0cDovL3d3dy5yZWRkaXQuY29tL3IvamF2YS8ucnNzeHNxAH4APHQAIWh0dHA6Ly93d3cuYnV6\n" +
"emZlZWQuY29tL2luZGV4LnhtbHhzcQB+ADx0ACpodHRwOi8vZmVlZHMuZHpvbmUuY29tL2phdmFs\n" +
"b2JieS9mcm9udHBhZ2V4c3EAfgA8dABGaHR0cDovL2ZlZWRzLmRlbGljaW91cy5jb20vdjIvcnNz\n" +
"L09yYWNsZVRlY2hub2xvZ3lOZXR3b3JrL290bmhlYWRsaW5lc3h4cHB4";

    private CombinedFeed savedCombinedFeed;

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.savedCombinedFeed = new CombinedFeed("test", "test popis");
        List<URI> uris = savedCombinedFeed.getUris();
        uris.add(URI.create("http://www.reddit.com/r/worldnews/.rss"));
        uris.add(URI.create("http://www.reddit.com/r/java/.rss"));
        uris.add(URI.create("http://www.buzzfeed.com/index.xml"));
        uris.add(URI.create("http://feeds.dzone.com/javalobby/frontpage"));
        uris.add(URI.create("http://feeds.delicious.com/v2/rss/OracleTechnologyNetwork/otnheadlines"));
    }

    /**
     * Test of getInstance method, of class InMemoryDataStoreSingleton.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        DaoFactory.getCombinedFeedDao().deleteAllCombinedFeeds();//polution from other tests
        InMemoryDataStoreSingleton result = InMemoryDataStoreSingleton.getInstance();
        assertNotNull(result);
    }

    /**
     * Test of put method, of class InMemoryDataStoreSingleton.
     */
    @Test(expected=ClassCastException.class)
    public void testGetClassCast() {
        System.out.println("put");
        String key = "testGetClassCast";
        String data = "data";      
        InMemoryDataStore instance = InMemoryDataStoreSingleton.getInstance();
        assertNull(instance.put(key, data));
        assertEquals("data",instance.get(key, String.class));
        Serializable result = instance.get(key, Integer.class);
        fail("Should throw ClassCastException");
    }

    /**
     * Test of put method, of class InMemoryDataStoreSingleton.
     */
    @Test
    public void testPutGetOldValue() {
        System.out.println("testPutGetOldValue");
        String key = "testPutGetOldValue";
        String data = "data";
        String data2 = "data2";
        InMemoryDataStoreSingleton instance = InMemoryDataStoreSingleton.getInstance();
        assertNull(instance.put(key, data));
        assertEquals(data,instance.put(key, data2));
        assertEquals(data2,instance.get(key, String.class));
    }
    
    /**
     * Test of put method, of class InMemoryDataStoreSingleton.
     */
    @Test(expected=Exception.class)
    public void testPutWithKeyNull() {
        System.out.println("testPutGetOldValue");
        String key = null;
        String data = "data";       
        InMemoryDataStoreSingleton instance = InMemoryDataStoreSingleton.getInstance();
        instance.put(key, data);
    }

    /**
     * Test of get method, of class InMemoryDataStoreSingleton.
     */
    @Test
    public void testGet() {
        System.out.println("testGet");
        InMemoryDataStoreSingleton instance = InMemoryDataStoreSingleton.getInstance();
        Object result = instance.get("key",String.class);
        assertNull(result);
    }

    /**
     * Test of get method, of class InMemoryDataStoreSingleton.
     */
    @Test
    public void testGetNull() {
        System.out.println("testGetNull");
        InMemoryDataStoreSingleton instance = InMemoryDataStoreSingleton.getInstance();
        Object result = instance.get("key",String.class);
        assertNull(result);
    }



    /**
     * Test of save method, testing if output stream is not closing
     */
    @Test
    public void testSave() throws Exception {
        System.out.println("testSave");
        DaoFactory.getCombinedFeedDao().deleteAllCombinedFeeds();//its singleton other tests can polute it
        DaoFactory.getCombinedFeedDao().createCombinedFeed(savedCombinedFeed);
        CloseAwareOutputStream out = new CloseAwareOutputStream();
        Assert.assertFalse(out.isClosed);
        InMemoryDataStoreSingleton inMemoryDataStore = InMemoryDataStoreSingleton.getInstance();
        inMemoryDataStore.save(out);
        Assert.assertFalse(out.isClosed);
        out.close();
        Assert.assertTrue(out.isClosed);
        //System.out.println(new String(Base64.encodeBase64Chunked(out.toByteArray())));
        Assert.assertArrayEquals(Base64.decodeBase64(SAVED_IN_MEMORY_STORE), out.toByteArray());
    }

    /**
     * Test of load method, , testing if input stream is not closing
     * @throws java.lang.Exception
     */
    @Test
    public void testLoad() throws Exception {
        System.out.println("testLoad");
        CloseAwareInputStream is = new CloseAwareInputStream(Base64.decodeBase64(SAVED_IN_MEMORY_STORE));
        InMemoryDataStoreSingleton inMemoryDataStore = InMemoryDataStoreSingleton.getInstance();
        DaoFactory.getCombinedFeedDao().deleteAllCombinedFeeds();//polution from other tests
        inMemoryDataStore.load(is);
        Assert.assertFalse(is.isClosed);
        is.close();
        Assert.assertTrue(is.isClosed);

        CombinedFeed combinedFeed = DaoFactory.getCombinedFeedDao().getAllCombinedFeeds().get(0);
        assertEquals(JSONUtils.toJSON(savedCombinedFeed), JSONUtils.toJSON(combinedFeed));
    }

    /**
     * Used for testing if inputstream has been closed
     */
    private static class CloseAwareInputStream extends ByteArrayInputStream{
        private boolean isClosed = false;

        public CloseAwareInputStream(byte[] buf) {
            super(buf);
        }

        @Override
        public void close() throws IOException {
            super.close();
            this.isClosed = true;
        }

        public boolean isClosed() {
            return isClosed;
        }
    }
    /**
     * Used for testing if outputstream has been closed
     */
    private static class CloseAwareOutputStream extends ByteArrayOutputStream{
        private boolean isClosed = false;

        @Override
        public void close() throws IOException {
            super.close();
            this.isClosed = true;
        }

        public boolean isClosed() {
            return isClosed;
        }
    }
}
