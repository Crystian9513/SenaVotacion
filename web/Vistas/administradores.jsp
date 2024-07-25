<%@page import="Entidades.Administrador"%>
<%@page import="Controladores.AdministradorJpaController"%>
<%@page import="Controladores.UsuarioJpaController"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.FormacionJpaController"%>
<%@page import="Entidades.Formacion"%>
<%@page import="Entidades.Sede"%>
<%@page import="Controladores.SedeJpaController"%>
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
        <title>Administradores</title>
        <link rel="stylesheet" href="../css/style.css">
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">


    </head>

    <script >
        function obtenerDatosAdministradores(Codigo, Nombres, Apellidos, Cedula, Clave) {
            // Actualiza los campos del modal con los datos de la sede seleccionada
            $("#codigoModal2").val(Codigo);
            $("#nombreModal2").val(Nombres);
            $("#apellidoModal2").val(Apellidos);
            $("#cedulaModal2").val(Cedula);
            $("#claveModal2").val(Clave);

        }
    </script>



    <body data-bs-theme="light">
        <!-- MENU INICIO -->


        <div class="vertical-nav bg-white " id="sidebar" >
            <div class="py-4 px-3 mb-4 menuColor text-white">
                <div class="media d-flex align-items-center ">
                    <div class="media-body text-white text-center">
                        <h4 class="m-0">

                             <% if (usuario != null) {
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
                <div class='dashboard-nav-dropdown'><a href="#"
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

        <!-- CONTENIDO INICIAL -->
        <div class="container-fluid  py-2" >
            <div  class="page-content data-bs-theme="light" contenido py-3" id="content" >

                <button id="sidebarCollapse" type="button" class="menuColor rounded-pill shadow-sm px-4"><i
                        class="fa fa-bars mr-2"></i><small class="text-uppercase text-white font-weight-bold">Menu</small>
                </button>

                    <img class="float-end" src="../img/inicioSesion_sena.jpg" alt="" height="80px" width="80px">
               
                <div class="container-fluid data-bs-theme="light" contenido " >
                    <p class="letra text-center"> Administradores </p>
                    <div class="row">
                        <div class="container">
                            <div class="row">
                                <div class="col-6">
                                    <form action="<%=request.getContextPath()%>/ServletSedes" method="post" class="py-3">
                                        <div class="input-group mb-2">
                                            <div class="input-group-text col-6"><b>Guardar un registro nuevo:</b></div>
                                            <button id="editarBtnFormaciones" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#formularioModal"><b>Formulario</b></button>
                                        </div>
                                    </form>
                                </div>

                                <div class="col-6">
                                    <form action="<%=request.getContextPath()%>/ServletSedes" method="post" class="py-3">
                                        <div class="input-group mb-2">
                                            <div class="input-group-text col-4"><b>Buscar:</b></div>
                                            <input type="text" class="form-control" id="filtro1">
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <!-- TABLA SEDE INICIO -->
                        <div class="col-md-12 pb-3"  data-aos="zoom-in"  data-aos-duration="500">
                            <div
                                class="container-fluid  tablaSede table-responsive  table-wrapper-scroll-y my-custom-scrollbar text-center">

                                <table id="tablaSede" class="table align-middle tabla table-sm table-hover table-bordered border-primary mb-0">
                                    <thead class="border-primary table-success">
                                        <tr>
                                            <th scope="col">Codigo</th>
                                            <th scope="col">Nombre</th>
                                            <th scope="col">Apellido</th>
                                            <th scope="col">Cedula</th>
                                            <th scope="col">Clave</th>
                                            <th scope="col">Opciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                            AdministradorJpaController usuarioControlador = new AdministradorJpaController();
                                            List<Administrador> adm = usuarioControlador.findAdministradorEntities();

                                            if (adm.isEmpty()) {
                                        %>
                                        <tr>
                                            <td colspan="6">No se encontraron Usuarios en la base de datos.</td>
                                        </tr>
                                        <%
                                        } else {
                                            for (Administrador administrador : adm) {
                                        %>

                                        <tr>
                                            <td><%= administrador.getCodigo()%></td>
                                            <td><%= administrador.getNombres()%></td>
                                            <td><%= administrador.getApellidos()%></td>
                                            <td><%= administrador.getCedula()%></td>
                                            <td><%= administrador.getClave()%></td>

                                            <td>
                                                <button type="button" class="btn  btn-outline-danger  btn-sm " data-bs-toggle="modal" data-bs-target="#formulario2Modal" 
                                                        onclick="obtenerDatosAdministradores('<%= administrador.getCodigo()%>', '<%= administrador.getNombres()%>', '<%= administrador.getApellidos()%>'
                                                                        , '<%= administrador.getCedula()%>', '<%= administrador.getClave()%>')">
                                                    Opciones 
                                                </button>
                                            </td>
                                        </tr>

                                        <% }
                                            }
                                        %>
                                    </tbody>
                                </table>
                            </div>

                            <!-- TABLA SEDE FINAL -->
                        </div>
                    </div>
                </div>


                <!-- PIED DE PAGINA INICIO -->
                <footer class="bg-dark">
                    <div class="container text-center">
                        <div class="row align-items-center">
                            <div class="col-md-4 mt-3">
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
                            <div class="col-md-4 mt-3">
                                <hr class="border bg-light opacity-100">
                            </div>
                        </div>
                        <div class="container-fluid text-white">
                            <h3 class="pt-2">Elecciones Sena</h3>
                            <p class="pb-3">Copyright © 2023 Crystian Peralta, Desarrollador.</p>
                        </div>
                    </div>
                </footer>
                <!-- PIED DE PAGINA FINAL -->
            </div>
        </div>
        <!-- CONTENIDO FINAL -->

        <!-- MODALES DE SEDES GUARDAR INICIO -->
        <div class="modal fade" id="formularioModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <form action="<%=request.getContextPath()%>/ServletAdministradores" method="post" class="row g-2 "
                              
                              >
                            <h2 class="pt-5 pb-4 text-center">Registrar Administrador</h2>

                            <div class="col-12">
                                <div class="input-group">
                                    <div class="input-group-text col-5"><b>Nombres:</b></div>

                                    <input type="text" class="form-control" id="nombreModal" name="nombre" required min="1">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group">
                                    <div class="input-group-text col-5"><b>Apellido:</b></div>

                                    <input type="text" class="form-control" id="apellidoModal" name="apellido" required min="1">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group">
                                    <div class="input-group-text col-5"><b>Cedula:</b></div>

                                    <input type="number" class="form-control" id="cedulaModal" name="cedula" required min="1" max="999999999999">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group">
                                    <div class="input-group-text col-5"><b>Clave:</b></div>

                                    <input type="text" class="form-control" id="claveModal" name="clave" required min="1" maxlength="20">
                                </div>
                            </div>
                            <div class="col-12 text-center py-5 pt-5"><!-- bottones -->
                                <button type="submit" class="btn botones btn-primary px-4"
                                        value="Guardar" name="action"><b>Guardar</b></button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" >Cerrar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- MODALES DE SEDES GUARDAR FINAL -->

        <!-- MODALES DE SEDES OPCIONES INICIO -->
        <div class="modal fade" id="formulario2Modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <form action="<%=request.getContextPath()%>/ServletAdministradores" method="post" class="row g-2 "
                              onsubmit="return validarCamposVacios();"
                              >
                            <h2 class="pt-5 pb-4 text-center">Administrador</h2>
                            <div class="col-12">
                                <div class="input-group">
                                    <div class="input-group-text col-5"><b>Codigo</b></div>

                                    <input type="text" class="form-control" id="codigoModal2" name="codigo2" required min="1" readonly>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group">
                                    <div class="input-group-text col-5"><b>Nombre:</b></div>

                                    <input type="text" class="form-control" id="nombreModal2" name="nombre2" required min="1">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group">
                                    <div class="input-group-text col-5"><b>Apellido:</b></div>

                                    <input type="text" class="form-control" id="apellidoModal2" name="apellido2" required min="1">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group">
                                    <div class="input-group-text col-5"><b>Cedula:</b></div>

                                    <input type="number" class="form-control" id="cedulaModal2" name="cedula2" required min="1" max="999999999999">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group">
                                    <div class="input-group-text col-5"><b>Clave:</b></div>

                                    <input type="text" class="form-control" id="claveModal2" name="clave2" required min="1" maxlength="20">
                                </div>
                            </div>
                            <div class="col-12 text-center py-5 pt-5"><!-- bottones -->
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
        <!-- MODALES DE SEDES OPCIONES FINAL -->


        <script src="../js/modoOscuro.js"></script>
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>AOS.init();</script>
        <script src="../js/menu.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </body>
</html>



<!-- ALERTAS INICIO --> 

<%
    String mensaje = request.getParameter("respuesta");

    if (mensaje != null) {

        switch (mensaje) {
            case "Existe":
%>
<script>
                                  Swal.fire(
                                          '¡Oops!',
                                          'El codigo ya existe en la base de datos',
                                          'warning'
                                          );</script>

<%
        break;
    case "Guardado":
%>
<script>
    Swal.fire(
            '¡Éxito!',
            '¡La sede se ha sido guardada!',
            'success'
            );
</script>
<%
        break;
    case "AdministradorEliminado":
%>
<script>
    Swal.fire(
            '¡Éxito!',
            '¡Administrador eliminado!',
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
            '¡Administrador Actualizado!',
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
            '¡Error Al Guardar!',
            'warning'
            );
</script>
<%
        break;
    case "ErrorClave":
%>
<script>
    Swal.fire(
            '¡Oops!',
            '¡Error la clave no puede se igual a la anterior!',
            'warning'
            );
</script>
<%
        break;
    case "ErrorEditar":
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
               
        }
    }

%>

<script>
    const filtroInput = document.getElementById("filtro1");
    const filas = document.querySelectorAll("#tablaSede tbody tr");
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
