package modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NotificacionTest {

    @Test
    void alCrearNotificacion_noEstaEnviada() {
        Notificacion n = new Notificacion("Mensaje de prueba");

        assertEquals("Mensaje de prueba", n.getMensaje());
        assertFalse(n.isEnviada());
    }

    @Test
    void marcarEnviada_cambiaEstado() {
        Notificacion n = new Notificacion("Mensaje");

        n.marcarEnviada();

        assertTrue(n.isEnviada());
    }
}
