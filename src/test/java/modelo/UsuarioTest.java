package modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void crearUsuario_devuelveDatosCorrectos() {
        Usuario u = new Usuario(1, "Carlos", "carlos@test.com", "0987654321");

        assertEquals(1, u.getId());
        assertEquals("Carlos", u.getNombre());
        assertEquals("carlos@test.com", u.getEmail());
        assertEquals("0987654321", u.getTelefono());
    }
}
