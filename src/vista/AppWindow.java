package vista;

import controlador.ConsultaDAO;
import modelo.Equipo;
import modelo.Jugador;
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
    private JPanel contenedorGestionAlta;
    private JPanel conetenedorCrudEquipos;
    private JPanel contenedorJugadores;
    private JPanel conetendorEquipos;
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
    private JPanel conetendorConsultasAvanzadas;
    private JButton buttonNuevoJugadorCrud;
    private JButton eliminarButtonCrudJugador;
    private JButton buttonActualizarCrudJugador;
    private JPanel contenedorBotonesCrud;
    private JScrollPane conetendorScrollTextArea;
    private JTextArea textArea;
    private JButton buttonListarJugadores;
    private JPanel contenedorTablaJugadores;
    private JScrollPane scrollPaneTablaJugadores;
    private JTable tablaJugadores;
    private JPanel contenedorGestionJugadores;
    private static JFrame frame;
    private ModeloTablaJugadores modeloTablaJugadores;


    public AppWindow() throws IOException, SAXException, ParserConfigurationException {

        tablaJugadores.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 9));
        modeloTablaJugadores = new ModeloTablaJugadores(ConsultaDAO.listarJugadoresDom());
        tablaJugadores.setModel(modeloTablaJugadores);

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


                    Equipo equipo = new Equipo(cod, name, entre, cat, campo);

                    try {
                        CreadorColeccionEquipo.añadirEquipoAlaColeccion(equipo);
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

                    JOptionPane.showMessageDialog(null, "Insertado nuevo equipo");

                } else {

                    JOptionPane.showMessageDialog(null, "Introduce todos lo campos");

                }

                limpiarTextFieldsEquipo();

            }
        });
        buttonListarJugadores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConsultaDAO.conectar();

                ConsultaDAO.listarJugadores(textArea);
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
