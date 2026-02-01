package modelo;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CitaTest {

    @Test
    void constructorConId_inicializaCorrectamente() {
        LocalDateTime fecha = LocalDateTime.now();

        Cita cita = new Cita(
                1,
                "Ana Pérez",
                "ana@test.com",
                "099999999",
                fecha
        );

        assertEquals(1, cita.getId());
        assertEquals("Ana Pérez", cita.getNombreUsuario());
        assertEquals("ana@test.com", cita.getEmail());
        assertEquals("099999999", cita.getTelefono());
        assertEquals(fecha, cita.getFechaHora());
        assertEquals("Confirmada", cita.getEstado());
    }

    @Test
    void constructorSinId_inicializaCorrectamente() {
        LocalDateTime fecha = LocalDateTime.now();

        Cita cita = new Cita(
                "Carlos López",
                "carlos@test.com",
                "088888888",
                fecha
        );

        assertEquals("Carlos López", cita.getNombreUsuario());
        assertEquals("carlos@test.com", cita.getEmail());
        assertEquals("088888888", cita.getTelefono());
        assertEquals(fecha, cita.getFechaHora());
        assertEquals("Confirmada", cita.getEstado());
    }

    @Test
    void setId_actualizaId() {
        Cita cita = new Cita(
                "Laura",
                "laura@test.com",
                "077777777",
                LocalDateTime.now()
        );

        cita.setId(10);

        assertEquals(10, cita.getId());
    }

    @Test
    void setEstado_actualizaEstado() {
        Cita cita = new Cita(
                2,
                "Pedro",
                "pedro@test.com",
                "066666666",
                LocalDateTime.now()
        );

        cita.setEstado("Cancelada");

        assertEquals("Cancelada", cita.getEstado());
    }
}
