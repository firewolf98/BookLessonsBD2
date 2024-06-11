package controller;

import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Paginazione")
public class Paginazione extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Paginazione() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession s=request.getSession();
		int p=Integer.parseInt(request.getParameter("page"));
		int start=p*5;
		request.setAttribute("start", String.valueOf(start));
		request.setAttribute("end",String.valueOf(start+5));
		String red=(String)s.getAttribute("pagina");
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(red);
		requestDispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
