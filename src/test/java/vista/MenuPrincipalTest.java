package vista;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuPrincipalTest {

    @Test
    void constructor_menuPrincipal_noLanzaExcepcion() {
        assertDoesNotThrow(() -> {
            MenuPrincipal menu = new MenuPrincipal();
        });
    }

    @Test
    void menuPrincipal_seCreaCorrectamente() {
        MenuPrincipal menu = new MenuPrincipal();

        assertNotNull(menu);
        assertEquals("Sistema de Gestión de Citas", menu.getTitle());
    }
}
