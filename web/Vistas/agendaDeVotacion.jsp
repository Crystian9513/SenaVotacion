<%@page import="java.text.SimpleDateFormat"%>
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

<%@page import="Entidades.AgendaVotacion"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.AgendaVotacionJpaController"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Agenda de Votacion</title>
        <link rel="stylesheet" href="../css/style.css">
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">


        <script>
            // Función para obtener los datos de la sede y mostrarlos en el modal
            function obtenerDatosAgenda(IdAgenda, Nombre, FechaInicio, FechaFin, HoraInicio, HoraFin, Descrpcion) {
                // Actualiza los campos del modal con los datos de la sede seleccionada
                document.getElementById('id2').value = IdAgenda;
                document.getElementById('nombre2').value = Nombre;
                document.getElementById('fechaInicio2').value = FechaInicio;
                document.getElementById('fechaFinal2').value = FechaFin;
                document.getElementById('horaInicio2').value = HoraInicio;
                document.getElementById('horaFinal2').value = HoraFin;
                document.getElementById('Descripcion2').value = Descrpcion;
            }
        </script>


    </head>
    <body data-bs-theme="light">

        <!-- MENU INICIO -->

        <div class="vertical-nav bg-white" id="sidebar">
            <div class="py-4 px-3 mb-4 menuColor text-white">
                <div class="media d-flex align-items-center ">
                    <div class="media-body text-white text-center">
                        <h4 class="m-0"> 
                            <%                            if (usuario != null) {
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
            <div class="page-content  " id="content">
                <button id="sidebarCollapse" type="button"
                        class=" menuColor rounded-pill shadow-sm px-4 "><i class="fa fa-bars mr-2"></i><small
                        class="text-uppercase text-white font-weight-bold">Menu</small>
                </button>

                <img class="float-end" src="../img/inicioSesion_sena.jpg" alt="" height="80px" width="80px">

                <div class="container-fluid  ">
                    <p class="letra text-center "> Agenda 2024   </p> 
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
                                <form action="<%=request.getContextPath()%>/ServletAgenda" method="post" >
                                    <div class="input-group mb-2">
                                        <div class="input-group-text col-4"><b>Buscar:</b></div>
                                        <input type="text" class="form-control" id="filtro3">
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-12 mb-5" data-aos="zoom-in"  data-aos-duration="500">
                            <!-- TABLA CANDIDATOS INICIO -->
                            <div  
                                class="container-fluid tablaAgenda table-responsive table-wrapper-scroll-y my-custom-scrollbar text-center">
                                <table id="tablaAgenda" class="table table-sm align-middle table-hover table-bordered border-primary  mb-0  ">
                                    <thead class=" border-primary table-success">
                                        <tr>
                                            <th scope="col" >Codigo</th>
                                            <th scope="col" >Nombre</th>
                                            <th scope="col">Fecha de Inicio</th>
                                            <th scope="col">Fecha Final</th>
                                            <th scope="col">Hora de Inicio</th>                                            
                                            <th scope="col">Hora Final</th>
                                            <th scope="col">Descripcion</th>
                                            <th scope="col">Opcciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% AgendaVotacionJpaController contrAgenda = new AgendaVotacionJpaController();
                                            List<AgendaVotacion> Agen = contrAgenda.findAgendaVotacionEntities();

                                            if (Agen.isEmpty()) {

                                        %>

                                        <tr>
                                            <td colspan="7">No se encontraron agendas en la base de datos.</td>
                                        </tr>
                                        <%                                        } else {
                                            for (AgendaVotacion agenda : Agen) {

                                        %>
                                        <%  SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

                                            String fechaInicioFormateada = dateFormat.format(agenda.getFechaInicio());
                                            String fechaFinFormateada = dateFormat.format(agenda.getFechaFin());
                                            String horaInicioFormateada = timeFormat.format(agenda.getHoraInicio());
                                            String horaFinFormateada = timeFormat.format(agenda.getHoraFin());
                                        %>
                                        <tr>
                                            <td> <%= agenda.getIdAgenda()%> </td>
                                            <td> <%= agenda.getNombre()%> </td>
                                            <td> <%= fechaInicioFormateada%></td>
                                            <td> <%= fechaFinFormateada%> </td>
                                            <td> <%= horaInicioFormateada%> </td>
                                            <td> <%= horaFinFormateada%> </td>
                                            <td> <%= agenda.getDescrpcion()%> </td>
                                            <td> 
                                                <% SimpleDateFormat dateFormatInput = new SimpleDateFormat("yyyy-MM-dd");%>
                                                <button type="button" class="btn btn-outline-danger btn-sm" data-bs-toggle="modal" data-bs-target="#formulario2Modal"
                                                        onclick="obtenerDatosAgenda('<%= agenda.getIdAgenda()%>', '<%= agenda.getNombre()%>', 
                                                                    '<%= dateFormatInput.format(agenda.getFechaInicio())%>',
                                                                        '<%= dateFormatInput.format(agenda.getFechaFin())%>',
                                                                        '<%= timeFormat.format(agenda.getHoraInicio())%>', 
                                                                        '<%= timeFormat.format(agenda.getHoraFin())%>', 
                                                                        '<%= agenda.getDescrpcion()%>')">
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

                            <!-- TABLA CANDIDATOS FINAL --> 

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


        <!-- MODALES GUARDADO INICIO -->
        <div class="modal fade" id="formularioModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <!-- FORMULARIO AGENDA INICIAL -->

                    <h2 class="pt-4 pb-2 text-center">Agenda de Votacion</h2>
                    <div class="modal-body">
                        <form action="<%=request.getContextPath()%>/ServletAgenda" method="post" class="row g-2" onsubmit="return validarCamposVacios();">

                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Nombre:</b></div>
                                    <input type="text" class="form-control" id="nombre" name="nombre" required min="1" maxlength="30">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Fecha de Inicio:</b></div>
                                    <input type="date" class="form-control" id="fechaInicio" name="fechaInicio" required min="1">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Fecha Final:</b></div>
                                    <input type="date" class="form-control" id="fechaFinal" name="fechaFinal" required min="1">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Hora de Inicio:</b></div>
                                    <input type="time" class="form-control" id="horaInicio" name="horaInicio" required min="1">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Hora Final:</b></div>
                                    <input type="time" class="form-control" id="horaFinal" name="horaFinal" required min="1">
                                </div>
                            </div>
                            <div class="input-group-sm">
                                <div class="input-group-text col-12"><b>Descripcion de Votacion:</b></div>
                                <textarea class="form-control mt-2" id="Descripcion" name="Descripcion" rows="3" required min="1" placeholder="Maximo 200 caracteres" maxlength="200"></textarea>
                            </div>
                            <div class="col-12 text-center "><!--bottones-->
                                <button type="submit" class="btn botones btn-primary "
                                        name="action" value="Guardar"><b>Guardar</b></button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- MODALES GUARDADO FINAL -->

        <!-- MODAL OPCCIONES INICIO -->
        <div class="modal fade" id="formulario2Modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <!-- FORMULARIO AGENDA INICIAL -->

                    <h2 class="pt-4 pb-2 text-center">Agenda de Votacion</h2>
                    <div class="modal-body">
                        <form action="<%=request.getContextPath()%>/ServletAgenda" method="post" class="row g-2" >
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Codigo</b></div>
                                    <input type="text" class="form-control" id="id2" name="id2" readonly>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Nombre:</b></div>
                                    <input type="text" class="form-control" id="nombre2" name="nombre2" maxlength="30">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Fecha de Inicio:</b></div>
                                    <input type="date" class="form-control" id="fechaInicio2" name="fechaInicio2">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Fecha Final:</b></div>
                                    <input type="date" class="form-control" id="fechaFinal2" name="fechaFinal2">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Hora de Inicio:</b></div>
                                    <input type="time" class="form-control" id="horaInicio2" name="horaInicio2">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Hora Final:</b></div>
                                    <input type="time" class="form-control" id="horaFinal2" name="horaFinal2">
                                </div>
                            </div>
                            <div class="input-group-sm">
                                <div class="input-group-text col-12"><b>Descripcion de Votacion:</b></div>
                                <textarea class="form-control mt-2" id="Descripcion2" name="Descripcion2" rows="3" placeholder="Maximo 200 caracteres" maxlength="200"></textarea>
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
            case "Guardado":
%>
<script>
                            Swal.fire(
                                    '¡Éxito!',
                                    'La sede se ha sido guardada correctamente.',
                                    'success'
                                    );
</script>
<%
        break;
    case "agendaexiste":
%>
<script>
    Swal.fire(
            '¡Oops!',
            '¡El nombre ya existe en la base de datos!',
            'warning'
            );
</script>
<%
        break;
    case "ErrorGuarda":
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
            '¡La agenda ha sido eliminado!',
            'success'
            );
</script>
<%
        break;
    case "Error":
%>
<script>
    Swal.fire(
            '¡Oops!',
            '¡La agenda no se puede eliminar!',
            'warning'
            );
</script>
<%
        break;
    case "Editado":
%>
<script>
    Swal.fire(
            '¡Éxito!',
            '¡La agenda ha sido actualizada!',
            'success'
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
<%
                break;
            default:
                throw new AssertionError();
        }
    }
%>

<script>
    const filtroInput3 = document.getElementById("filtro3");
    const filasAgenda = document.querySelectorAll("#tablaAgenda tbody tr");
    filtroInput3.addEventListener("input", function () {
        const filtro = filtroInput3.value.trim().toLowerCase();

        filasAgenda.forEach(function (fila) {
            const textoFila = fila.textContent.toLowerCase();
            if (textoFila.includes(filtro)) {
                fila.style.display = "";
            } else {
                fila.style.display = "none";
            }
        });
    });
</script>