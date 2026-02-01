package dao;

import modelo.Usuario;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class UsuarioDAO {

    private MongoCollection<Document> coleccion;

    public UsuarioDAO() {
        coleccion = MongoConexion.conectar()
                .getCollection("usuarios");
    }

    public void guardar(Usuario u) {

        Document doc = new Document("id", u.getId())
                .append("nombre", u.getNombre())
                .append("email", u.getEmail())
                .append("telefono", u.getTelefono());

        coleccion.insertOne(doc);
    }
}
