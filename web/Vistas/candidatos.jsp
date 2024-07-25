<%@page import="Entidades.Formacion"%>
<%@page import="Controladores.FormacionJpaController"%>
<%@page import="Entidades.AgendaVotacion"%>
<%@page import="Controladores.AgendaVotacionJpaController"%>
<%@page import="Entidades.Tipodocumento"%>
<%@page import="Controladores.TipodocumentoJpaController"%>
<%@page import="Entidades.Sede"%>
<%@page import="Controladores.SedeJpaController"%>
<%@page import="Entidades.Candidato"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.CandidatoJpaController"%>

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
        <title>Candidatos</title>
        <link rel="stylesheet" href="../css/style.css">
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

        <script>
            function obtenerDatosCandidatos(IdCandidato, TipoDocumentoFk, NumeroDocumento, Nombres, Apellidos, AgendaFk, FormacionFk, SedeFk, Fotografia, PropuestaCampana) {
                // Actualiza los campos del modal con los datos del candidato seleccionado
                $("#codigo2").val(IdCandidato);
                $("#tipoDocumento2").val(TipoDocumentoFk);
                $("#cedula2").val(NumeroDocumento);
                $("#nombres2").val(Nombres);
                $("#apellidos2").val(Apellidos);
                $("#agenda2").val(AgendaFk);
                $("#formacion2").val(FormacionFk);
                $("#sede22").val(SedeFk);
                //$("#foto2").val(Fotografia);
                $("#propuesta2").val(PropuestaCampana);

                // Realiza las solicitudes AJAX para obtener datos adicionales si es necesario
                $.ajax({
                    type: "POST",
                    url: "../Busquedas/obtenerSede.jsp",
                    data: {idsede: SedeFk},
                    dataType: "html",
                    success: function (data) {
                        $("#sede22").empty().append(data);
                    }
                });

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
                    url: "../Busquedas/obtenerAgenda.jsp",
                    data: {agendaTipos: AgendaFk},
                    dataType: "html",
                    success: function (data) {
                        $("#agenda2").empty().append(data);
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

            }
        </script>
        
        <script>
            function obtenerReportes (Nombre){
                $("#AgendaReportes").val(Nombre);
            }
            
            $.ajax({
                    type: "POST",
                    url: "../Busquedas/obtenerAgenda.jsp",
                    data: {agendaTipos: Nombre},
                    dataType: "html",
                    success: function (data) {
                        $("#AgendaReportes").empty().append(data);
                    }
                });
        </script>
        
        
    </head>
    <body data-bs-theme="light">
        <!-- MENU INICIO -->

        <div class="vertical-nav bg-white" id="sidebar">
            <div class="py-4 px-3 mb-4 menuColor text-white">
                <div class="media d-flex align-items-center ">
                    <div class="media-body text-white text-center">
                        <h4 class="m-0">
                             <%                                if (usuario != null) {
                                    out.print(usuario.getNombres());

                                }%>
                        </h4>
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

        <!-- CONTENIDO INICIO -->
        <div class="container-fluid py-2">
            <div class="page-content " id="content">

                <button id="sidebarCollapse" type="button" class=" menuColor rounded-pill shadow-sm px-4 "><i
                        class="fa fa-bars mr-2"></i><small
                        class="text-uppercase text-white font-weight-bold">Menu</small>
                </button>

                    <img class="float-end" src="../img/inicioSesion_sena.jpg" alt="" height="80px" width="80px">

                <div class="container-fluid">

                    <p class="letra text-center "> Candidatos 2024  </p> 
                    <div class="container">
                        <div class="row">
                            <div class="col-6">
                                <form action="<%=request.getContextPath()%>/ServletAgenda" method="post" >
                                    <div class="input-group mb-2">
                                        <div class="input-group-text col-6"><b>Guardar un registro nuevo:</b></div>
                                        <button id="editarBtnFormaciones" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#formularioModal"><b>Formulario</b></button>
                                    </div>
                                </form>
                            </div>
                            <div class="col-6">
                                <div class="input-group mb-2">
                                    <div class="input-group-text col-4"><b>Buscar:</b></div>
                                    <input type="text" class="form-control" id="filtro4">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row ">
                        <!-- TABLA CANDIDATOS INICIO -->
                        <div class="col-12 mb-5 " data-aos="zoom-in"  data-aos-duration="500">
                            <div
                                class="container-fluid tablaCandidatos table-responsive table-wrapper-scroll-y my-custom-scrollbar text-center">
                                <table id="tablaCandidatos" class="table align-middle table-sm  table-hover table-bordered border-primary  mb-0  ">
                                    <thead class=" border-primary table-success">
                                        <tr>
                                            <th scope="col">Codigo</th>
                                            <th scope="col">Tipo de Documento</th>
                                            <th scope="col">Cedula</th>
                                            <th scope="col">Nombres</th>
                                            <th scope="col">Apellidos</th>
                                            <th scope="col">Agenda</th>
                                            <th scope="col">Formacion</th>
                                            <th scope="col">Sede</th>
                                            <th scope="col">Fotografia </th>
                                            <th scope="col">Propuesta </th>
                                            <th scope="col">Opcciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                            CandidatoJpaController controCandidatos = new CandidatoJpaController();
                                            List<Candidato> can = controCandidatos.findCandidatoEntities();
                                            if (can.isEmpty()) {


                                        %>
                                        <tr>
                                            <td colspan="12">No se encontraron condidatos en la base de datos.</td>
                                        </tr>
                                        <% } else {
                                            for (Candidato candidatos : can) {

                                        %>
                                        <tr>
                                            <td> <%= candidatos.getIdCandidato()%> </td>
                                            <td> <%= candidatos.getTipoDocumentoFk()%> </td>
                                            <td> <%= candidatos.getNumeroDocumento()%> </td>
                                            <td> <%= candidatos.getNombres()%> </td>
                                            <td> <%= candidatos.getApellidos()%> </td>
                                            <td> <%= candidatos.getAgendaFk()%> </td>
                                            <td> <%= candidatos.getFormacionFk()%> </td>
                                            <td> <%= candidatos.getSedeFk()%> </td>
                                            <td> 
                                                <img src="<%= candidatos.getFotografia()%>" width="120px" height="120px">
                                            </td>
                                            <td> <%= candidatos.getPropuestaCampana()%> </td>

                                            <td> 
                                                <button type="button" class="btn btn-outline-danger  btn-sm" data-bs-toggle="modal" data-bs-target="#formularioModal2"

                                                        onclick="obtenerDatosCandidatos('<%= candidatos.getIdCandidato()%>', '<%= candidatos.getTipoDocumentoFk().getIdTipoDocumento()%>', '<%= candidatos.getNumeroDocumento()%>',
                                                                        '<%= candidatos.getNombres()%>', '<%= candidatos.getApellidos()%>', '<%= candidatos.getAgendaFk().getIdAgenda()%>',
                                                                        '<%= candidatos.getFormacionFk().getIdFormacion()%>', '<%= candidatos.getSedeFk().getIdSede()%>', '<%= candidatos.getFotografia() %>',
                                                                        '<%= candidatos.getPropuestaCampana()%>')">
                                                    Opciones
                                                </button>

                                            </td>
                                        </tr>
                                        <%
                                                }
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
            </div>    <!-- CONTENIDO FINAL -->
        </div>

        <!-- MODAL GUARDADO INICIO -->
        <div class="modal fade" id="formularioModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <!-- FORMULARIO AGENDA INICIAL -->

                    <h2 class="pt-4 pb-2 text-center">Registro de Candidatos</h2>
                    <div class="modal-body">
                        <form action="<%=request.getContextPath()%>/ServletCandidatos" method="post" class="row g-2" enctype="multipart/form-data">
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Codigo:</b></div>
                                    <input type="number" class="form-control col-6 " id="codigo" name="codigo" required min="1" max="999999999999">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Tipo de Documento:</b></div>
                                    <select name="tipoDocumento" id="tipoDocumento"
                                            class="from-selec-sm col-6" required min="1">
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
                                    <div class="input-group-text col-6"><b>Nombres:</b></div>
                                    <input type="text" class="form-control" id="nombres" name="nombres" required min="1" maxlength="30">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Apellidos:</b></div>
                                    <input type="text" class="form-control" id="apellidos" name="apellidos" required min="1" maxlength="30">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Agenda de votacion:</b></div>
                                    <select name="agenda" id="agenda"
                                            class="from-selec col-6" required min="1">
                                          <option value="" disabled selected hidden>-- Elija --</option>
                                        <%
                                            AgendaVotacionJpaController contrAgen = new AgendaVotacionJpaController();
                                            List listaA = contrAgen.findAgendaVotacionEntities();

                                            for (int i = 0; i < listaA.size(); i++) {
                                                AgendaVotacion agen = (AgendaVotacion) listaA.get(i);
                                                out.print("<option value='" + agen.getIdAgenda() + "'>");
                                                out.print(agen.getNombre());
                                                out.print("</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Formacion:</b></div>
                                    <select name="formacion" id="formacion"
                                            class="from-selec col-6" required min="1">
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
                                            SedeJpaController controlaroSede = new SedeJpaController();
                                            List listaSede = controlaroSede.findSedeEntities();

                                            for (int i = 0; i < listaSede.size(); i++) {
                                                Sede de = (Sede) listaSede.get(i);
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
                                    <div class="input-group-text col-6"><b>Fotografia:</b></div>
                                    <input type="file" class="form-control" id="foto" name="foto" accept="image/*" required min="1">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class=" input-group-sm">
                                    <div class="input-group-text col-12"><b>Propuesta de Campaña:</b></div>
                                    <textarea class="form-control mt-2" id="propuesta" name="propuesta" rows="3" required min="1" 
                                              placeholder="Maximo 200 Caracteres" maxlength="200" ></textarea>
                                </div>
                            </div>
                            <div class="col-12 text-center "><!--bottones-->
                                <button type="submit" class="btn botones btn-primary "
                                        name="action" value="Guardar"><b>Guardar</b></button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" >Cerrar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- MODAL GUARDADO FINAL -->   

        <!-- MODAL OPCCIONES INICIO -->
        <div class="modal fade" id="formularioModal2" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <!-- FORMULARIO AGENDA INICIAL -->

                    <h2 class="pt-4 pb-2 text-center">Registro de Candidatos</h2>
                    <div class="modal-body">
                        <form action="<%=request.getContextPath()%>/ServletCandidatos" method="post" class="row g-2" enctype="multipart/form-data">
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Codigo:</b></div>
                                    <input type="number" class="form-control col-6 " id="codigo2" name="codigoEliminar" required min="1" readonly>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Tipo de Documento:</b></div>
                                    <select name="tipoDocumento20" id="tipoDocumento2"
                                            class="from-selec-sm col-6">
                                    </select>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Cedula:</b></div>
                                    <input type="number" class="form-control" id="cedula2" name="cedula20" required min="1" max="999999999999">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Nombres:</b></div>
                                    <input type="text" class="form-control" id="nombres2" name="nombres20" required min="1" maxlength="30">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Apellidos:</b></div>
                                    <input type="text" class="form-control" id="apellidos2" name="apellidos20" required min="1" maxlength="30">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Agenda de votacion:</b></div>
                                    <select name="agenda20" id="agenda2"
                                            class="from-selec col-6" required>
                                        
                                    </select>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Formacion:</b></div>
                                    <select name="formacion20" id="formacion2"
                                            class="from-selec col-6" required>
                                       
                                    </select>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Sede:</b></div>
                                    <select name="sede22" id="sede22"
                                            class="from-selec col-6">

                                    </select>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Fotografia:</b></div>
                                    <input type="file" class="form-control" id="foto2" name="foto20" accept="image/*">
                                    
                                </div>
                            </div>
                            <div class="col-12">
                                <div class=" input-group-sm">
                                    <div class="input-group-text col-12"><b>Propuesta de Campaña:</b></div>
                                    <textarea class="form-control mt-2" id="propuesta2" name="propuesta20" rows="3" required min="1"
                                               placeholder="Maximo 200 Caracteres" maxlength="200"></textarea>
                                </div>
                            </div>
                            <div class="col-12 text-center "><!--bottones-->
                                <button type="submit" class="btn botones btn-primary "
                                        name="action" value="Editar"><b>Actualizar</b></button>
                                <button type="submit" class="btn btn-primary" name="action" value="Eliminar"><b>Eliminar</b></button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" " >Cerrar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>                             
        <!-- MODAL OPCIONES FINAL -->    
        
       
       

        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script>AOS.init();</script>
        <script src="../js/menu.js"></script>
        <script src="../js/modoOscuro.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </body>
</html>

<%
    String mensaje = request.getParameter("respuesta");

    if (mensaje != null) {

        switch (mensaje) {
            case "ElcodigoExiste":
%>
<script>
                                                            Swal.fire(
                                                                    '¡Oops!',
                                                                    '¡El codigo ya existe en la base de datos!',
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
            '¡El candidato ha sido guardado correctamente.!',
            'success'
            );
</script>
<%
        break;
    case "ErrorGuardar":
%>
<script>
    Swal.fire(
            '¡Oops!',
            '¡Error al Guardar!',
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
            '¡El candidato ha sido eliminado!',
            'success'
            );
</script>
<%
        break;
    case "candidatoEditado":
%>
<script>
    Swal.fire(
            '¡Éxito!',
            '¡El candidato ha sido Actualizado!',
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
            '¡Error al Editar!',
            'warning'
            );
</script>
<% break;
            default:
                throw new AssertionError();
        }
    }

%>

<script>
     const filtroInput4 = document.getElementById("filtro4");
    const filasCandidatos = document.querySelectorAll("#tablaCandidatos tbody tr");
    filtroInput4.addEventListener("input", function () {
        const filtro = filtroInput4.value.trim().toLowerCase();

        filasCandidatos.forEach(function (fila) {
            const textoFila = fila.textContent.toLowerCase();
            if (textoFila.includes(filtro)) {
                fila.style.display = "";
            } else {
                fila.style.display = "none";
            }
        });
    });
    
</script>