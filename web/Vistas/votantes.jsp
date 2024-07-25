<%@page import="Entidades.Candidato"%>
<%@page import="Controladores.CandidatoJpaController"%>
<%@page import="Entidades.AgendaVotacion"%>
<%@page import="Controladores.AgendaVotacionJpaController"%>
<%@page import="Entidades.Formacion"%>
<%@page import="Controladores.FormacionJpaController"%>
<%@page import="Entidades.Tipodocumento"%>
<%@page import="Controladores.TipodocumentoJpaController"%>
<%@page import="Entidades.Sede"%>
<%@page import="Controladores.SedeJpaController"%>
<%@page import="Entidades.Votante"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.VotanteJpaController"%>
<%@page import="Entidades.Usuario"%>
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
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Votantes</title>
        <link rel="stylesheet" href="../css/style.css">
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

        <script>
            // Función para obtener los datos de la sede y mostrarlos en el modal
            function obtenerDatosVotantes(TipoDocumentoFk, IdVotante, NombresCompleto, FormacionFk, SedeFk, AgendaFk)
            {
                $('#tipoDocumento2').val(TipoDocumentoFk);
                $('#cedula2').val(IdVotante);
                $('#nombreCompleto2').val(NombresCompleto);
                $('#formacion2').val(FormacionFk);
                $('#sede2').val(SedeFk);
                $('#agenda2').val(AgendaFk);

                // Realiza las solicitudes AJAX para obtener datos adicionales si es necesario
                $.ajax({
                    type: "POST",
                    url: "../Busquedas/obtenerTipoDocumento.jsp",
                    data: {tipoDocumento: TipoDocumentoFk},
                    dataType: "html",
                    success: function (data) {
                        $("#tipoDocumento2").empty().append(data);
                    }
                });

                $.ajax({
                    type: "POST",
                    url: "../Busquedas/obtenerSede.jsp",
                    data: {idsede: SedeFk},
                    dataType: "html",
                    success: function (data) {
                        $("#sede2").empty().append(data);
                    }
                });

                $.ajax({
                    type: "POST",
                    url: "../Busquedas/obtenerFormaciones.jsp",
                    data: {formacionesTipos: FormacionFk},
                    dataType: "html",
                    success: function (data) {
                        $("#formacion2").empty().append(data);
                    }
                });

                $.ajax({
                    type: "POST",
                    url: "../Busquedas/obtenerAgenda.jsp",
                    data: {agendaTipos: AgendaFk},
                    dataType: "html",
                    success: function (data) {
                        $("#agenda2").empty().append(data);
                    }
                });
            }

        </script>  

    </head>
    <body data-bs-theme="light">

        <!-- MENU INICIO -->

        <div class="vertical-nav bg-white" id="sidebar">
            <div class="py-4 px-3 mb-4 menuColor text-white">
                <div class="media d-flex align-items-center ">
                    <div class="media-body text-white text-center">
                        <h4 class="m-0"><%                            if (usuario != null) {
                                out.print(usuario.getNombres());

                            }%></h4>
                        <p class="font-weight-light mb-0">Administrador</p>
                    </div>
                </div>
            </div>
            <button class="btn rounded-fill" onclick="cambiarTema()"><i id="dl-icon" class="bi bi-moon-fill"></i>Modo Oscuro</button>
            <ul class="nav flex-column bg-white py-3 mb-0">
                 <li class="nav-item">
                    <form action="<%=request.getContextPath()%>/ServletVerificarAgenda" target="_blank" method="post" class="row g-2 ">
                        <button type="submit" class="  px-4 ov-btn-slide-left"
                                value="Entrar" name="action"><b>Votacion en Vivo</b>
                        </button>
                    </form>
                </li>
                <li class="nav-item">
                    <a href="agendaDeVotacion.jsp" class="nav-link text-dark font-italic bg-light">
                        <img src="../img/icon_calendar.png" alt="">
                        Agendar Votacion
                    </a>
                </li>
                <div class='dashboard-nav-dropdown'>
                    <a href="candidatos.jsp" class="dashboard-nav-item dashboard-nav-dropdown-toggle text-dark"
                       style="text-decoration: none;">
                        <img src="../img/icon_candidato.png" alt="">
                        Candidatos
                    </a>
                </div>

                <div class='dashboard-nav-dropdown'>
                    <a href="votantes.jsp"
                       class="dashboard-nav-item dashboard-nav-dropdown-toggle text-dark" style="text-decoration: none;">
                        <img src="../img/icon_vota.png" alt="">
                        Votantes
                    </a>

                </div>
                <div class='dashboard-nav-dropdown'>
                    <a href="sedesFormaciones.jsp"
                       class="dashboard-nav-item dashboard-nav-dropdown-toggle text-dark" style="text-decoration: none;">
                        <img src="../img/icon_sede.png" alt="">
                        Sedes y Formaciones Sena
                    </a>
                </div>
                <div class='dashboard-nav-dropdown'><a href="administradores.jsp"
                                                       class="dashboard-nav-item dashboard-nav-dropdown-toggle text-dark" style="text-decoration: none;">
                        <img src="../img/icon_admin.png" alt="">
                        Administradores </a>
                </div>
                <div class='dashboard-nav-dropdown'><a href="reportes.jsp"
                                                       class="dashboard-nav-item dashboard-nav-dropdown-toggle text-dark" style="text-decoration: none;">
                        <img src="../img/icon_reportes.png" alt="">
                        Reportes</a>
                </div>
                <li class="nav-item">
                    <a href="menuPrincipal.jsp" class="nav-link text-dark font-italic">
                        <img src="../img/icon_menuPrincipal.png" alt="">
                        Menu Principal
                    </a>
                </li>
                <li class="nav-item">
                    <a href="cerrarSisionUsuario.jsp" class="nav-link text-dark font-italic">
                        <img src="../img/icon_salir.png" alt="">
                        Salir
                    </a>
                </li>

            </ul>
        </div>
        <!-- MENU FINAL -->

        <div class="container-fluid  py-2">
            <div class="page-content contenido " id="content">

                <button id="sidebarCollapse" type="button"
                        class=" menuColor rounded-pill shadow-sm px-4 "><i class="fa fa-bars mr-2"></i><small
                        class="text-uppercase text-white font-weight-bold">Menu</small>
                </button>

                    <img class="float-end" src="../img/inicioSesion_sena.jpg" alt="" height="80px" width="80px">

                <div class="container-fluid ">
                    <p class="letra text-center ">Votantes</p> 
                    <div class="row">
                        <div class="container">
                            <div class="row">
                                <div class="col-6">
                                    <form action="<%=request.getContextPath()%>/ServletSedes" method="post" class="">
                                        <div class="input-group mb-2">
                                            <div class="input-group-text col-6"><b>Guardar un registro nuevo:</b></div>
                                            <button id="editarBtnFormaciones" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#formularioModal"><b>Formulario</b></button>
                                        </div>
                                    </form>
                                </div>

                                <div class="col-6">
                                    <form action="<%=request.getContextPath()%>/ServletSedes" method="post" class="">
                                        <div class="input-group mb-2">
                                            <div class="input-group-text col-4"><b>Buscar:</b></div>
                                            <input type="text" class="form-control" id="filtro1">
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 mb-5" data-aos="zoom-in"  data-aos-duration="500">

                            <!-- TABLA CANDIDATOS INICIO -->
                            <div
                                class="container-fluid tablaVotantes table-responsive table-wrapper-scroll-y my-custom-scrollbar text-center">

                                <table id="tablaVotantes" class="table table-sm align-middle table-hover table-bordered border-primary  mb-0  ">
                                    <thead class=" border-primary table-success">
                                        <tr>

                                            <th scope="col">Tipo de Documento</th>
                                            <th scope="col">Cedula</th>
                                            <th scope="col">Nombre Completo</th>
                                            <th scope="col">Formacion</th>
                                            <th scope="col">Sede</th>
                                            <th scope="col">Agenda</th>
                                            <th scope="col">Opciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                            VotanteJpaController controVotante = new VotanteJpaController();
                                            List<Votante> votos = controVotante.findVotanteEntities();

                                            if (votos.isEmpty()) {


                                        %>
                                        <tr>
                                            <td colspan="8">No se encontraron Votantes en la base de datos.</td>
                                        </tr>
                                        <% } else {
                                            for (Votante voto : votos) {


                                        %>
                                        <tr>
                                            <td><%= voto.getTipoDocumentoFk()%></td>
                                            <td><%= voto.getIdVotante()%></td>
                                            <td><%= voto.getNombresCompleto()%></td>
                                            <td><%= voto.getFormacionFk()%></td>
                                            <td><%= voto.getSedeFk()%></td>
                                            <td><%= voto.getAgendaFk()%></td>

                                            <td>
                                                <button type="button" class="btn btn-outline-danger  btn-sm" name="Opciones" value="action" data-bs-toggle="modal" data-bs-target="#formulario2Modal"
                                                        onclick="obtenerDatosVotantes(<%= voto.getTipoDocumentoFk().getIdTipoDocumento()%>, '<%= voto.getIdVotante()%>', '<%= voto.getNombresCompleto()%>',
                                                                        '<%= voto.getFormacionFk().getIdFormacion()%>', '<%= voto.getSedeFk().getIdSede()%>', '<%= voto.getAgendaFk().getIdAgenda()%>')">
                                                    Opciones
                                                </button>
                                            </td>
                                        </tr>
                                        <%                                                }
                                            }
                                        %>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <!-- PIE DE PAGINA INCIO-->
                        <footer class="bg-dark">
                            <div class="container text-center">
                                <div class="row align-items-center">
                                    <div class="col-md-4  mt-3">
                                        <hr class="border bg-light opacity-100">
                                    </div>
                                    <div class="col-md-4">
                                        <div id="" class="templatemo-line-header mt-3" style="margin-top: 0px;">
                                            <div class="text-center">
                                                <a href="http://"><img src="../img/icon_facebook.png" alt=""></a>
                                                <a href="http://"><img src="../img/icon_instagram.png" alt=""></a>
                                                <a href="http://"><img src="../img/icon_x.png" alt=""></a>
                                                <a href="http://"><img src="../img/icon_youtube.png" alt=""></a>
                                                <a href="http://"><img src="../img/icon_tiktok.png" alt=""></a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4  mt-3">
                                        <hr class="border bg-light opacity-100">
                                    </div>
                                </div>
                                <div class="container-fluid text-white">
                                    <h3 class="pt-2">Elecciones Sena</h3>
                                    <p class="pb-3">Copyright Â© 2023 Crystian Peralta, Desarrollador.</p>
                                </div>
                            </div>
                        </footer>
                        <!-- PIE DE PAGINA FINAL-->
                    </div>
                </div>
            </div>
        </div>
        <!-- MODAL GUARDAR INICIO-->
        <div class="modal fade" id="formularioModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">

                    <div class="modal-body">
                        <form action="<%=request.getContextPath()%>/ServletVotantes" method="post" class="row g-2 " >

                            <h2 class="pt-5 pb-4 text-center">Registrar Votante</h2>

                            <div class="col-12">

                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Tipo de Documento:</b></div>
                                    <select name="tipoDocumento" id="tipoDocumento"
                                            class="from-selec-sm col-6" required>
                                          <option value="" disabled selected hidden>-- Elija --</option>
                                        <%
                                            TipodocumentoJpaController se = new TipodocumentoJpaController();
                                            List lista = se.findTipodocumentoEntities();

                                            for (int i = 0; i < lista.size(); i++) {
                                                Tipodocumento tipo = (Tipodocumento) lista.get(i);
                                                out.print("<option value='" + tipo.getIdTipoDocumento() + "'>");
                                                out.print(tipo.getNombre());
                                                out.print("</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                            </div>
                            <div class="col-12">

                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Cedula:</b></div>
                                    <input type="number" class="form-control" id="cedula" name="cedula" required min="1" max="999999999999">
                                </div>
                            </div>
                            <div class="col-12">

                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Nombre Completo:</b></div>
                                    <input type="text" class="form-control" id="nombreCompleto" name="nombreCompleto" required min="1" maxlength="45">
                                </div>
                            </div>
                            <div class="col-12">

                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Formacion:</b></div>
                                    <select name="formacion" id="formacion"
                                            class="from-selec col-6 " required min="1">
                                           <option value="" disabled selected hidden>-- Elija --</option>
                                        <%
                                            FormacionJpaController controF = new FormacionJpaController();
                                            List listaC = controF.findFormacionEntities();

                                            for (int i = 0; i < listaC.size(); i++) {
                                                Formacion form = (Formacion) listaC.get(i);
                                                out.print("<option value='" + form.getIdFormacion() + "'>");
                                                out.print(form.getNombre());
                                                out.print("</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                            </div>
                            <div class="col-12">

                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Sede:</b></div>
                                    <select name="sede" id="sede"
                                            class="from-selec col-6" required min="1">
                                          <option value="" disabled selected hidden>-- Elija --</option>
                                        <%
                                            SedeJpaController ses = new SedeJpaController();
                                            List lista2 = ses.findSedeEntities();

                                            for (int i = 0; i < lista2.size(); i++) {
                                                Sede de = (Sede) lista2.get(i);
                                                out.print("<option value='" + de.getIdSede() + "'>");
                                                out.print(de.getNombre());
                                                out.print("</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                            </div>
                            <div class="col-12">

                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Agenda</b></div>
                                    <select name="agenda" id="agenda"
                                            class="from-selec col-6" required min="1">
                                          <option value="" disabled selected hidden>-- Elija --</option>
                                        <%
                                            AgendaVotacionJpaController agen = new AgendaVotacionJpaController();
                                            List lista3 = agen.findAgendaVotacionEntities();

                                            for (int i = 0; i < lista3.size(); i++) {
                                                AgendaVotacion des = (AgendaVotacion) lista3.get(i);
                                                out.print("<option value='" + des.getIdAgenda() + "'>");
                                                out.print(des.getNombre());
                                                out.print("</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                            </div>

                            <div class="col-12 text-center py-5 pt-5"><!--bottones-->
                                <button type="submit" class="btn botones btn-primary px-4 "
                                        name="action" value="Guardar"><b>Guardar</b></button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            </div>
                        </form>
                    </div>

                </div>
            </div>
        </div>                           
        <!-- MODAL GUARDAR FINAL-->

        <!-- MODAL INICIO-->
        <div class="modal fade" id="formulario2Modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">

                    <div class="modal-body">
                        <form action="<%=request.getContextPath()%>/ServletVotantes" method="post" class="row g-2 " >

                            <h2 class="pt-5 pb-4 text-center">Votante</h2>

                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Tipo de Documento:</b></div>
                                    <select name="tipoDocumento2" id="tipoDocumento2"
                                            class="from-selec-sm col-6">
                                    </select>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Cedula:</b></div>
                                    <input type="number" class="form-control" id="cedula2" name="cedula2" required min="1" max="999999999999" readonly>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Nombre Completo:</b></div>
                                    <input type="text" class="form-control" id="nombreCompleto2" name="nombreCompleto2" required min="1" maxlength="45">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Formacion:</b></div>
                                    <select name="formacion2" id="formacion2"
                                            class="from-selec col-6">
                                    </select>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Sede:</b></div>
                                    <select name="sede2" id="sede2"
                                            class="from-selec col-6">
                                    </select>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Agenda</b></div>
                                    <select name="agenda2" id="agenda2"
                                            class="from-selec col-6">
                                    </select>
                                </div>
                            </div>

                            <div class="col-12 text-center py-5 pt-5"><!--bottones-->
                                <button type="submit" class="btn botones btn-primary px-4"
                                        name="action" value="Editar"><b>Actualizar</b></button>
                                <button type="submit" class="btn btn-primary" name="action" value="Eliminar"><b>Eliminar</b></button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            </div>
                        </form>
                    </div>

                </div>
            </div>
        </div>                           
        <!-- MODAL FINAL-->

        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script>AOS.init();</script>
        <script src="../js/menu.js"></script>
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
        <script src="../js/modoOscuro.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </body>
</html>


<%
    String mensaje = request.getParameter("respuesta");

    if (mensaje != null) {

        switch (mensaje) {
            case "Existe":
%>
<script>
                                                            Swal.fire(
                                                                    '¡Oops!',
                                                                    '¡La cedula ya existe en la base de datos!',
                                                                    'warning'
                                                                    );
</script>

<%
        break;
    case "Guardado":
%>
<script>
    Swal.fire(
            '¡Éxito!',
            '¡El votante ha sido guardado!',
            'success'
            );
</script>
<%
        break;
    case "errorguardar":
%>
<script>
    Swal.fire(
            '¡Oops!',
            '¡Error al guardar!',
            'warning'
            );
</script>
<%
        break;
    case "Eliminado":
%>
<script>
    Swal.fire(
            '¡Éxito!',
            '¡El votante ha sido eliminado!',
            'success'
            );
</script>
<%
        break;
    case "Editado":
%>
<script>
    Swal.fire(
            '¡Éxito!',
            '¡El votante ha sido Actualizado!',
            'success'
            );
</script>
<%
        break;
    case "erroreditar":
%>
<script>
    Swal.fire(
            '¡Oops!',
            '¡Error al editar!',
            'warning'
            );
</script>
<%break;
            default:
                throw new AssertionError();
        }
    }

%>


<script>
    const filtroInput = document.getElementById("filtro1");
    const filas = document.querySelectorAll("#tablaVotantes tbody tr");
    filtroInput.addEventListener("input", function () {
        const filtro = filtroInput.value.trim().toLowerCase();
        filas.forEach(function (fila) {
            const textoFila = fila.textContent.toLowerCase();
            if (textoFila.includes(filtro)) {
                fila.style.display = "";
            } else {
                fila.style.display = "none";
            }
        });
    });</script>
