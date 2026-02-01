package modelo;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class GestorHorarios {

    private Map<String, ConfiguracionHorario> configuraciones;

    public GestorHorarios() {
        configuraciones = new HashMap<>();
        inicializarConfiguraciones();
    }

    private void inicializarConfiguraciones() {
        String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        for (String dia : dias) {
            configuraciones.put(dia, new ConfiguracionHorario(dia));
        }
        // Domingo desactivado por defecto
        configuraciones.get("Domingo").setActivo(false);
    }

    public ConfiguracionHorario getConfiguracion(String dia) {
        return configuraciones.get(dia);
    }

    public void actualizarConfiguracion(String dia, LocalTime inicio, LocalTime fin,
            int duracion, int descanso, boolean activo) {
        ConfiguracionHorario config = configuraciones.get(dia);
        config.setHoraInicio(inicio);
        config.setHoraFin(fin);
        config.setDuracionCita(duracion);
        config.setTiempoDescanso(descanso);
        config.setActivo(activo);
    }

    public Map<String, ConfiguracionHorario> getTodasConfiguraciones() {
        return new HashMap<>(configuraciones);
    }
}
