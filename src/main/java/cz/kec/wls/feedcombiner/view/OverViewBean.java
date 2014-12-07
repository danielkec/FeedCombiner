package cz.kec.wls.feedcombiner.view;

import cz.kec.wls.feedcombiner.model.CombinedFeed;
import cz.kec.wls.feedcombiner.utils.Marshallable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since 0:07:11 6.12.2014
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OverViewBean extends Marshallable {

    private String title;

    private List<CombinedFeed> combinedFeedList = new ArrayList<CombinedFeed>();

    public OverViewBean() {
    }

    public OverViewBean(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public List<CombinedFeed> getCombinedFeedList() {
        return combinedFeedList;
    }

    public void setCombinedFeedList(List<CombinedFeed> combinedFeedList) {
        this.combinedFeedList = combinedFeedList;
    }

}
