<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*,model.Bean.*"%>
<!DOCTYPE html>
<html>
<head>
	<title>Prenota le tue ripetizioni </title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
	<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
	<link rel="stylesheet" href="css/style.css">
	<link rel="shortcut icon" href="img/icona.ico" type="image/x-icon">
</head>
<body>

	<!-- Header -->
	<%@include file="header.jsp"%>

	<!-- Sezione Jumbotron -->
	<div class="jumbotron" >
		<div id="contentj">
			<div align="center">
				<h2>Trova i migliori insegnanti per le tue ripetizioni</h2>
				<form class="form-inline" action="RicercaInsegnanti">
					<div class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-book"></i></span> <input type="text"
							class="form-control" id="materia" placeholder="Inserisci materia"
							name="materia" required list="ricerca-datalist" onkeyup="ricercaMateria(this.value)">
							<datalist id="ricerca-datalist"></datalist>
					</div>
					<div class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-education"></i></span> <select
							class="form-control" id="sel1" name="livello">
							<option value="Universita">Università</option>
							<option value="Elementari">Elementari</option>
							<option value="Superiori">Superiori</option>
							<option value="Medie">Medie</option>
						</select>
					</div>
					<div class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-map-marker"></i></span> <input type="text"
							class="form-control" id="citta" placeholder="Inserisci città"
							name="citta">
					</div>
					<button type="submit" class="btn btn-primary">
						<span class="glyphicon glyphicon-search"></span> Search
					</button>
				</form>
			</div>
		</div>
	</div>


	<!-- Sezione commenti -->
	<div class="container">
		<div class="row">
			<div class="col-md-4 text-center">
				<div>
					<i class="fa fa-search circle-icon"></i>
					<h3>Trova l'insegnante perfetto</h3>
					<hr>
					<p>Cerca il tuo insegnante tra i nostri insegnanti qualificati</p>
				</div>
			</div>
			<div class="col-md-4 text-center">
				<div>
					<i class="fa fa-calendar circle-icon"></i>
					<h3>Prenota la tua lezione</h3>
					<hr>
					<p>Prenota la lezione a casa dell'insegnante, casa tua oppure
						via Skype</p>
				</div>
			</div>
			<div class="col-md-4 text-center">
				<div>
					<i class="fa fa-check-square circle-icon "></i>
					<h3>Migliora i tuoi voti</h3>
					<hr>
					<p>Migliora i tuoi voti con sole poche ore di lezione</p>
				</div>
			</div>
		</div>

		<div class="col-md-5 riga"></div>
		<%List<RecensioneBean> slide=(ArrayList) session.getAttribute("slide");
		if (slide==null) response.sendRedirect("Commenti"); 
		else{%>
		<div class="row mycarousel" >
			<div id="myCarousel" class="carousel slide" data-ride="carousel">
				<!-- Indicators -->
				<ol class="carousel-indicators">
					<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
					<li data-target="#myCarousel" data-slide-to="1"></li>
					<li data-target="#myCarousel" data-slide-to="2"></li>
				</ol>

				<!-- Wrapper for slides -->
				<div class="carousel-inner">
				<% if (slide != null && !slide.isEmpty()) { %>
					<div class="item active">
						<div class="col-md-3 text-center">
						<%RecensioneBean slide1=slide.get(0); %>
						<img src="image?id=<%= slide1.getInsegnante().getProfileImageId() %>" class="img-circle">

						</div>
						<div class="col-md-9">
							<h3><%=slide1.getStudente().getNome()%> ha utilizzato il servizio di BookLessons.
							Ecco cosa pensa del suo insegnante <%=slide1.getInsegnante().getNome()%></h3>
							<blockquote><%=slide1.getCommento()%></blockquote>
							<%for (int i=0;i<slide1.getVoto();i++){ %>
							<i class="fa fa-star"></i>
							<%} %>
						</div>
					</div>

					<div class="item">
						<div class="col-md-3 text-center">
						<%RecensioneBean slide2=slide.get(1); %>
						<img src="image?id=<%= slide2.getInsegnante().getProfileImageId() %>" class="img-circle">

						</div>
						<div class="col-md-9">
							<h3><%=slide2.getStudente().getNome()%> ha utilizzato il servizio di BookLessons.
							Ecco cosa pensa del suo insegnante <%=slide2.getInsegnante().getNome()%></h3>
							<blockquote><%=slide2.getCommento()%></blockquote>
							<%for (int i=0;i<slide2.getVoto();i++){ %>
							<i class="fa fa-star"></i>
							<%} %>
						</div>
					</div>

					<div class="item">
						<div class="col-md-3 text-center">
						<%RecensioneBean slide3=slide.get(2); %>
						<img src="image?id=<%= slide3.getInsegnante().getProfileImageId() %>" class="img-circle">

						</div>
						<div class="col-md-9">
							<h3><%=slide3.getStudente().getNome()%> ha utilizzato il servizio di BookLessons.
							Ecco cosa pensa del suo insegnante <%=slide3.getInsegnante().getNome()%></h3>
							<blockquote><%=slide3.getCommento()%></blockquote>
							<%for (int i=0;i<slide3.getVoto();i++){ %>
							<i class="fa fa-star"></i>
							<%} %>
						</div>
					</div>
					<% } else { %>
        <div class="item active">
            <div class="col-md-3 text-center">
                <p>Nessuna recensione disponibile</p>
            </div>
        </div>
    <% } %>
				</div>
				

				<!-- Left and right controls -->
				<a class="left carousel-control" style="background:none;" href="#myCarousel"
					data-slide="prev"> <span
					class="glyphicon glyphicon-chevron-left" style="left:10px;"></span> <span
					class="sr-only">Previous</span>
				</a> <a class="right carousel-control" style="background:none;" href="#myCarousel"
					data-slide="next"> <span
					class="glyphicon glyphicon-chevron-right" style="right:10px;"></span> <span
					class="sr-only">Next</span>
				</a>
			</div>
		</div>
		<%} %>
	</div>

	<!-- Footer -->
	<%@include file="footer.jsp"%>
	
	<script src="scripts/ricercaMateria.js"></script>
</body>
</html>