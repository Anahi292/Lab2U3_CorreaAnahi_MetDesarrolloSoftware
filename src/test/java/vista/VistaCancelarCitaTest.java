package vista;

import controlador.ControladorCancelarCita;
import modelo.Cita;
import org.junit.jupiter.api.Test;


import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VistaCancelarCitaTest {

    @Test
    void constructor_noLanzaExcepcion() {
        assertDoesNotThrow(() -> {
            VistaCancelarCita vista = new VistaCancelarCita();
            vista.dispose();
        });
    }

    @Test
    void tituloCorrecto() {
        VistaCancelarCita vista = new VistaCancelarCita();
        assertEquals("❌ Cancelar Citas - Sistema de Gestión", vista.getTitle());
        vista.dispose();
    }

    

    @Test
    void actualizarTabla_listaVacia() {
        VistaCancelarCita vista = new VistaCancelarCita();

        vista.actualizarTabla(new ArrayList<>());

        JTable tabla = obtenerTabla(vista);
        assertEquals(0, tabla.getRowCount());

        vista.dispose();
    }

   

    // ===== helper =====
    private JTable obtenerTabla(JFrame frame) {
        return (JTable) buscar(frame.getContentPane(), JTable.class);
    }

    private JComponent buscar(java.awt.Container c, Class<?> tipo) {
        for (java.awt.Component comp : c.getComponents()) {
            if (tipo.isInstance(comp)) return (JComponent) comp;
            if (comp instanceof java.awt.Container) {
                JComponent encontrado = buscar((java.awt.Container) comp, tipo);
                if (encontrado != null) return encontrado;
            }
        }
        return null;
    }
}
