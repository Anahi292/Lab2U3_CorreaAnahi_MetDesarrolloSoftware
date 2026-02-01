package controlador;

import modelo.GestorCitas;
import vista.VistaCancelarCita;

public class ControladorCancelarCita {

    private GestorCitas modelo;
    private VistaCancelarCita vista;

    public ControladorCancelarCita(GestorCitas m, VistaCancelarCita v) {
        modelo = m;
        vista = v;
        vista.setControlador(this);
    }

    public void iniciar() {
        vista.setVisible(true);
        actualizarListaCitas();
    }

    public void actualizarListaCitas() {
        vista.actualizarTabla(modelo.getCitas());
    }

    // UPDATE
    public void cancelarCita(int id) {
        if (modelo.cancelarCita(id)) {
            vista.mostrarExito("Cita cancelada correctamente");
            actualizarListaCitas();
        } else {
            vista.mostrarError("No se pudo cancelar");
        }
    }

    // DELETE
    public void eliminarCita(int id) {
        if (modelo.eliminarCita(id)) {
            vista.mostrarExito("Cita eliminada correctamente");
            actualizarListaCitas();
        } else {
            vista.mostrarError("No se pudo eliminar");
        }
    }
}
