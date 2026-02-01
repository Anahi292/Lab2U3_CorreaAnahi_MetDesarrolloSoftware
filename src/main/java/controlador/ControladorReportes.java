package controlador;

import modelo.Reporte;
import java.util.ArrayList;
import java.util.List;

public class ControladorReportes {

    public List<Reporte> generarReportesBasicos() {
        List<Reporte> lista = new ArrayList<>();
        lista.add(new Reporte("Total Citas", 10));
        lista.add(new Reporte("Citas Canceladas", 2));
        return lista;
    }
}
