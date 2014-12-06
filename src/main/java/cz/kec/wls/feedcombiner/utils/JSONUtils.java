package cz.kec.wls.feedcombiner.utils;

import java.io.IOException;
import javax.json.stream.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 4, 2014
 */
public class JSONUtils {
    public static String toJSON(Object obj){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
	try {
            return mapper.writeValueAsString(obj);
	} catch (JsonGenerationException ex) {
            throw new RuntimeException("Error when marshalling bean to JSON."+ex);
	} catch (IOException ex) {
            throw new RuntimeException("Error when marshalling bean to JSON."+ex);
        }
    }

    public static String xmlToJSON(String xml){
        try {
            JSONObject xmlJSONObj = XML.toJSONObject(xml);
            String jsonPrettyPrintString = xmlJSONObj.toString();
            return jsonPrettyPrintString;
        } catch (JSONException ex) {
            throw new RuntimeException("Error when converting xml to json."+ex);
        }
    }
}
