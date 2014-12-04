package cz.kec.wls.feedcombiner;

import cz.kec.wls.feedcombiner.rs.ExposeResource;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Path;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.grizzly.http.server.HttpServer;

public class Main {

    private static final String URL = "http://localhost:8182/feedcombiner/";

    public static void main(String[] args) {
            ResourceConfig resourceConfig = new ResourceConfig(ExposeResource.class);
            HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(URL), resourceConfig);

            URI fullUrl = URI.create(URL+resourceConfig
                                            .getApplication()
                                            .getClasses()
                                            .iterator().next()
                                            .getAnnotation(Path.class).value());
            
            System.out.println("Grizzly roars on "+fullUrl);            
            System.out.println("Hit any key to kill it!");  
            
            if(Desktop.isDesktopSupported()){
                try {
                    Desktop.getDesktop().browse(fullUrl);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            new Scanner(System.in).nextLine();           
            httpServer.shutdown();
    }
}
