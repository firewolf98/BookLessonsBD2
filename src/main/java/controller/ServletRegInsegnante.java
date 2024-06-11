package controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.DBConnector;
import model.Bean.DisponibilitaBean;
import model.Bean.InsegnanteBean;
import model.DAO.InsegnanteDAO;

@WebServlet("/ServletRegInsegnante")
@MultipartConfig
public class ServletRegInsegnante extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public ServletRegInsegnante() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 	
		Part filePart = request.getPart("foto");
		InputStream content = filePart.getInputStream();
		ObjectId imageFileId = null;
		
		if (content.available() != 0) {
            GridFSBucket gridFSBucket = DBConnector.getGridFSBucket();
            GridFSUploadOptions options = new GridFSUploadOptions().chunkSizeBytes(358400);

            imageFileId = gridFSBucket.uploadFromStream(filePart.getSubmittedFileName(), content, options);
        }
		

		String nome=request.getParameter("nome");
		String cognome=request.getParameter("cognome");
		String email=request.getParameter("email");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String citta=request.getParameter("citta");
		String livello=request.getParameter("livello");
		String sesso=request.getParameter("sesso");
		String tariffaOraria=request.getParameter("tariffaOraria");

		if (validazione(nome,cognome,email,username,password,citta,livello,sesso, tariffaOraria)) {
			InsegnanteBean insegnante=new InsegnanteBean(new ObjectId(),nome, cognome, email,username,password,citta,
					imageFileId,livello,0, Integer.parseInt(tariffaOraria),"", sesso.charAt(0),new ArrayList<String>(),new ArrayList<DisponibilitaBean>());
			InsegnanteDAO inse=new InsegnanteDAO();

			boolean inserito;
			try {
				inserito = inse.createInsegnante(insegnante);
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
		
	private boolean validazione(String nome, String cognome, String email, String username, String password, String citta, String livello, String sesso, String tariffaOraria) {
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
		if (livello.equals("")||sesso.equals("")||tariffaOraria.equals(""))
			valido=false;
		return valido;
	}
}
