package cz.kec.wls.feedcombiner.view;

import cz.kec.wls.feedcombiner.model.CombinedFeed;
import cz.kec.wls.feedcombiner.utils.Marshallable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Obejct representation of the UI overview of combined feeds. Will be
 * marshalled and Xslt-ted to HTML, then served over rest resource.
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since 0:07:11 6.12.2014
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OverViewBean extends Marshallable {

    private String title;

    private List<CombinedFeed> combinedFeedList = new ArrayList<CombinedFeed>();

    private OverViewBean() {
        //for JAXB only
    }

    /**
     * Creates new model for UI.
     * @param title title to be rendered on top of the overview page.
     */
    public OverViewBean(String title) {
        this.title = title;
    }

    /**
     * Returns title rendered on top of the overview page.
     * @return title to be rendered on top of the overview page
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns list of the combined feeds to be rendered on overview page.
     * @return List of the combined feeds pojos
     */
    public List<CombinedFeed> getCombinedFeedList() {
        return combinedFeedList;
    }

    /**
     * Sets list of the combined feeds to be rendered on overview page.
     * @param combinedFeedList List of the combined feeds pojos
     */
    public void setCombinedFeedList(List<CombinedFeed> combinedFeedList) {
        this.combinedFeedList = combinedFeedList;
    }

}
