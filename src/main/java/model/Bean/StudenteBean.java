package model.Bean;

import org.bson.types.ObjectId;

public class StudenteBean extends UtenteBean{
	
	public StudenteBean(ObjectId id, String nome, String cognome, String email, String username, String password, String citta, ObjectId profileImageId) {
		super(id,nome,cognome,email,username,password,citta, profileImageId);
	}
	
	public StudenteBean() {}

	@Override
	public String toString() {
		return super.toString();
	}
	
}
