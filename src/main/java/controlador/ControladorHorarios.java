package controlador;

import modelo.*;
import vista.VistaGestionHorarios;
import java.time.LocalTime;

public class ControladorHorarios {
    private GestorHorarios modelo;
    private VistaGestionHorarios vista;
    
    public ControladorHorarios(GestorHorarios modelo, VistaGestionHorarios vista) {
        this.modelo = modelo;
        this.vista = vista;
        vista.setControlador(this);
    }
    
    public void iniciar() {
        cargarConfiguracion("Lunes");
        vista.setVisible(true);
    }
    
    public void cargarConfiguracion(String dia) {
        ConfiguracionHorario config = modelo.getConfiguracion(dia);
        vista.cargarDatos(config);
    }
    
    public void guardarConfiguracion(String dia, LocalTime inicio, LocalTime fin,
                                     int duracion, int descanso, boolean activo) {
        modelo.actualizarConfiguracion(dia, inicio, fin, duracion, descanso, activo);
        vista.mostrarExito("✅ Configuración guardada exitosamente para " + dia);
        cargarConfiguracion(dia);
    }
    

}