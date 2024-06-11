<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*,model.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Prenota le tue ripetizioni</title>
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
<link rel="stylesheet" href="css/style.css?ts=<?=time()?>&quot">
<link rel="shortcut icon" href="img/icona.ico" type="image/x-icon">
</head>

<body>
	<%@ include file="header.jsp"%>
	<div class="container bg-white margin-30-top">
		<ol class="breadcrumb" itemscope=""
			itemtype="http://schema.org/BreadcrumbList">
			<li><a href="index.jsp">Home</a></li>
			<li class="active">Login</li>
		</ol>

		<div class="row">
			<div class="col-md-12">
				<div class="row">
					<div class="col-md-6 col-md-offset-3">
						<div class="well margin-30-top">
						<%boolean ris=(boolean)request.getAttribute("ris");
						if (ris){%>
							<h2 class="alert alert-success">
								<i>Prenotazione effettuata con successo!</i>
							</h2>
							<%}else{%>
							<h2 class="alert alert-success">
								<i>Prenotazione non andata a buon fine!</i>
							</h2>
							<% }%>
							<hr>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Footer -->
	<%@include file="footer.jsp"%>
</body>
</html>