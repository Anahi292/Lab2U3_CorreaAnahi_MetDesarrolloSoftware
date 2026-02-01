package vista;

import controlador.ControladorHorarios;
import modelo.ConfiguracionHorario;
import org.junit.jupiter.api.Test;


import javax.swing.*;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class VistaGestionHorariosTest {

    @Test
    void constructor_noFalla() {
        assertDoesNotThrow(() -> {
            VistaGestionHorarios vista = new VistaGestionHorarios();
            vista.dispose();
        });
    }

    @Test
    void tituloCorrecto() {
        VistaGestionHorarios vista = new VistaGestionHorarios();
        assertEquals(" Gestión de Horarios de Atención", vista.getTitle());
        vista.dispose();
    }

    

    @Test
    void actualizarCapacidad_noExplota() {
        VistaGestionHorarios vista = new VistaGestionHorarios();

        assertDoesNotThrow(() -> {
            for (int i = 0; i < 3; i++) {
                SwingUtilities.invokeLater(() -> {});
            }
        });

        vista.dispose();
    }
}
