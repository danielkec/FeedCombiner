package cz.kec.wls.feedcombiner.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.jdom2.JDOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.jdom2.output.XMLOutputter;
import org.jdom2.input.SAXBuilder;

/**
 * Simple toolbelt for everyone who is working with W3C DOM or JDOM
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since 3.12.2014
 */
public abstract class XMLUtils {

    public static String transform(String inXml, InputStream xsltInputStream) {
        try {
            Source xmlInput = new DOMSource(XMLUtils.parseNSAware(inXml));
            Source xsl = new StreamSource(xsltInputStream);
            //Document dom = XMLUtils.createNSAwareW3CDOM();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Result xmlOutput = new StreamResult(byteArrayOutputStream);
            Transformer transformer = TransformerFactory.newInstance().newTransformer(xsl);
            transformer.transform(xmlInput, xmlOutput);
            return byteArrayOutputStream.toString("UTF-8");
        } catch (TransformerException e) {
            throw new RuntimeException("Error during xslt transformation.",e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error output serialization.",e);
        }

    }

    public static String toString(org.jdom2.Document dom) {
        XMLOutputter outputter = new XMLOutputter();
        return outputter.outputString(dom);
    }

    /**
     * Serialize w3c Document to the text full featured Xml document
     *
     * @param dom to be serialized
     * @return text xml document
     */
    public static String toString(Document dom) {
        return serializeNSAware(dom, false);
    }

    /**
     * Serialize w3c Element to text xml fragment
     *
     * @param el to be serialized
     * @return text xml fragment
     */
    public static String toString(Element el) {
        return serializeNSAware(el, true);
    }

    public static org.jdom2.Document toJDOM(String xml) throws JDOMException, IOException {
        SAXBuilder saxBuilder = new SAXBuilder();
        return saxBuilder.build(new StringReader(xml));
    }

    public static Document createNSAwareW3CDOM(){
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            return dBuilder.newDocument();
        } catch (ParserConfigurationException ex) {
            throw new RuntimeException("Error when creating fresh W3C DOM.",ex);
        }
    }

    /**
     * Parsuje stringove xml do DOM ktery bere v uvahu namespacy
     *
     * @param xmlString xml jako string
     * @return W3C DOM
     */
    public static Document parseNSAware(String xmlString) {
        try {
            InputSource xmlInputSource = new InputSource(new StringReader(xmlString));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            return dBuilder.parse(xmlInputSource);
        } catch (Exception ex) {
            throw new RuntimeException("Error when parsing Xml", ex);
        }
    }

    /**
     * Serializing W3C Node to text
     *
     * @param n node for serialization
     * @param omitProlog If true xml text is just fragment, if true its doc with
     * prolog
     * @return xml as text
     */
    public static String serializeNSAware(Node n, boolean omitProlog) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, omitProlog ? "yes" : "no");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(n), new StreamResult(writer));
            return writer.getBuffer().toString();
        } catch (TransformerException ex) {
            throw new RuntimeException("Error when serializing Xml", ex);
        }
    }
}
