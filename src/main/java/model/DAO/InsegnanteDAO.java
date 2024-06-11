package model.DAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import model.Bean.DisponibilitaBean;
import model.Bean.InsegnanteBean;
import model.DBConnector;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InsegnanteDAO {

    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    public InsegnanteDAO() {
        this.database = DBConnector.getDatabase();
        this.collection = database.getCollection("teachers");
    }

    public boolean createInsegnante(InsegnanteBean insegnante) {
    	try {
    		List<Document> disponibilitaDocs = new ArrayList<>();
            for (DisponibilitaBean disponibilita : insegnante.getDisponibilita()) {
                Document doc = new Document("giorno", disponibilita.getGiorno())
                        .append("oraInizio", disponibilita.getOraInizio())
                        .append("oraFine", disponibilita.getOraFine());
                disponibilitaDocs.add(doc);
            }
    		
	        Document doc = new Document("_id", new ObjectId())
	                .append("Nome", insegnante.getNome())
	                .append("Cognome", insegnante.getCognome())
	                .append("Email", insegnante.getEmail())
	                .append("Username", insegnante.getUsername())
	                .append("Password", insegnante.getPassword())
	                .append("Citta", insegnante.getCitta())
	                .append("ProfileImageId", insegnante.getProfileImageId())
	                .append("Livello", insegnante.getLivello())
	                .append("Rifiuti", insegnante.getRifiuti())
	                .append("TariffaOraria", insegnante.getTariffaOraria())
	                .append("Descrizione", insegnante.getDescrizione())
	                .append("Sesso", String.valueOf(insegnante.getSesso()))
	                .append("Materie", insegnante.getMaterie())
	                .append("Disponibilita", disponibilitaDocs);
	        collection.insertOne(doc);
	        return true;
    	} catch(Exception e) {
    		e.printStackTrace();
            return false;
    	}
    }

    public InsegnanteBean getInsegnanteById(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        return docToInsegnante(doc);
    }
    
    public InsegnanteBean getInsegnanteByUsername(String username) {
        Document doc = collection.find(Filters.eq("Username", username)).first();
        return docToInsegnante(doc);
    }

    public List<InsegnanteBean> getAllInsegnanti() {
        List<InsegnanteBean> insegnanti = new ArrayList<>();
        for (Document doc : collection.find()) {
            insegnanti.add(docToInsegnante(doc));
        }
        return insegnanti;
    }
    
    public List<String> getAllUniqueMaterie() {
        Set<String> uniqueMaterie = new HashSet<>();
        for (Document doc : collection.find()) {
            List<String> materie = (List<String>) doc.get("Materie");
            if (materie != null) {
                uniqueMaterie.addAll(materie);
            }
        }
        return new ArrayList<>(uniqueMaterie);
    }
    
    public List<String> getAjaxMaterie(String query){
        List<String> materie = getAllUniqueMaterie();
        List<String> materieQuery = new ArrayList<String>();
        for(String materia:materie) {
        	if(materia.startsWith(query)) {
        		materieQuery.add(materia);
        	}
        }
        return materieQuery;
    }


    public void updateInsegnante(InsegnanteBean insegnante) {
    	 List<Document> disponibilitaDocs = new ArrayList<>();
    	    for (DisponibilitaBean disponibilita : insegnante.getDisponibilita()) {
    	        Document doc = new Document("giorno", disponibilita.getGiorno())
    	                .append("oraInizio", disponibilita.getOraInizio())
    	                .append("oraFine", disponibilita.getOraFine());
    	        disponibilitaDocs.add(doc);
    	    }
    	
        Document doc = new Document("Nome", insegnante.getNome())
                .append("Cognome", insegnante.getCognome())
                .append("Email", insegnante.getEmail())
                .append("Username", insegnante.getUsername())
                .append("Password", insegnante.getPassword())
                .append("Citta", insegnante.getCitta())
                .append("ProfileImageId", insegnante.getProfileImageId())
                .append("Livello", insegnante.getLivello())
                .append("Rifiuti", insegnante.getRifiuti())
                .append("TariffaOraria", insegnante.getTariffaOraria())
                .append("Descrizione", insegnante.getDescrizione())
                .append("Sesso", String.valueOf(insegnante.getSesso()))
                .append("Materie", insegnante.getMaterie())
                .append("Disponibilita", disponibilitaDocs);
        collection.updateOne(Filters.eq("_id", insegnante.getId()), new Document("$set", doc));
    }

    public void deleteInsegnante(ObjectId id) {
        collection.deleteOne(Filters.eq("_id", id));
    }
    
    public List<InsegnanteBean> getInsegnantiByMateriaLivello(String materia, String livello) {
        List<InsegnanteBean> insegnantiByMateriaLivello = new ArrayList<>();
        Bson filtro = Filters.and(Filters.eq("Materie", materia), Filters.eq("Livello", livello));
        for (Document doc : collection.find(filtro)) {
            insegnantiByMateriaLivello.add(docToInsegnante(doc));
        }
        return insegnantiByMateriaLivello;
    }
    
    public List<InsegnanteBean> getInsegnantiByMateriaLivelloCitta(String materia, String livello, String citta) {
        List<InsegnanteBean> insegnantiByMateriaLivelloCitta = new ArrayList<>();
        Bson filtro = Filters.and(Filters.eq("Materie", materia), Filters.eq("Livello", livello), Filters.eq("Citta", citta));
        for (Document doc : collection.find(filtro)) {
            insegnantiByMateriaLivelloCitta.add(docToInsegnante(doc));
        }
        return insegnantiByMateriaLivelloCitta;
    }
    
    public List<InsegnanteBean> doRetrieveByPrice(List<InsegnanteBean> insegnanti, int minPrezzo, int maxPrezzo) {
        List<InsegnanteBean> filteredList = new ArrayList<>();
        for (InsegnanteBean insegnante : insegnanti) {
            if (insegnante.getTariffaOraria() >= minPrezzo && insegnante.getTariffaOraria() <= maxPrezzo) {
                filteredList.add(insegnante);
            }
        }
        return filteredList;
    }

    public List<InsegnanteBean> doRetrieveByGender(List<InsegnanteBean> insegnanti, char sesso) {
        List<InsegnanteBean> filteredList = new ArrayList<>();
        for (InsegnanteBean insegnante : insegnanti) {
            if (insegnante.getSesso() == sesso) {
                filteredList.add(insegnante);
            }
        }
        return filteredList;
    }
    
    public List<DisponibilitaBean> getDisponibilitaByInsegnanteId(ObjectId insegnanteId) {
        Document doc = collection.find(Filters.eq("_id", insegnanteId)).first();
        if (doc != null) {
            List<Document> disponibilitaDocs = (List<Document>) doc.get("Disponibilita");
            List<DisponibilitaBean> disponibilita = new ArrayList<>();
            if (disponibilitaDocs != null) {
                for (Document disponibilitaDoc : disponibilitaDocs) {
                    DisponibilitaBean disponibilitaBean = new DisponibilitaBean();
                    disponibilitaBean.setGiorno(disponibilitaDoc.getString("giorno"));
                    disponibilitaBean.setOraInizio(disponibilitaDoc.getInteger("oraInizio"));
                    disponibilitaBean.setOraFine(disponibilitaDoc.getInteger("oraFine"));
                    disponibilita.add(disponibilitaBean);
                }
            }
            return disponibilita;
        }
        return null;
    }

    private InsegnanteBean docToInsegnante(Document doc) {
        if (doc == null) {
            return null;
        }
        InsegnanteBean insegnante = new InsegnanteBean();
        insegnante.setId(doc.getObjectId("_id"));
        insegnante.setNome(doc.getString("Nome"));
        insegnante.setCognome(doc.getString("Cognome"));
        insegnante.setEmail(doc.getString("Email"));
        insegnante.setUsername(doc.getString("Username"));
        insegnante.setPassword(doc.getString("Password"));
        insegnante.setCitta(doc.getString("Citta"));
        insegnante.setProfileImageId(doc.getObjectId("ProfileImageId"));
        insegnante.setLivello(doc.getString("Livello"));
        insegnante.setRifiuti(doc.getInteger("Rifiuti"));
        insegnante.setTariffaOraria(doc.getInteger("TariffaOraria"));
        insegnante.setDescrizione(doc.getString("Descrizione"));
        insegnante.setSesso(doc.getString("Sesso").charAt(0));
        insegnante.setMaterie((List<String>) doc.get("Materie"));
        List<DisponibilitaBean> disponibilita = new ArrayList<>();
        List<Document> disponibilitaDocs = (List<Document>) doc.get("Disponibilita");
        if (disponibilitaDocs != null) {
            for (Document disponibilitaDoc : disponibilitaDocs) {
                DisponibilitaBean disponibilitaBean = new DisponibilitaBean();
                disponibilitaBean.setGiorno(disponibilitaDoc.getString("giorno"));
                disponibilitaBean.setOraInizio(disponibilitaDoc.getInteger("oraInizio"));
                disponibilitaBean.setOraFine(disponibilitaDoc.getInteger("oraFine"));
                disponibilita.add(disponibilitaBean);
            }
        }
        insegnante.setDisponibilita(disponibilita);
        return insegnante;
    }
}
