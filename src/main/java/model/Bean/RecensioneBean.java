package model.Bean;

public class RecensioneBean {
	private int voto;
    private String commento;
    private String dataVoto;
    private InsegnanteBean insegnante;
    private StudenteBean studente;

    public RecensioneBean(int voto, String commento, String dataVoto, InsegnanteBean insegnante, StudenteBean studente) {
    	this.voto = voto;
    	this.commento = commento;
    	this.dataVoto = dataVoto;
    	this.insegnante = insegnante;
    	this.studente = studente;
    }
    
    public RecensioneBean() {}

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }

    public String getDataVoto() {
        return dataVoto;
    }

    public void setDataVoto(String dataVoto) {
        this.dataVoto = dataVoto;
    }

	public InsegnanteBean getInsegnante() {
		return insegnante;
	}

	public void setInsegnante(InsegnanteBean insegnante) {
		this.insegnante = insegnante;
	}

	public StudenteBean getStudente() {
		return studente;
	}

	public void setStudente(StudenteBean studente) {
		this.studente = studente;
	}
}
