package modelo;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModeloTablaEquipos extends AbstractTableModel {

    private ArrayList<Equipo> equipos = new ArrayList();
    private String[] columnas = {"Cod.Equipo", "Nombre", "Entrenador", "Categoría", "Campo Entre."};

    public ModeloTablaEquipos() {
    }

    public ModeloTablaEquipos(ArrayList<Equipo> equipos) {
        this.equipos = equipos;
    }

    @Override
    public int getRowCount() {
        return equipos.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Equipo e = this.equipos.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return e.getCodigoEquipo();
            case 1:
                return e.getNombre();
            case 2:
                return e.getEntrenador();
            case 3:
                return e.getCategoria();
            case 4:
                return e.getCampoEntrenamiento();
        }


        return null;
    }

    public void adicionarEquipo(Equipo equipo) {

        equipos.add(equipo);
        fireTableDataChanged();

    }

    public void eliminarEquipo(int rowIndex) {
        equipos.remove(rowIndex);
        fireTableDataChanged();


    }

    public Equipo obtenerEquipo(int rowIndex) {

        return this.equipos.get(rowIndex);
    }

    public String getColumnName(int columnIndex) {

        return this.columnas[columnIndex];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return true;
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {

        Equipo e = equipos.get(rowIndex);

        switch (columnIndex) {
            case 0:
                e.setCodigoEquipo(value.toString());
                break;
            case 1:
                e.setNombre(value.toString());
                break;
            case 2:
                e.setEntrenador(value.toString());
                break;
            case 3:
                e.setCategoria(value.toString());
                break;
            case 4:
                e.setCampoEntrenamiento(value.toString());
                break;
        }

        fireTableRowsUpdated(rowIndex, columnIndex);

    }
}
