package cz.kec.wls.feedcombiner.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Simple tool for XML transforming.
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since 3.12.2014
 */
public abstract class XMLUtils {

    /**
     * Runs Xslt transformation on supplied xml doc
     * @param inXml xml source document
     * @param xsltInputStream xslt as template
     * @return 
     */
    public static String transform(String inXml, InputStream xsltInputStream) {
        try {
            Source xmlInput = new DOMSource(XMLUtils.parseNSAware(inXml));
            Source xsl = new StreamSource(xsltInputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Result xmlOutput = new StreamResult(byteArrayOutputStream);
            Transformer transformer = TransformerFactory.newInstance().newTransformer(xsl);
            transformer.transform(xmlInput, xmlOutput);
            return byteArrayOutputStream.toString("UTF-8");
        } catch (TransformerException e) {
            throw new RuntimeException("Error during xslt transformation.", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error output serialization.", e);
        }

    }

    /**
     * Parsuje stringove xml do DOM ktery bere v uvahu namespacy
     *
     * @param xmlString xml doc as string
     * @return W3C DOM
     */
    private static Document parseNSAware(String xmlString) {
        try {
            InputSource xmlInputSource = new InputSource(new StringReader(xmlString));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            return dBuilder.parse(xmlInputSource);
        } catch (Exception ex) {
            throw new RuntimeException("Error when parsing Xml doc", ex);
        }
    }
}
