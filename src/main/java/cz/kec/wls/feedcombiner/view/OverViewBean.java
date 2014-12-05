package cz.kec.wls.feedcombiner.view;

import cz.kec.wls.feedcombiner.model.CombinedFeed;
import cz.kec.wls.feedcombiner.utils.Marshallable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since 0:07:11 6.12.2014
 */
public class OverViewBean extends Marshallable{
    private final String title;
    private List<CombinedFeed> combinedFeedList = new ArrayList<CombinedFeed>();

    public OverViewBean(String title) {
        this.title = title;
    }

    public List<CombinedFeed> getCombinedFeedList() {
        return combinedFeedList;
    }

    public void setCombinedFeedList(List<CombinedFeed> combinedFeedList) {
        this.combinedFeedList = combinedFeedList;
    }


}
