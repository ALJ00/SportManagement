package modelo;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModeloTablaJugadores extends AbstractTableModel {

    private ArrayList<Jugador> jugadores = new ArrayList();
    private String[] columnas = {"Dni","Cod.Equ.","Nombre","Apellido","Telf","Fecha Nac.","Puesto","Salario"};

    public ModeloTablaJugadores() {
    }

    public ModeloTablaJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    @Override
    public int getRowCount() {
        return jugadores.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Jugador j = this.jugadores.get(rowIndex);

        switch(columnIndex){
            case 0:
                return j.getDni();
            case 1:
                return j.getCodigoEquipo();
            case 2:
                return j.getNombre();
            case 3:
                return j.getApellido();
            case 4:
                return j.getTfno();
            case 5:
                return j.getFechaNacimiento();
            case 6:
                return j.getDemarcacion();
            case 7:
                return j.getSalario();

        }


        return null;
    }

    public void adicionarJugador(Jugador nuevoJugador){

        jugadores.add(nuevoJugador);
        fireTableDataChanged();

    }

    public void eliminarJugador(int rowIndex){
        jugadores.remove(rowIndex);
        fireTableDataChanged();


    }

    public Jugador obtenerJugador(int rowIndex){

        return this.jugadores.get(rowIndex);
    }

    public String getColumnName(int columnIndex){

        return this.columnas[columnIndex];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex){

        return true;
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex){

        Jugador jugador = jugadores.get(rowIndex);

        switch(columnIndex){
            case 0:
                jugador.setDni(value.toString());
                break;
            case 1:
                jugador.setCodigoEquipo(value.toString());
                break;
            case 2:
                jugador.setNombre(value.toString());
                break;
            case 3:
                jugador.setApellido(value.toString());
                break;
            case 4:
                jugador.setTfno(value.toString());
                break;
            case 5:
                jugador.setFechaNacimiento(value.toString());
                break;
            case 6:
                jugador.setDemarcacion(value.toString());
                break;
            case 7:
                jugador.setSalario(value.toString());
                break;

        }

        fireTableRowsUpdated(rowIndex, columnIndex);

    }

}
