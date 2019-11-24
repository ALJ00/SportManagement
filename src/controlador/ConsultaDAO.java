package controlador;

import modelo.Equipo;
import modelo.Jugador;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XPathQueryService;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ConsultaDAO {

    static String driver = "org.exist.xmldb.DatabaseImpl"; //Driver para eXist
    static String URI = "xmldb:exist://localhost:8083/exist/xmlrpc/db/Coleccionclub"; //URI colección
    static String usu = "admin"; //Usuario
    static String usuPwd = "12345Abcde"; //Clave
    static Collection col = null;

    public ConsultaDAO() {
    }

    // metodo para la conexion
    public static Collection conectar(){
        try {
            Class cl = Class.forName(driver); //Cargar del driver
            Database database = (Database) cl.newInstance(); //Instancia de la BD
            DatabaseManager.registerDatabase(database); //Registro del driver
            col = (Collection) DatabaseManager.getCollection(URI, usu, usuPwd);
            return col;
        } catch (XMLDBException e) {
            System.out.println("Error al inicializar la BD eXist.");
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error en el driver.");
            //e.printStackTrace();
        } catch (InstantiationException e) {
            System.out.println("Error al instanciar la BD.");
            //e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("Error al instanciar la BD.");
            //e.printStackTrace();
        }
        return null;

    }

    // metodo para listarJugadores
    public static void listarJugadores(JTextArea textArea){
        if (conectar() != null) {
            try {

                //ResourceSet result = servicio.query("for $de in doc('file:///C:/Users/usuario/Desktop/test.xml')
                // /departamentos/DEP_ROW return $de");
                XPathQueryService servicio;
                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                ResourceSet result = servicio.query("/Jugadores/Jugador");

                long numeroNodos = result.getSize();

                // recorrer los datos del recurso.
                ResourceIterator i;
                i = result.getIterator();
                if (!i.hasMoreResources()) {
                    System.out.println(" LA CONSULTA NO DEVUELVE NADA O ESTÁ MAL ESCRITA");
                }
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();
                    System.out.println("--------------------------------------------");
                    textArea.append((String) r.getContent()+"\n");


                    System.out.println((String) r.getContent());
                }
                col.close();
            } catch (XMLDBException e) {
                System.out.println(" ERROR AL CONSULTAR DOCUMENTO.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

    }

    // metodo para listarEquipos
    public static void listarEquipos(JTextArea textArea){
        if (conectar() != null) {
            try {

                //ResourceSet result = servicio.query("for $de in doc('file:///C:/Users/usuario/Desktop/test.xml')
                // /departamentos/DEP_ROW return $de");
                XPathQueryService servicio;
                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                ResourceSet result = servicio.query("/Equipos/Equipo");

                // recorrer los datos del recurso.
                ResourceIterator i;
                i = result.getIterator();
                if (!i.hasMoreResources()) {
                    System.out.println(" LA CONSULTA NO DEVUELVE NADA O ESTÁ MAL ESCRITA");
                }
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();
                    System.out.println("--------------------------------------------");

                    // añado contenido al textArea
                    textArea.append((String) r.getContent()+"\n");

                    System.out.println((String) r.getContent());
                }
                col.close();
            } catch (XMLDBException e) {
                System.out.println(" ERROR AL CONSULTAR DOCUMENTO.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

    }

    // metod to get Xml data with Dom
    public static ArrayList<Equipo> listarEquiposDom() throws ParserConfigurationException, IOException, SAXException {

        ArrayList<Equipo> equipos = new ArrayList<>();

        //Get Document Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Build Document
        Document document = builder.parse(new File("equipos.xml"));

        //Normalize the XML Structure; It's just too important !!
        document.getDocumentElement().normalize();

        //Here comes the root node
        Element root = document.getDocumentElement();
        System.out.println(root.getNodeName());

        //Get all players
        NodeList nList = document.getElementsByTagName("Equipo");
        System.out.println("============================");

        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node node = nList.item(temp);
            System.out.println("");    //Just a separator
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                //Print each player´s detail
                Element eElement = (Element) node;

                String cod = eElement.getAttribute("codigoequipo");
                String nombre = eElement.getElementsByTagName("nombre").item(0).getTextContent();
                String ent = eElement.getElementsByTagName("entrenador").item(0).getTextContent();
                String cat = eElement.getElementsByTagName("categoria").item(0).getTextContent();
                String campo = eElement.getElementsByTagName("campoentrenamiento").item(0).getTextContent();

                Equipo nuevoEquipo = new Equipo(cod,nombre,ent,cat,campo);
                equipos.add(nuevoEquipo);

                System.out.println("Codigo equipo : "    + eElement.getAttribute("codigoequipo"));
                System.out.println("Nombre : "  + eElement.getElementsByTagName("nombre").item(0).getTextContent());
                System.out.println("Entrenador : "  + eElement.getElementsByTagName("entrenador").item(0).getTextContent());
                System.out.println("Categoría : "   + eElement.getElementsByTagName("categoria").item(0).getTextContent());
                System.out.println("Campo entrenamiento : "   + eElement.getElementsByTagName("campoentrenamiento").item(0).getTextContent());

            }
        }

        return equipos;
    }

    // metod to get Xml data with Dom
    public static ArrayList<Jugador> listarJugadoresDom() throws ParserConfigurationException, IOException, SAXException {

        ArrayList<Jugador> jugadores = new ArrayList<>();

        //Get Document Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Build Document
        Document document = builder.parse(new File("jugadores.xml"));

        //Normalize the XML Structure; It's just too important !!
        document.getDocumentElement().normalize();

        //Here comes the root node
        Element root = document.getDocumentElement();
        System.out.println(root.getNodeName());

        //Get all players
        NodeList nList = document.getElementsByTagName("Jugador");
        System.out.println("============================");

        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node node = nList.item(temp);
            System.out.println("");    //Just a separator
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                //Print each player´s detail
                Element eElement = (Element) node;

                String dni = eElement.getAttribute("dni");
                String cod = eElement.getAttribute("codigoequipo");
                String nombre = eElement.getElementsByTagName("nombre").item(0).getTextContent();
                String ape = eElement.getElementsByTagName("apellido").item(0).getTextContent();
                String tfno = eElement.getElementsByTagName("telefono").item(0).getTextContent();
                String fnac = eElement.getElementsByTagName("fechanacimiento").item(0).getTextContent();
                String demar = eElement.getElementsByTagName("demarcacion").item(0).getTextContent();
                String sal = eElement.getElementsByTagName("salario").item(0).getTextContent();

                Jugador nuevoJugador = new Jugador(dni,cod,nombre,ape,tfno,fnac,demar,sal);
                jugadores.add(nuevoJugador);

                System.out.println("Jugador dni: "    + eElement.getAttribute("dni"));
                System.out.println("Codigo equipo jgador: "    + eElement.getAttribute("codigoequipo"));
                System.out.println("Nombre : "  + eElement.getElementsByTagName("nombre").item(0).getTextContent());
                System.out.println("Apellido : "  + eElement.getElementsByTagName("apellido").item(0).getTextContent());
                System.out.println("Teléfono : "   + eElement.getElementsByTagName("telefono").item(0).getTextContent());
                System.out.println("Fecha Nacimiento : "   + eElement.getElementsByTagName("fechanacimiento").item(0).getTextContent());
                System.out.println("Demarcación : "    + eElement.getElementsByTagName("demarcacion").item(0).getTextContent());
                System.out.println("Salario : "    + eElement.getElementsByTagName("salario").item(0).getTextContent());
            }
        }

        return jugadores;
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        //ConsultaDAO.listarJugadoresDom();
        ConsultaDAO.listarEquiposDom();
    }


}
