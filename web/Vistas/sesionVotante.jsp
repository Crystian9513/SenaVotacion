<%@page import="Entidades.Usuario"%>
<%@page import="Entidades.AgendaVotacion"%>
<%@page import="Controladores.AgendaVotacionJpaController"%>
<%@page import="Entidades.Votante"%>
<%@page import="java.util.List"%>
<%@page import="Entidades.Candidato"%>
<%@page import="Controladores.CandidatoJpaController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setHeader("Expires", "0"); // Proxies
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Votante</title>
        <link rel="stylesheet" href="../css/votante.css"/>
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    </head>
    <script>
        // Espera a que el DOM esté completamente cargado
        document.addEventListener("DOMContentLoaded", function () {
            // Encuentra todos los elementos con el atributo data-bs-toggle="popover"
            var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));

            // Itera sobre cada elemento y crea un nuevo objeto Popover para él
            var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
                return new bootstrap.Popover(popoverTriggerEl);
            });
        });
    </script>
    <body class="fondo">
        <%
            HttpSession sesion = request.getSession();
            Votante usuario = (Votante) sesion.getAttribute("votante");

            if (usuario == null) {
                response.sendRedirect("inicioSesion.jsp");
            } else {
                AgendaVotacion agenda = usuario.getAgendaFk();
                if (agenda != null) {
                    List<Candidato> candidatos = agenda.getCandidatoList();
        %>
        <div class="container pt-5">
            <div class="text-center text-white">
                <h1>Bienvenido al sistema de votación: <%= usuario.getNombresCompleto()%></h1>
                <h5 class="pt-4">Por favor, elija uno de los candidatos disponibles y vote. Una vez que haya votado, ya no tendrá acceso a esta página.</h5>
            </div>
        </div>
       <div class="container-fluid pt-2 ">
    <div class="container-fluid pt-5 ">
    <div class="row">
        <% 
        int cardsPerRow = Math.min(candidatos.size(), 4); // Determina el número máximo de tarjetas por fila
        int cardsCount = 0; // Contador de tarjetas
        for (Candidato candidato : candidatos) {
            %>
            <div class="col-lg-<%= 12 / cardsPerRow %> col-md-6 col-sm-12 mt-1 d-flex align-items-center justify-content-center font-weight-bold">
                <div class="section_our_solution">
                    <div class="our_solution_category">
                        <div class="solution_cards_box">
                            <div class="solution_card">
                                <div class="row">
                                    <div class="col-12">
                                        <div class="card">
                                            <div class="card-image-container">
                                                <img src="<%= candidato.getFotografia() %>" alt="" width="150px" height="180px">
                                            </div>
                                            <p class="card-title">
                                                <h5 class="text-center"><%= candidato.getNombres()%> <%= candidato.getApellidos()%></h5>
                                            </p>
                                            <div class="d-flex align-items-center justify-content-center">
                                                <span class="d-inline-block" tabindex="0" data-bs-toggle="popover" data-bs-trigger="hover focus"
                                                    data-bs-content="<%= candidato.getPropuestaCampana()%>">
                                                    <button class="btn btn-primary btn-md fw-bold" type="button" disabled>Propuesta</button>
                                                </span>
                                            </div>
                                            <br>
                                            <a class="btn botones btn-danger btn-md fw-bold" onclick="mostrarAlerta('<%= candidato.getNumeroDocumento()%>')">Votar</a>
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
                // Cerrar la fila después de cada tarjeta según el número máximo por fila o al final de la lista de candidatos
            %>
    </div>
    <div class="row">
        <% }
        } // Fin del bucle for
        %>
    </div>
</div>


        <%
                } else {
                    // Manejo de caso en el que no se encuentra la agenda asociada al votante
                    out.println("No se encontró la agenda de votación asociada al votante.");
                }
            }
        %>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="link">
                                                        const popoverTriggerList = document.querySelectorAll('[data-bs-toggle="popover"]')
                                                        const popoverList = [...popoverTriggerList].map(popoverTriggerEl => new bootstrap.Popover(popoverTriggerEl))
        </script> 
    </body>
</html>
<script>
    // Función para mostrar la alerta de confirmación
    function mostrarAlerta(numeroDocumento) {
        Swal.fire({
            title: "Estás seguro?",
            text: "Tu voto será registrado y se cerrará la página!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Votar!"
        }).then((result) => {
            if (result.isConfirmed) {
                // Mostrar alerta de voto registrado correctamente
                Swal.fire(
                        "Voto registrado",
                        "Tu voto ha sido registrado correctamente.",
                        "success"
                        ).then(() => {
                    // Redireccionar después de mostrar la alerta
                    window.location.href = "registrarVoto.jsp?numeroDocumento=" + numeroDocumento;
                });
            } else {
                // Si el usuario cancela, no hacer nada
                console.log("El usuario canceló el voto.");
            }
        });
    }
</script>


<%




%>