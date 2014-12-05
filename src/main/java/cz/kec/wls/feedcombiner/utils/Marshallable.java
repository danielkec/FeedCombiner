package cz.kec.wls.feedcombiner.utils;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.*;

/**
 * Marshallable class can be used in two ways:
 * 1 - Static methods for marshalling and unmarshalling<br/>
 * 2 - Extending it and adding by that ability to marshal and unmashal istself<br/>
 *
 * @author Daniel Kec <Daniel.Kec at syntea.cz>
 * @since 30.7.2012
 * @version 1.0
 */
public abstract class Marshallable {

    /**
     * Marshalling itself to XML document by JAXB annotations and serializing it to String
     * @return serialized XML document
     * @throws MarshalException
     */
    public String marshal() throws MarshalException{
       return marshal(this);
    }

    /**
     * Unmarshalling itself from XML document by JAXB annotations
     * @param xml xml document serialized as String
     * @return deserialized object
     * @throws JAXBException
     */
    public Object unmarshal(String xml) throws JAXBException{
        return unmarshal(xml,this.getClass());
    }

    /**
     * Marshalling supplied object to XML document by JAXB annotations and serializing it to String
     * @param obj object to be marshalled
     * @return serialized XML document
     * @throws MarshalException
     */
    public static String marshal(Object obj) throws MarshalException{
        try {
            StringWriter sw = new StringWriter();
            JAXBContext jc = JAXBContext.newInstance(obj.getClass());
            Marshaller m = jc.createMarshaller();
            //m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(obj, sw);
            return sw.toString();
        } catch( JAXBException jbe ){
            throw new MarshalException("Error when marshalling " +obj.getClass().getCanonicalName(),jbe);
        }
    }

    /**
     * Unmarshalling itself from XML document by JAXB annotations
     * @param xml xml document serialized as String
     * @param clazz Class to which shoud be object unmarshalled
     * @return serialized XML document
     * @throws JAXBException
     */
    public static Object unmarshal(String xml,Class clazz) throws JAXBException{
        JAXBContext jc = JAXBContext.newInstance(clazz);
        Unmarshaller u = jc.createUnmarshaller();
        return u.unmarshal(new StringReader(xml));
    }
}
