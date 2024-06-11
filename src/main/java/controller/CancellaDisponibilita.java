package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Bean.DisponibilitaBean;
import model.Bean.InsegnanteBean;
import model.DAO.InsegnanteDAO;

@WebServlet("/CancellaDisponibilita")
public class CancellaDisponibilita extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CancellaDisponibilita() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		String giorno=request.getParameter("giorno");
		int oraInizio=Integer.parseInt(request.getParameter("oraInizio"));
		int oraFine=Integer.parseInt(request.getParameter("oraFine"));
		InsegnanteBean x=(InsegnanteBean) session.getAttribute("utente");
		
		

		InsegnanteDAO dao=new InsegnanteDAO();
		try {
			List<DisponibilitaBean> disponibili=(ArrayList<DisponibilitaBean>) session.getAttribute("disponibilita");
			boolean presente = false;
			int index = 0;
			for(int i =0;i<disponibili.size();i++) {
				DisponibilitaBean d=disponibili.get(i);
				if(d.getGiorno().equals(giorno) && d.getOraInizio()==oraInizio && d.getOraFine()==oraFine) {
					presente = true;
					index = i;
				}
			}
			if(presente) {
				disponibili.remove(index);
				x.setDisponibilita(disponibili);
				dao.updateInsegnante(x);
			}

			session.setAttribute("disponibilita", disponibili);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("modProfiloIns.jsp");
			requestDispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
