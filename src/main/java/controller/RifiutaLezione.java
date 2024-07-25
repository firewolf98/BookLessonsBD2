package controller;

import java.io.IOException;
import java.util.List;
import org.bson.types.ObjectId;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Bean.InsegnanteBean;
import model.Bean.LezioneBean;
import model.DAO.LezioneDAO;


@WebServlet("/RifiutaLezione")
public class RifiutaLezione extends HttpServlet{
	private static final long serialVersionUID = 1L;

    public RifiutaLezione() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String codiceLezione = request.getParameter("codiceLezione");
		if (codiceLezione != null) {
			LezioneDAO lezioneDAO = new LezioneDAO();
			try {
				lezioneDAO.deleteLezione(new ObjectId(codiceLezione));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		HttpSession session=request.getSession();
		InsegnanteBean insegnante=(InsegnanteBean) session.getAttribute("utente");
		LezioneDAO ldao = new LezioneDAO();
		List<LezioneBean> lezioni=ldao.getLezioniByInsegnante(insegnante.getId());
		session.setAttribute("lezioni", lezioni);
		response.sendRedirect("lezProIns.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
