package controlador;

import modelo.Reporte;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControladorReportesTest {

    @Test
    void generarReportesBasicos_devuelveListaCorrecta() {
        ControladorReportes controlador = new ControladorReportes();

        List<Reporte> reportes = controlador.generarReportesBasicos();

        assertEquals(2, reportes.size());

        assertEquals("Total Citas", reportes.get(0).getTitulo());
        assertEquals(10, reportes.get(0).getTotal());

        assertEquals("Citas Canceladas", reportes.get(1).getTitulo());
        assertEquals(2, reportes.get(1).getTotal());
    }
}
