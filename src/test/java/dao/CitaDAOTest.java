package dao;

import modelo.Cita;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para CitaDAO
 * Estos tests interactúan con MongoDB real
 * Asegúrate de tener MongoDB corriendo en localhost:27017
 */
public class CitaDAOTest {

    private CitaDAO dao;

    @BeforeEach
    public void setUp() {
        dao = new CitaDAO();
        limpiarBaseDatos();
    }

    @AfterEach
    public void tearDown() {
        limpiarBaseDatos();
    }

    private void limpiarBaseDatos() {
        // Eliminar todas las citas de prueba
        List<Cita> citas = dao.listar();
        for (Cita c : citas) {
            dao.eliminar(c.getId());
        }
    }

    @Test
    public void testGuardarCita() {
        // Arrange
        LocalDateTime fecha = LocalDateTime.of(2026, 3, 15, 10, 0);
        Cita cita = new Cita("Juan Pérez", "juan@test.com", "0991234567", fecha);

        // Act
        dao.guardar(cita);

        // Assert
        assertTrue(cita.getId() > 0, "El ID debe ser generado automáticamente");
        
        // Verificar que se guardó en la BD
        Cita citaRecuperada = dao.buscarPorId(cita.getId());
        assertNotNull(citaRecuperada, "La cita debe existir en la BD");
        assertEquals("Juan Pérez", citaRecuperada.getNombreUsuario());
        assertEquals("juan@test.com", citaRecuperada.getEmail());
    }

    @Test
    public void testGuardarMultiplesCitasConIDsUnicos() {
        // Arrange
        LocalDateTime fecha1 = LocalDateTime.of(2026, 3, 15, 9, 0);
        LocalDateTime fecha2 = LocalDateTime.of(2026, 3, 15, 10, 0);
        
        Cita cita1 = new Cita("María López", "maria@test.com", "0992345678", fecha1);
        Cita cita2 = new Cita("Pedro García", "pedro@test.com", "0993456789", fecha2);

        // Act
        dao.guardar(cita1);
        dao.guardar(cita2);

        // Assert
        assertTrue(cita1.getId() > 0);
        assertTrue(cita2.getId() > 0);
        assertNotEquals(cita1.getId(), cita2.getId(), "Los IDs deben ser diferentes");
        assertTrue(cita2.getId() > cita1.getId(), "Los IDs deben ser secuenciales");
    }

    @Test
    public void testListarCitas() {
        // Arrange
        LocalDateTime fecha1 = LocalDateTime.of(2026, 3, 20, 11, 0);
        LocalDateTime fecha2 = LocalDateTime.of(2026, 3, 20, 14, 0);
        
        Cita cita1 = new Cita("Ana Martínez", "ana@test.com", "0994567890", fecha1);
        Cita cita2 = new Cita("Carlos Ruiz", "carlos@test.com", "0995678901", fecha2);
        
        dao.guardar(cita1);
        dao.guardar(cita2);

        // Act
        List<Cita> citas = dao.listar();

        // Assert
        assertNotNull(citas, "La lista no debe ser null");
        assertTrue(citas.size() >= 2, "Debe haber al menos 2 citas");
        
        // Verificar que contiene las citas guardadas
        boolean encontradaAna = false;
        boolean encontradoCarlos = false;
        
        for (Cita c : citas) {
            if (c.getNombreUsuario().equals("Ana Martínez")) {
                encontradaAna = true;
            }
            if (c.getNombreUsuario().equals("Carlos Ruiz")) {
                encontradoCarlos = true;
            }
        }
        
        assertTrue(encontradaAna, "Debe encontrar la cita de Ana");
        assertTrue(encontradoCarlos, "Debe encontrar la cita de Carlos");
    }

    @Test
    public void testListarCitasVacio() {
        // Act
        List<Cita> citas = dao.listar();

        // Assert
        assertNotNull(citas, "La lista no debe ser null aunque esté vacía");
        assertTrue(citas.isEmpty(), "La lista debe estar vacía");
    }

    @Test
    public void testCancelarCitaExistente() {
        // Arrange
        LocalDateTime fecha = LocalDateTime.of(2026, 3, 25, 15, 0);
        Cita cita = new Cita("Laura Fernández", "laura@test.com", "0996789012", fecha);
        dao.guardar(cita);
        int idCita = cita.getId();

        // Act
        boolean resultado = dao.cancelar(idCita);

        // Assert
        assertTrue(resultado, "Debería cancelar exitosamente");
        
        // Verificar que el estado cambió
        Cita citaActualizada = dao.buscarPorId(idCita);
        assertNotNull(citaActualizada);
        assertEquals("Cancelada", citaActualizada.getEstado());
    }

    @Test
    public void testCancelarCitaNoExistente() {
        // Act
        boolean resultado = dao.cancelar(99999);

        // Assert
        assertFalse(resultado, "No debería cancelar una cita que no existe");
    }

