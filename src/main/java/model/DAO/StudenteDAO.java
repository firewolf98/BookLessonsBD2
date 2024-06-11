package model.DAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import model.Bean.StudenteBean;
import model.DBConnector;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class StudenteDAO {

    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    public StudenteDAO() {
        this.database = DBConnector.getDatabase();
        this.collection = database.getCollection("students");
    }

    public boolean createStudente(StudenteBean studente) {
        try {
            Document doc = new Document("_id", new ObjectId())
                    .append("Nome", studente.getNome())
                    .append("Cognome", studente.getCognome())
                    .append("Email", studente.getEmail())
                    .append("Username", studente.getUsername())
                    .append("Password", studente.getPassword())
                    .append("Citta", studente.getCitta())
                    .append("ProfileImageId", studente.getProfileImageId());
            collection.insertOne(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public StudenteBean getStudenteById(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        return docToStudente(doc);
    }
    
    public StudenteBean getStudenteByUsername(String username) {
        Document doc = collection.find(Filters.eq("Username", username)).first();
        return docToStudente(doc);
    }

    public List<StudenteBean> getAllStudenti() {
        List<StudenteBean> studenti = new ArrayList<>();
        for (Document doc : collection.find()) {
            studenti.add(docToStudente(doc));
        }
        return studenti;
    }

    public void updateStudente(StudenteBean studente) {
        Document doc = new Document("Nome", studente.getNome())
                .append("Cognome", studente.getCognome())
                .append("Email", studente.getEmail())
                .append("Username", studente.getUsername())
                .append("Password", studente.getPassword())
                .append("Citta", studente.getCitta())
                .append("ProfileImageId", studente.getProfileImageId());
        collection.updateOne(Filters.eq("_id", studente.getId()), new Document("$set", doc));
    }

    public void deleteStudente(String id) {
        collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }

    private StudenteBean docToStudente(Document doc) {
        if (doc == null) {
            return null;
        }
        StudenteBean studente = new StudenteBean();
        studente.setId(doc.getObjectId("_id"));
        studente.setNome(doc.getString("Nome"));
        studente.setCognome(doc.getString("Cognome"));
        studente.setEmail(doc.getString("Email"));
        studente.setUsername(doc.getString("Username"));
        studente.setPassword(doc.getString("Password"));
        studente.setCitta(doc.getString("Citta"));
        studente.setProfileImageId(doc.getObjectId("ProfileImageId"));
        return studente;
    }
}
