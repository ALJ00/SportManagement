package vista;

import controlador.ConsultaDAO;
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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
    private JRadioButton equiposPorCódigoRadioButton;
    private JRadioButton equiposPorNombreRadioButton;
    private JRadioButton equiposPorCategoríaRadioButton;
    private JButton buttonBuscAvanEquip;
    private JRadioButton jugadorPorDniRadioButton;
    private JRadioButton jugadorPorNombreRadioButton;
    private JRadioButton salario1000RadioButton;
    private JRadioButton jugadorPorDemarcaciónRadioButton;
    private JRadioButton jugadoresPorNombreEquipoRadioButton;
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

                    try {

                        Jugador jugador = new Jugador(dni, cod, name, ap, tfno, fnac, de, s);
                        modeloTablaJugadores.adicionarJugador(jugador);
                        CreadorColeccionJugador.añadirJugadorAlaColeccion(jugador);

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

    public boolean comprobarTextFieldsJugador(String d, String cod, String n, String ap, String tfno, String fnac, String demarc, String sala) {

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
