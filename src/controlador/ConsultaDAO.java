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
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ConsultaDAO {

    static String driver = "org.exist.xmldb.DatabaseImpl"; //Driver para eXist
    static String URI = "xmldb:exist://localhost:8083/exist/xmlrpc/db/SportManagement"; //URI colección
    static String usu = "admin"; //Usuario
    static String usuPwd = "12345Abcde"; //Clave
    static Collection col = null;

    public ConsultaDAO() {
    }

    // metodo para la conexion
    public static Collection conectar() {
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

    // **************** Consultas de equipos ******************************************************

    // metodo para listarEquipos
    public static void listarEquipos(JTextArea textArea) {

        if (conectar() != null) {
            try {

                //ResourceSet result = servicio.query("for $de in doc('file:///C:/Users/usuario/Desktop/test.xml')
                // /departamentos/DEP_ROW return $de");
                XPathQueryService servicio;
                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                ResourceSet result = servicio.query("for $eq in doc('file:///C:/Users/armas/Desktop/SportManagement/equipos.xml') /Equipos/Equipo return $eq");

                // recorrer los datos del recurso.
                ResourceIterator i;
                i = result.getIterator();
                if (!i.hasMoreResources()) {
                    System.out.println(" LA CONSULTA NO DEVUELVE NADA O ESTÁ MAL ESCRITA");
                }
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();
                    System.out.println("--------------------------------------------");

                    String unformattedXml = r.getContent().toString();

                    String leastPrettifiedXml = unformattedXml.replaceAll("><", ">\n<");

                    // añado contenido al textArea
                    textArea.append(leastPrettifiedXml);

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

    // metodo para listar los equipos usando Dom
    public static ArrayList<Equipo> listarEquiposDom() throws ParserConfigurationException, IOException, SAXException {

        ArrayList<Equipo> equipos = new ArrayList<>();

        //Get Document Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        try {
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

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node node = nList.item(temp);
                System.out.println("");    //Just a separator
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    //Print each player´s detail
                    Element eElement = (Element) node;

                    String cod = eElement.getAttribute("codigoequipo");
                    String nombre = eElement.getElementsByTagName("nombre").item(0).getTextContent();
                    String ent = eElement.getElementsByTagName("entrenador").item(0).getTextContent();
                    String cat = eElement.getElementsByTagName("categoria").item(0).getTextContent();
                    String campo = eElement.getElementsByTagName("campoentrenamiento").item(0).getTextContent();

                    Equipo nuevoEquipo = new Equipo(cod, nombre, ent, cat, campo);
                    equipos.add(nuevoEquipo);

                    System.out.println("Codigo equipo : " + eElement.getAttribute("codigoequipo"));
                    System.out.println("Nombre : " + eElement.getElementsByTagName("nombre").item(0).getTextContent());
                    System.out.println("Entrenador : " + eElement.getElementsByTagName("entrenador").item(0).getTextContent());
                    System.out.println("Categoría : " + eElement.getElementsByTagName("categoria").item(0).getTextContent());
                    System.out.println("Campo entrenamiento : " + eElement.getElementsByTagName("campoentrenamiento").item(0).getTextContent());

                }
            }


        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "El archivo equipos aún no ha sido creado, inserte equipos");
        }


        return equipos;
    }

    //insertar nuevo equipo
    public static void insertarNuevoEquipo(Equipo e, JTextArea textArea) {

        String nuevoequipo = "<Equipo codigoequipo=" +"'"+ e.getCodigoEquipo()+"'" + " ><nombre>" + e.getNombre() + "</nombre><entrenador>" + e.getEntrenador()
                + "</entrenador><categoria>" + e.getCategoria() + "</categoria><campoentrenamiento>" + e.getCampoEntrenamiento() + "</campoentrenamiento></Equipo>";

        System.out.println(nuevoequipo);
        if (conectar() != null) {
            try {

                //for $de in
                //doc('file:///D:/XML/pruebaxquery/NUEVOS_DEP.xml')
                ///NUEVOS_DEP/DEP_ROW
                //return update insert $de into /departamentos

                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                System.out.printf("Inserto: %s \n", e.getNombre());

                ResourceSet result = servicio.query("update insert " + nuevoequipo + " into /Equipos");
                //"for $de in doc('file:///C:/Users/armas/Desktop/SportManagement/equipos.xml') /" + nuevoequipo + "return update insert $de into /Equipos"
                String formateado = nuevoequipo.replaceAll("><", ">\n<");
                textArea.append(formateado);
                col.close(); //borramos

                System.out.println("Dep insertado.");
            } catch (Exception ex) {
                System.out.println("Error al insertar empleado.");
                ex.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }
    }

    // metodo que elimina un nodo del documento en funcion de su codigo
    public static boolean eliminarEquipoDomXpath(Equipo equipo) throws ParserConfigurationException,
            IOException, SAXException, TransformerException {

        boolean respuesta = false;

        // 1. cargar el XML original
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("equipos.xml"));

        // 2. buscar y eliminar el elemento Equipo de entre
        //    muchos elementos <Equipo> ubicados en cualquier posicion del documento
        NodeList items = doc.getElementsByTagName("Equipo");
        for (int ix = 0; ix < items.getLength(); ix++) {
            Element element = (Element) items.item(ix);
            // elejir un elemento especifico por algun atributo
            if (element.getAttribute("codigoequipo").equalsIgnoreCase(equipo.getCodigoEquipo())) {
                // borrar elemento
                element.getParentNode().removeChild(element);


                // 3. Exportar nuevamente el XML
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                Result output = new StreamResult(new File("equipos.xml"));
                Source input = new DOMSource(doc);
                transformer.transform(input, output);

                respuesta = true;

            } else {
                respuesta = false;
                System.out.println("Error no e ha eliminado nada");
            }
        }


        return respuesta;

    }

    // comprobar si existe un equipo con codigo x
    public static boolean comprobarSiExisteEquipo(Equipo equipo) throws ParserConfigurationException, IOException, SAXException {

        boolean respuesta = false;

        // 1. cargar el XML original
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("equipos.xml"));

        // 2. buscar y eliminar el elemento Equipo de entre
        //    muchos elementos <Equipo> ubicados en cualquier posicion del documento
        NodeList items = doc.getElementsByTagName("Equipo");
        for (int ix = 0; ix < items.getLength(); ix++) {
            Element element = (Element) items.item(ix);
            // elejir un elemento especifico por algun atributo
            if (element.getAttribute("codigoequipo").equalsIgnoreCase(equipo.getCodigoEquipo())){
                System.out.println("El equipo ya existe");

                respuesta = true;

            }



        }


        return respuesta;


    }

    // modificar un equipo
    public static boolean modificarEquipo(Equipo equipo) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        boolean respuesta = false;

        // 1. cargar el XML original
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("equipos.xml"));

        // 2. buscar y eliminar el elemento Equipo de entre
        //    muchos elementos <Equipo> ubicados en cualquier posicion del documento
        NodeList items = doc.getElementsByTagName("Equipo");
        for (int ix = 0; ix < items.getLength(); ix++) {
            Element element = (Element) items.item(ix);
            // elejir un elemento especifico por algun atributo
            if (element.getAttribute("codigoequipo").equalsIgnoreCase(equipo.getCodigoEquipo())) {


                element.setAttribute("codigoequipo",equipo.getCodigoEquipo());
                element.getElementsByTagName("nombre").item(0).setTextContent(equipo.getNombre());
                element.getElementsByTagName("entrenador").item(0).setTextContent(equipo.getEntrenador());
                element.getElementsByTagName("categoria").item(0).setTextContent(equipo.getCategoria());
                element.getElementsByTagName("campoentrenamiento").item(0).setTextContent(equipo.getCampoEntrenamiento());


                // 3. Exportar nuevamente el XML
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                Result output = new StreamResult(new File("equipos.xml"));
                Source input = new DOMSource(doc);
                transformer.transform(input, output);

                System.out.println("El equipo ha sido modificado correctamente");

                respuesta = true;


            }
        }




        return respuesta;

    }

    // **************** Consultas de jugadores ***************************************************************

    // metodo para listarJugadores
    public static void listarJugadores(JTextArea textArea) {

        if (conectar() != null) {
            try {

                //ResourceSet result = servicio.query("for $de in doc('file:///C:/Users/usuario/Desktop/test.xml')
                // /departamentos/DEP_ROW return $de");

                XPathQueryService servicio;
                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                ResourceSet result = servicio.query("for $eq in doc('file:///C:/Users/armas/Desktop/SportManagement/jugadores.xml') /Jugadores/Jugador return $eq");


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
                    String unformattedXml = r.getContent().toString();

                    String leastPrettifiedXml = unformattedXml.replaceAll("><", ">\n<");

                    // añado contenido al textArea
                    textArea.append(leastPrettifiedXml);


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

    // metodo para listar los jugadores usando Dom
    public static ArrayList<Jugador> listarJugadoresDom() throws ParserConfigurationException, IOException, SAXException {

        ArrayList<Jugador> jugadores = new ArrayList<>();

        //Get Document Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        try {

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

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node node = nList.item(temp);
                System.out.println("");    //Just a separator
                if (node.getNodeType() == Node.ELEMENT_NODE) {
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

                    Jugador nuevoJugador = new Jugador(dni, cod, nombre, ape, tfno, fnac, demar, sal);
                    jugadores.add(nuevoJugador);

                    System.out.println("Jugador dni: " + eElement.getAttribute("dni"));
                    System.out.println("Codigo equipo jgador: " + eElement.getAttribute("codigoequipo"));
                    System.out.println("Nombre : " + eElement.getElementsByTagName("nombre").item(0).getTextContent());
                    System.out.println("Apellido : " + eElement.getElementsByTagName("apellido").item(0).getTextContent());
                    System.out.println("Teléfono : " + eElement.getElementsByTagName("telefono").item(0).getTextContent());
                    System.out.println("Fecha Nacimiento : " + eElement.getElementsByTagName("fechanacimiento").item(0).getTextContent());
                    System.out.println("Demarcación : " + eElement.getElementsByTagName("demarcacion").item(0).getTextContent());
                    System.out.println("Salario : " + eElement.getElementsByTagName("salario").item(0).getTextContent());
                }
            }

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "El archivo jugadores aún no ha sido creado, inserte jugadores");

        }


        return jugadores;
    }

    //insertar nuevo jugador
    public static void insertarNuevoJugador(Jugador e, JTextArea textArea) {

        String nuevoJugador = "<Jugador dni="+"'"+  e.getDni() +"'"+ " codigoequipo=" +"'"+ e.getCodigoEquipo()+"'" + "><nombre>" + e.getNombre()
                + "</nombre><apellido>" + e.getApellido() + "</apellido><telefono>" + e.getTfno() + "</telefono><fechanacimiento>" + e.getFechaNacimiento() + "</fechanacimiento>" +
                "<demarcacion>" + e.getDemarcacion() + "</demarcacion><salario>" + e.getSalario() + "</salario></Jugador>";

        System.out.println(nuevoJugador);
        if (conectar() != null) {
            try {

                //for $de in
                //doc('file:///D:/XML/pruebaxquery/NUEVOS_DEP.xml')
                ///NUEVOS_DEP/DEP_ROW
                //return update insert $de into /departamentos

                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                System.out.printf("Inserto: %s \n", e.getNombre());

                ResourceSet result = servicio.query("update insert " + nuevoJugador + " into /Jugadores");
                //"for $de in doc('file:///C:/Users/armas/Desktop/SportManagement/equipos.xml') /" + nuevoequipo + "return update insert $de into /Equipos"
                String formateado = nuevoJugador.replaceAll("><", ">\n<");
                textArea.append(formateado);
                col.close(); //borramos

                System.out.println("Jug insertado.");
            } catch (Exception ex) {
                System.out.println("Error al insertar jugador.");
                ex.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }
    }

    // metodo que elimina un nodo del documento en funcion de su codigo
    public static boolean eliminarJugadorDomXpath(Jugador jugador) throws ParserConfigurationException,
            IOException, SAXException, TransformerException {

        boolean respuesta = false;

        // 1. cargar el XML original
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("jugadores.xml"));

        // 2. buscar y eliminar el elemento Equipo de entre
        //    muchos elementos <Equipo> ubicados en cualquier posicion del documento
        NodeList items = doc.getElementsByTagName("Jugador");
        for (int ix = 0; ix < items.getLength(); ix++) {
            Element element = (Element) items.item(ix);
            // elejir un elemento especifico por algun atributo
            if (element.getAttribute("dni").equalsIgnoreCase(jugador.getDni())) {
                // borrar elemento
                element.getParentNode().removeChild(element);


                // 3. Exportar nuevamente el XML
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                Result output = new StreamResult(new File("jugadores.xml"));
                Source input = new DOMSource(doc);
                transformer.transform(input, output);

                respuesta = true;

            } else {
                respuesta = false;
                System.out.println("Error no se ha eliminado nada");
            }
        }


        return respuesta;

    }

    // comprobar si existe un equipo con codigo x
    public static boolean comprobarSiExisteJugador(Jugador jugador) throws ParserConfigurationException, IOException, SAXException {

        boolean respuesta = false;



        // 1. cargar el XML original
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("jugadores.xml"));

        // 2. buscar y eliminar el elemento Equipo de entre
        //    muchos elementos <Equipo> ubicados en cualquier posicion del documento
        NodeList items = doc.getElementsByTagName("Jugador");
        for (int ix = 0; ix < items.getLength(); ix++) {
            Element element = (Element) items.item(ix);
            // elejir un elemento especifico por algun atributo
            if (element.getAttribute("dni").equalsIgnoreCase(jugador.getDni())) {

                System.out.println("El jugador ya existe");

                respuesta = true;

            } else {
                respuesta = false;
                System.out.println("Error no se ha eliminado nada");
            }
        }


        return respuesta;


    }

    // modificar un equipo
    public static boolean modificarJugador(Jugador jugador) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        boolean respuesta = false;

        // 1. cargar el XML original
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("jugadores.xml"));

        // 2. buscar y eliminar el elemento Equipo de entre
        //    muchos elementos <Equipo> ubicados en cualquier posicion del documento
        NodeList items = doc.getElementsByTagName("Jugador");
        for (int ix = 0; ix < items.getLength(); ix++) {
            Element element = (Element) items.item(ix);
            // elejir un elemento especifico por algun atributo
            if (element.getAttribute("dni").equalsIgnoreCase(jugador.getDni())) {

                element.setAttribute("dni",jugador.getDni());
                element.setAttribute("codigoequipo",jugador.getCodigoEquipo());

                element.getElementsByTagName("nombre").item(0).setTextContent(jugador.getNombre());
                element.getElementsByTagName("apellido").item(0).setTextContent(jugador.getApellido());
                element.getElementsByTagName("telefono").item(0).setTextContent(jugador.getTfno());
                element.getElementsByTagName("fechanacimiento").item(0).setTextContent(jugador.getFechaNacimiento());
                element.getElementsByTagName("demarcacion").item(0).setTextContent(jugador.getDemarcacion());
                element.getElementsByTagName("salario").item(0).setTextContent(jugador.getSalario());


                // 3. Exportar nuevamente el XML
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                Result output = new StreamResult(new File("jugadores.xml"));
                Source input = new DOMSource(doc);
                transformer.transform(input, output);

                System.out.println("El jugador ha sido modificado correctamente");

                respuesta = true;


            }
        }




        return respuesta;

    }


    // *********************************** main para testear ***********************************************
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException,
            XPathExpressionException, TransformerException {
        //ConsultaDAO.listarJugadoresDom();
        //ConsultaDAO.listarEquiposDom();

        Equipo e = new Equipo();
        e.setCodigoEquipo("a");
        e.setNombre("hola");
        e.setEntrenador("peio");
        e.setCategoria("hola");
        e.setCampoEntrenamiento("hola");

        //eliminarEquipoDomXpath(e);

        //listarEquiposDom();

        //comprobarSiExisteEquipo(e);

        modificarEquipo(e);


    }

}
