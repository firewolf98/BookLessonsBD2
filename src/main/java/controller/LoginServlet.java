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
import model.Bean.LezioneBean;
import model.Bean.StudenteBean;
import model.DAO.InsegnanteDAO;
import model.DAO.LezioneDAO;
import model.DAO.StudenteDAO;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		StudenteDAO sdao=new StudenteDAO();
		InsegnanteDAO idao=new InsegnanteDAO();
		StudenteBean s;
		InsegnanteBean i;
		boolean snull=false,inull=false;
	
		HttpSession session=request.getSession();
		String redirect="";
		
		
		try {
			s = sdao.getStudenteByUsername(username);
			if (s!=null) {
				if (s.getPassword().equals(password)) {
					if (s.isAmministratore()) {
						session.setAttribute("isLogged", true);
						session.setAttribute("utente", s);
						redirect="index.jsp";
						session.setAttribute("tipoUtente", "admin");
						
						InsegnanteDAO daoI=new InsegnanteDAO();
						List<InsegnanteBean> insegnanti=daoI.getAllInsegnanti();
						session.setAttribute("insegnantiAdmin", insegnanti);
					}

					else {
						session.setAttribute("isLogged", true);
						session.setAttribute("utente", s);
						redirect="index.jsp";
						session.setAttribute("tipoUtente", "studente");
						
						LezioneDAO lDAO=new LezioneDAO();
						List<LezioneBean> lezioni=lDAO.getLezioniByStudente(s.getId());
						session.setAttribute("lezioni", lezioni);
					}
				}
				else {
					redirect="login.jsp";
				}
			}
			else {
				snull=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			i=idao.getInsegnanteByUsername(username);
			if (i!=null) {
				if (i.getPassword().equals(password))
					if (i.isAmministratore()) {
						session.setAttribute("isLogged", true);
						session.setAttribute("utente", i);
						redirect="index.jsp";
						session.setAttribute("tipoUtente", "admin");
						InsegnanteDAO daoI=new InsegnanteDAO();
						List<InsegnanteBean> insegnanti=daoI.getAllInsegnanti();
						session.setAttribute("insegnantiAdmin", insegnanti);
					}
					else {
						session.setAttribute("isLogged", true);
						session.setAttribute("utente", i);
						redirect="index.jsp";
						session.setAttribute("tipoUtente", "insegnante");
						
						LezioneDAO lDAO=new LezioneDAO();
						List<LezioneBean> lezioni=lDAO.getLezioniByInsegnante(i.getId());
						session.setAttribute("lezioni", lezioni);
						if(i.getMaterie()==null) {
							session.setAttribute("materieInsegnate", new ArrayList<String>());
						}else {
							session.setAttribute("materieInsegnate", i.getMaterie());
						}
						if(i.getDisponibilita()==null) {
							session.setAttribute("disponibilita", new ArrayList<DisponibilitaBean>());
						}else {
							session.setAttribute("disponibilita", i.getDisponibilita());
						}
					}
				
				else {
					redirect="login.jsp";
				}
			}else {
				inull=true;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		if (snull&& inull) {
			redirect="registrati.jsp";
		}
		
		if (redirect.equals("index.jsp")) {
			List<String> materie;
			try {
				materie = idao.getAllUniqueMaterie();
				session.setAttribute("materie", materie);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		
		}
				
		if (redirect.equals("login.jsp")) {
			request.setAttribute("message", "Username o password errati");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(redirect);
			requestDispatcher.forward(request, response);
		}
		else {
			response.sendRedirect(redirect);
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	

}
