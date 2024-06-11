package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Bean.RecensioneBean;
import model.DAO.InsegnanteDAO;
import model.DAO.LezioneDAO;

@WebServlet("/Commenti")
public class Commenti extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Commenti() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<RecensioneBean> slide = new ArrayList<>();
        synchronized (session) {
            LezioneDAO lezione = new LezioneDAO();
            try {
                List<RecensioneBean> voti = lezione.getAllRecensioni();
                Random random = new Random();
                int count = 0;
                while (count < 3 && !voti.isEmpty()) {
                    int index = random.nextInt(voti.size());
                    RecensioneBean voto = voti.get(index);
                    if (voto.getVoto() != 0 && voto.getCommento() != null) {
                        slide.add(voto);
                        count++;
                    }
                    voti.remove(index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            InsegnanteDAO ins = new InsegnanteDAO();
            try {
                List<String> materie = ins.getAllUniqueMaterie();
                session.setAttribute("materie", materie);
            } catch (Exception e) {
                e.printStackTrace();
            }

            session.setAttribute("slide", slide);
            response.sendRedirect("index.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
