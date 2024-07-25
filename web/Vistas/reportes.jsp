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
        <link rel="stylesheet" href="../css/reportes.css"/>
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

        <script>
            function verReporte() {
                // Obtener el valor seleccionado en el select
                var agendaId = document.getElementById("agendaId").value;

                // Redirigir al enlace con el ID seleccionado como parámetro
                window.open("<%= request.getContextPath()%>/Reportes/reporteCandidato.jsp?agendaId=" + agendaId, "_blank");
            }
        </script>

        <script>
            function verReporte2() {
                // Obtener el valor seleccionado en el select
                var agendaId = document.getElementById("agendaId2").value;

                // Redirigir al enlace con el ID seleccionado como parámetro
                window.open("<%= request.getContextPath()%>/Reportes/reporteVotantes.jsp?agendaId2=" + agendaId, "_blank");
            }
        </script>
        
        <script>
            function verReporte3() {
                // Obtener el valor seleccionado en el select
                var agendaId = document.getElementById("agendaId3").value;

                // Redirigir al enlace con el ID seleccionado como parámetro
                window.open("<%= request.getContextPath()%>/Reportes/reporteFormaciones.jsp?agendaId3=" + agendaId, "_blank");
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
            <button class="btn rounded-fill" onclick="cambiarTema()"><i id="dl-icon"  class="bi bi-moon-fill"></i>Modo Oscuro</button>
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
                <div class='dashboard-nav-dropdown'><a href="#!"
                                                       class="dashboard-nav-item dashboard-nav-dropdown-toggle text-dark" style="text-decoration: none;">
                        <img src="../img/icon_reportes.png" alt="">
                        Reportes</a>
                    <div class='dashboard-nav-dropdown-menu'>
                    </div>
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

        <div class="container-fluid  ">
            <div class="page-content contenido " id="content">

                <button id="sidebarCollapse" type="button"
                        class=" menuColor rounded-pill shadow-sm px-4 "><i class="fa fa-bars mr-2"></i><small
                        class="text-uppercase text-white font-weight-bold">Menu</small>
                </button>

                <img class="float-end" src="../img/inicioSesion_sena.jpg" alt="" height="80px" width="80px">

                <div class="container-fluid ">
                    <p class="letra text-center ">Reportes</p> 
                    <div class="row">
                        <div class="container">
                            <div class="row text-center">
                                <div class="col-lg-4 col-md-6 col-sd-12">
                                    <div class="  " data-aos="flip-left"
                                         data-aos-easing="ease-out-cubic"
                                         data-aos-duration="2000">

                                        <article class="card">
                                            <img class="imge" src="../img/reporte_candidatos.jpg" alt="">
                                            <section class="body">
                                                <h3 class="py-2">Candidatos</h3>
                                                <div class="col-12 px-1">
                                                    <div class="input-group mb-3">
                                                        <div class="input-group-text col-5"><b>Agenda</b></div>
                                                        <select name="agendaId" id="agendaId" class="from-selec col-7" >

                                                            <% AgendaVotacionJpaController contrAgen = new AgendaVotacionJpaController();
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
                                                    <a href="#" onclick="verReporte()"  class="ov-btn-slide-left">VER</a>
                                            </section>
                                        </article>
                                    </div> 
                                </div>

                                <div class="col-lg-4 col-md-6 col-sd-12">
                                    <div class=" " data-aos="flip-left"
                                         data-aos-easing="ease-out-cubic"
                                         data-aos-duration="2000">

                                        <article class="card">
                                            <img class="imge" src="../img/reporte_agenda.jpg" alt="">
                                            <section class="body">
                                                <h3 class="py-2">Votantes</h3>
                                                <div class="col-12  px-1">
                                                    <div class="input-group mb-3">
                                                        <div class="input-group-text col-5"><b>Agenda</b></div>
                                                        <select name="agendaId2" id="agendaId2"
                                                                class="from-selec col-7"  >

                                                            <%
                                                                AgendaVotacionJpaController contrAgen2 = new AgendaVotacionJpaController();
                                                                List listaA2 = contrAgen2.findAgendaVotacionEntities();

                                                                for (int i = 0; i < listaA2.size(); i++) {
                                                                    AgendaVotacion agen2 = (AgendaVotacion) listaA2.get(i);
                                                                    out.print("<option value='" + agen2.getIdAgenda() + "'>");
                                                                    out.print(agen2.getNombre());
                                                                    out.print("</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                    <a href="#" onclick="verReporte2()"  class="ov-btn-slide-left">VER</a>
                                                </div>
                                            </section>
                                        </article>
                                    </div>
                                </div>
                                <div class="col-lg-4 col-md-6 col-sd-12">
                                    <div class="" data-aos="flip-left"
                                         data-aos-easing="ease-out-cubic"
                                         data-aos-duration="2000">

                                        <article class="card">
                                            <img class="imge" src="../img/reporte_formacion.png" alt="">
                                            <section class="body">
                                                <h3 class="py-2">Formacion</h3>
                                                <div class="col-12 ">
                                                    <div class="input-group mb-3">
                                                        <div class="input-group-text col-5"><b>Nombre</b></div>
                                                        <select name="agendaId3" id="agendaId3"
                                                                class="from-selec col-7"  >

                                                            < <%
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
                                                    <a href="#" onclick="verReporte3()"  class="ov-btn-slide-left">VER</a>
                                                </div>
                                            </section>
                                        </article>
                                    </div>
                                </div>
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

        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script>AOS.init();</script>
        <script src="../js/menu.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
        <script src="../js/modoOscuro.js"></script>

    </body>
</html>

