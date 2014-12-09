package cz.kec.wls.feedcombiner.config;

import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 4, 2014
 */
@javax.ws.rs.ApplicationPath("/rest")
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        //register(new FeedCombinerBinder());//injections
        packages(true, "cz.kec.wls.feedcombiner.rs");//rest resources
    }

    
    
//    @Override
//    public Set<Class<?>> getClasses() {
//        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
//        addRestResourceClasses(resources);        
//        return resources;
//    }
//
//    /**
//     * Do not modify addRestResourceClasses() method.
//     * It is automatically populated with
//     * all resources defined in the project.
//     * If required, comment out calling this method in getClasses().
//     */
//    private void addRestResourceClasses(Set<Class<?>> resources) {
//        resources.add(cz.kec.wls.feedcombiner.rs.ExposeResource.class);
//        resources.add(cz.kec.wls.feedcombiner.rs.ManageResource.class);
//        resources.add(cz.kec.wls.feedcombiner.rs.TimerResource.class);
//    }

}
