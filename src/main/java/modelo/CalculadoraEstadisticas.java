package modelo;

import java.util.List;
import modelo.Cita;

public class CalculadoraEstadisticas {

    public int totalCitas(List<Cita> citas) {
        return citas.size();
    }

    public int totalCanceladas(List<Cita> citas) {
        int c = 0;
        for (Cita x : citas) {
            if (x.getEstado().equals("Cancelada")) c++;
        }
        return c;
    }
}
