package modelo;

public class Equipo {

    private String codigoEquipo;
    private String nombre;
    private String entrenador;
    private String categoria;
    private String campoEntrenamiento;

    public Equipo(String codigoEquipo, String nombre, String entrenador, String categoria, String campoEntrenamiento) {
        this.codigoEquipo = codigoEquipo;
        this.nombre = nombre;
        this.entrenador = entrenador;
        this.categoria = categoria;
        this.campoEntrenamiento = campoEntrenamiento;
    }

    public String getCodigoEquipo() {
        return codigoEquipo;
    }

    public void setCodigoEquipo(String codigoEquipo) {
        this.codigoEquipo = codigoEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(String entrenador) {
        this.entrenador = entrenador;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCampoEntrenamiento() {
        return campoEntrenamiento;
    }

    public void setCampoEntrenamiento(String campoEntrenamiento) {
        this.campoEntrenamiento = campoEntrenamiento;
    }
}
