<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="java.util.*,model.*"%>
<footer>
    <%
    List<String> materie = (ArrayList<String>) session.getAttribute("materie");
    %>
    <div class="container footer-container">
        <div class="row">
            <!-- Logo and Text Section -->
            <div class="col-xs-12 col-sm-3 logo-footer">
                <div class="footer-section text-center">
                    <img src="img/logo.png" alt="Logo" class="img-responsive" style="max-width: 200px; margin: 0 auto;">
                    <p class="desc-footer">Il supporto accademico di cui hai bisogno, quando ne hai bisogno</p>
                </div>
            </div>
            <!-- Materie Section -->
            <div class="col-xs-12 col-sm-7">
                <div class="footer-section">
                    <strong class="title-footer">Materie</strong>
                    <hr>
                    <div class="row">
                        <%
                        if (materie != null) {
                            for (int i = 0; i < materie.size(); i++) {
                                String mat = materie.get(i);
                        %>
                        <div class="col-xs-6 col-md-4">
                            <p><%= mat %></p>
                        </div>
                        <%
                            }
                        }
                        %>
                    </div>
                </div>
            </div>
            <!-- Support and FAQ Section -->
            <div class="col-xs-12 col-sm-2">
                <div class="footer-section">
                	<div>
	                	<strong class="title-footer">Hai bisogno di aiuto?</strong>
	                    <hr>
	                    <p class="phone">
	                        <i class="fa fa-phone fa-lg"></i> 3889532837
	                    </p>
	                    <p>Lun-Ven (9-13.30 / 14.30-18)</p>
                	</div>
					<div class="come-funziona-footer">
						<strong class="title-footer">Come funziona</strong>
	                    <hr>
	                    <ul>
	                        <li><a class="link-footer" href="comeFunziona.jsp">FAQ Studente</a></li>
	                        <li><a class="link-footer" href="comeFunziona.jsp">FAQ Insegnante</a></li>
	                    </ul>
						</div>
                </div>
            </div>
        </div>
    </div>
</footer>