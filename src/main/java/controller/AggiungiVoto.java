package controller;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.bson.types.ObjectId;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Bean.LezioneBean;
import model.Bean.RecensioneBean;
import model.Bean.StudenteBean;
import model.DAO.LezioneDAO;

@WebServlet("/AggiungiVoto")
public class AggiungiVoto extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AggiungiVoto() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ins=request.getParameter("idLezione");
		LezioneDAO ldao =new LezioneDAO();
		LezioneBean l = new LezioneBean();
		try {
			l=ldao.getLezioneById(new ObjectId(ins));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		int v=Integer.parseInt(request.getParameter("voto"));
		String des=request.getParameter("commento");
		HttpSession session=request.getSession();
		StudenteBean x=(StudenteBean) session.getAttribute("utente");
		
		RecensioneBean r = new RecensioneBean();
		
		Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String todayDate = dateFormat.format(calendar.getTime());

		r.setVoto(v);
		r.setCommento(des.trim());
		r.setInsegnante(l.getInsegnante());
		r.setStudente(x);
		r.setDataVoto(todayDate);
		l.setRecensione(r);
		try {
			ldao.updateLezione(l);
			
			List<LezioneBean> lezioni=ldao.getLezioniByStudente(x.getId());
			session.setAttribute("lezioni", lezioni);
			
			response.sendRedirect("lezPasStud.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
