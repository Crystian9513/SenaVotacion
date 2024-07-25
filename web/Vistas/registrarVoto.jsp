
<%@page import="java.time.ZoneId"%>
<%@page import="java.time.LocalDate"%>
<%@page import="Controladores.VotanteJpaController"%>
<%@page import="java.util.Date"%>
<%@page import="Entidades.Votante"%>
<%@page import="Entidades.InformacionVoto"%>
<%@page import="Controladores.InformacionVotoJpaController"%>
<%@page import="Controladores.UsuarioJpaController"%>
<%@page import="Entidades.Usuario"%>
<%@page import="Entidades.Candidato"%>
<%@page import="Controladores.CandidatoJpaController"%>
<%
    int cedula = Integer.parseInt(request.getParameter("numeroDocumento"));
    CandidatoJpaController cc = new CandidatoJpaController();
    Candidato candidatos = cc.findCandidatoByDocumento(cedula);//BUSCA EL CANDIDATO POR EL NUMERO DE CEDULA
    //ACTUALIZAMOS EL VOTO A ESE CANDIDATO
    candidatos.setNumeroVotos(candidatos.getNumeroVotos() + 1);
    cc.edit(candidatos);

    //ACTUALIZACION DE ESTADO 
    HttpSession sesion = request.getSession();

    Votante usuario = (Votante) sesion.getAttribute("votante");

    System.out.println(usuario);

    UsuarioJpaController controlador = new UsuarioJpaController();
    Usuario user = controlador.findUsuario(usuario.getIdVotante());
    user.setEstado(1);
    controlador.edit(user);

    LocalDate fechaActual = LocalDate.now();

    ZoneId defaulZoneid = ZoneId.systemDefault();
    Date fechaActualDate = Date.from(fechaActual.atStartOfDay().atZone(defaulZoneid).toInstant());

    Date horaActual = new Date();

    VotanteJpaController votanteController = new VotanteJpaController();
    Votante votante = votanteController.findVotante(usuario.getIdVotante()); // Suponiendo que existe un método similar para encontrar un votante por su cédula

    InformacionVotoJpaController infoVotoController = new InformacionVotoJpaController();
    InformacionVoto infoVoto = infoVotoController.findInformacionVoto(usuario.getIdVotante());

    if (infoVoto == null) { // Si no existe una entrada de InformacionVoto para el usuario, crea una nueva
        infoVoto = new InformacionVoto();
        infoVoto.setVotante(votante);
    }

    infoVoto.setFechaVoto(fechaActualDate);
    infoVoto.setHoraVoto(horaActual);
    infoVotoController.create(infoVoto); // Método para crear o actualizar la información de voto

    sesion.removeAttribute("votante");
    sesion = null;
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setHeader("Expires", "0"); // Proxies
    
    String mensaje = "votoExitoso";
    response.sendRedirect("sesionVotante.jsp?respuesta=" + mensaje);


%>


