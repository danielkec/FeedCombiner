package cz.kec.wls.feedcombiner.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Simple tool for XML transforming.
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since 3.12.2014
 */
public abstract class XMLUtils {

    private static Logger LOG = LoggerFactory.getLogger(XMLUtils.class);

    /**
     * Validates xml by schema
     *
     * @param data xml document as string to be validated
     * @param xsd schema to be validated against
     * @return true if valid
     */
    public static boolean validate(String data, InputStream xsd) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsd));
            Validator validator = schema.newValidator();
            validator.validate(new DOMSource(XMLUtils.parseNSAware(data)));
            return true;
        } catch (Exception ex) {
            LOG.error("Error when validating Xml", ex);
            return false;
        }

    }

    /**
     * Runs Xslt transformation on supplied xml doc
     *
     * @param inXml xml source document
     * @param xsltInputStream xslt as template
     * @return transfomation result as text
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
     * Removes all nodes of specified namespace
     *
     * @param dom w3c dom to be cleaned from spec. ns nodes
     * @param namespace 
     */
    public static void removeAllNodesOfNS(Document dom, String namespace) {
        List<Node> nodes = getAllNodesByNamespaceRecursive(dom, namespace);
        for (Node node : nodes) {
            node.getParentNode().removeChild(node);
        }
    }

    /**
     * Parsing xml doc text as w3c dom
     *
     * @param xmlString xml doc as string
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
            throw new RuntimeException("Error when parsing Xml doc", ex);
        }
    }

    /**
     * Serializing W3C Node to Xml soc in string, is NS aware
     *
     * @param n node to be serializing
     * @return xml doc as string
     */
    public static String serializeNSAware(Node n) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(n), new StreamResult(writer));
            return writer.getBuffer().toString();
        } catch (TransformerException ex) {
            throw new RuntimeException("Chyba pri serializaci xml", ex);
        }
    }

    /**
     * Recursively fetches all nodes of specified ns. For multi ns documents
     *
     * @param node the starting node.
     * @param namespace desired ns
     */
    private static List<Node> getAllNodesByNamespaceRecursive(Node node, String namespace) {
        List nsNodeList = new ArrayList();
        if (node.getNamespaceURI()!=null && node.getNamespaceURI().equals(namespace)) {
            nsNodeList.add(node);
        }
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); ++i) {
            nsNodeList.addAll(getAllNodesByNamespaceRecursive(list.item(i), namespace));
        }
        return nsNodeList;
    }

}
