package modelo;

public class Jugador {

    private String dni;
    private String codigoEquipo;
    private String nombre;
    private String apellido;
    private String tfno;
    private String fechaNacimiento;
    private String demarcacion;
    private String salario;

    public Jugador(String dni, String codigoEquipo, String nombre, String apellido, String tfno,
                   String fechaNacimiento, String demarcacion, String salario) {
        this.dni = dni;
        this.codigoEquipo = codigoEquipo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tfno = tfno;
        this.fechaNacimiento = fechaNacimiento;
        this.demarcacion = demarcacion;
        this.salario = salario;
    }

    public String getDni() {
        return dni;
    }

    public Jugador() {
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTfno() {
        return tfno;
    }

    public void setTfno(String tfno) {
        this.tfno = tfno;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDemarcacion() {
        return demarcacion;
    }

    public void setDemarcacion(String demarcacion) {
        this.demarcacion = demarcacion;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }
}
