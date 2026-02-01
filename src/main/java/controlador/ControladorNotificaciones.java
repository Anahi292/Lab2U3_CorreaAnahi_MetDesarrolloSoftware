package controlador;

import modelo.Notificacion;
import java.util.ArrayList;
import java.util.List;

public class ControladorNotificaciones {

    private List<Notificacion> cola;

    public ControladorNotificaciones() {
        cola = new ArrayList<>();
    }

    public void agregarNotificacion(String mensaje) {
        cola.add(new Notificacion(mensaje));
    }

    public void procesarNotificaciones() {
        for (Notificacion n : cola) {
            n.marcarEnviada();
        }
    }

    public int totalPendientes() {
        int c = 0;
        for (Notificacion n : cola) {
            if (!n.isEnviada()) c++;
        }
        return c;
    }
}
