package model.Bean;

import org.bson.types.ObjectId;

public class LezioneBean {
	private int oraInizio;
    private int oraFine;
    private double costo;
    private String dataLezione;
    private StudenteBean studente;
    private InsegnanteBean insegnante;
    private String materia;
    private boolean accreditata;
    private String luogo;
    private RecensioneBean recensione;
    private ObjectId id;
	
    public LezioneBean(ObjectId id, int oraInizio, int oraFine, double costo, String dataLezione, StudenteBean studente, InsegnanteBean insegnante, String materia, boolean accreditata, String luogo, RecensioneBean recensione) {
        this.id = id;
    	this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.costo = costo;
        this.dataLezione = dataLezione;
        this.studente = studente;
        this.insegnante = insegnante;
        this.materia = materia;
        this.accreditata = accreditata;
        this.luogo = luogo;
        this.recensione = recensione;
    }
    
    public LezioneBean() {}
    
    public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public int getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(int oraInizio) {
        this.oraInizio = oraInizio;
    }

    public int getOraFine() {
        return oraFine;
    }

    public void setOraFine(int oraFine) {
        this.oraFine = oraFine;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getDataLezione() {
        return dataLezione;
    }

    public void setDataLezione(String dataLezione) {
        this.dataLezione = dataLezione;
    }

    public StudenteBean getStudente() {
        return studente;
    }

    public void setStudente(StudenteBean studente) {
        this.studente = studente;
    }

    public InsegnanteBean getInsegnante() {
        return insegnante;
    }

    public void setInsegnante(InsegnanteBean insegnante) {
        this.insegnante = insegnante;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public boolean isAccreditata() {
        return accreditata;
    }

    public void setAccreditata(boolean accreditata) {
        this.accreditata = accreditata;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public RecensioneBean getRecensione() {
        return recensione;
    }

    public void setRecensione(RecensioneBean recensione) {
        this.recensione = recensione;
    }
	
}
