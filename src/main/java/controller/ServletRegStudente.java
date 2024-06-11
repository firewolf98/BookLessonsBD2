package controller;

import java.io.IOException;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Bean.StudenteBean;
import model.DAO.StudenteDAO;

@WebServlet("/ServletRegStudente")
public class ServletRegStudente extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public ServletRegStudente() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nome=request.getParameter("nome");
		String cognome=request.getParameter("cognome");
		String email=request.getParameter("email");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String citta=request.getParameter("citta");

		if (valido(nome,cognome,email,username,password,citta)) {
			StudenteBean s=new StudenteBean(new ObjectId(),nome, cognome, email, username, password, citta,new ObjectId("66588b0fd13be1acd4964da5"));
			StudenteDAO stud=new StudenteDAO();

			boolean inserito;
			try {
				inserito = stud.createStudente(s);
				request.setAttribute("ris", inserito);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else request.setAttribute("ris", false);

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("outputRegistrazione.jsp");
		requestDispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private boolean valido(String nome, String cognome, String email, String username, String password, String citta ) {
		boolean valido=true;
		String expNome="^[A-Z]{1}[A-Za-z ]*$";
		String expCognome="^[A-Z]{1}[A-Za-z ]*$";
		String expEmail="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		String expUsername="^[a-zA-Z0-9]{1}[a-z_A-Z0-9]{2,19}$";
		String expPassword="^[a-zA-Z0-9]{8,19}$";
		String expCitta="^[A-Z]{1}[a-z]*$";
		
		if (!Pattern.matches(expNome, nome))
			valido=false;
		if (!Pattern.matches(expCognome, cognome))
			valido=false;
		if (!Pattern.matches(expUsername, username))
			valido=false;
		if (!Pattern.matches(expPassword, password))
			valido=false;
		if (!Pattern.matches(expCitta, citta))
			valido=false;
		if (!Pattern.matches(expEmail, email))
			valido=false;
		return valido;
	}
}
