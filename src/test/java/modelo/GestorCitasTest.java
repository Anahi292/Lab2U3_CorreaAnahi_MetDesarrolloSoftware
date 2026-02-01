package modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para GestorCitas
 * Cobertura: Métodos principales del gestor
 */
public class GestorCitasTest {

    private GestorCitas gestor;

    @BeforeEach
    public void setUp() {
        gestor = new GestorCitas();
        // Limpiar la base de datos antes de cada test
        limpiarBaseDatos();
    }

    @AfterEach
    public void tearDown() {
        // Limpiar después de cada test
        limpiarBaseDatos();
    }

    private void limpiarBaseDatos() {
        // Eliminar todas las citas de prueba
        List<Cita> citas = gestor.getCitas();
        for (Cita c : citas) {
            gestor.eliminarCita(c.getId());
        }
    }

    @Test
    public void testAgendarCitaExitosa() {
        // Arrange
        String nombre = "Carlos López";
        String email = "carlos@test.com";
        String telefono = "0998877665";
        LocalDateTime fecha = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0);

        // Act
        boolean resultado = gestor.agendarCita(nombre, email, telefono, fecha);

        // Assert
        assertTrue(resultado, "La cita debería agendarse exitosamente");
        
        List<Cita> citas = gestor.getCitas();
        assertFalse(citas.isEmpty(), "Debe haber al menos una cita");
        
