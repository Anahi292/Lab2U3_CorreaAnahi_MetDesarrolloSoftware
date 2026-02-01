package modelo;

import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class GestorHorariosTest {

    @Test
    void domingo_inicia_inactivo() {
        GestorHorarios g = new GestorHorarios();
        assertFalse(g.getConfiguracion("Domingo").isActivo());
    }

    @Test
    void actualizar_configuracion() {
        GestorHorarios g = new GestorHorarios();

        g.actualizarConfiguracion(
                "Lunes",
                LocalTime.of(7,0),
                LocalTime.of(12,0),
                20,
                5,
                true
        );

        ConfiguracionHorario c = g.getConfiguracion("Lunes");

        assertEquals(LocalTime.of(7,0), c.getHoraInicio());
        assertEquals(20, c.getDuracionCita());
    }
}
