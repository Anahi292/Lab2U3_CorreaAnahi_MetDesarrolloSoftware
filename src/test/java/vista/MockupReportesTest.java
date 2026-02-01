package vista;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class MockupReportesTest {

    @Test
    void constructor_noLanzaExcepcion() {
        assertDoesNotThrow(() -> {
            MockupReportes vista = new MockupReportes();
            vista.dispose();
        });
    }

    @Test
    void ventana_tieneTituloCorrecto() {
        MockupReportes vista = new MockupReportes();

        assertEquals(" Panel de Reportes - Administrador", vista.getTitle());

        vista.dispose();
    }

    @Test
    void seCreanComponentesPrincipales() {
        MockupReportes vista = new MockupReportes();

        assertTrue(contieneComponente(vista, JComboBox.class));
        assertTrue(contieneComponente(vista, JTextField.class));
        assertTrue(contieneComponente(vista, JTable.class));
        assertTrue(contieneComponente(vista, JButton.class));
        assertTrue(contieneComponente(vista, JSplitPane.class));

        vista.dispose();
    }

    @Test
    void tablaTieneDatosCargados() {
        MockupReportes vista = new MockupReportes();

        JTable tabla = obtenerTabla(vista);
        assertNotNull(tabla);
        assertTrue(tabla.getRowCount() > 0);

        vista.dispose();
    }

    @Test
    void panelGraficosSeCreaCorrectamente() {
        MockupReportes vista = new MockupReportes();

        assertTrue(contienePanelConCanvas(vista));

        vista.dispose();
    }

    private boolean contieneComponente(JFrame frame, Class<?> tipo) {
        return buscarComponente(frame.getContentPane(), tipo) != null;
    }

    private JTable obtenerTabla(JFrame frame) {
        return (JTable) buscarComponente(frame.getContentPane(), JTable.class);
    }

    private boolean contienePanelConCanvas(JFrame frame) {
        return buscarCanvas(frame.getContentPane());
    }

    private boolean buscarCanvas(Container contenedor) {
        for (Component c : contenedor.getComponents()) {
            if (c instanceof JPanel) {
                JPanel panel = (JPanel) c;
                if (panel.getPreferredSize() != null &&
                    panel.getPreferredSize().width >= 400) {
                    return true;
                }
            }
            if (c instanceof Container) {
                if (buscarCanvas((Container) c)) {
                    return true;
                }
            }
        }
        return false;
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
