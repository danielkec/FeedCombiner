package cz.kec.wls.feedcombiner;

import cz.kec.wls.feedcombiner.rs.ExposeResource;
import cz.kec.wls.feedcombiner.rs.ManageResource;
import cz.kec.wls.feedcombiner.utils.MockUtils;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.grizzly.http.server.HttpServer;
import org.slf4j.LoggerFactory;

public class Main {
    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(Main.class);

    //private static final String URL = "http://localhost:8182/feedcombiner/";
    private static final String URL = "http://localhost:8182";
    private static final String RESOURCE = "feedcombiner";
    private static final String URL_RESOURCE = URL + "/" + RESOURCE;


    public static void main(String[] args) {
            ResourceConfig resourceConfig = new ResourceConfig(ExposeResource.class,ManageResource.class);
            HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(URL_RESOURCE), resourceConfig);

            URI fullUrl = URI.create(URL_RESOURCE+"/expose");
//                        URI.create(URL_RESOURCE+"/"+resourceConfig
//                                            .getApplication()
//                                            .getClasses()
//                                            .iterator().next()
//                                            .getAnnotation(Path.class).value());

            LOG.info("Grizzly roars on {}",fullUrl);
            LOG.info("Hit any key to kill it!");


            createMockFeeds();
            MockUtils.mockSync();

            if(Desktop.isDesktopSupported()){
                try {
                    Desktop.getDesktop().browse(fullUrl);
                } catch (IOException ex) {
                   LOG.error("Error when opening default browser",ex);
                }
            }

            new Scanner(System.in).nextLine();
            httpServer.shutdown();
    }

    public static void createMockFeeds() {
        ClientBuilder.newClient()
              .target(URL).path(RESOURCE).path("manage").path("create")
              .queryParam("title","All in one")
              .queryParam("description", "created by unit test")
              .queryParam("urls",  "http://www.reddit.com/r/worldnews/.rss")
              .queryParam("urls",  "http://www.buzzfeed.com/index.xml")
              .queryParam("urls",  "http://feeds.dzone.com/javalobby/frontpage")
              .queryParam("urls",  "http://feeds.delicious.com/v2/rss/OracleTechnologyNetwork/otnheadlines")
              .request(MediaType.APPLICATION_JSON_TYPE)
              .get(String.class);
        ClientBuilder.newClient()
              .target(URL).path(RESOURCE).path("manage").path("create")
              .queryParam("title","Reddit and Buzzfeed")
              .queryParam("description", "created by unit test")
              .queryParam("urls",  "http://www.reddit.com/r/worldnews/.rss")
              .queryParam("urls",  "http://www.buzzfeed.com/index.xml")
              .request(MediaType.APPLICATION_JSON_TYPE)
              .get(String.class);
        ClientBuilder.newClient()
              .target(URL).path(RESOURCE).path("manage").path("create")
              .queryParam("title","Reddit only")
              .queryParam("description", "created by unit test")
              .queryParam("urls",  "http://www.reddit.com/r/worldnews/.rss")
              .request(MediaType.APPLICATION_JSON_TYPE)
              .get(String.class);
        ClientBuilder.newClient()
              .target(URL).path(RESOURCE).path("manage").path("create")
              .queryParam("title","Empty feed")
              .queryParam("description", "created by unit test")
              .request(MediaType.APPLICATION_JSON_TYPE)
              .get(String.class);
        ClientBuilder.newClient()
              .target(URL).path(RESOURCE).path("manage").path("create")
              .queryParam("title","Coding the Web")
              .queryParam("description","Jersey news")
              .queryParam("urls","http://marek.potociar.net/feed/")
              .request(MediaType.APPLICATION_JSON_TYPE)
              .get(String.class);
        ClientBuilder.newClient()
              .target(URL).path(RESOURCE).path("manage").path("create")
              .queryParam("title","testGetAllCombinedFeedsNo6")
              .queryParam("description", "created by unit test")
              .queryParam("urls",  "http://www.reddit.com/r/worldnews/.rss")
              .queryParam("urls",  "http://feeds.dzone.com/javalobby/frontpage")
              .request(MediaType.APPLICATION_JSON_TYPE)
              .get(String.class);
        ClientBuilder.newClient()
              .target(URL).path(RESOURCE).path("manage").path("create")
              .queryParam("title","testGetAllCombinedFeedsNo7")
              .queryParam("description", "created by unit test")
              .queryParam("urls",  "http://www.buzzfeed.com/index.xml")
              .request(MediaType.APPLICATION_JSON_TYPE)
              .get(String.class);
    }
}
