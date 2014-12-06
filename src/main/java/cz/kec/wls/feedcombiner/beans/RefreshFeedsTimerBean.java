package cz.kec.wls.feedcombiner.beans;

import cz.kec.wls.feedcombiner.utils.MockUtils;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 */
@Startup
@Singleton
public class RefreshFeedsTimerBean {
private final Logger LOG = LoggerFactory.getLogger(RefreshFeedsTimerBean.class);

static{
    MockUtils.createMockFeeds();
}

    @Schedule(dayOfWeek = "*", month = "*", hour = "*", dayOfMonth = "*", year = "*", minute = "*", second = "20", persistent = false)
    public void refreshFeedsTimer() {
        LOG.info("Automatically refreshing feeds.");

        LOG.info("Refreshing ...");
        MockUtils.mockSync();
        LOG.info("Refreshing DONE!");
    }

}
