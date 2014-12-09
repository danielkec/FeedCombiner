package cz.kec.wls.feedcombiner.rs;

import cz.kec.wls.feedcombiner.beans.RefreshFeedsTimerBean;
import cz.kec.wls.feedcombiner.exceptions.WrongIntervalException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rest interface for working with refresh interval.
 * 
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 9, 2014
 */
@Path("timer")
@Stateless
public class TimerResource {
    Logger LOG = LoggerFactory.getLogger(TimerResource.class);
    
    @Inject 
    RefreshFeedsTimerBean refreshFeedsTimerBean;
    
    @GET
    public Integer getTimeLeft(){
        int timeLeft = this.refreshFeedsTimerBean.getTimeLeft();
        LOG.info("getTimeLeft {}",timeLeft);
        return timeLeft;
    }
    
    @GET
    @Path("getinterval")
    public Integer getTimerInteval(){
        int timerIntervalInSeconds = refreshFeedsTimerBean.getTimerIntervalInSeconds();
        LOG.info("getTimerInteval {}",timerIntervalInSeconds);
        return timerIntervalInSeconds;
    }
        
    @GET
    @Path("setinterval")
    @Consumes("application/json")
    public Response setTimerInteval(@QueryParam("interval") String interval){
        LOG.info("setTimerInteval interval: {}",interval);
        try {
            this.refreshFeedsTimerBean.setTimerIntevalInSeconds(Integer.parseInt(interval));
            return Response.ok().build();
        } catch (NumberFormatException ex) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).type("text/plain")
                .entity(ex.getMessage()).build();
        } catch (WrongIntervalException ex) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).type("text/plain")
                .entity(ex.getMessage()).build();
        }
    }
    
}
