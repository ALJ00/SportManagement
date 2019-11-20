package resources;

import modelo.Jugador;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class CreadorColeccionJugador {

    public CreadorColeccionJugador() {
    }

    public static void añadirJugadorAlaColeccion(Jugador jugador) throws ParserConfigurationException,
            TransformerException, IOException, SAXException {

        // creo instancia para construir el parser
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document;

        // variable file para comprobar si el archivo existe o no
        File file = new File("jugadores.xml");

        // compruebo si el archivo existe
        if (!file.exists()) {

            DOMImplementation implementation = builder.getDOMImplementation();

            // creo el document
            document = implementation.createDocument(null, "Jugadores", null);
            document.setXmlVersion("1.0");

        } else {

            // leo jugadores.xml file
            document = builder.parse("jugadores.xml");
        }


        //Creo el nodo raiz del documento xml
        Element raiz = document.getDocumentElement(); //nodo jugador


        // Creo los items Jugador y sus atributos
        Element itemElemento = document.createElement("Jugador");
        itemElemento.setAttribute("codigoequipo", jugador.getCodigoEquipo());
        itemElemento.setAttribute("dni", jugador.getDni());

        // Creo el elemento nombre
        Element nombre = document.createElement("nombre");
        Text text1 = document.createTextNode(jugador.getNombre()); //damos valor
        nombre.appendChild(text1);

        // creo el elemento apellido
        Element apellido = document.createElement("apellido");
        Text text2 = document.createTextNode(jugador.getApellido()); //damos valor
        apellido.appendChild(text2);

        // creo el elemento tfno
        Element tfno = document.createElement("telefono");
        Text text3 = document.createTextNode(jugador.getTfno()); //damos valor
        tfno.appendChild(text3);

        // creo el elemento fechanac
        Element fechanac = document.createElement("fechanacimiento");
        Text text4 = document.createTextNode(jugador.getFechaNacimiento()); //damos valor
        fechanac.appendChild(text4);

        // creo el elemento demarcacion
        Element demarcacion = document.createElement("demarcacion");
        Text text5 = document.createTextNode(jugador.getDemarcacion()); //damos valor
        fechanac.appendChild(text5);


        // creo el elemento salario
        Element salario = document.createElement("salario");
        Text text6 = document.createTextNode(jugador.getSalario()); //damos valor
        salario.appendChild(text6);


        // Añado los hijos al elemeto padre item
        itemElemento.appendChild(nombre);
        itemElemento.appendChild(apellido);
        itemElemento.appendChild(tfno);
        itemElemento.appendChild(fechanac);
        itemElemento.appendChild(demarcacion);
        itemElemento.appendChild(salario);

        // añado a la raiz del documento el elemento item con sus correspondientes hijos
        raiz.appendChild(itemElemento);


        //Se crea la fuente XML a partir del documento
        Source source = new DOMSource(document);

        //Se crea el resultado en el fichero Empleados.xml
        Result result = new StreamResult(new java.io.File("jugadores.xml"));

        //Se obtiene un TransformerFactory
        Transformer transformer = TransformerFactory.newInstance().newTransformer();

        //Se realiza la transformación de documento a fichero
        transformer.transform(source, result);


    }
}
