<%@page import="java.time.Instant"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.LocalTime"%>
<%@page import="java.time.ZonedDateTime"%>
<%@page import="java.time.ZoneId"%>
<%@page import="Controladores.VotanteJpaController"%>
<%@page import="Entidades.Votante"%>
<%@page import="java.util.Date"%>
<%@page import="Entidades.AgendaVotacion"%>
<%@page import="Entidades.Usuario"%>
<%@page import="Controladores.UsuarioJpaController"%>
<!DOCTYPE html>
<html lang="es">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Inicio de Sesion</title>
        <link rel="stylesheet" href="../css/style.css">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.min.css">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

    </head>
    <body>

        <div class="fondo px-4 py-5  text-center text-lg-start d-flex align-items-center justify-content-center">
            <div class="row gx-lg-5 align-items-center mb-5">
                <div class="col-md-6 col-sd-12 mb-5 mb-lg-0" style="z-index: 10">
                    <p class="letra text-center mb-5">
                        <a href="index.jsp">
                            <img class="img-fluid float-end mt-5" src="../img/inicioSesion_sena.jpg" height="100px"
                                 width="200px">
                        </a>
                    </p>
                </div>

                <div class="col-md-6 col-sd-12 mb-5 mb-lg-0 position-relative">
                    <div class="card bg-light bg-opacity-75 font-weight-bold">
                        <div class="card-body px-md-5">
                            <form action="" method="post" class="row g-4">
                                <div class="col-12 pt-4">
                                    <p class="letra text-center mb-3">Inicio de Sesion <br></p>
                                    <label class="mt-2">Numero de Cedula<span class="text-danger">*</span></label>
                                    <div class="input-group">
                                        <div class="input-group-text"><i class="bi bi-person-fill"></i></div>
                                        <input type="number" id="numero" name="numero" class="form-control"
                                               placeholder="Numero de Cedula" required min="1" max="999999999999">
                                    </div>
                                </div>

                                <div class="col-12">
                                    <label>Contraseña<span class="text-danger">*</span></label>
                                    <div class="input-group">
                                        <div class="input-group-text"><i class="bi bi-lock-fill"></i></div>
                                        <input type="password" id="clave" name="clave" class="form-control"
                                               placeholder="Contraseña" required min="1" maxlength="25">
                                    </div>
                                </div>
                                <div class="col-12 py-4 text-center">
                                    <button type="submit" name="btninicio" class="btn btn-primary mx-auto mt-2">Iniciar
                                        Sesion</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--FINAL CONTENEDOR-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous">
        </script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </body>

</html>

<%
    String boton = request.getParameter("btninicio");

    if (boton != null) {
        Integer cedula = Integer.parseInt(request.getParameter("numero"));
        String clave = request.getParameter("clave");

        // Verificamos si existe un usuario con la misma cédula en la base de datos
        UsuarioJpaController usuarioController = new UsuarioJpaController();
        Usuario usuario = usuarioController.findUsuario(cedula);

        if (usuario == null) {
            // Si el usuario no existe, redirigimos a la página de inicio de sesión con un mensaje de error
            String mensaje = "documentoNoExiste";
            response.sendRedirect("inicioSesion.jsp?respuesta=" + mensaje);
        } else {
            // Si el usuario existe, obtenemos el rol y la contraseña
            int rol = usuario.getRol();
            int estado = usuario.getEstado();
            String contraseñaUsuario = usuario.getClave(); // Obtener la contraseña del usuario
            
           

            // Verificamos si la contraseña coincide con la almacenada en la base de datos
            if (usuarioController.DencryptarClave(contraseñaUsuario, clave)) {
                // Verificamos si el usuario es un votante
                if (rol == 1 && estado == 0) {
                    // Si es un votante, buscamos su información en la tabla de votantes
                    VotanteJpaController votanteController = new VotanteJpaController();
                    Votante votante = votanteController.findVotante(cedula);

                    // Verificamos si el votante existe
                    if (votante == null) {
                        // Si el votante no existe, redirigimos a la página de inicio de sesión con un mensaje de error
                        String mensaje = "documentoNoExiste";
                        response.sendRedirect("inicioSesion.jsp?respuesta=" + mensaje);
                    } else {
                        // Si el votante existe, verificamos la fecha y hora de votación
                        AgendaVotacion agendaVotacion = votante.getAgendaFk();

                        //Encontramos Fecha Actual
                        LocalDate fechaActualLocalDate = LocalDate.now();
                        // Convertir LocalDate a Date
                        ZoneId defaultZoneId = ZoneId.systemDefault();
                        Date fechaActualDate = Date.from(fechaActualLocalDate.atStartOfDay(defaultZoneId).toInstant());
                        //Encontrar la HoraActual
                        Date horaActual = new Date();

                        // Verificar si la fecha y hora actual están dentro del rango de la agenda de votación
                        if (agendaVotacion.getFechaInicio().before(fechaActualDate) || agendaVotacion.getFechaFin().after(fechaActualDate)
                                   || agendaVotacion.getFechaInicio().equals(fechaActualDate)
                                && agendaVotacion.getHoraInicio().before(horaActual) && agendaVotacion.getHoraFin().after(horaActual)) {
                            // Si es así, establecemos el atributo "votante" en la sesión y redirigimos a la página de sesión de votante
                          
                            System.out.println(agendaVotacion.getFechaInicio());
                            
                            HttpSession sessionActual = request.getSession();
                            sessionActual.setAttribute("votante", votante);
                            response.sendRedirect("sesionVotante.jsp?numero=" + cedula);
                        } else {
                            // Si la fecha y hora actual están fuera del rango, redirigimos a la página de inicio de sesión con un mensaje de error
                            String mensaje = "agendaVotacionInactiva";
                            response.sendRedirect("inicioSesion.jsp?respuesta=" + mensaje);
                        }

                    }
                } else if (rol == 2) {
                    // Si el usuario tiene rol 2, lo redirigimos al menú principal
                    HttpSession sessionActual = request.getSession();
                    sessionActual.setAttribute("user", usuario);
                    response.sendRedirect("menuPrincipal.jsp");
                } else {
                    // Si el usuario tiene otro rol, redirigimos a la página de inicio de sesión con un mensaje de error
                    String mensaje = "usuarioSinAcceso";
                    response.sendRedirect("inicioSesion.jsp?respuesta=" + mensaje);
                }
            } else {
                // Si la contraseña no coincide, redirigimos a la página de inicio de sesión con un mensaje de error
                String mensaje = "conIncorrecta";
                response.sendRedirect("inicioSesion.jsp?respuesta=" + mensaje);
            }
        }
    }
%>


<%
    String mensaje2 = request.getParameter("respuesta");

    if (mensaje2
            != null) {
        switch (mensaje2) {
            case "documentoNoExiste":
%>
<script>
                    Swal.fire(
                            '¡Oops!',
                            '¡La cedula no existe!',
                            'warning'
                            );
</script>
<%
        break;
    case "usuarioSinAcceso":
%>
<script>
    Swal.fire(
            '¡Oops!',
            '¡Usted ya Voto!',
            'warning'
            );
</script>
<%
        break;
    case "conIncorrecta":
%>
<script>
    Swal.fire(
            '¡Oops!',
            '¡Contraseña Incorrecta!',
            'warning'
            );
</script>
<%
        break;
    case "agendaVotacionInactiva":
%>
<script>
    Swal.fire(
            '¡Oops!',
            '¡Votacion cerrada!',
            'warning'
            );
</script>
<%
                break;
            default:

        }
    }
%>