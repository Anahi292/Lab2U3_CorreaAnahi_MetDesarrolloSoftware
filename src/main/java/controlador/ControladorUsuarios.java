package controlador;

import modelo.Usuario;
import dao.UsuarioDAO;

public class ControladorUsuarios {

    UsuarioDAO dao = new UsuarioDAO();
    private int contador = 1;

    public Usuario crearUsuario(String nombre, String email, String telefono) {
        Usuario u = new Usuario(contador++, nombre, email, telefono);
        dao.guardar(u);
        return u;
    }
}
