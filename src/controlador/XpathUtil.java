package controlador;

import modelo.Jugador;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.util.ArrayList;

public class XpathUtil {

    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private Document doc;
    private XPathFactory xpathfactory;
    private XPath xpath;
    private XPathExpression expr;
    private Object result;

    public XpathUtil() throws ParserConfigurationException, IOException, SAXException {

        //Build DOM
        factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // never forget this!
        builder = factory.newDocumentBuilder();
        doc = builder.parse("jugadores.xml");

        //Create XPath
        xpathfactory = XPathFactory.newInstance();
        xpath = xpathfactory.newXPath();


    }

    public Jugador configurarObjectoJugador(Element eElement){

        String dni = eElement.getAttribute("dni");
        String cod = eElement.getAttribute("codigoequipo");
        String nombre = eElement.getElementsByTagName("nombre").item(0).getTextContent();
        String ape = eElement.getElementsByTagName("apellido").item(0).getTextContent();
        String tfno = eElement.getElementsByTagName("telefono").item(0).getTextContent();
        String fnac = eElement.getElementsByTagName("fechanacimiento").item(0).getTextContent();
        String demar = eElement.getElementsByTagName("demarcacion").item(0).getTextContent();
        String sal = eElement.getElementsByTagName("salario").item(0).getTextContent();

        Jugador nuevoJugador = new Jugador(dni, cod, nombre, ape, tfno, fnac, demar, sal);

        return nuevoJugador;

    }

    public ArrayList<String> getNombreJugadoresConSalarioMayorDe(String cantidad) throws XPathExpressionException {

        ArrayList<String> juagdores = new ArrayList<>();

        // obtener los jugadores que ganan mas de 1000 euros
        XPathExpression expr = xpath.compile("//Jugador[salario>"+"'"+cantidad+"'"+"]/nombre/text()");
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;

        for (int i = 0; i < nodes.getLength(); i++) {
            juagdores.add(nodes.item(i).getNodeValue());
        }

        return juagdores;



    }

    public ArrayList<String>getNombreJugadoresPorDemarcacion(String demarcacion) throws XPathExpressionException {

        ArrayList<String> jugadores = new ArrayList<>();

        // obtener los jugadores por demarcacion
        XPathExpression expr = xpath.compile("//Jugador[demarcacion="+"'"+demarcacion+"'"+"]/nombre/node()");
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;

        for (int i = 0; i < nodes.getLength(); i++) {
            jugadores.add(nodes.item(i).getNodeValue());
        }

        return jugadores;

    }

    public ArrayList<String>getNombreJugadoresPorDni(String dni) throws XPathExpressionException {

        ArrayList<String> jugadores = new ArrayList<>();

        // obtener los jugadores por dni
        XPathExpression expr = xpath.compile("//Jugador[@dni="+"'"+dni+"'"+"]/nombre/text()");
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;

        for (int i = 0; i < nodes.getLength(); i++) {
            jugadores.add(nodes.item(i).getNodeValue());
        }

        return jugadores;

    }



}
