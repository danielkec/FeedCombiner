package cz.kec.wls.feedcombiner;

import cz.kec.wls.feedcombiner.rs.ExposeResource;
import cz.kec.wls.feedcombiner.rs.ManageResource;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;
import javax.ws.rs.Path;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.grizzly.http.server.HttpServer;
import org.slf4j.LoggerFactory;

public class Main {
    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(Main.class);
    
    private static final String URL = "http://localhost:8182/feedcombiner/";

    public static void main(String[] args) {
            ResourceConfig resourceConfig = new ResourceConfig(ManageResource.class,ExposeResource.class);
            HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(URL), resourceConfig);

            URI fullUrl = URI.create(URL+resourceConfig
                                            .getApplication()
                                            .getClasses()
                                            .iterator().next()
                                            .getAnnotation(Path.class).value());
            
            LOG.info("Grizzly roars on {}",fullUrl);            
            LOG.info("Hit any key to kill it!");  
            
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
}
