package controlador;

import modelo.Cita;
import modelo.GestorCitas;
import vista.VistaCancelarCita;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ControladorCancelarCita
 * Usa clases Mock para simular las dependencias
 */
public class ControladorCancelarCitaTest {

    private ControladorCancelarCita controlador;
    private GestorCitasMock gestorMock;
    private VistaCancelarCitaMock vistaMock;

    @BeforeEach
    public void setUp() {
        gestorMock = new GestorCitasMock();
        vistaMock = new VistaCancelarCitaMock();
        controlador = new ControladorCancelarCita(gestorMock, vistaMock);
    }

    @Test
    public void testIniciarMuestraVistaYActualizaLista() {
        // Act
        controlador.iniciar();

        // Assert
        assertTrue(vistaMock.estaVisible, "La vista debe estar visible");
        assertTrue(vistaMock.tablaActualizada, "La tabla debe estar actualizada");
        assertNotNull(vistaMock.ultimasCitas, "Debe tener citas");
    }

    @Test
    public void testActualizarListaCitas() {
        // Act
        controlador.actualizarListaCitas();

        // Assert
        assertTrue(vistaMock.tablaActualizada, "La tabla debe estar actualizada");
        assertEquals(2, vistaMock.ultimasCitas.size(), "Debe tener 2 citas de prueba");
    }

    @Test
    public void testCancelarCitaExitosa() {
        // Arrange
        int idCita = 1;
        gestorMock.resultadoCancelar = true;

        // Act
        controlador.cancelarCita(idCita);

        // Assert
        assertTrue(vistaMock.mostroExito, "Debe mostrar mensaje de éxito");
        assertEquals("Cita cancelada correctamente", vistaMock.ultimoMensajeExito);
        assertTrue(vistaMock.tablaActualizada, "Debe actualizar la tabla");
        assertFalse(vistaMock.mostroError, "No debe mostrar error");
        assertEquals(idCita, gestorMock.ultimoIdCancelado);
    }

    @Test
    public void testCancelarCitaFallida() {
        // Arrange
        int idCita = 999;
        gestorMock.resultadoCancelar = false;

        // Act
        controlador.cancelarCita(idCita);

        // Assert
        assertTrue(vistaMock.mostroError, "Debe mostrar error");
        assertEquals("No se pudo cancelar", vistaMock.ultimoMensajeError);
        assertFalse(vistaMock.mostroExito, "No debe mostrar éxito");
        assertFalse(vistaMock.tablaActualizada, "No debe actualizar la tabla si falló");
    }

    @Test
    public void testEliminarCitaExitosa() {
        // Arrange
        int idCita = 2;
        gestorMock.resultadoEliminar = true;

        // Act
        controlador.eliminarCita(idCita);

        // Assert
        assertTrue(vistaMock.mostroExito, "Debe mostrar mensaje de éxito");
        assertEquals("Cita eliminada correctamente", vistaMock.ultimoMensajeExito);
        assertTrue(vistaMock.tablaActualizada, "Debe actualizar la tabla");
        assertFalse(vistaMock.mostroError, "No debe mostrar error");
        assertEquals(idCita, gestorMock.ultimoIdEliminado);
    }

    @Test
    public void testEliminarCitaFallida() {
        // Arrange
        int idCita = 999;
        gestorMock.resultadoEliminar = false;

        // Act
        controlador.eliminarCita(idCita);

        // Assert
        assertTrue(vistaMock.mostroError, "Debe mostrar error");
        assertEquals("No se pudo eliminar", vistaMock.ultimoMensajeError);
        assertFalse(vistaMock.mostroExito, "No debe mostrar éxito");
        assertFalse(vistaMock.tablaActualizada, "No debe actualizar la tabla si falló");
    }

