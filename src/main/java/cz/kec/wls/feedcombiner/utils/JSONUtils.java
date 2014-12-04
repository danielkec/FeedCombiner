package cz.kec.wls.feedcombiner.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.stream.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 4, 2014
 */
public class JSONUtils {
    public static String toJSON(Object obj){
        ObjectMapper mapper = new ObjectMapper();
	try {
		return mapper.writeValueAsString(obj); 
	} catch (JsonGenerationException e) {
		e.printStackTrace();
	} catch (IOException ex) {
            Logger.getLogger(JSONUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
