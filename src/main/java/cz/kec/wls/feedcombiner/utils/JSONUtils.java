package cz.kec.wls.feedcombiner.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import javax.json.stream.JsonGenerationException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple utils for marshaling objects to json, and converting XML to JSON.
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 4, 2014
 */
public abstract class JSONUtils {

    private static Logger LOG = LoggerFactory.getLogger(JSONUtils.class);

    /**
     * Validate Json document against supplied json schema
     *
     * @see ProcessingReport
     * @param data Json doc as string text to be validated
     * @param schemaInputStream Json schema to be validated against
     * @return ProcessingReport which has method isSuccess indeicating validity
     * result
     */
    public static ProcessingReport validateJSON(String data, InputStream schemaInputStream) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode dataNode = mapper.readTree(data);
            JsonNode schemaNode = mapper.readTree(new String(IOUtils.toByteArray(schemaInputStream), "UTF-8"));
            return JsonSchemaFactory.byDefault().getValidator().validate(schemaNode, dataNode);
        } catch (Exception ex) {
            throw new RuntimeException("Error when validating Json by schema.", ex);
        }
    }

    public static String toJSON(Object obj) {
        org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonGenerationException ex) {
            throw new RuntimeException("Error when marshalling bean to JSON.", ex);
        } catch (IOException ex) {
            throw new RuntimeException("IO Error when marshalling bean to JSON.", ex);
        }
    }

    /**
     * Converts XML doc to JSON doc
     *
     * @param xml doc to be converted to JSON
     * @return JSON doc created from suplied xml
     */
    public static String xmlToJSON(String xml) {
        try {
            JSONObject xmlJSONObj = XML.toJSONObject(xml);
            String jsonString = xmlJSONObj.toString();
            return jsonString;
        } catch (JSONException ex) {
            throw new RuntimeException("Error when converting xml to json.", ex);
        }
    }
}
