package dto;

import java.util.Date;

public class Cita {
    private String dni;
    private String nombre;
    private String apellidos;
    private Especialidad especialidad;
    private Date fecha;
    private boolean asistencia;

    public Cita(String dni, String nombre, String apellidos, Especialidad especialidad, Date fecha, boolean asistencia) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.especialidad = especialidad;
        this.fecha = fecha;
        this.asistencia = asistencia;
    }

    public String getDni() {
        return dni;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public boolean isAsistencia() {
        return asistencia;
    }
}
