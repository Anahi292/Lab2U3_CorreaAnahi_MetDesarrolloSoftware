package modelo;

import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class ConfiguracionHorarioTest {

    @Test
    void calcula_citas_por_dia_activo() {
        ConfiguracionHorario c = new ConfiguracionHorario("Lunes");
        c.setHoraInicio(LocalTime.of(8,0));
        c.setHoraFin(LocalTime.of(10,0));
        c.setDuracionCita(30);
        c.setTiempoDescanso(0);

        assertEquals(4, c.calcularCitasPorDia());
    }

    @Test
    void dia_inactivo_devuelve_cero() {
        ConfiguracionHorario c = new ConfiguracionHorario("Martes");
        c.setActivo(false);

        assertEquals(0, c.calcularCitasPorDia());
    }
}
