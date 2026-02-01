package dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConexion {

    private static final String URI = "mongodb://localhost:27017";
    private static final String DB = "clinica";

    public static MongoDatabase conectar() {
        MongoClient client = MongoClients.create(URI);
        return client.getDatabase(DB);
    }
}
