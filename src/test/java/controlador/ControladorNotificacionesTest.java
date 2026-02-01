package controlador;

import modelo.Notificacion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ControladorNotificaciones
 */
public class ControladorNotificacionesTest {

    private ControladorNotificaciones controlador;

    @BeforeEach
    public void setUp() {
        controlador = new ControladorNotificaciones();
    }

    @Test
    public void testConstructor() {
        assertNotNull(controlador);
        assertEquals(0, controlador.totalPendientes());
    }

    @Test
    public void testAgregarNotificacion() {
        controlador.agregarNotificacion("Cita agendada");
        
        assertEquals(1, controlador.totalPendientes());
    }

    @Test
    public void testAgregarMultiplesNotificaciones() {
        controlador.agregarNotificacion("Notificacion 1");
        controlador.agregarNotificacion("Notificacion 2");
        controlador.agregarNotificacion("Notificacion 3");
        
        assertEquals(3, controlador.totalPendientes());
    }

    @Test
    public void testAgregarNotificacionVacia() {
        controlador.agregarNotificacion("");
        
        assertEquals(1, controlador.totalPendientes());
    }

    @Test
    public void testAgregarNotificacionNula() {
        controlador.agregarNotificacion(null);
        
        assertEquals(1, controlador.totalPendientes());
    }

    @Test
    public void testProcesarNotificaciones() {
        controlador.agregarNotificacion("Notificacion 1");
        controlador.agregarNotificacion("Notificacion 2");
        
        assertEquals(2, controlador.totalPendientes());
        
        controlador.procesarNotificaciones();
        
        assertEquals(0, controlador.totalPendientes());
    }

    @Test
    public void testProcesarNotificacionesSinNotificaciones() {
        controlador.procesarNotificaciones();
        
        assertEquals(0, controlador.totalPendientes());
    }

    @Test
    public void testTotalPendientesInicial() {
        int total = controlador.totalPendientes();
        
        assertEquals(0, total);
    }

    @Test
    public void testTotalPendientesDespuesDeProcesar() {
        controlador.agregarNotificacion("Test 1");
        controlador.agregarNotificacion("Test 2");
        controlador.agregarNotificacion("Test 3");
        
        assertEquals(3, controlador.totalPendientes());
        
        controlador.procesarNotificaciones();
        
        assertEquals(0, controlador.totalPendientes());
    }

    @Test
    public void testAgregarYProcesarMultiplesVeces() {
        controlador.agregarNotificacion("Mensaje 1");
        controlador.agregarNotificacion("Mensaje 2");
        assertEquals(2, controlador.totalPendientes());
        
        controlador.procesarNotificaciones();
        assertEquals(0, controlador.totalPendientes());
        
        controlador.agregarNotificacion("Mensaje 3");
        assertEquals(1, controlador.totalPendientes());
        
        controlador.procesarNotificaciones();
        assertEquals(0, controlador.totalPendientes());
    }

    @Test
    public void testSecuenciaCompleta() {
        assertEquals(0, controlador.totalPendientes());
        
        controlador.agregarNotificacion("Notificacion A");
        assertEquals(1, controlador.totalPendientes());
        
        controlador.agregarNotificacion("Notificacion B");
        assertEquals(2, controlador.totalPendientes());
        
        controlador.agregarNotificacion("Notificacion C");
        assertEquals(3, controlador.totalPendientes());
        
        controlador.procesarNotificaciones();
        assertEquals(0, controlador.totalPendientes());
    }

    @Test
    public void testMensajesConCaracteresEspeciales() {
        controlador.agregarNotificacion("Mensaje con ñ y tildes: áéíóú");
        controlador.agregarNotificacion("Mensaje con símbolos: @#$%&*");
        controlador.agregarNotificacion("Mensaje con números: 12345");
        
        assertEquals(3, controlador.totalPendientes());
    }

    @Test
    public void testMensajesLargos() {
        String mensajeLargo = "Este es un mensaje muy largo que contiene mucho texto " +
                            "para verificar que el controlador puede manejar mensajes " +
                            "de cualquier longitud sin problemas de ningun tipo.";
        
        controlador.agregarNotificacion(mensajeLargo);
        
        assertEquals(1, controlador.totalPendientes());
    }

    @Test
    public void testProcesarDosVecesConsecutivas() {
        controlador.agregarNotificacion("Test");
        controlador.procesarNotificaciones();
        
        assertEquals(0, controlador.totalPendientes());
        
        controlador.procesarNotificaciones();
        
        assertEquals(0, controlador.totalPendientes());
    }

    @Test
    public void testGranCantidadDeNotificaciones() {
        for (int i = 0; i < 100; i++) {
            controlador.agregarNotificacion("Notificacion " + i);
        }
        
        assertEquals(100, controlador.totalPendientes());
        
        controlador.procesarNotificaciones();
        
        assertEquals(0, controlador.totalPendientes());
    }
}