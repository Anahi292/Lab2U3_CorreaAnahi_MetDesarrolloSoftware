package modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidadorDatosTest {

    private final ValidadorDatos validador = new ValidadorDatos();

    @Test
    void nombreValido_correcto() {
        assertTrue(validador.nombreValido("Ana"));
    }

    @Test
    void nombreValido_invalidoPorLongitud() {
        assertFalse(validador.nombreValido("Al"));
    }

    @Test
    void nombreValido_nulo() {
        assertFalse(validador.nombreValido(null));
    }

    @Test
    void emailValido_correcto() {
        assertTrue(validador.emailValido("correo@test.com"));
    }

    @Test
    void emailValido_incorrecto() {
        assertFalse(validador.emailValido("correotest.com"));
    }

    @Test
    void emailValido_nulo() {
        assertFalse(validador.emailValido(null));
    }

    @Test
    void telefonoValido_correcto() {
        assertTrue(validador.telefonoValido("09999999"));
    }

    @Test
    void telefonoValido_incorrecto() {
        assertFalse(validador.telefonoValido("123"));
    }

    @Test
    void telefonoValido_nulo() {
        assertFalse(validador.telefonoValido(null));
    }
}