    @Test
    public void testEliminarCitaExistente() {
        // Arrange
        LocalDateTime fecha = LocalDateTime.of(2026, 3, 30, 16, 0);
        Cita cita = new Cita("Diego Torres", "diego@test.com", "0997890123", fecha);
        dao.guardar(cita);
        int idCita = cita.getId();

        // Act
        boolean resultado = dao.eliminar(idCita);

        // Assert
        assertTrue(resultado, "Debería eliminar exitosamente");
        
        // Verificar que ya no existe
        Cita citaEliminada = dao.buscarPorId(idCita);
        assertNull(citaEliminada, "La cita no debería existir después de eliminarla");
    }

    @Test
    public void testEliminarCitaNoExistente() {
        // Act
        boolean resultado = dao.eliminar(99999);

        // Assert
        assertFalse(resultado, "No debería eliminar una cita que no existe");
    }

    @Test
    public void testBuscarPorIdExistente() {
        // Arrange
        LocalDateTime fecha = LocalDateTime.of(2026, 4, 5, 10, 30);
        Cita cita = new Cita("Sofía Ramírez", "sofia@test.com", "0998901234", fecha);
        dao.guardar(cita);
        int idBuscado = cita.getId();

        // Act
        Cita citaEncontrada = dao.buscarPorId(idBuscado);

        // Assert
        assertNotNull(citaEncontrada, "Debería encontrar la cita");
        assertEquals(idBuscado, citaEncontrada.getId());
        assertEquals("Sofía Ramírez", citaEncontrada.getNombreUsuario());
        assertEquals("sofia@test.com", citaEncontrada.getEmail());
        assertEquals("0998901234", citaEncontrada.getTelefono());
        assertEquals(fecha, citaEncontrada.getFechaHora());
    }

    @Test
    public void testBuscarPorIdNoExistente() {
        // Act
        Cita cita = dao.buscarPorId(99999);

        // Assert
        assertNull(cita, "No debería encontrar una cita inexistente");
    }

    @Test
    public void testEstadoInicialConfirmada() {
        // Arrange
        LocalDateTime fecha = LocalDateTime.of(2026, 4, 10, 11, 0);
        Cita cita = new Cita("Roberto Castro", "roberto@test.com", "0999012345", fecha);

        // Act
        dao.guardar(cita);

        // Assert
        Cita citaRecuperada = dao.buscarPorId(cita.getId());
        assertEquals("Confirmada", citaRecuperada.getEstado(), 
            "El estado inicial debe ser 'Confirmada'");
    }

    @Test
    public void testGuardarYRecuperarTodosLosCampos() {
        // Arrange
        LocalDateTime fecha = LocalDateTime.of(2026, 4, 15, 14, 30);
        Cita citaOriginal = new Cita(
            "Elena Vargas",
            "elena@test.com",
            "0990123456",
            fecha
        );

        // Act
        dao.guardar(citaOriginal);
        Cita citaRecuperada = dao.buscarPorId(citaOriginal.getId());

        // Assert
        assertNotNull(citaRecuperada);
        assertEquals(citaOriginal.getId(), citaRecuperada.getId());
        assertEquals(citaOriginal.getNombreUsuario(), citaRecuperada.getNombreUsuario());
        assertEquals(citaOriginal.getEmail(), citaRecuperada.getEmail());
        assertEquals(citaOriginal.getTelefono(), citaRecuperada.getTelefono());
        assertEquals(citaOriginal.getFechaHora(), citaRecuperada.getFechaHora());
        assertEquals(citaOriginal.getEstado(), citaRecuperada.getEstado());
    }

    @Test
    public void testOperacionesCRUDCompletas() {
        // Test de integración: CREATE, READ, UPDATE, DELETE
        
        // CREATE
        LocalDateTime fecha = LocalDateTime.of(2026, 4, 20, 9, 0);
        Cita cita = new Cita("Pablo Morales", "pablo@test.com", "0991234560", fecha);
        dao.guardar(cita);
        int id = cita.getId();
        
        // READ
        Cita citaLeida = dao.buscarPorId(id);
        assertNotNull(citaLeida);
        assertEquals("Pablo Morales", citaLeida.getNombreUsuario());
        
        // UPDATE (cancelar)
        boolean cancelada = dao.cancelar(id);
        assertTrue(cancelada);
        
        Cita citaCancelada = dao.buscarPorId(id);
        assertEquals("Cancelada", citaCancelada.getEstado());
        
        // DELETE
        boolean eliminada = dao.eliminar(id);
        assertTrue(eliminada);
        
        Cita citaEliminada = dao.buscarPorId(id);
        assertNull(citaEliminada);
    }

    @Test
    public void testListarDespuesDeEliminar() {
        // Arrange
        LocalDateTime fecha1 = LocalDateTime.of(2026, 4, 25, 10, 0);
        LocalDateTime fecha2 = LocalDateTime.of(2026, 4, 25, 11, 0);
        
        Cita cita1 = new Cita("Usuario1", "u1@test.com", "111", fecha1);
        Cita cita2 = new Cita("Usuario2", "u2@test.com", "222", fecha2);
        
        dao.guardar(cita1);
        dao.guardar(cita2);
        
        int cantidadAntes = dao.listar().size();
        
        // Act
        dao.eliminar(cita1.getId());
        
        // Assert
        int cantidadDespues = dao.listar().size();
        assertEquals(cantidadAntes - 1, cantidadDespues, 
            "Debe haber una cita menos después de eliminar");
    }
}