package controlador;

import modelo.Equipo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.util.ArrayList;

public class XpathEquiposUtil {

    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private Document doc;
    private XPathFactory xpathfactory;
    private XPath xpath;
    private XPathExpression expr;
    private Object result;

    public XpathEquiposUtil() throws ParserConfigurationException, IOException, SAXException {

        //Build DOM
        factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // never forget this!
        builder = factory.newDocumentBuilder();
        doc = builder.parse("equipos.xml");

        //Create XPath
        xpathfactory = XPathFactory.newInstance();
        xpath = xpathfactory.newXPath();


    }

    public Equipo configurarObjetoEquipo(Element eElement) {


        String cod = eElement.getAttribute("codigoequipo");
        String nombre = eElement.getElementsByTagName("nombre").item(0).getTextContent();
        String ent = eElement.getElementsByTagName("entrenador").item(0).getTextContent();
        String cat = eElement.getElementsByTagName("categoria").item(0).getTextContent();
        String campo = eElement.getElementsByTagName("campoentrenamiento").item(0).getTextContent();

        Equipo nuevoEquipo = new Equipo(cod, nombre, ent, cat, campo);

        return nuevoEquipo;


    }

    public ArrayList<String> getEquiposPorCodigo(String codigo) throws XPathExpressionException {

        ArrayList<String> equipos = new ArrayList<>();

        // obtener los equipos por codigo
        XPathExpression expr = xpath.compile("//Equipo[@codigoequipo="+"'"+codigo+"'"+"]/nombre/text()");
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;

        for (int i = 0; i < nodes.getLength(); i++) {
            equipos.add(nodes.item(i).getNodeValue());
        }
        return equipos;
    }

    public ArrayList<Equipo> getEquiposPorNombre(String nombre) throws XPathExpressionException {
        ArrayList<Equipo> equipos = new ArrayList<>();

        // obtener los equipos por nombre
        XPathExpression expr = xpath.compile("//Equipo[nombre=" + "'" + nombre + "'" + "]/nombre/node()");
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;

                equipos.add(configurarObjetoEquipo(eElement));


            }

        }


        return equipos;


    }

    public ArrayList<Equipo> getEquiposPorCategoria(String categoria) throws XPathExpressionException {
        ArrayList<Equipo> equipos = new ArrayList<>();

        // obtener los equipos por nombre
        XPathExpression expr = xpath.compile("//Equipo[categoria=" + "'" + categoria + "'" + "]/nombre/node()");
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;

                equipos.add(configurarObjetoEquipo(eElement));


            }

        }


        return equipos;


    }

    public ArrayList<Equipo> getEquiposPorCampoEntreno(String campo) throws XPathExpressionException {
        ArrayList<Equipo> equipos = new ArrayList<>();

        // obtener los equipos por nombre
        XPathExpression expr = xpath.compile("//Equipo[campoentrenamiento=" + "'" + campo + "'" + "]/nombre/node()");
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;

                equipos.add(configurarObjetoEquipo(eElement));


            }

        }


        return equipos;


    }

    public static void main(String[] args) {
        ArrayList<Equipo> equipos = new ArrayList<>();
    }


}