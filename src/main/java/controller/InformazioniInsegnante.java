package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Bean.DisponibilitaBean;
import model.Bean.InsegnanteBean;
import model.Bean.RecensioneBean;
import model.DAO.InsegnanteDAO;
import model.DAO.LezioneDAO;

@WebServlet("/InformazioniInsegnante")
public class InformazioniInsegnante extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InformazioniInsegnante() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		InsegnanteDAO model = new InsegnanteDAO();
		try {
			InsegnanteBean i = model.getInsegnanteByUsername(username);
			request.setAttribute("insegnante", i);
			String giorni[] = { "domenica", "lunedi", "martedi", "mercoledi", "giovedi", "venerdi", "sabato" };

			List<DisponibilitaBean> disp = model.getDisponibilitaByInsegnanteId(i.getId());

			request.setAttribute("disponibilita", disp);

			JSONArray dJson = new JSONArray();
			for (DisponibilitaBean d : disp) {
				JSONObject obj = new JSONObject();
				obj.append("giorno", d.getGiorno());
				obj.append("oraInizio", d.getOraInizio());
				obj.append("oraFine", d.getOraFine());
				dJson.put(obj);
			}
			request.setAttribute("j", dJson.toString());

			ArrayList<String> giorniDisp = new ArrayList<>();
			for (DisponibilitaBean d : disp) {
				giorniDisp.add(d.getGiorno());
			}

			String giorniDisponibili = "";
			for (int z = 0; z < giorni.length; z++) {
				if (giorniDisp.contains(giorni[z]))
					giorniDisponibili += "," + z;
			}
			request.setAttribute("giorniDisp", giorniDisponibili);

			LezioneDAO ldao = new LezioneDAO();
			List<RecensioneBean> recensioni = ldao.getRecensioniByInsegnante(i.getId());
			request.setAttribute("recensioni", recensioni);
			HttpSession session = request.getSession();
			session.setAttribute("recensioni", recensioni);

		} catch (Exception e) {
			e.printStackTrace();
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("informazioniInsegnante.jsp");
		requestDispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
