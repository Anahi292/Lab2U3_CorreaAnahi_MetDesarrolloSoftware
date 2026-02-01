package modelo;

import java.time.LocalDateTime;

public class Cita {

    private int id;
    private String nombreUsuario;
    private String email;
    private String telefono;
    private LocalDateTime fechaHora;
    private String estado;

    // CONSTRUCTOR
    public Cita(int id, String nombreUsuario, String email, String telefono, LocalDateTime fechaHora) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.telefono = telefono;
        this.fechaHora = fechaHora;
        this.estado = "Confirmada";
    }

    // CONSTRUCTOR SIN ID (PARA CUANDO AÚN NO EXISTE)
    public Cita(String nombreUsuario, String email, String telefono, LocalDateTime fechaHora) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.telefono = telefono;
        this.fechaHora = fechaHora;
        this.estado = "Confirmada";
    }

    // ===== GETTERS =====

    public int getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    // ===== SETTERS =====

    public void setId(int id) {
        this.id = id;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
