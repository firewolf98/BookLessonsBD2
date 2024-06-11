package controller;

import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO.InsegnanteDAO;


@WebServlet("/RicercaAjaxMateria")
public class RicercaAjaxMateria extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RicercaAjaxMateria() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String query = request.getParameter("q");
		if (query != null) {
			InsegnanteDAO idao = new InsegnanteDAO();
			List<String> materie;
			try {
				materie = idao.getAjaxMaterie(query);
				String jsonResponse = new Gson().toJson(materie);
				response.setContentType("application/json");
				response.getWriter().append(jsonResponse);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
