package vista;

import controlador.ConsultaDAO;
import controlador.XpathEquiposUtil;
import controlador.XpathUtil;
import modelo.Equipo;
import modelo.Jugador;
import modelo.ModeloTablaEquipos;
import modelo.ModeloTablaJugadores;
import org.xml.sax.SAXException;
import resources.CreadorColeccionEquipo;
import resources.CreadorColeccionJugador;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AppWindow {
    private JPanel contenedorPrincipal;
    private JPanel contenedorGestionEquipos;
    private JPanel conetenedorGestionJugadores;
    private JPanel contenedorJugadores;
    private JPanel conetendorDatosEquipos;
    private JButton buttonAutomaticJug;
    private JButton altaButtonJugador;
    private JTextField textFieldDniJug;
    private JTextField textFieldNombreJug;
    private JTextField textFieldCodEquJug;
    private JTextField textFieldApelliJug;
    private JTextField textFieldTfnoJug;
    private JTextField textFieldFchaNacJug;
    private JTextField textFieldDemarcJug;
    private JTextField textFieldSalarJug;
    private JButton buttonAutomaticEquipo;
    private JButton altaButtonEquipo;
    private JTextField textFieldCodEq;
    private JTextField textFieldNombreEquip;
    private JTextField textFieldEntrenEquip;
    private JTextField textFieldCategEquip;
    private JTextField textFieldCampoEntreEquip;
    private JButton eliminarButtonCrudJugador;
    private JButton buttonActualizarCrudJugador;
    private JPanel contenedorBotonesCrud;
    private JScrollPane conetendorScrollTextArea;
    private JTextArea textArea;
    private JButton buttonListarJugadores;
    private JPanel contenedorTablaJugadores;
    private JScrollPane scrollPaneTablaJugadores;
    private JTable tablaJugadores;
    private JPanel contenedorTablaTextAreaJugadores;
    private JPanel conetendorGestAvanzJugadores;
    private JPanel conetenedorLabelsTexfGestAvanzJugadores;
    private JPanel conetendorDatosJugadores;
    private JPanel contendorTablaTextAreaEquipos;
    private JPanel contenedorTablaEquipos;
    private JScrollPane conetenedorScrollTextAreaEquipos;
    private JPanel conetenedoCrudBotonesEquipos;
    private JTextArea textAreaEquipos;
    private JScrollPane scrollPaneEquipos;
    private JTable tabalEquipos;
    private JPanel conetendorEquiposTexfIeldsLabelsGestAvanz;
    private JPanel contenedorGestAvanzEquipos;
    private JButton listarEquiposButton;
    private JButton eliminarButtonEquipos;
    private JButton actualizarButtonEquipos;
    private JRadioButton equiposPorCodigoRadioButton;
    private JRadioButton equiposPorNombreRadioButton;
    private JRadioButton equiposPorCategoríaRadioButton;
    private JButton buttonBuscAvanEquip;
    private JRadioButton jugadorPorDniRadioButton;
    private JRadioButton jugadorPorCodigoRadioButton;
    private JRadioButton salarioXRadioButton;
    private JRadioButton jugadorPorDemarcaciónRadioButton;
    private JButton buttonBusqAvanzJugador;
    private static JFrame frame;
    private ModeloTablaJugadores modeloTablaJugadores = new ModeloTablaJugadores();
    private ModeloTablaEquipos modeloTablaEquipos = new ModeloTablaEquipos();

    public AppWindow() throws IOException, SAXException, ParserConfigurationException {

        // listeners buttons
        altaButtonJugador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String dni = textFieldDniJug.getText();
                String cod = textFieldCodEquJug.getText();
                String name = textFieldNombreJug.getText();
                String ap = textFieldApelliJug.getText();
                String tfno = textFieldTfnoJug.getText();
                String fnac = textFieldFchaNacJug.getText();
                String de = textFieldDemarcJug.getText();
                String s = textFieldSalarJug.getText();


                if (comprobarTextFieldsJugador(dni, cod, name, ap, tfno, fnac, de, s)) {

                    Jugador jugador = new Jugador(dni, cod, name, ap, tfno, fnac, de, s);

                    try {

                        File file = new File("jugadores.xml");

                        // si el archivo no existe en la nueva creacion de un jugador creo la coleccion y por tanto el archivo
                        if(!file.exists()){
                            //inserto nuevo equipo en la coleccion local
                            CreadorColeccionJugador.añadirJugadorAlaColeccion(jugador);

                            // inserto nuevo equipo en la coleccion de exists
                            ConsultaDAO.insertarNuevoJugador(jugador,textArea);

                            // añado al table model un equipo para visualizarlo en la tabla
                            modeloTablaJugadores.adicionarJugador(jugador);
                            JOptionPane.showMessageDialog(null, "Insertado nuevo jugador");

                        }else{

                            // si existe el archivo compruebo si hay algun jugdor repetido
                            if(ConsultaDAO.comprobarSiExisteJugador(jugador)){

                                JOptionPane.showMessageDialog(null, "El jugador ya existe");

                            }else {
                                //inserto nuevo equipo en la coleccion local
                                CreadorColeccionJugador.añadirJugadorAlaColeccion(jugador);

                                // inserto nuevo equipo en la coleccion de exists
                                ConsultaDAO.insertarNuevoJugador(jugador,textArea);

                                // añado al table model un equipo para visualizarlo en la tabla
                                modeloTablaJugadores.adicionarJugador(jugador);
                                JOptionPane.showMessageDialog(null, "Insertado nuevo jugador");

                            }

                        }

                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    } catch (TransformerException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    } catch (SAXException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }

                    JOptionPane.showMessageDialog(null, "Insertado nuevo jugador");

                } else {

                    JOptionPane.showMessageDialog(null, "Introduce todos lo campos");

                }

                limpiarTextFieldsJugador();


            }
        });
        altaButtonEquipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String cod = textFieldCodEq.getText();
                String name = textFieldNombreEquip.getText();
                String entre = textFieldEntrenEquip.getText();
                String cat = textFieldCategEquip.getText();
                String campo = textFieldCampoEntreEquip.getText();

                if (comprobarTextFieldsEquipo(cod, name, entre, cat, campo)) {

                    try {
                        Equipo equipo = new Equipo(cod, name, entre, cat, campo);
                        File file = new File("equipos.xml");

                        // si el archivo no existe ceo la coleccion y por tanto el nuevo equipo
                        if(!file.exists()){

                            //inserto nuevo equipo en la coleccion local
                            CreadorColeccionEquipo.añadirEquipoAlaColeccion(equipo);

                            // inserto nuevo equipo en la coleccion de exists
                            ConsultaDAO.insertarNuevoEquipo(equipo,textAreaEquipos);

                            // añado al table model un equipo para visualizarlo en la tabla
                            modeloTablaEquipos.adicionarEquipo(equipo);
                            JOptionPane.showMessageDialog(null, "Insertado nuevo equipo");



                        }else if(file.exists()){

                            // si existe el archivo compruebo si hay equipos repetidos
                            if(ConsultaDAO.comprobarSiExisteEquipo(equipo)){

                                JOptionPane.showMessageDialog(null, "El equipo ya existe");

                            }else {
                                //inserto nuevo equipo en la coleccion local
                                CreadorColeccionEquipo.añadirEquipoAlaColeccion(equipo);

                                // inserto nuevo equipo en la coleccion de exists
                                ConsultaDAO.insertarNuevoEquipo(equipo,textAreaEquipos);

                                // añado al table model un equipo para visualizarlo en la tabla
                                modeloTablaEquipos.adicionarEquipo(equipo);
                                JOptionPane.showMessageDialog(null, "Insertado nuevo equipo");

                            }


                        }



                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    } catch (TransformerException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    } catch (SAXException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }



                } else {

                    JOptionPane.showMessageDialog(null, "Introduce todos lo campos");

                }

                limpiarTextFieldsEquipo();

            }
        });
        buttonListarJugadores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {
                    // tabla jugadores
                    tablaJugadores.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 10));
                    textArea.setText("");
                    modeloTablaJugadores = new ModeloTablaJugadores(ConsultaDAO.listarJugadoresDom());
                    tablaJugadores.setModel(modeloTablaJugadores);

                    ConsultaDAO.conectar();
                    ConsultaDAO.listarJugadores(textArea);

                } catch (ParserConfigurationException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SAXException ex) {
                    ex.printStackTrace();
                }

            }
        });
        listarEquiposButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    // tabla equipos
                    tabalEquipos.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 10));
                    textAreaEquipos.setText("");

                    modeloTablaEquipos = new ModeloTablaEquipos(ConsultaDAO.listarEquiposDom());
                    tabalEquipos.setModel(modeloTablaEquipos);

                    ConsultaDAO.conectar();
                    ConsultaDAO.listarEquipos(textAreaEquipos);


                } catch (ParserConfigurationException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SAXException ex) {
                    ex.printStackTrace();
                }

            }
        });
        eliminarButtonEquipos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // selecciono la fila de la tabla seleccionada
                int row = tabalEquipos.getSelectedRow();

                if(row == -1){
                    JOptionPane.showMessageDialog(null,"Seleccione una fila de la tabla equipos");
                }else{

                    Equipo eq = new Equipo();
                    eq.setCodigoEquipo(modeloTablaEquipos.getValueAt(row,0).toString().toLowerCase());
                    eq.setNombre(modeloTablaEquipos.getValueAt(row,1).toString());
                    eq.setEntrenador(modeloTablaEquipos.getValueAt(row,2).toString());
                    eq.setCategoria(modeloTablaEquipos.getValueAt(row,3).toString());
                    eq.setCampoEntrenamiento(modeloTablaEquipos.getValueAt(row,4).toString());


                    try {
                        if(!ConsultaDAO.eliminarEquipoDomXpath(eq)){
                            JOptionPane.showMessageDialog(null,"Error, no se ha eliminado ningún equipo.");
                        }else{

                            //actualizo el table model
                            modeloTablaEquipos.eliminarEquipo(row);

                            // actualizo el TextArea
                            textAreaEquipos.setText("");
                            ConsultaDAO.conectar();
                            ConsultaDAO.listarEquipos(textAreaEquipos);

                            JOptionPane.showMessageDialog(null,"Equipo eliminado correctamente.");
                        }
                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SAXException ex) {
                        ex.printStackTrace();
                    } catch (TransformerException ex) {
                        ex.printStackTrace();
                    }

                }


            }
        });
        actualizarButtonEquipos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Equipo eq;

                if(!tabalEquipos.isEditing()){

                    JOptionPane.showMessageDialog(null,"Seleccione una fila de la tabla equipos");

                }else{

                    // selecciono la fila de la tabla seleccionada
                    int row = tabalEquipos.getEditingRow();

                    eq = new Equipo();
                    eq.setCodigoEquipo(modeloTablaEquipos.getValueAt(row,0).toString());
                    eq.setNombre(modeloTablaEquipos.getValueAt(row,1).toString());
                    eq.setEntrenador(modeloTablaEquipos.getValueAt(row,2).toString());
                    eq.setCategoria(modeloTablaEquipos.getValueAt(row,3).toString());
                    eq.setCampoEntrenamiento(modeloTablaEquipos.getValueAt(row,4).toString());


                    try {
                        if(!ConsultaDAO.modificarEquipo(eq)){
                            JOptionPane.showMessageDialog(null,"Error, no se ha actualizado ningún equipo.");
                        }else{

                            //actualizo el table model
                            modeloTablaEquipos.setValueAt(eq.getCodigoEquipo(),row,0);
                            modeloTablaEquipos.setValueAt(eq.getNombre(),row,1);
                            modeloTablaEquipos.setValueAt(eq.getEntrenador(),row,2);
                            modeloTablaEquipos.setValueAt(eq.getCategoria(),row,3);
                            modeloTablaEquipos.setValueAt(eq.getCampoEntrenamiento(),row,4);

                            // actualizo el TextArea
                            textAreaEquipos.setText("");
                            ConsultaDAO.conectar();
                            ConsultaDAO.listarEquipos(textAreaEquipos);

                            JOptionPane.showMessageDialog(null,"Equipo actualizado correctamente.");
                        }
                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SAXException ex) {
                        ex.printStackTrace();
                    } catch (TransformerException ex) {
                        ex.printStackTrace();
                    }

                }

            }
        });
        eliminarButtonCrudJugador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // selecciono la fila de la tabla seleccionada
                int row = tablaJugadores.getSelectedRow();

                if(row == -1){
                    JOptionPane.showMessageDialog(null,"Seleccione una fila de la tabla jugadores");
                }else{

                    Jugador eq = new Jugador();
                    eq.setDni(modeloTablaJugadores.getValueAt(row,0).toString());
                    eq.setCodigoEquipo(modeloTablaJugadores.getValueAt(row,1).toString());
                    eq.setNombre(modeloTablaJugadores.getValueAt(row,2).toString());
                    eq.setApellido(modeloTablaJugadores.getValueAt(row,3).toString());
                    eq.setTfno(modeloTablaJugadores.getValueAt(row,4).toString());
                    eq.setFechaNacimiento(modeloTablaJugadores.getValueAt(row,5).toString());
                    eq.setDemarcacion(modeloTablaJugadores.getValueAt(row,6).toString());
                    eq.setSalario(modeloTablaJugadores.getValueAt(row,7).toString());


                    try {
                        if(!ConsultaDAO.eliminarJugadorDomXpath(eq)){
                            JOptionPane.showMessageDialog(null,"Error, no se ha eliminado ningún jugador.");
                        }else{

                            //actualizo el table model
                            modeloTablaJugadores.eliminarJugador(row);

                            // actualizo el TextArea
                            textArea.setText("");
                            ConsultaDAO.conectar();
                            ConsultaDAO.listarJugadores(textArea);

                            JOptionPane.showMessageDialog(null,"Equipo eliminado correctamente.");
                        }
                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SAXException ex) {
                        ex.printStackTrace();
                    } catch (TransformerException ex) {
                        ex.printStackTrace();
                    }

                }


            }
        });
        buttonActualizarCrudJugador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Jugador eq;

                if(!tablaJugadores.isEditing()){

                    JOptionPane.showMessageDialog(null,"Seleccione una fila de la tabla jugadores");

                }else{

                    // selecciono la fila de la tabla seleccionada
                    int row = tablaJugadores.getEditingRow();

                    eq = new Jugador();
                    eq.setDni(modeloTablaJugadores.getValueAt(row,0).toString());
                    eq.setCodigoEquipo(modeloTablaJugadores.getValueAt(row,1).toString());
                    eq.setNombre(modeloTablaJugadores.getValueAt(row,2).toString());
                    eq.setApellido(modeloTablaJugadores.getValueAt(row,3).toString());
                    eq.setTfno(modeloTablaJugadores.getValueAt(row,4).toString());
                    eq.setFechaNacimiento(modeloTablaJugadores.getValueAt(row,5).toString());
                    eq.setDemarcacion(modeloTablaJugadores.getValueAt(row,6).toString());
                    eq.setSalario(modeloTablaJugadores.getValueAt(row,7).toString());


                    try {
                        if(!ConsultaDAO.modificarJugador(eq)){
                            JOptionPane.showMessageDialog(null,"Error, no se ha actualizado ningún equipo.");
                        }else{

                            //actualizo el table model
                            modeloTablaJugadores.setValueAt(eq.getDni(),row,0);
                            modeloTablaJugadores.setValueAt(eq.getCodigoEquipo(),row,1);
                            modeloTablaJugadores.setValueAt(eq.getNombre(),row,2);
                            modeloTablaJugadores.setValueAt(eq.getApellido(),row,3);
                            modeloTablaJugadores.setValueAt(eq.getTfno(),row,4);
                            modeloTablaJugadores.setValueAt(eq.getFechaNacimiento(),row,5);
                            modeloTablaJugadores.setValueAt(eq.getDemarcacion(),row,6);
                            modeloTablaJugadores.setValueAt(eq.getSalario(),row,7);

                            // actualizo el TextArea
                            textArea.setText("");
                            ConsultaDAO.conectar();
                            ConsultaDAO.listarJugadores(textArea);

                            JOptionPane.showMessageDialog(null,"Jugador actualizado correctamente.");
                        }
                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SAXException ex) {
                        ex.printStackTrace();
                    } catch (TransformerException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });
        buttonBusqAvanzJugador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String dni = textFieldDniJug.getText();
                String nombe = textFieldNombreJug.getText();
                String salario = textFieldSalarJug.getText();
                String demarcacion = textFieldDemarcJug.getText();
                String codigo = textFieldCodEquJug.getText();

                if(jugadorPorDniRadioButton.isSelected() && !dni.equals("")){

                    try {
                        XpathUtil xpathUtil = new XpathUtil();

                        ArrayList<String> jugadores = xpathUtil.getNombreJugadoresPorDni(dni);

                        textArea.setText("");

                        for (String eq:jugadores
                        ) {

                            textArea.setLineWrap(true);
                            textArea.append(eq+"\n");
                        }


                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SAXException ex) {
                        ex.printStackTrace();
                    } catch (XPathExpressionException ex) {
                        ex.printStackTrace();
                    }




                }else if(jugadorPorCodigoRadioButton.isSelected() && !codigo.equals("")){

                    try {
                        XpathUtil xpathUtil = new XpathUtil();

                        ArrayList<String> jugadores = xpathUtil.getJugadoresPorCodigo(codigo);

                        textArea.setText("");

                        for (String eq:jugadores
                        ) {

                            textArea.setLineWrap(true);
                            textArea.append(eq+"\n");
                        }


                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SAXException ex) {
                        ex.printStackTrace();
                    } catch (XPathExpressionException ex) {
                        ex.printStackTrace();
                    }




                }else if(salarioXRadioButton.isSelected() && !salario.equals("")){

                    try {
                        XpathUtil xpathUtil = new XpathUtil();

                        ArrayList<String> jugadores = xpathUtil.getNombreJugadoresConSalarioMayorDe(salario);

                        textArea.setText("");

                        for (String eq:jugadores
                        ) {

                            textArea.setLineWrap(true);
                            textArea.append(eq+"\n");
                        }


                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SAXException ex) {
                        ex.printStackTrace();
                    } catch (XPathExpressionException ex) {
                        ex.printStackTrace();
                    }




                }else if(jugadorPorDemarcaciónRadioButton.isSelected() && !demarcacion.equals("")){

                    try {
                        XpathUtil xpathUtil = new XpathUtil();

                        ArrayList<String> jugadores = xpathUtil.getNombreJugadoresPorDemarcacion(demarcacion);

                        textArea.setText("");

                        for (String eq:jugadores
                        ) {

                            textArea.setLineWrap(true);
                            textArea.append(eq+"\n");
                        }


                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SAXException ex) {
                        ex.printStackTrace();
                    } catch (XPathExpressionException ex) {
                        ex.printStackTrace();
                    }




                }else{


                    JOptionPane.showMessageDialog(null, "Error, seleccione el radioButton correspondiente y " +
                            "rellene el campo correcto");
                }





            }
        });
        buttonBuscAvanEquip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String cod = textFieldCodEq.getText();
                String nombre = textFieldNombreEquip.getText();
                String entrenador = textFieldEntrenEquip.getText();
                String categoria = textFieldCategEquip.getText();
                String campo = textFieldCampoEntreEquip.getText();

                if(equiposPorCodigoRadioButton.isSelected() && !cod.equals("")){

                    try {
                        XpathEquiposUtil xpathEquiposUtil = new XpathEquiposUtil();

                        ArrayList<String> equipos = xpathEquiposUtil.getEquiposPorCodigo(cod);

                        textAreaEquipos.setText("");

                        for (String eq:equipos
                        ) {

                            textAreaEquipos.setLineWrap(true);
                            textAreaEquipos.append(eq+"\n");
                        }


                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SAXException ex) {
                        ex.printStackTrace();
                    } catch (XPathExpressionException ex) {
                        ex.printStackTrace();
                    }


                }else if(equiposPorNombreRadioButton.isSelected() && !nombre.equals("")){

                    try {
                        XpathEquiposUtil xpathEquiposUtil = new XpathEquiposUtil();

                        ArrayList<String> equipos = xpathEquiposUtil.getEquiposPorNombre(nombre);

                        textAreaEquipos.setText("");

                        for (String eq:equipos
                        ) {

                            textAreaEquipos.setLineWrap(true);
                            textAreaEquipos.append(eq+"\n");
                        }


                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SAXException ex) {
                        ex.printStackTrace();
                    } catch (XPathExpressionException ex) {
                        ex.printStackTrace();
                    }

                }else if(equiposPorCategoríaRadioButton.isSelected() && !categoria.equals("")){

                    try {
                        XpathEquiposUtil xpathEquiposUtil = new XpathEquiposUtil();

                        ArrayList<String> equipos = xpathEquiposUtil.getEquiposPorCategoria(categoria);

                        textAreaEquipos.setText("");

                        for (String eq:equipos
                        ) {

                            textAreaEquipos.setLineWrap(true);
                            textAreaEquipos.append(eq+"\n");

                        }


                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SAXException ex) {
                        ex.printStackTrace();
                    } catch (XPathExpressionException ex) {
                        ex.printStackTrace();
                    }

                }else {

                    JOptionPane.showMessageDialog(null,"Error, seleccione el radiobutton correspondiente e " +
                            "introduzca el campo a buscar");

                }

            }
        });

        buttonAutomaticEquipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Equipo equipo1 = new Equipo("C","ContinentalA","Pablo",
                        "Continental","Mendizorroza");

                Equipo equipo2 = new Equipo("A","AmateurA","Gaizka",
                        "Amateur","Betoño");



                try {
                    CreadorColeccionEquipo.añadirEquipoAlaColeccion(equipo1);
                    CreadorColeccionEquipo.añadirEquipoAlaColeccion(equipo2);


                    JOptionPane.showMessageDialog(null, "Creada la colección equipos.xml, cargue el " +
                            "documento en Exits BD y empiece a operar con el resto de utilidades de la aplicación.");

                } catch (ParserConfigurationException ex) {
                    ex.printStackTrace();
                } catch (TransformerException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SAXException ex) {
                    ex.printStackTrace();
                }

            }
        });
        buttonAutomaticJug.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Jugador jugador1 = new Jugador("12345678L","A","Pedro","Pérez",
                        "12345678","01/01/1979","Delantero","1000");

                Jugador jugador2 = new Jugador("87654321XL","C","Oscar","García",
                        "660058534","01/01/1978","Portero","1200");


                try {
                    CreadorColeccionJugador.añadirJugadorAlaColeccion(jugador1);
                    CreadorColeccionJugador.añadirJugadorAlaColeccion(jugador2);

                    JOptionPane.showMessageDialog(null, "Creada la colección jugadores.xml, cargue el " +
                            "documento en Exits BD y empiece a operar con el resto de utilidades de la aplicación.");

                } catch (ParserConfigurationException ex) {
                    ex.printStackTrace();
                } catch (TransformerException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SAXException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        frame = new JFrame("App Sport Management");
        frame.setContentPane(new AppWindow().contenedorPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public boolean comprobarTextFieldsJugador(String d, String cod, String n, String ap, String tfno, String fnac,
                                              String demarc, String sala) {

        boolean respuesta = false;

        if (n.equals("") || d.equals("") || ap.equals("") || tfno.equals("") || fnac.equals("") || demarc.equals("")
                || sala.equals("") || cod.equals("")) {


            respuesta = false;
        } else {
            respuesta = true;
        }

        return respuesta;

    }

    public boolean comprobarTextFieldsEquipo(String cod, String n, String entr, String cat, String cpentre) {

        boolean respuesta = false;

        if (n.equals("") || cod.equals("") || entr.equals("") || cat.equals("") || cpentre.equals("")) {

            respuesta = false;
        } else {
            respuesta = true;
        }

        return respuesta;

    }

    public void limpiarTextFieldsJugador() {
        textFieldNombreJug.setText("");
        textFieldDniJug.setText("");
        textFieldApelliJug.setText("");
        textFieldDemarcJug.setText("");
        textFieldFchaNacJug.setText("");
        textFieldCodEquJug.setText("");
        textFieldSalarJug.setText("");
        textFieldTfnoJug.setText("");
    }

    public void limpiarTextFieldsEquipo() {
        textFieldCodEq.setText("");
        textFieldNombreEquip.setText("");
        textFieldEntrenEquip.setText("");
        textFieldCategEquip.setText("");
        textFieldCampoEntreEquip.setText("");

    }
}
