package vista;

import controlador.ControladorCitas;
import org.junit.jupiter.api.Test;


import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VistaAgendarCitaTest {

    @Test
    void constructor_noLanzaExcepcion() {
        assertDoesNotThrow(() -> {
            VistaAgendarCita vista = new VistaAgendarCita();
            vista.dispose();
        });
    }

    @Test
    void ventana_tieneTituloCorrecto() {
        VistaAgendarCita vista = new VistaAgendarCita();

        assertEquals("🗓️ Sistema de Gestión de Citas - Agendar", vista.getTitle());

        vista.dispose();
    }

    @Test
    void actualizarComboHorarios_listaVacia() {
        VistaAgendarCita vista = new VistaAgendarCita();

        vista.actualizarComboHorarios(new ArrayList<>());

        JComboBox<?> combo = obtenerCombo(vista);
        assertEquals(1, combo.getItemCount());
        assertFalse(combo.isEnabled());

        vista.dispose();
    }

    @Test
    void actualizarComboHorarios_conDatos() {
        VistaAgendarCita vista = new VistaAgendarCita();

        List<LocalDateTime> horarios = new ArrayList<>();
        horarios.add(LocalDateTime.now().plusDays(1));
        horarios.add(LocalDateTime.now().plusDays(2));

        vista.actualizarComboHorarios(horarios);

        JComboBox<?> combo = obtenerCombo(vista);
        assertEquals(2, combo.getItemCount());
        assertTrue(combo.isEnabled());

        vista.dispose();
    }

    @Test
    void limpiarFormulario_noLanzaExcepcion() {
        VistaAgendarCita vista = new VistaAgendarCita();

        assertDoesNotThrow(vista::limpiarFormulario);

        vista.dispose();
    }

    

    @Test
    void validacionesEmailYTelefono_casosBasicos() throws Exception {
        VistaAgendarCita vista = new VistaAgendarCita();

        var metodoEmail = VistaAgendarCita.class
                .getDeclaredMethod("validarEmail", String.class);
        metodoEmail.setAccessible(true);

        var metodoTelefono = VistaAgendarCita.class
                .getDeclaredMethod("validarTelefono", String.class);
        metodoTelefono.setAccessible(true);

        assertTrue((boolean) metodoEmail.invoke(vista, "test@mail.com"));
        assertFalse((boolean) metodoEmail.invoke(vista, "correo-malo"));

        assertTrue((boolean) metodoTelefono.invoke(vista, "0991234567"));
        assertFalse((boolean) metodoTelefono.invoke(vista, "abc123"));

        vista.dispose();
    }

    // ================= MÉTODOS AUXILIARES =================

    private JComboBox<?> obtenerCombo(JFrame frame) {
        return (JComboBox<?>) buscarComponente(frame.getContentPane(), JComboBox.class);
    }

    private JComponent buscarComponente(java.awt.Container cont, Class<?> tipo) {
        for (java.awt.Component c : cont.getComponents()) {
            if (tipo.isInstance(c)) {
                return (JComponent) c;
            }
            if (c instanceof java.awt.Container) {
                JComponent encontrado = buscarComponente((java.awt.Container) c, tipo);
                if (encontrado != null) return encontrado;
            }
        }
        return null;
    }
}
