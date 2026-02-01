package controlador;

import modelo.GestorCitas;
import vista.VistaAgendarCita;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ControladorCitas
 * Usa clases Mock para simular las dependencias
 */
public class ControladorCitasTest {

    private ControladorCitas controlador;
    private GestorCitasMock gestorMock;
    private VistaAgendarCitaMock vistaMock;

    @BeforeEach
    public void setUp() {
        gestorMock = new GestorCitasMock();
        vistaMock = new VistaAgendarCitaMock();
        controlador = new ControladorCitas(gestorMock, vistaMock);
    }

    @Test
    public void testIniciarMuestraVista() {
        // Act
        controlador.iniciar();

        // Assert
        assertTrue(vistaMock.estaVisible, "La vista debe estar visible");
        assertTrue(vistaMock.horariosActualizados, "Los horarios deben estar actualizados");
    }

    @Test
    public void testActualizarHorariosDisponibles() {
        // Act
        controlador.actualizarHorariosDisponibles();

        // Assert
        assertTrue(vistaMock.horariosActualizados, "Los horarios deben estar actualizados");
        assertNotNull(vistaMock.ultimosHorarios, "Debe tener horarios");
        assertEquals(3, vistaMock.ultimosHorarios.size(), "Debe tener 3 horarios de prueba");
    }

    @Test
    public void testAgendarCitaExitosa() {
        // Arrange
        String nombre = "Juan Pérez";
        String email = "juan@test.com";
        String telefono = "0991234567";
        LocalDateTime fecha = LocalDateTime.now().plusDays(1);
        
        gestorMock.resultadoAgendar = true;

        // Act
        controlador.agendarCita(nombre, email, telefono, fecha);

        // Assert
        assertTrue(vistaMock.mostroExito, "Debe mostrar mensaje de éxito");
        assertTrue(vistaMock.formularioLimpio, "Debe limpiar el formulario");
        assertTrue(vistaMock.horariosActualizados, "Debe actualizar horarios");
        assertFalse(vistaMock.mostroError, "No debe mostrar error");
        
        // Verificar que se llamó al gestor con los datos correctos
        assertEquals(nombre, gestorMock.ultimoNombre);
        assertEquals(email, gestorMock.ultimoEmail);
        assertEquals(telefono, gestorMock.ultimoTelefono);
        assertEquals(fecha, gestorMock.ultimaFecha);
    }

    @Test
    public void testAgendarCitaHorarioNoDisponible() {
        // Arrange
        String nombre = "María López";
        String email = "maria@test.com";
        String telefono = "0992345678";
        LocalDateTime fecha = LocalDateTime.now().plusDays(1);
        
        gestorMock.resultadoAgendar = false; // Simular horario no disponible

        // Act
        controlador.agendarCita(nombre, email, telefono, fecha);

        // Assert
        assertTrue(vistaMock.mostroError, "Debe mostrar error");
        assertEquals("Horario no disponible", vistaMock.ultimoMensajeError);
        assertFalse(vistaMock.mostroExito, "No debe mostrar éxito");
        assertFalse(vistaMock.formularioLimpio, "No debe limpiar el formulario");
    }

    @Test
    public void testAgendarCitaNombreVacio() {
        // Arrange
        String nombre = "";
        String email = "test@test.com";
        String telefono = "0993456789";
        LocalDateTime fecha = LocalDateTime.now().plusDays(1);

        // Act
        controlador.agendarCita(nombre, email, telefono, fecha);

        // Assert
        assertTrue(vistaMock.mostroError, "Debe mostrar error");
        assertEquals("Todos los campos son obligatorios", vistaMock.ultimoMensajeError);
        assertFalse(gestorMock.seIntentoAgendar, "No debe intentar agendar en el gestor");
    }

    @Test
    public void testAgendarCitaEmailVacio() {
        // Arrange
        String nombre = "Pedro García";
        String email = "";
        String telefono = "0994567890";
        LocalDateTime fecha = LocalDateTime.now().plusDays(1);

        // Act
        controlador.agendarCita(nombre, email, telefono, fecha);

        // Assert
        assertTrue(vistaMock.mostroError, "Debe mostrar error");
        assertEquals("Todos los campos son obligatorios", vistaMock.ultimoMensajeError);
        assertFalse(gestorMock.seIntentoAgendar, "No debe intentar agendar");
    }

    @Test
    public void testAgendarCitaTelefonoVacio() {
        // Arrange
        String nombre = "Ana Martínez";
        String email = "ana@test.com";
        String telefono = "";
        LocalDateTime fecha = LocalDateTime.now().plusDays(1);

        // Act
        controlador.agendarCita(nombre, email, telefono, fecha);

        // Assert
        assertTrue(vistaMock.mostroError, "Debe mostrar error");
        assertEquals("Todos los campos son obligatorios", vistaMock.ultimoMensajeError);
        assertFalse(gestorMock.seIntentoAgendar, "No debe intentar agendar");
    }

    @Test
    public void testAgendarCitaTodosCamposVacios() {
        // Arrange
        String nombre = "";
        String email = "";
        String telefono = "";
        LocalDateTime fecha = LocalDateTime.now().plusDays(1);

        // Act
        controlador.agendarCita(nombre, email, telefono, fecha);

        // Assert
        assertTrue(vistaMock.mostroError, "Debe mostrar error");
        assertEquals("Todos los campos son obligatorios", vistaMock.ultimoMensajeError);
    }

    // ==================== CLASES MOCK ====================

    /**
     * Mock de GestorCitas para testing
     */
    class GestorCitasMock extends GestorCitas {
        boolean resultadoAgendar = true;
        boolean seIntentoAgendar = false;
        String ultimoNombre;
        String ultimoEmail;
        String ultimoTelefono;
        LocalDateTime ultimaFecha;

        @Override
        public boolean agendarCita(String nombre, String email, String telefono, LocalDateTime fecha) {
            seIntentoAgendar = true;
            ultimoNombre = nombre;
            ultimoEmail = email;
            ultimoTelefono = telefono;
            ultimaFecha = fecha;
            return resultadoAgendar;
        }

        @Override
        public List<LocalDateTime> getHorariosDisponibles() {
            List<LocalDateTime> horarios = new ArrayList<>();
            LocalDateTime base = LocalDateTime.now().plusDays(1).withHour(9).withMinute(0);
            horarios.add(base);
            horarios.add(base.plusHours(1));
            horarios.add(base.plusHours(2));
            return horarios;
        }
    }

    /**
     * Mock de VistaAgendarCita para testing
     */
    class VistaAgendarCitaMock extends VistaAgendarCita {
        boolean estaVisible = false;
        boolean horariosActualizados = false;
        boolean formularioLimpio = false;
        boolean mostroExito = false;
        boolean mostroError = false;
        String ultimoMensajeExito;
        String ultimoMensajeError;
        List<LocalDateTime> ultimosHorarios;

        @Override
        public void setVisible(boolean visible) {
            this.estaVisible = visible;
        }

        @Override
        public void actualizarComboHorarios(List<LocalDateTime> horarios) {
            this.horariosActualizados = true;
            this.ultimosHorarios = horarios;
        }

        @Override
        public void limpiarFormulario() {
            this.formularioLimpio = true;
        }

        @Override
        public void mostrarExito(String mensaje) {
            this.mostroExito = true;
            this.ultimoMensajeExito = mensaje;
        }

        @Override
        public void mostrarError(String mensaje) {
            this.mostroError = true;
            this.ultimoMensajeError = mensaje;
        }

        @Override
        public void setControlador(ControladorCitas c) {
            // No hacer nada en el mock
        }
    }
}