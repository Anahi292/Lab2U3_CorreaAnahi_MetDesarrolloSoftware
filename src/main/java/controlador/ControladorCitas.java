package controlador;

import modelo.GestorCitas;
import vista.VistaAgendarCita;
import java.time.LocalDateTime;
import java.util.List;

public class ControladorCitas {

    private GestorCitas gestor;
    private VistaAgendarCita vista;

    public ControladorCitas(GestorCitas gestor, VistaAgendarCita vista) {
        this.gestor = gestor;
        this.vista = vista;

        // CONECTA CONTROLADOR CON LA VISTA
        this.vista.setControlador(this);
    }

    public void iniciar() {
        vista.setVisible(true);
        actualizarHorariosDisponibles();
    }

    public void actualizarHorariosDisponibles() {

        List<LocalDateTime> horarios =
                gestor.getHorariosDisponibles();

        vista.actualizarComboHorarios(horarios);
    }

    public void agendarCita(String nombre,
                            String email,
                            String telefono,
                            LocalDateTime fecha) {

        if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
            vista.mostrarError("Todos los campos son obligatorios");
            return;
        }

        boolean ok =
                gestor.agendarCita(nombre, email, telefono, fecha);

        if (ok) {
            vista.mostrarExito("Cita registrada correctamente");
            vista.limpiarFormulario();
            actualizarHorariosDisponibles();
        } else {
            vista.mostrarError("Horario no disponible");
        }
    }
}
