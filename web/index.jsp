
<%@page import="Entidades.Usuario"%>
<%@page import="Controladores.UsuarioJpaController"%>
<!DOCTYPE html>
<html lang="es">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Inicio de Sesion</title>
        <link rel="stylesheet" href="../css/indexx.css">
        <link rel="stylesheet" href="../css/style.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.min.css">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">

    </head>
    <body data-bs-theme="light">

        <nav class="navbar navbar-expand-lg ">
            <div class="container">

                <div class="row">

                    <div class="col-md-2">
                        <a href="index.jsp">
                            <img class="" src="../img/inicioSesion_sena.jpg" alt="" height="80px" width="80px">
                        </a>
                    </div>

                    <div class="col-md-5">
                        <h2 class="mt-3 letras"> SENA Votaciones 2024
                            <button class="btn rounded-fill" onclick="cambiarTema()"><i id="dl-icon" class="bi bi-moon-fill"></i></button></h2>
                    </div>
                    <div class="col-md-4">
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                                data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false"
                                aria-label="Toggle navigation">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse mt-2" id="navbarNavDropdown">
                            <ul class="navbar-nav ms-auto navbar-brand">
                                <li class="nav-item">
                                    <a class="nav-link" aria-current="page" href="#dr-inicio">Inicio</a>
                                </li>
                                <li class="nav-item ">
                                    <a class="nav-link" href="#dr-proceso">Proceso de Votacion</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#dr-contactenos">Contactenos</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="inicioSesion.jsp">Inicio de Sesion  </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </nav>

        <div class="bienvenida">
            <div class="row">
                <div class="col-md-6">
                </div>
                <div id="dr-inicio" class="col-md-6 pt-5 text-white justify-content-center align-items-center " data-aos="zoom-out"
                     data-aos-easing="linear" data-aos-duration="1500">
                    <h2 class="mt-5">¡Bienvenidos!</h2>
                    <h5 class="pt-3 pb-4">Elección del Representante de Aprendices del Centro de Formación. Participa en este
                        proceso crucial para elegir a quien liderará y representará a la comunidad de aprendices. Inicie
                        sesion para votar por su representante.</h5>

                    <a href="inicioSesion.jsp" class="ov-btn-slide-left">Inicio de Sesion</a>
                </div>
            </div>
        </div>

        <div class="container-fluid">

            <div class="row mt-5">

                <h2 id="dr-proceso" class="text-center letras">Proceso de Votacion</h2>
                <div class="col-lg-3" data-aos="fade-right"
                     data-aos-offset="400"
                     data-aos-easing="ease-in-sine">
                    <div class="card mt-5">

                        <div class="header">

                            <div class="div_image_v">
                                <div class="image">
                                    <img src="../img/index-iniciosesion.jpeg" alt="" height="150px" width="100%">
                                </div>
                            </div>
                            <div class="content">
                                <span class="title">Inicio de Sesión</span>
                                <p class="message">Para poder participar en la votación, es necesario iniciar sesión en la
                                    plataforma.</p>
                            </div>

                        </div>
                    </div>

                </div>
                <div class="col-lg-3" class="col-lg-3" data-aos="fade-right"
                     data-aos-offset="425"
                     data-aos-easing="ease-in-sine">
                    <div class="card mt-5">

                        <div class="header">

                            <div class="div_image_v">
                                <div class="image">
                                    <img src="../img/index-agenda.webp" alt="" height="150px" width="100%">
                                </div>
                            </div>
                            <div class="content">
                                <span class="title">Verificación de Agenda</span>
                                <p class="message pb-3">Antes de votar, se verificará si tienes una agenda activada.</p>
                            </div>

                        </div>
                    </div>

                </div>
                <div class="col-lg-3" data-aos="fade-right"
                     data-aos-offset="450"
                     data-aos-easing="ease-in-sine">
                    <div class="card mt-5">

                        <div class="header">

                            <div class="div_image_v">
                                <div class="image">
                                    <img src="../img/index-candidato.jpg" alt="" height="150px" width="100%">
                                </div>
                            </div>
                            <div class="content">
                                <span class="title">Elección de Candidatos</span>
                                <p class="message pb-3">Selecciona tus candidatos preferidos durante el periodo de votación. <br></p>
                            </div>

                        </div>
                    </div>

                </div>
                <div class="col-lg-3" data-aos="fade-right"
                     data-aos-offset="475"
                     data-aos-easing="ease-in-sine">
                    <div class="card mt-5">

                        <div class="header">

                            <div class="div_image_v">
                                <div class="image">
                                    <img src="../img/index-confirmacion.jpg" alt="" height="150px" width="100%">
                                </div>
                            </div>
                            <div class="content">
                                <span class="title">Confirmación del Voto</span>
                                <p class="message"> <br>Revisa tus selecciones y confirma tu voto.</p>
                            </div>

                        </div>
                    </div>

                </div>
            </div>
        </div>


        <div class="container-fluid">

            <div class="row mt-5">
                <h2 id="dr-contactenos" class="text-center pb-3">Contactenos</h2>

                <div class="container" data-aos="fade-up">
                    <div class="row">
                        <div class="col-lg-6 col-md-6">

                            <div class="form-left py-5 px-4">
                                <p><i class="bi bi-telephone-fill"></i><b> Direccion</b><br> Cra 52 # 76 167 Oficina 112 Sincelejo/Colombia</p>
                                <p><i class="bi bi-map-fill"></i><b> Telefonos</b><br> 3007698521</p>
                                <p><i class="bi bi-bank2"></i><b> Quejas y Reclamos</b><br> quejasyreclamos@sena.com.co</p>
                            </div>
                        </div>
                        <div class="col-lg-6 col-md-6">

                            <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3938.069139804575!2d-75.41704102603757!3d9.238114485682747!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x8e596b4b9a9ecdfd%3A0x94b4f19a2fb3a821!2sSENA%20La%20Gallera!5e0!3m2!1ses-419!2sco!4v1682627793595!5m2!1ses-419!2sco" width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
                            <p>Puede encontrarnos en nuestra sede principal ubicada en Sincelejo.</p>

                        </div>
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


        <script src="../js/modoOscuro.js"></script>
      
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script>AOS.init();</script>
    </body>

</html>

