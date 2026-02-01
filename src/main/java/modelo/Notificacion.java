package modelo;

public class Notificacion {

    private String mensaje;
    private boolean enviada;

    public Notificacion(String mensaje) {
        this.mensaje = mensaje;
        this.enviada = false;
    }

    public String getMensaje() {
        return mensaje;
    }

    public boolean isEnviada() {
        return enviada;
    }

    public void marcarEnviada() {
        enviada = true;
    }
}
