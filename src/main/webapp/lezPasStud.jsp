<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*,model.*,java.text.*"%>
<!DOCTYPE html>
<html>
<head>
<title><%=((StudenteBean)session.getAttribute("utente")).getNome() %> <%=((StudenteBean)session.getAttribute("utente")).getCognome() %></title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/style.css?ts=<?=time()?>&quot">
<link rel="stylesheet" href="css/visIns.css?ts=<?=time()?>&quot">
<link rel="shortcut icon" href="img/icona.ico" type="image/x-icon">
</head>
<body>
	<!--Menu-->
	<%@include file="header.jsp"%>

	<%@include file="headerStd.jsp"%>


		<%
			ArrayList lezioni = (ArrayList) session.getAttribute("lezioni");
			ArrayList<LezioneBean> lezioniPas = new ArrayList<>();
			Date today = new Date();
			for (int i = 0; i < lezioni.size(); i++) {
				LezioneBean y = (LezioneBean) lezioni.get(i);
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				Date date = dateFormat.parse(y.getDataLezione());
				if (date.before(today)) {
					lezioniPas.add(y);
				}
			}
			
			//paginazione
			String s = (String) request.getAttribute("start");
			int start, end;
			if (s == null)
				start = 0;
			else
				start = Integer.parseInt(s);

			String e = (String) request.getAttribute("end");
			if (e == null)
				end = start + 5;
			else
				end = Integer.parseInt(e);
			if (lezioniPas.size() < end)
				end = lezioniPas.size();

			int p;
			if (start == 0)
				p = 0;
			else
				p = Integer.parseInt(request.getParameter("page"));
			
			session.setAttribute("pagina","lezPasStud.jsp");
		%>

		<%

			for (int i = 0; i < lezioniPas.size(); i++) {
				LezioneBean y = (LezioneBean) lezioniPas.get(i);
			}
		%>

<div class="container">
<div class="row">
			<div class="col-md-7">
					<%if (lezioniPas.size()!=0){ 
					for (int i = 0; i < lezioniPas.size(); i++) {
						LezioneBean y = (LezioneBean) lezioni.get(i);
				%>
	
				<div class="row">

				<div class="col-md-6">
					<br> Insegnante: <b><i><%=y.getInsegnante().getNome()%>
							<%=y.getInsegnante().getCognome() %></i></b> <br>Materia: <b><i><%=y.getMateria()%></i></b><br>

					<img src="image?id=<%=y.getInsegnante().getProfileImageId() %>" class="img-responsive img-circle">

				</div>
				<div class="col-md-6">
					<br> Codice Lezione: <b><i><%=y.getId() %></i></b> <br>Data
					Lezione: <b><i><%=y.getDataLezione()%></i></b> <br>Ora Inizio:
					<b><i><%=y.getOraInizio()%></i></b> <br>Ora Fine: <b><i><%=y.getOraFine()%></i></b>
					<br>Luogo: <b><i><%=y.getLuogo()%></i></b> <br>Costo: <b><i><%=y.getCosto()%>&euro;</i></b>
				</div>
				</div>
				<div style="height: 20px"><hr></div>
			

			<%
					}
				%>
			</div>
			<div class="col-md-1"></div>
			<div class="col-md-4">
				<br>
				<div class="panel">
					<div class="panel-heading">
						<h4 style="text-align:center">Aggiungi recensione per una lezione</h4>
					</div>
					<div class="panel-body">
						<form action="AggiungiVoto" method="post">

							<%if (lezioniPas.size()>0){ %>


							<div class="col-md-6">
								<h5>Lezione:</h5>
								<div class="form-group">
									<select name="idLezione" class="form-control">
										<%
											for(LezioneBean l: lezioniPas) {
												
												if(l.getRecensione().getCommento()==null) {
										%>

										<option value=<%=l.getId()%>><%=l.getId()%></option>

										<%
							}}
						%>
									</select>
								</div>
							</div>
							<div class="col-md-6">
								<h5>Voto:</h5>
								<div class="form-group">
									<input type="number" class="form-control" name="voto"
										placeholder="voto 1-5" min="1" max="5" required>
								</div>
							</div>
							<div class="col-md-6">
								<h5>Commento:</h5>
								<div class="form-group">
									<textarea class="form-control rounded-0"
										id="exampleFormControlTextarea1" rows="10" name="commento"
										style="width: 300px; resize: none; height: 70px;" required>
							</textarea>
								</div>
							</div>
							<div class="col-md-12" align="center">
								<button type="submit" class="btn btn-default">Vota</button>
							</div>



						</form>
					</div>

				</div>
			</div>
		</div>
		
		
		<!-- paginazione -->
		<nav aria-label="...">
			<ul class="pagination">
				<%
							if (start == 0) {
						%>
				<li class="page-item disabled"><a class="page-link">Previous</a>
					<%
								} else {
							%>
				<li class="page-item"><a class="page-link"
					href="Paginazione?page=<%=p - 1%>">Previous</a></li>
				<%
							}
							int l = lezioniPas.size();
							for (int j = 0; l > 0; j++) {
								l = l / 5;
						%>
				<li class="page-item"><a class="page-link"
					href="Paginazione?page=<%=j%>"><%=j + 1%></a></li>
				<%
							}
							if (end < lezioniPas.size() ) {
						%>
				<li class="page-item"><a class="page-link"
					href="Paginazione?page=<%=p + 1%>">Next</a></li>
				<%
							} else {
						%>
				<li class="page-item disabled"><a class="page-link">Next</a></li>
				<%
							}
						%>
			</ul>
		</nav>


		<%}} else{ %>
		<h3 class="text-center">Non ci sono lezioni da mostrare</h3>
		<%} %>

	</div>


</body>
</html>