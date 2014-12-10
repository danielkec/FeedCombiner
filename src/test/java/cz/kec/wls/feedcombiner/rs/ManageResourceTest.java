package cz.kec.wls.feedcombiner.rs;

import java.net.URI;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ManageResourceTest {

private static final String URL         = "http://localhost:8183";
private static final String RESOURCE    = "feedcombiner";
private static final String URL_RESOURCE    = URL+"/"+RESOURCE;

    private static HttpServer httpServer;

    @BeforeClass
    public static void setUpClass() {
        ResourceConfig resourceConfig = new ResourceConfig(ManageResource.class);
        httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(URL_RESOURCE), resourceConfig);
        System.out.println("Grizzly roars on "+URL_RESOURCE);
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Grizzly died!");
        httpServer.shutdownNow();
    }

    /**
     * Test of createCombinedFeed method, of class ManageResource.
     */
    @Test
    public void test1CreateCombinedFeed() {
        System.out.println("testCreateCombinedFeed");
        String response = ClientBuilder.newClient()
              .target(URL).path(RESOURCE).path("manage").path("create")
              .queryParam("title","testGetAllCombinedFeeds")
              .queryParam("description", "created by unit test")
              .queryParam("urls",  "http://www.reddit.com/r/worldnews/.rss")
              .queryParam("urls",  "http://www.buzzfeed.com/index.xml")
              .queryParam("urls",  "http://feeds.dzone.com/javalobby/frontpage")
              .queryParam("urls",  "http://feeds.delicious.com/v2/rss/OracleTechnologyNetwork/otnheadlines")
              .request(MediaType.APPLICATION_JSON_TYPE)
              .get(String.class);
        //System.out.println(response);
    }

    /**
     * Test of getAllCombinedFeeds method, of class ManageResource.
     */
    //@Test
    public void test2GetAllCombinedFeeds() {
        System.out.println("testGetAllCombinedFeeds");
              String response = ClientBuilder.newClient()
              .target(URL).path(RESOURCE).path("manage")
              .request(MediaType.APPLICATION_JSON_TYPE)
              .get(String.class);


        Assert.assertTrue(response.contains("\"name\":\"testGetAllCombinedFeeds\""));
        //System.out.println(response);

    }



}
