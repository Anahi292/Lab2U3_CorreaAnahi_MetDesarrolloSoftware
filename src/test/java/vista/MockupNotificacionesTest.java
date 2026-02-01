package vista;

import java.awt.Component;
import java.awt.Container;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class MockupNotificacionesTest {

    @Test
    void constructor_noLanzaExcepcion() {
        assertDoesNotThrow(() -> {
            MockupNotificaciones vista = new MockupNotificaciones();
            vista.dispose();
        });
    }

    @Test
    void ventana_tieneTituloCorrecto() {
        MockupNotificaciones vista = new MockupNotificaciones();

        assertEquals(" Configuración de Notificaciones", vista.getTitle());

        vista.dispose();
    }

    @Test
    void seCreanComponentesPrincipales() {
        MockupNotificaciones vista = new MockupNotificaciones();

        assertTrue(contieneComponente(vista, JCheckBox.class));
        assertTrue(contieneComponente(vista, JComboBox.class));
        assertTrue(contieneComponente(vista, JTextArea.class));
        assertTrue(contieneComponente(vista, JButton.class));

        vista.dispose();
    }

    @Test
    void previewEmail_noEstaVacio() {
        MockupNotificaciones vista = new MockupNotificaciones();

        JTextArea preview = obtenerTextArea(vista);
        assertNotNull(preview);
        assertFalse(preview.getText().isEmpty());

        vista.dispose();
    }

    private boolean contieneComponente(JFrame frame, Class<?> tipo) {
        return buscarComponente(frame.getContentPane(), tipo) != null;
    }

    private JTextArea obtenerTextArea(JFrame frame) {
        return (JTextArea) buscarComponente(frame.getContentPane(), JTextArea.class);
    }

    private Component buscarComponente(Container contenedor, Class<?> tipo) {
        for (Component c : contenedor.getComponents()) {
            if (tipo.isInstance(c)) {
                return c;
            }
            if (c instanceof Container) {
                Component encontrado = buscarComponente((Container) c, tipo);
                if (encontrado != null) {
                    return encontrado;
                }
            }
        }
        return null;
    }
}
