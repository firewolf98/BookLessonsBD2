<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="java.util.*,model.Bean.*"%>
    
    <%StudenteBean x=(StudenteBean) session.getAttribute("utente"); %>
	
	<div class="container">
		<div class="col-md-12" align="center">
			<h2>Bentornato, <i><%=x.getNome() %></i></h2>
		
	
		</div>
		<div class="row" style="border: transparent;">
		<div class="col-md-6">
			<img src="image?id=<%= x.getProfileImageId() %>" class="img-responsive img-thumbnail">
		</div>
		<div class="dates">
			<p>
			<h3>
				<b>Email</b>:
				<%=x.getEmail() %><br> <b>Citt&#224;</b>:
				<%=x.getCitta() %>
			</h3>
		</div>
	</div>
	</div>