        Cita citaGuardada = citas.get(citas.size() - 1);
        assertEquals(nombre, citaGuardada.getNombreUsuario());
        assertEquals(email, citaGuardada.getEmail());
        assertEquals(telefono, citaGuardada.getTelefono());
    }

    @Test
    public void testAgendarCitaHorarioNoDisponible() {
        // Arrange
        LocalDateTime fecha = LocalDateTime.now().plusDays(1).withHour(9).withMinute(0);
        
        // Primera cita
        gestor.agendarCita("Juan", "juan@test.com", "0991111111", fecha);
        
        // Act - Intentar agendar otra cita en el mismo horario
        boolean resultado = gestor.agendarCita("María", "maria@test.com", "0992222222", fecha);
        
        // Assert
        assertFalse(resultado, "No debería permitir agendar en horario ocupado");
    }

    @Test
    public void testGetHorariosDisponibles() {
        // Act
        List<LocalDateTime> horarios = gestor.getHorariosDisponibles();

        // Assert
        assertNotNull(horarios, "La lista de horarios no debe ser null");
        assertFalse(horarios.isEmpty(), "Debe haber horarios disponibles");
        assertTrue(horarios.size() >= 1, "Debe haber al menos un horario");
        
        // Verificar que los horarios son del futuro
        LocalDateTime ahora = LocalDateTime.now();
        for (LocalDateTime h : horarios) {
            assertTrue(h.isAfter(ahora), "Los horarios deben ser futuros");
        }
    }

    @Test
    public void testGetCitas() {
        // Arrange
        LocalDateTime fecha1 = LocalDateTime.now().plusDays(1).withHour(9).withMinute(0);
        LocalDateTime fecha2 = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0);
        
        gestor.agendarCita("Pedro", "pedro@test.com", "0993333333", fecha1);
        gestor.agendarCita("Ana", "ana@test.com", "0994444444", fecha2);

        // Act
        List<Cita> citas = gestor.getCitas();

        // Assert
        assertNotNull(citas, "La lista no debe ser null");
        assertTrue(citas.size() >= 2, "Debe haber al menos 2 citas");
    }

    @Test
    public void testCancelarCitaExistente() {
        // Arrange
        LocalDateTime fecha = LocalDateTime.now().plusDays(1).withHour(11).withMinute(0);
        gestor.agendarCita("Luis", "luis@test.com", "0995555555", fecha);
        
        List<Cita> citas = gestor.getCitas();
        int idCita = citas.get(citas.size() - 1).getId();

        // Act
        boolean resultado = gestor.cancelarCita(idCita);

        // Assert
        assertTrue(resultado, "La cita debería cancelarse exitosamente");
        
        // Verificar que el estado cambió
        List<Cita> citasActualizadas = gestor.getCitas();
        Cita citaCancelada = null;
        for (Cita c : citasActualizadas) {
            if (c.getId() == idCita) {
                citaCancelada = c;
                break;
            }
        }
        
        assertNotNull(citaCancelada);
        assertEquals("Cancelada", citaCancelada.getEstado());
    }

    @Test
    public void testCancelarCitaNoExistente() {
        // Act
        boolean resultado = gestor.cancelarCita(99999);

        // Assert
        assertFalse(resultado, "No debería cancelar una cita inexistente");
    }

    @Test
    public void testEliminarCitaExistente() {
        // Arrange
        LocalDateTime fecha = LocalDateTime.now().plusDays(1).withHour(12).withMinute(0);
        gestor.agendarCita("Sofia", "sofia@test.com", "0996666666", fecha);
        
        List<Cita> citasAntes = gestor.getCitas();
        int cantidadAntes = citasAntes.size();
        int idCita = citasAntes.get(citasAntes.size() - 1).getId();

        // Act
        boolean resultado = gestor.eliminarCita(idCita);

        // Assert
        assertTrue(resultado, "La cita debería eliminarse exitosamente");
        
        List<Cita> citasDespues = gestor.getCitas();
        assertEquals(cantidadAntes - 1, citasDespues.size(), "Debe haber una cita menos");
    }

    @Test
    public void testEliminarCitaNoExistente() {
        // Act
        boolean resultado = gestor.eliminarCita(99999);

        // Assert
        assertFalse(resultado, "No debería eliminar una cita inexistente");
    }

    @Test
    public void testContarCitas() {
        // Arrange
        int cantidadInicial = gestor.contarCitas();
        
        LocalDateTime fecha = LocalDateTime.now().plusDays(1).withHour(13).withMinute(0);
        gestor.agendarCita("Diego", "diego@test.com", "0997777777", fecha);

        // Act
        int cantidadFinal = gestor.contarCitas();

        // Assert
        assertEquals(cantidadInicial + 1, cantidadFinal, "Debe incrementar en 1");
    }

    @Test
    public void testGetCitasActivas() {
        // Arrange
        LocalDateTime fecha1 = LocalDateTime.now().plusDays(1).withHour(14).withMinute(0);
        LocalDateTime fecha2 = LocalDateTime.now().plusDays(1).withHour(15).withMinute(0);
        
        gestor.agendarCita("Elena", "elena@test.com", "0998888888", fecha1);
        gestor.agendarCita("Pablo", "pablo@test.com", "0999999999", fecha2);
        
        // Cancelar una
        List<Cita> todasCitas = gestor.getCitas();
        gestor.cancelarCita(todasCitas.get(todasCitas.size() - 1).getId());

        // Act
        List<Cita> citasActivas = gestor.getCitasActivas();

        // Assert
        assertNotNull(citasActivas);
        // Verificar que ninguna esté cancelada
        for (Cita c : citasActivas) {
            assertNotEquals("Cancelada", c.getEstado());
        }
    }

    @Test
    public void testBuscarCitaPorIdExistente() {
        // Arrange
        LocalDateTime fecha = LocalDateTime.now().plusDays(1).withHour(16).withMinute(0);
        gestor.agendarCita("Roberto", "roberto@test.com", "0991010101", fecha);
        
        List<Cita> citas = gestor.getCitas();
        int idBuscado = citas.get(citas.size() - 1).getId();

        // Act
        Cita citaEncontrada = gestor.buscarCitaPorId(idBuscado);

        // Assert
        assertNotNull(citaEncontrada, "Debería encontrar la cita");
        assertEquals(idBuscado, citaEncontrada.getId());
        assertEquals("Roberto", citaEncontrada.getNombreUsuario());
    }

    @Test
    public void testBuscarCitaPorIdNoExistente() {
        // Act
        Cita citaEncontrada = gestor.buscarCitaPorId(99999);

        // Assert
        assertNull(citaEncontrada, "No debería encontrar una cita inexistente");
    }

    @Test
    public void testHorariosSeReducenAlAgendar() {
        // Arrange
        List<LocalDateTime> horariosIniciales = gestor.getHorariosDisponibles();
        int cantidadInicial = horariosIniciales.size();
        
        if (cantidadInicial > 0) {
            LocalDateTime primerHorario = horariosIniciales.get(0);
            
            // Act
            gestor.agendarCita("Test", "test@test.com", "0990000000", primerHorario);
            List<LocalDateTime> horariosFinales = gestor.getHorariosDisponibles();
            
            // Assert
            assertEquals(cantidadInicial - 1, horariosFinales.size(), 
                "Debe haber un horario menos disponible");
            assertFalse(horariosFinales.contains(primerHorario), 
                "El horario agendado no debe estar disponible");
        }
    }

    @Test
    public void testMultiplesOperaciones() {
        // Test de integración: varias operaciones seguidas
        
        // Agendar
        LocalDateTime fecha1 = LocalDateTime.now().plusDays(2).withHour(9).withMinute(0);
        LocalDateTime fecha2 = LocalDateTime.now().plusDays(2).withHour(10).withMinute(0);
        
        assertTrue(gestor.agendarCita("Usuario1", "u1@test.com", "111", fecha1));
        assertTrue(gestor.agendarCita("Usuario2", "u2@test.com", "222", fecha2));
        
        // Verificar
        List<Cita> citas = gestor.getCitas();
        assertTrue(citas.size() >= 2);
        
        // Cancelar una
        int id1 = citas.get(citas.size() - 2).getId();
        assertTrue(gestor.cancelarCita(id1));
        
        // Eliminar otra
        int id2 = citas.get(citas.size() - 1).getId();
        assertTrue(gestor.eliminarCita(id2));
        
        // Verificar estado final
        List<Cita> citasFinales = gestor.getCitas();
        boolean encontradaCancelada = false;
        boolean encontradaEliminada = true;
        
        for (Cita c : citasFinales) {
            if (c.getId() == id1 && c.getEstado().equals("Cancelada")) {
                encontradaCancelada = true;
            }
            if (c.getId() == id2) {
                encontradaEliminada = false;
            }
        }
        
        assertTrue(encontradaCancelada, "Debe existir la cita cancelada");
        assertTrue(encontradaEliminada, "No debe existir la cita eliminada");
    }
}