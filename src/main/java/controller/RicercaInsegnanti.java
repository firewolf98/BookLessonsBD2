package controller;

import java.io.IOException;

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

@WebServlet("/RicercaInsegnanti")
public class RicercaInsegnanti extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RicercaInsegnanti() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String materia=request.getParameter("materia");
    	String livello=request.getParameter("livello");
    	String citta=request.getParameter("citta");

    	HttpSession s=request.getSession();

    	synchronized(s) {
    		request.setAttribute("materia", materia);
    		request.setAttribute("livello", livello);
    		request.setAttribute("citta", citta);
    		
    		if (livello.equals("")||materia.equals("")) {
    			request.setAttribute("message", "Devi inserire materia e livello per poter effettuare la ricerca");
    			response.sendRedirect("errorPage.jsp");
    		}

    		s.setAttribute("materia", materia);
    		s.setAttribute("livello", livello);
    		s.setAttribute("citta", citta);

    		InsegnanteDAO i=new InsegnanteDAO();
    		try {
    			List<InsegnanteBean> insegnanti;
    			if (citta.equals(""))
    				insegnanti=i.getInsegnantiByMateriaLivello(materia, livello);
    			else
    				insegnanti=i.getInsegnantiByMateriaLivelloCitta(materia, livello, citta);
    			request.setAttribute("insegnanti", insegnanti);
    			s.setAttribute("insegnanti", insegnanti);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}

    		request.setAttribute("start", "0");
    		request.setAttribute("end", "5");
    		RequestDispatcher requestDispatcher = request.getRequestDispatcher("ricercaInsegnanti.jsp");
    		requestDispatcher.forward(request, response);
    	}
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
