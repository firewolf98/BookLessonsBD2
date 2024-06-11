package model.Bean;

import org.bson.types.ObjectId;

public class UtenteBean {
	private String nome, cognome, email, username, password, citta;
	private boolean amministratore;
	private ObjectId profileImageId, id;
	
	public UtenteBean(ObjectId id, String nome, String cognome, String email, String username, String password, String citta, ObjectId profileImageId) {
		this.id= id;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.username = username;
		this.password = password;
		this.citta=citta;
		this.amministratore=false;
		this.profileImageId = profileImageId;
	}
	
	public UtenteBean() {}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public boolean isAmministratore() {
		return amministratore;
	}

	public void setAmministratore(boolean amministratore) {
		this.amministratore = amministratore;
	}
	
	public ObjectId getProfileImageId() {
        return profileImageId;
    }

    public void setProfileImageId(ObjectId profileImageId) {
        this.profileImageId = profileImageId;
    }

	@Override
	public String toString() {
		return "UtenteBean [nome=" + nome + ", cognome=" + cognome + ", email=" + email + ", username=" + username
				+ ", password=" + password + ", citta=" + citta + ", amministratore=" + amministratore + "]";
	}
	
	
}
