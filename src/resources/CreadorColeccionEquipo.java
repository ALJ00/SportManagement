package resources;

import modelo.Equipo;
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

public class CreadorColeccionEquipo {

    public CreadorColeccionEquipo() {
    }

    public static void añadirEquipoAlaColeccion(Equipo equipo) throws ParserConfigurationException,
            TransformerException, IOException, SAXException {

        // creo instancia para construir el parser
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document;

        // variable file para comprobar si el archivo existe o no
        File file = new File("equipos.xml");

        // compruebo si el archivo existe
        if (!file.exists()) {

            DOMImplementation implementation = builder.getDOMImplementation();

            // creo el document
            document = implementation.createDocument(null, "Equipos", null);
            document.setXmlVersion("1.0");

        } else {

            // leo jugadores.xml file
            document = builder.parse("equipos.xml");
        }


        //Creo el nodo raiz del documento xml
        Element raiz = document.getDocumentElement(); //nodo jugador


        // Creo los items Equipo y sus atributos
        Element itemElemento = document.createElement("Equipo");
        itemElemento.setAttribute("codigoequipo", equipo.getCodigoEquipo());


        // Creo el elemento nombre
        Element nombre = document.createElement("nombre");
        Text text1 = document.createTextNode(equipo.getNombre()); //damos valor
        nombre.appendChild(text1);

        // creo el elemento entrenador
        Element entre = document.createElement("entrenador");
        Text text2 = document.createTextNode(equipo.getEntrenador()); //damos valor
        entre.appendChild(text2);

        // creo el elemento categ
        Element cat = document.createElement("categoria");
        Text text3 = document.createTextNode(equipo.getCategoria()); //damos valor
        cat.appendChild(text3);

        // creo el elemento campo
        Element campo = document.createElement("campoentrenamiento");
        Text text4 = document.createTextNode(equipo.getCampoEntrenamiento()); //damos valor
        campo.appendChild(text4);


        // Añado los hijos al elemeto padre item
        itemElemento.appendChild(nombre);
        itemElemento.appendChild(entre);
        itemElemento.appendChild(cat);
        itemElemento.appendChild(campo);


        // añado a la raiz del documento el elemento item con sus correspondientes hijos
        raiz.appendChild(itemElemento);


        //Se crea la fuente XML a partir del documento
        Source source = new DOMSource(document);

        //Se crea el resultado en el fichero Empleados.xml
        Result result = new StreamResult(new java.io.File("equipos.xml"));

        //Se obtiene un TransformerFactory
        Transformer transformer = TransformerFactory.newInstance().newTransformer();

        //Se realiza la transformación de documento a fichero
        transformer.transform(source, result);


    }
}
