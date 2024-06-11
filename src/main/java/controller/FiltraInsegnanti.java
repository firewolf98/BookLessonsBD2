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

@WebServlet("/FiltraInsegnanti")
public class FiltraInsegnanti extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FiltraInsegnanti() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession s=request.getSession();
		String materia=(String) s.getAttribute("materia");
		String citta=(String) s.getAttribute("citta");
		String livello=(String)s.getAttribute("livello");
		InsegnanteDAO iDao=new InsegnanteDAO();
		
		String min=request.getParameter("minPrezzo");
		int minPrezzo;
		if (min==null||min.equals("")) minPrezzo=-1;
		else minPrezzo=Integer.parseInt(min);
		
		String max=request.getParameter("maxPrezzo");
		int maxPrezzo;
		if (max==null||max.equals("")) maxPrezzo=-1;
		else maxPrezzo=Integer.parseInt(max);
		
		String sesso=request.getParameter("sesso");
		
		try {
			List<InsegnanteBean> i;
			if (citta.equals(""))
				i=iDao.getInsegnantiByMateriaLivello(materia, livello);
			else
				i = iDao.getInsegnantiByMateriaLivelloCitta(materia, livello, citta);
		
			if (minPrezzo!=-1||maxPrezzo!=-1)
				i=iDao.doRetrieveByPrice(i, minPrezzo, maxPrezzo);
			
			if (sesso!=null)
				i=iDao.doRetrieveByGender(i, sesso.charAt(0));
			
			request.setAttribute("insegnanti", i);
			s.setAttribute("insegnanti", i);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
		request.setAttribute("materia", materia);
		request.setAttribute("citta", citta);
		request.setAttribute("minPrezzo", minPrezzo);
		request.setAttribute("maxPrezzo", maxPrezzo);
		request.setAttribute("sesso", sesso);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("ricercaInsegnanti.jsp");
		requestDispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
