package model.DAO;

import com.mongodb.client.MongoCollection;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import model.Bean.InsegnanteBean;
import model.Bean.LezioneBean;
import model.Bean.RecensioneBean;
import model.Bean.StudenteBean;
import model.DBConnector;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class LezioneDAO {

    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    public LezioneDAO() {
        this.database = DBConnector.getDatabase();
        this.collection = database.getCollection("lessons");
    }

    public void createLezione(LezioneBean lezione) {
        Document doc = new Document("_id", new ObjectId())
                .append("oraInizio", lezione.getOraInizio())
                .append("oraFine", lezione.getOraFine())
                .append("costo", lezione.getCosto())
                .append("dataLezione", lezione.getDataLezione())
                .append("studente", lezione.getStudente().getId())
                .append("insegnante", lezione.getInsegnante().getId())
                .append("materia", lezione.getMateria())
                .append("accreditata", lezione.isAccreditata())
                .append("luogo", lezione.getLuogo())
                .append("recensione", new Document("voto", lezione.getRecensione().getVoto())
                        .append("commento", lezione.getRecensione().getCommento())
                        .append("dataVoto", lezione.getRecensione().getDataVoto()));
        collection.insertOne(doc);
    }

    public LezioneBean getLezioneById(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        return docToLezione(doc);
    }

    public List<LezioneBean> getAllLezioni() {
        List<LezioneBean> lezioni = new ArrayList<>();
        for (Document doc : collection.find()) {
            lezioni.add(docToLezione(doc));
        }
        return lezioni;
    }
    
    public List<LezioneBean> getLezioniByStudente(ObjectId studente) {
        List<LezioneBean> lezioni = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("studente", studente))) {
            lezioni.add(docToLezione(doc));
        }
        return lezioni;
    }
    
    public List<LezioneBean> getLezioniByInsegnante(ObjectId insegnante) {
        List<LezioneBean> lezioni = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("insegnante", insegnante))) {
            lezioni.add(docToLezione(doc));
        }
        return lezioni;
    }
    
    public List<RecensioneBean> getAllRecensioni() {
        List<RecensioneBean> recensioni = new ArrayList<>();
        InsegnanteDAO idao = new InsegnanteDAO();
        StudenteDAO sdao = new StudenteDAO();
        for (Document doc : collection.find()) {
        	Document recensioneDoc = doc.get("recensione", Document.class);
            RecensioneBean recensione = new RecensioneBean();
            recensione.setVoto(recensioneDoc.getInteger("voto"));
            recensione.setCommento(recensioneDoc.getString("commento"));
            recensione.setDataVoto(recensioneDoc.getString("dataVoto"));
            InsegnanteBean ins = idao.getInsegnanteById(doc.getObjectId("insegnante"));
            StudenteBean stu = sdao.getStudenteById(doc.getObjectId("studente"));
            recensione.setInsegnante(ins);
            recensione.setStudente(stu);
            recensioni.add(recensione);
        }
        return recensioni;
    }

    public void updateLezione(LezioneBean lezione) {
        Document doc = new Document("oraInizio", lezione.getOraInizio())
                .append("oraFine", lezione.getOraFine())
                .append("costo", lezione.getCosto())
                .append("dataLezione", lezione.getDataLezione())
                .append("studente", lezione.getStudente().getId())
                .append("insegnante", lezione.getInsegnante().getId())
                .append("materia", lezione.getMateria())
                .append("accreditata", lezione.isAccreditata())
                .append("luogo", lezione.getLuogo())
                .append("recensione", new Document("voto", lezione.getRecensione().getVoto())
                        .append("commento", lezione.getRecensione().getCommento())
                        .append("dataVoto", lezione.getRecensione().getDataVoto()));
        collection.updateOne(Filters.eq("_id", lezione.getId()), new Document("$set", doc));
    }

    public void deleteLezione(ObjectId id) {
        collection.deleteOne(Filters.eq("_id", id));
    }
    
    public List<RecensioneBean> getRecensioniByInsegnante(ObjectId insegnanteId) {
        List<RecensioneBean> recensioni = new ArrayList<>();
        InsegnanteDAO idao = new InsegnanteDAO();
        StudenteDAO sdao = new StudenteDAO();
        for (Document doc : collection.find(Filters.eq("insegnante", insegnanteId))) {
            Document recensioneDoc = doc.get("recensione", Document.class);
            if (recensioneDoc != null) {
                RecensioneBean recensione = new RecensioneBean();
                recensione.setVoto(recensioneDoc.getInteger("voto"));
                recensione.setCommento(recensioneDoc.getString("commento"));
                recensione.setDataVoto(recensioneDoc.getString("dataVoto"));
                InsegnanteBean ins = idao.getInsegnanteById(insegnanteId);
                StudenteBean stu = sdao.getStudenteById(doc.getObjectId("studente"));
                recensione.setInsegnante(ins);
                recensione.setStudente(stu);
                recensioni.add(recensione);
            }
        }
        return recensioni;
    }


    private LezioneBean docToLezione(Document doc) {
        if (doc == null) {
            return null;
        }
        LezioneBean lezione = new LezioneBean();
        lezione.setId(doc.getObjectId("_id"));
        lezione.setOraInizio(doc.getInteger("oraInizio"));
        lezione.setOraFine(doc.getInteger("oraFine"));
        lezione.setCosto(doc.getDouble("costo"));
        lezione.setDataLezione(doc.getString("dataLezione"));
        lezione.setMateria(doc.getString("materia"));
        lezione.setAccreditata(doc.getBoolean("accreditata"));
        lezione.setLuogo(doc.getString("luogo"));
        InsegnanteDAO idao = new InsegnanteDAO();
        StudenteDAO sdao = new StudenteDAO();
        InsegnanteBean ins = idao.getInsegnanteById(doc.getObjectId("insegnante"));
        StudenteBean stu = sdao.getStudenteById(doc.getObjectId("studente"));
        lezione.setStudente(stu);
        lezione.setInsegnante(ins);
        
        Document recensioneDoc = doc.get("recensione", Document.class);
        if (recensioneDoc != null) {
            RecensioneBean recensione = new RecensioneBean();
            if (recensioneDoc.containsKey("voto")) {
                recensione.setVoto(recensioneDoc.getInteger("voto"));
            } else {
                recensione.setVoto(0);
            }
            if (recensioneDoc.containsKey("commento")) {
                recensione.setCommento(recensioneDoc.getString("commento"));
            } else {
                recensione.setCommento(null);
            }
            if (recensioneDoc.containsKey("dataVoto")) {
                recensione.setDataVoto(recensioneDoc.getString("dataVoto"));
            } else {
                recensione.setDataVoto(null);
            }
            recensione.setInsegnante(ins);
            recensione.setStudente(stu);
            lezione.setRecensione(recensione);
        } else {
            lezione.setRecensione(null);
        }

        return lezione;
    }
}
