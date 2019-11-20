package vista;

import javax.swing.*;

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
    private JTable table1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private static JFrame frame;


    public AppWindow() {
    }

    public static void main(String[] args) {
        frame = new JFrame("App Sport Management");
        frame.setContentPane(new AppWindow().contenedorPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
