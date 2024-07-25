package controller;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Bean.InsegnanteBean;
import model.Bean.LezioneBean;
import model.Bean.RecensioneBean;
import model.Bean.StudenteBean;
import model.DAO.LezioneDAO;


@WebServlet("/Prenotazione")
public class Prenotazione extends HttpServlet{
	private static final long serialVersionUID = 1L;

    public Prenotazione() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String luogo= request.getParameter("luogo");
		HttpSession session=request.getSession();
		String inizio=(String) request.getParameter("oraInizio");
		String fine=(String) request.getParameter("oraFine");
		String date=request.getParameter("data");

		synchronized(session) {
			if (!date.equals("") && !inizio.equals("") && !fine.equals("") && !date.equals("")) {
				InsegnanteBean insegnante=(InsegnanteBean) session.getAttribute("insegnante");
				StudenteBean studente=(StudenteBean)session.getAttribute("utente");
				if (studente==null) {
					studente=new StudenteBean();
				}
				String materia=(String)session.getAttribute("materia");
				int oraInizio=Integer.parseInt(inizio);
				int oraFine=Integer.parseInt(fine);
				double costo=insegnante.getTariffaOraria()*(oraFine-oraInizio);

				if(luogo.equals("online")) {
					luogo = "Online";
				}else if(luogo.equals("studente")) {
					luogo = "Casa studente";
				}else if(luogo.equals("insegnante")) {
					luogo = "Casa insegnante";
				}
				
				try {

					LezioneBean lezione=new LezioneBean(new ObjectId(), oraInizio,oraFine, costo ,date ,studente, insegnante, materia,false,luogo,new RecensioneBean());
					LezioneDAO ldao = new LezioneDAO();
					ldao.createLezione(lezione);
					request.setAttribute("ris", true);
					
					List<LezioneBean> lezioni=ldao.getLezioniByStudente(studente.getId());
					session.setAttribute("lezioni", lezioni);
					
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("outputPrenotazione.jsp");
					requestDispatcher.forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				

			} else {
				request.setAttribute("message", "Devi inserire tutti i parametri per poter prenotare!");
				response.sendRedirect("errorPage.jsp");
			}
		}
	

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
