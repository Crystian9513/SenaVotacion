<%@page import="Entidades.Usuario"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalTime"%>
<%@page import="java.time.ZoneId"%>
<%@page import="java.time.LocalDate"%>
<%@page import="Entidades.Candidato"%>
<%@page import="java.util.List"%>
<%@page import="Entidades.AgendaVotacion"%>

<% HttpSession sesion = request.getSession();

    Usuario usuario = (Usuario) sesion.getAttribute("user");

    if (usuario == null) {
        response.sendRedirect("inicioSesion.jsp");
    } else {

    }

    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setHeader("Expires", "0"); // Proxies
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Votaciones en vivo</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/votosEnVivo.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    </head>

    <body class="fondo">
        
        <%
            List<AgendaVotacion> agendasActivas = (List<AgendaVotacion>) request.getAttribute("agendasActivas");
            if (agendasActivas != null && !agendasActivas.isEmpty()) {
                for (AgendaVotacion agenda : agendasActivas) {
                    // Convertir Date a LocalDate
                    LocalDate fechaFin = agenda.getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    // Convertir Date a LocalTime
                    LocalTime horaFin = agenda.getHoraFin().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        %>
        <h1 >Agenda Activa : <%= agenda.getNombre()%></h1>
      
        <h5 >Fecha y hora de finalización: <%= fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%> - <%= horaFin.format(DateTimeFormatter.ofPattern("HH:mm"))%></h5>
        <%
            List<Candidato> candidatos = (List<Candidato>) request.getAttribute("candidatos_" + agenda.getIdAgenda());
            if (candidatos != null && !candidatos.isEmpty()) {
        %>
        <div class="container-fluid pt-2">
            <div class="container-fluid pt-2">
                <div class="row">
                    <%
                        int cardsPerRow = Math.min(candidatos.size(), 3); // Determina el número máximo de tarjetas por fila
                        int cardsCount = 0; // Contador de tarjetas
                        for (Candidato candidato : candidatos) {
                    %>
                    <div class="col-lg-<%= 12 / cardsPerRow%> col-md-6 col-sm-12 mt-1 d-flex align-items-center justify-content-center font-weight-bold">
                        <div class="section_our_solution">
                            <div class="our_solution_category">
                                <div class="solution_cards_box">
                                    <div class="solution_card">
                                        <div class="row">
                                            <div class="col-12">
                                                
                                                <div class="card text-center bg-dark text-white">


                                                    <img class="rounded mx-auto d-block mt-2" src="${pageContext.request.contextPath}/Vistas/<%= candidato.getFotografia()%>" alt="" width="150px" height="180px">

                                                    <p class="card-title">
                                                    <h5 class=""><%= candidato.getNombres()%> <%= candidato.getApellidos()%></h5>
                                                    <h5 class="">Votos: <%= candidato.getNumeroVotos()%></h5>
                                                    </p>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%
                        cardsCount++;
                        if (cardsCount % cardsPerRow == 0 || cardsCount == candidatos.size()) {
                    %>
                </div>
                <div class="row">
                    <%
                            }
                        } // Fin del bucle for
                    %>
                </div>
            </div>
        </div>
        <%
        } else { // No hay candidatos asociados a esta agenda
        %>
        <p>No hay candidatos asociados a esta agenda.</p>
        <%
                }
            } // Fin del bucle for
        } else { // No hay agendas activas
        %>
        <p>No hay agendas activas en este momento.</p>
        <%
            }
        %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    </body>
</html>
<script>
    // Función para recargar la página cada cierto intervalo de tiempo
    function recargarPagina() {
        location.reload(); // Recargar la página
    }

    // Llamar a la función recargarPagina cada 5 segundos (5000 milisegundos)
    setInterval(recargarPagina, 5000); // 5000 ms = 5 segundos
</script>