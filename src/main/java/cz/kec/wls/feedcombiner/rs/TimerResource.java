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
 * Rest interface for working with refresh interval. Only resource to be also an
 * EJB, because i had problems to inject RefreshFeedsTimerBean to a POJO.
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

    /**
     * Rest method returning seconds left before refresh timer kicks in.
     * @return seconds left
     */
    @GET
    public Integer getTimeLeft() {
        int timeLeft = this.refreshFeedsTimerBean.getTimeLeft();
        LOG.info("getTimeLeft {}", timeLeft);
        return timeLeft;
    }

    /**
     * Rest method returning lenght of refreshing interval in seconds.
     * @return lenght of refreshing interval in seconds
     */
    @GET
    @Path("getinterval")
    public Integer getTimerInteval() {
        int timerIntervalInSeconds = refreshFeedsTimerBean.getTimerIntervalInSeconds();
        LOG.info("getTimerInteval {}", timerIntervalInSeconds);
        return timerIntervalInSeconds;
    }

    /**
     * Sets the interval between refreshing combined feeds.
     * @param interval lenght of refreshing interval in seconds
     * @return Response status
     */
    @GET
    @Path("setinterval")
    @Consumes("application/json")
    public Response setTimerInteval(@QueryParam("interval") String interval) {
        LOG.info("setTimerInteval interval: {}", interval);
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
