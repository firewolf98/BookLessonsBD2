package model.Bean;

import org.bson.types.ObjectId;
import java.util.List;

public class InsegnanteBean extends UtenteBean {
    private char sesso;
    private int rifiuti, tariffaOraria;
    private String livello, descrizione;
    private List<String> materie;
    private List<DisponibilitaBean> disponibilita;

    public InsegnanteBean(ObjectId id, String nome, String cognome, String email, String username, String password, String citta, 
            ObjectId profileImageId, String livello, int rifiuti, int tariffaOraria, String descrizione, char sesso, List<String> materie,
            List<DisponibilitaBean> disponibilita) {
        super(id, nome, cognome, email, username, password, citta, profileImageId);
        this.livello = livello;
        this.rifiuti = rifiuti;
        this.tariffaOraria = tariffaOraria;
        this.descrizione = descrizione;
        this.sesso = sesso;
        this.materie = materie;
        this.disponibilita = disponibilita;
    }

    public InsegnanteBean() {}

    public char getSesso() {
        return sesso;
    }

    public void setSesso(char sesso) {
        this.sesso = sesso;
    }

    public int getRifiuti() {
        return rifiuti;
    }

    public void setRifiuti(int rifiuti) {
        this.rifiuti = rifiuti;
    }

    public int getTariffaOraria() {
        return tariffaOraria;
    }

    public void setTariffaOraria(int tariffaOraria) {
        this.tariffaOraria = tariffaOraria;
    }

    public String getLivello() {
        return livello;
    }

    public void setLivello(String livello) {
        this.livello = livello;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public List<String> getMaterie() {
        return materie;
    }

    public void setMaterie(List<String> materie) {
        this.materie = materie;
    }
    
    public List<DisponibilitaBean> getDisponibilita() {
		return disponibilita;
	}

	public void setDisponibilita(List<DisponibilitaBean> disponibilita) {
		this.disponibilita = disponibilita;
	}

	@Override
    public String toString() {
        return "InsegnanteBean [sesso=" + sesso + ", rifiuti=" + rifiuti + ", tariffaOraria=" + tariffaOraria
                + ", livello=" + livello + ", descrizione=" + descrizione + ", materie=" + materie + "]";
    }
}
