package controlador;

import dao.UsuarioDAO;
import modelo.Usuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControladorUsuariosTest {

    @Test
    void crearUsuario_asignaIdYDevuelveUsuario() {
        ControladorUsuariosFake controlador = new ControladorUsuariosFake();

        Usuario u1 = controlador.crearUsuario("Ana", "ana@test.com", "0999");
        Usuario u2 = controlador.crearUsuario("Luis", "luis@test.com", "0888");

        assertEquals(1, u1.getId());
        assertEquals(2, u2.getId());

        assertEquals("Ana", u1.getNombre());
        assertEquals("Luis", u2.getNombre());
    }

    /* ========= CONTROLADOR FAKE ========= */
    static class ControladorUsuariosFake extends ControladorUsuarios {

        public ControladorUsuariosFake() {
            this.dao = new UsuarioDAOFake();
        }
    }

    /* ========= DAO FAKE ========= */
    static class UsuarioDAOFake extends UsuarioDAO {

        @Override
        public void guardar(Usuario u) {
            // NO hace nada (evita MongoDB)
        }
    }
}
