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



}
