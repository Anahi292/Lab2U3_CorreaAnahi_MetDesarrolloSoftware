package modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReporteTest {

    @Test
    void crearReporte_devuelveDatosCorrectos() {
        Reporte r = new Reporte("Reporte diario", 10);

        assertEquals("Reporte diario", r.getTitulo());
        assertEquals(10, r.getTotal());
    }
}