    @Test
    public void testCancelarMultiplesCitas() {
        // Arrange
        gestorMock.resultadoCancelar = true;

        // Act
        controlador.cancelarCita(1);
        controlador.cancelarCita(2);
        controlador.cancelarCita(3);

        // Assert
        assertEquals(3, gestorMock.contadorCancelaciones, 
            "Debe haber 3 intentos de cancelación");
        assertTrue(vistaMock.mostroExito, "Debe mostrar éxito");
    }

    @Test
    public void testEliminarMultiplesCitas() {
        // Arrange
        gestorMock.resultadoEliminar = true;

        // Act
        controlador.eliminarCita(1);
        controlador.eliminarCita(2);

        // Assert
        assertEquals(2, gestorMock.contadorEliminaciones, 
            "Debe haber 2 intentos de eliminación");
        assertTrue(vistaMock.mostroExito, "Debe mostrar éxito");
    }

    @Test
    public void testSecuenciaOperaciones() {
        // Simular una secuencia realista de operaciones
        gestorMock.resultadoCancelar = true;
        gestorMock.resultadoEliminar = true;

        // Iniciar
        controlador.iniciar();
        assertTrue(vistaMock.estaVisible);

        // Actualizar lista
        controlador.actualizarListaCitas();
        assertTrue(vistaMock.tablaActualizada);

        // Cancelar una cita
        controlador.cancelarCita(1);
        assertTrue(vistaMock.mostroExito);

        // Eliminar otra cita
        vistaMock.reset();
        controlador.eliminarCita(2);
        assertTrue(vistaMock.mostroExito);
    }

    // ==================== CLASES MOCK ====================

    /**
     * Mock de GestorCitas para testing de cancelación
     */
    class GestorCitasMock extends GestorCitas {
        boolean resultadoCancelar = true;
        boolean resultadoEliminar = true;
        int ultimoIdCancelado = -1;
        int ultimoIdEliminado = -1;
        int contadorCancelaciones = 0;
        int contadorEliminaciones = 0;

        @Override
        public List<Cita> getCitas() {
            List<Cita> citas = new ArrayList<>();
            
            LocalDateTime fecha1 = LocalDateTime.of(2026, 3, 15, 10, 0);
            LocalDateTime fecha2 = LocalDateTime.of(2026, 3, 16, 11, 0);
            
            Cita cita1 = new Cita("Juan Pérez", "juan@test.com", "0991234567", fecha1);
            cita1.setId(1);
            
            Cita cita2 = new Cita("María López", "maria@test.com", "0992345678", fecha2);
            cita2.setId(2);
            
            citas.add(cita1);
            citas.add(cita2);
            
            return citas;
        }

        @Override
        public boolean cancelarCita(int id) {
            ultimoIdCancelado = id;
            contadorCancelaciones++;
            return resultadoCancelar;
        }

        @Override
        public boolean eliminarCita(int id) {
            ultimoIdEliminado = id;
            contadorEliminaciones++;
            return resultadoEliminar;
        }
    }

    /**
     * Mock de VistaCancelarCita para testing
     */
    class VistaCancelarCitaMock extends VistaCancelarCita {
        boolean estaVisible = false;
        boolean tablaActualizada = false;
        boolean mostroExito = false;
        boolean mostroError = false;
        String ultimoMensajeExito;
        String ultimoMensajeError;
        List<Cita> ultimasCitas;

        @Override
        public void setVisible(boolean visible) {
            this.estaVisible = visible;
        }

        @Override
        public void actualizarTabla(List<Cita> citas) {
            this.tablaActualizada = true;
            this.ultimasCitas = citas;
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
        public void setControlador(ControladorCancelarCita c) {
            // No hacer nada en el mock
        }

        // Método auxiliar para resetear el estado del mock
        public void reset() {
            estaVisible = false;
            tablaActualizada = false;
            mostroExito = false;
            mostroError = false;
            ultimoMensajeExito = null;
            ultimoMensajeError = null;
            ultimasCitas = null;
        }
    }
}