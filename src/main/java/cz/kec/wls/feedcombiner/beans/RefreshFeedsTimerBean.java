package cz.kec.wls.feedcombiner.beans;

import cz.kec.wls.feedcombiner.exceptions.WrongIntervalException;
import cz.kec.wls.feedcombiner.utils.DateUtils;
import cz.kec.wls.feedcombiner.utils.MockUtils;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton EJB with configurable timer.
 * Running synchronization of all combined feeds in default interval 10 sec.
 * Interval is configurable, but not persistent.
 * 
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 9, 2014
 */
@Startup
@Singleton
public class RefreshFeedsTimerBean {
private final Logger LOG = LoggerFactory.getLogger(RefreshFeedsTimerBean.class);

public static final int DEFAULT_INTERVAL    = 10;
public static final int MIN_INTERVAL        = 3;
public static final int MAX_INTERVAL        = Integer.MAX_VALUE;

    @Resource
    TimerService timerService;
    private Timer timer;
    
    /** Interval of refresh in seconds */
    private int timeoutIntervalInSeconds = 10;//10 sec is default 

    @PostConstruct
    public void initTimer(){
        LOG.info("Initializing timer {}",RefreshFeedsTimerBean.class.getName());
        long timeoutInMilis = timeoutIntervalInSeconds*1000;
        this.timer = timerService.createIntervalTimer(timeoutInMilis,timeoutInMilis,
                new TimerConfig("Current combined feed refresh timeout", false));
        //if there are no combined feeds at startup, creates set of mockup ones
        MockUtils.createMockFeeds();
    }

    @Timeout
    public void refreshFeedsTimer() {
        LOG.info("Automatically refreshing feeds. Next refresh {}",
                DateUtils.humanReadableDuration(new Date(),this.timer.getNextTimeout()));
        MockUtils.mockSync();
        LOG.info("Refreshing DONE!");
    }
    
    /**
     * Returns actual refresh interval in seconds.
     * @return refresh interval in seconds
     */
    public int getTimerIntervalInSeconds(){
        return this.timeoutIntervalInSeconds;
    }
    
    /**
     * Returns time left to the next refresh in seconds.
     * @return time to next refresh in seconds
     */
    public int getTimeLeft(){
        return (int) (this.timer.getTimeRemaining()/1000);
    }
    
    /**
     * Sets new interval betweet automatic combined feeds refresh.
     * @param intervalDuration interval in seconds
     * @throws WrongIntervalException if interval is not within 3-INT_MAX.
     */
    public void setTimerIntevalInSeconds(int intervalDuration) throws WrongIntervalException {
        if(intervalDuration < MIN_INTERVAL){
            throw new WrongIntervalException(MIN_INTERVAL,MAX_INTERVAL,intervalDuration);
        }
        LOG.info("Setting a programmatic timeout for {} seconds from now.",intervalDuration);
        this.timeoutIntervalInSeconds = intervalDuration;
        timer.cancel();
        initTimer();
    }

}
