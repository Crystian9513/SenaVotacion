<%@page import="Entidades.Usuario"%>
<!DOCTYPE html>


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

<html lang="es">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Administrador</title>
        <link rel="stylesheet" href="../css/style.css">
        <link rel="stylesheet" href="../css/Menu.css"/>
        <script src="../js/modoOscuro.js"></script>
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    </head>

    <body data-bs-theme="light">

        <!-- MENU INICIO -->

        <div class="vertical-nav bg-white " id="sidebar">
            <div class="py-4 px-3 mb-4 menuColor text-white ">
                <div class="media d-flex align-items-center ">
                    <div class="media-body text-white text-center">
                        <h4 class="m-0" >
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
                    <form action="<%=request.getContextPath()%>/ServletVerificarAgenda" target="_blank" method="post" class="row g-2 " >
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
                <div class='dashboard-nav-dropdown'><a href="votantes.jsp"
                                                       class="dashboard-nav-item dashboard-nav-dropdown-toggle text-dark" style="text-decoration: none;">
                        <img src="../img/icon_vota.png" alt="">
                        Votantes </a>
                </div>
                <div class='dashboard-nav-dropdown'><a href="sedesFormaciones.jsp"
                                                       class="dashboard-nav-item dashboard-nav-dropdown-toggle text-dark" style="text-decoration: none;">
                        <img src="../img/icon_sede.png" alt="">
                        Sedes y Formaciones Sena </a>
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
                    <a href="#!" class="nav-link text-dark font-italic">
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

        <!-- CARD INICIO -->

        <div class="container-fluid py-2 " >
            <div class="page-content" id="content" >

                <div class="col-lg-12 ">

                    <button id="sidebarCollapse" type="button" class=" menuColor rounded-pill shadow-sm px-4 "><i
                            class="fa fa-bars mr-2"></i><small
                            class="text-uppercase text-white font-weight-bold">Menu</small></button>

                    <img class="float-end" src="../img/inicioSesion_sena.jpg" alt="" height="80px" width="80px">

                    <p class="letraMenu text-center ">Gestion de Candidatos y Votantes</p> 

                    <div class="container-fluid text-center " data-aos="fade-up">

                        <div class="row ">
                            <div class="col-lg-4 col-md-6 col-sd-12  py-4 card2" data-aos="flip-left"
                                 data-aos-easing="ease-out-cubic"
                                 data-aos-duration="2000">

                                <article class="card">
                                    <img class="imge" src="../img/agendar.jpg" alt="">
                                    <section class="body">
                                        <h3 class="py-2">Agendar</h3>
                                        <a href="agendaDeVotacion.jsp" class="ov-btn-slide-left">INGRESE</a>
                                    </section>
                                </article>
                            </div>
                            <div class="col-lg-4 col-md-6 col-sd-12  py-4" data-aos="flip-left"
                                 data-aos-easing="ease-out-cubic"
                                 data-aos-duration="2000">

                                <article class="card">
                                    <img class="imge" src="../img/candidatos.jpg" alt="">
                                    <section class="body">
                                        <h3 class="py-2">Cadidatos</h3>
                                        <a href="candidatos.jsp" class="ov-btn-slide-left">INGRESE</a>
                                    </section>
                                </article>
                            </div>
                            <div class="col-lg-4 col-md-6 col-sd-12  py-4" data-aos="flip-left"
                                 data-aos-easing="ease-out-cubic"
                                 data-aos-duration="2000">

                                <article class="card">
                                    <img class="imge" src="../img/votacion.webp" alt="">
                                    <section class="body">
                                        <h3 class="py-2">Votantes</h3>
                                        <a href="votantes.jsp" class="ov-btn-slide-left">INGRESE</a>
                                    </section>
                                </article>
                            </div>
                            <div class="col-lg-4 col-md-6 col-sd-12 py-4" data-aos="flip-left"
                                 data-aos-easing="ease-out-cubic"
                                 data-aos-duration="2000">

                                <article class="card">
                                    <img class="imge" src="../img/usuarios.jpg" alt="">
                                    <section class="body">
                                        <h3 class="py-2">Administradores</h3>
                                        <a href="administradores.jsp" class="ov-btn-slide-left">INGRESE</a>
                                    </section>
                                </article>
                            </div>
                            <div class="col-lg-4 col-md-6 col-sd-12 py-4" data-aos="flip-left"
                                 data-aos-easing="ease-out-cubic"
                                 data-aos-duration="2000">

                                <article class="card">
                                    <img class="imge" src="../img/sedeoFormacion.jpg" alt="">
                                    <section class="body">
                                        <h3 class="py-2">Sedes-Formaciones </h3>
                                        <a href="sedesFormaciones.jsp" class="ov-btn-slide-left">INGRESE</a>
                                    </section>
                                </article>
                            </div>
                            <div class="col-lg-4 col-md-6 col-sd-12 py-4" data-aos="flip-left"
                                 data-aos-easing="ease-out-cubic"
                                 data-aos-duration="2000">

                                <article class="card">
                                    <img class="imge" src="../img/reportes.jpg" alt="">
                                    <section class="body">
                                        <h3 class="py-2">Reportes</h3>
                                        <a href="reportes.jsp" target="_blank" class="ov-btn-slide-left">INGRESE</a>
                                    </section>
                                </article>
                            </div>
                            <!-- PIED DE PAGINA INCIO-->
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
                            <!-- PIED DE PAGINA FINAL-->
                        </div>
                    </div>
                </div>
            </div>
            <!-- CARD INICIO -->

        </div>

        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script>AOS.init();</script>
        <script src="../js/menu.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</html>

<%
    String mensaje = request.getParameter("respuesta");

    if (mensaje != null) {

        switch (mensaje) {
            case "agendaVotacionInactiva":
%>
<script>
                Swal.fire(
                        '¡Oops!',
                        '¡No hay ninguna Agenda abierta!',
                        'warning'
                        );</script>
<%break;
            default:

        }
    }

%>