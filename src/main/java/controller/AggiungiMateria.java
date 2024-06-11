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
import model.Bean.InsegnanteBean;
import model.DAO.InsegnanteDAO;


@WebServlet("/AggiungiMateria")
public class AggiungiMateria extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AggiungiMateria() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String materiaAgg=request.getParameter("nomeMateriaAgg");
		HttpSession session=request.getSession();
		InsegnanteBean x=(InsegnanteBean) session.getAttribute("utente");
		
		InsegnanteDAO dao=new InsegnanteDAO();
		try {
			List<String> materieInsegnate=(ArrayList<String>)session.getAttribute("materieInsegnate");
			materieInsegnate.add(materiaAgg);
			x.setMaterie(materieInsegnate);
			dao.updateInsegnante(x);
			session.setAttribute("materieInsegnate", materieInsegnate);
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
