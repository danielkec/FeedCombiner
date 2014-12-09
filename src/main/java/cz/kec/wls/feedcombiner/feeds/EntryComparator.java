package cz.kec.wls.feedcombiner.feeds;

import com.rometools.rome.feed.synd.SyndEntry;
import java.util.Comparator;

/**
 * Compare SyndEntries by date when been published or updated, depend on which
 * is initialized.
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since 3.12.2014
 */
public class EntryComparator implements Comparator<SyndEntry> {

    /**
     * Compares SyndEntries by date when been published or updated, depend on
     * which is initialized.
     *
     * @param o1 First entry to be comparised with second
     * @param o2 Second entry to be comparised with the first
     * @return 1 if fist is newer than second, -1 if otherwise or 0 if there are
     * no dates to compare
     */
    @Override
    public int compare(SyndEntry o1, SyndEntry o2) {
        if (o1.getPublishedDate() != null && o1.getPublishedDate() != null) {
            if (o1.getPublishedDate().after(o2.getPublishedDate())) {
                return 1;
            }
            return -1;
        }
        if (o1.getUpdatedDate() != null && o1.getUpdatedDate() != null) {
            if (o1.getUpdatedDate().after(o2.getUpdatedDate())) {
                return 1;
            }
            return -1;
        }
        return 0;
    }

}
