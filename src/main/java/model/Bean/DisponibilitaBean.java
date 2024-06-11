package model.Bean;


public class DisponibilitaBean {
	private String giorno;
	private int oraInizio,oraFine;

	public DisponibilitaBean(String giorno, int oraInizio,int oraFine) {
		this.giorno = giorno;
		this.oraInizio=oraInizio;
		this.oraFine=oraFine;
	}
	
	
	public DisponibilitaBean() {
	}


	public String getGiorno() {
		return giorno;
	}

	public void setGiorno(String giorno) {
		this.giorno = giorno;
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
}
