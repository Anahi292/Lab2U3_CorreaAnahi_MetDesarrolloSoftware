package vista;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class MockupHistorialCitasTest {

    @Test
    void constructor_noLanzaExcepcion() {
        assertDoesNotThrow(() -> {
            MockupHistorialCitas vista = new MockupHistorialCitas();
            vista.dispose();
        });
    }

    @Test
    void ventana_seCreaConTituloCorrecto() {
        MockupHistorialCitas vista = new MockupHistorialCitas();

        assertNotNull(vista);
        assertEquals("Historial de Citas - Vista del Usuario", vista.getTitle());

        vista.dispose();
    }

    @Test
    void tabla_historial_tieneDatosCargados() {
        MockupHistorialCitas vista = new MockupHistorialCitas();

        JTable tabla = obtenerTabla(vista);
        assertNotNull(tabla);
        assertTrue(tabla.getRowCount() > 0);

        vista.dispose();
    }

    @Test
    void renderer_estado_noEsNulo() {
        MockupHistorialCitas vista = new MockupHistorialCitas();

        JTable tabla = obtenerTabla(vista);
        assertNotNull(tabla.getColumnModel().getColumn(2).getCellRenderer());

        vista.dispose();
    }

    private JTable obtenerTabla(MockupHistorialCitas vista) {
        for (java.awt.Component c : vista.getContentPane().getComponents()) {
            if (c instanceof JPanel) {
                JTable tabla = buscarTablaEnPanel((JPanel) c);
                if (tabla != null) {
                    return tabla;
                }
            }
        }
        return null;
    }

    private JTable buscarTablaEnPanel(JPanel panel) {
        for (java.awt.Component c : panel.getComponents()) {
            if (c instanceof JTable) {
                return (JTable) c;
            }
            if (c instanceof JScrollPane) {
                JScrollPane sp = (JScrollPane) c;
                if (sp.getViewport().getView() instanceof JTable) {
                    return (JTable) sp.getViewport().getView();
                }
            }
            if (c instanceof JPanel) {
                JTable tabla = buscarTablaEnPanel((JPanel) c);
                if (tabla != null) {
                    return tabla;
                }
            }
        }
        return null;
    }
}
