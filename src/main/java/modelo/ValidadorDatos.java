package modelo;

public class ValidadorDatos {

    public boolean nombreValido(String n) {
        return n != null && n.length() >= 3;
    }

    public boolean emailValido(String e) {
        return e != null && e.contains("@");
    }

    public boolean telefonoValido(String t) {
        return t != null && t.length() >= 7;
    }
}
