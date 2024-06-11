package controller;


import java.io.IOException;
import java.io.InputStream;
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
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.DBConnector;
import model.Bean.InsegnanteBean;
import model.Bean.StudenteBean;
import model.DAO.InsegnanteDAO;
import model.DAO.StudenteDAO;

@WebServlet("/ModificaDatiPersonali")
@MultipartConfig
public class ModificaDatiPersonali extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public ModificaDatiPersonali() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		String tipoUtente=(String) session.getAttribute("tipoUtente");
		
		if (tipoUtente.equals("insegnante")) {
		    InsegnanteBean x = (InsegnanteBean) session.getAttribute("utente");
		    String passV = request.getParameter("passwordV");

		    if (!passV.equals("") && !passV.equals(x.getPassword())) {
		        request.setAttribute("message", "Password vecchia non corretta");
		        RequestDispatcher requestDispatcher = request.getRequestDispatcher("errorPage.jsp");
		        requestDispatcher.forward(request, response);
		    } else {
		        String citta = request.getParameter("citta");
		        String pass1 = request.getParameter("passwordN1");
		        String pass2 = request.getParameter("passwordN2");

		        if (!pass1.equals("") && !pass2.equals("") && !validaPass(pass1, pass2)) {
		            request.setAttribute("message", "Formato dei dati non corretto");
		            RequestDispatcher requestDispatcher = request.getRequestDispatcher("errorPage.jsp");
		            requestDispatcher.forward(request, response);
		        } else {
		            if (!pass1.equals(pass2)) {
		                request.setAttribute("message", "Le due password inserite non corrispondono");
		                RequestDispatcher requestDispatcher = request.getRequestDispatcher("errorPage.jsp");
		                requestDispatcher.forward(request, response);
		            } else {
		                if (pass1.equals("")) {
		                    pass1 = x.getPassword();
		                }

		                Part filePart = request.getPart("foto");
		                InputStream content = filePart.getInputStream();
		                ObjectId imageFileId = null;

		                if (content.available() != 0) {
		                    GridFSBucket gridFSBucket = DBConnector.getGridFSBucket();
		                    GridFSUploadOptions options = new GridFSUploadOptions().chunkSizeBytes(358400);

		                    imageFileId = gridFSBucket.uploadFromStream(filePart.getSubmittedFileName(), content, options);
		                    x.setProfileImageId(imageFileId);
		                }

		                String newDescrizione = request.getParameter("descrizione");
		                if (newDescrizione != null && !newDescrizione.trim().isEmpty()) {
		                    x.setDescrizione(newDescrizione.trim());
		                }

		                int tariffaOraria = Integer.parseInt(request.getParameter("tariffaOraria"));

		                InsegnanteDAO iDAO = new InsegnanteDAO();

		                if (!citta.equals(x.getCitta())) {
		                    x.setCitta(citta);
		                }
		                if (!pass1.equals(x.getPassword())) {
		                    x.setPassword(pass1);
		                }
		                if (tariffaOraria != x.getTariffaOraria()) {
		                    x.setTariffaOraria(tariffaOraria);
		                }

		                try {
		                    iDAO.updateInsegnante(x);
		                    session.setAttribute("utente", x);
		                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("profiloIns.jsp");
		                    requestDispatcher.forward(request, response);
		                } catch (Exception e) {
		                    e.printStackTrace();
		                }
		            }
		        }
		    }
		}

			else if (tipoUtente.equals("studente")) {
				StudenteBean x=(StudenteBean) session.getAttribute("utente");
				String citta=request.getParameter("citta");
				String pass1=request.getParameter("passwordN1");
				String pass2=request.getParameter("passwordN2");
				String passV=request.getParameter("passwordV");
				
				if (!passV.equals("")&&!passV.equals(x.getPassword())) {
					request.setAttribute("message", "Password vecchia non corretta");
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("errorPage.jsp");
					requestDispatcher.forward(request, response);
				}

				else {
					if (!pass1.equals("")&&!pass2.equals("")&&!validaPass(pass1,pass2)) {
						request.setAttribute("message", "Formato dei dati non corretto");
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("errorPage.jsp");
						requestDispatcher.forward(request, response);
					}
					else {
					if(!pass1.equals(pass2)) {
						request.setAttribute("message", "Le due password inserite non corrispondono");
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("errorPage.jsp");
						requestDispatcher.forward(request, response);
					}
					else {
						if (pass1.equals(""))
							pass1=x.getPassword();

						Part filePart = request.getPart("foto");
		                InputStream content = filePart.getInputStream();
		                ObjectId imageFileId = null;

		                if (content.available() != 0) {
		                    GridFSBucket gridFSBucket = DBConnector.getGridFSBucket();
		                    GridFSUploadOptions options = new GridFSUploadOptions().chunkSizeBytes(358400);

		                    imageFileId = gridFSBucket.uploadFromStream(filePart.getSubmittedFileName(), content, options);
		                    x.setProfileImageId(imageFileId);
		                }

						StudenteDAO sDAO=new StudenteDAO();

						if (!citta.equals(x.getCitta()))
							x.setCitta(citta);
						if (!pass1.equals(x.getPassword()))
							x.setPassword(pass1);
						try {
							sDAO.updateStudente(x);
							session.setAttribute("utente", x);

							RequestDispatcher requestDispatcher = request.getRequestDispatcher("profiloStud.jsp");
							requestDispatcher.forward(request, response);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				}
			}
	
		}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private boolean validaPass(String pass1,String pass2 ) {
		boolean valido=true;
		String expPassword="^[a-zA-Z0-9]{8,19}$";
		if (!Pattern.matches(expPassword, pass1)||!Pattern.matches(expPassword, pass2))
			valido=false;
		return valido;
	}
}

