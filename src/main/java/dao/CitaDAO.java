package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import modelo.Cita;
import org.bson.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {
    private MongoCollection<Document> coleccion;

    public CitaDAO() {
        MongoDatabase db = MongoConexion.conectar();
        coleccion = db.getCollection("citas");
    }

    // CREATE - CORREGIDO: Genera ID automáticamente
    public void guardar(Cita c) {
        // Generar ID automático
        int nuevoId = generarNuevoId();
        c.setId(nuevoId);
        
        Document doc = new Document("id", c.getId())
                .append("nombre", c.getNombreUsuario())
                .append("email", c.getEmail())
                .append("telefono", c.getTelefono())
                .append("fechaHora", c.getFechaHora().toString())
                .append("estado", c.getEstado());
        
        coleccion.insertOne(doc);
        System.out.println("✅ Cita guardada en MongoDB con ID: " + nuevoId);
    }

    // Método para generar ID único
    private int generarNuevoId() {
        int maxId = 0;
        for (Document doc : coleccion.find()) {
            Integer id = doc.getInteger("id");
            if (id != null && id > maxId) {
                maxId = id;
            }
        }
        return maxId + 1;
    }

    // READ - Listar todas las citas
    public List<Cita> listar() {
        List<Cita> lista = new ArrayList<>();
        
        for (Document doc : coleccion.find()) {
            try {
                Cita c = new Cita(
                        doc.getString("nombre"),
                        doc.getString("email"),
                        doc.getString("telefono"),
                        LocalDateTime.parse(doc.getString("fechaHora"))
                );
                c.setId(doc.getInteger("id"));
                c.setEstado(doc.getString("estado"));
                lista.add(c);
            } catch (Exception e) {
                System.err.println("Error al leer cita: " + e.getMessage());
            }
        }
        
        System.out.println("📋 Citas cargadas desde MongoDB: " + lista.size());
        return lista;
    }

    // UPDATE - Cancelar cita (cambiar estado)
    public boolean cancelar(int id) {
        long modificados = coleccion.updateOne(
                Filters.eq("id", id),
                Updates.set("estado", "Cancelada")
        ).getModifiedCount();
        
        boolean exito = modificados > 0;
        if (exito) {
            System.out.println("✅ Cita " + id + " cancelada");
        } else {
            System.err.println("❌ No se pudo cancelar la cita " + id);
        }
        return exito;
    }

    // DELETE - Eliminar cita permanentemente
    public boolean eliminar(int id) {
        long eliminados = coleccion.deleteOne(
                Filters.eq("id", id)
        ).getDeletedCount();
        
        boolean exito = eliminados > 0;
        if (exito) {
            System.out.println("🗑️ Cita " + id + " eliminada");
        } else {
            System.err.println("❌ No se pudo eliminar la cita " + id);
        }
        return exito;
    }

    // Método adicional: buscar cita por ID
    public Cita buscarPorId(int id) {
        Document doc = coleccion.find(Filters.eq("id", id)).first();
        
        if (doc != null) {
            Cita c = new Cita(
                    doc.getString("nombre"),
                    doc.getString("email"),
                    doc.getString("telefono"),
                    LocalDateTime.parse(doc.getString("fechaHora"))
            );
            c.setId(doc.getInteger("id"));
            c.setEstado(doc.getString("estado"));
            return c;
        }
        return null;
    }
}