/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Controladores.AgendaVotacionJpaController;
import Controladores.FormacionJpaController;
import Controladores.InformacionVotoJpaController;
import Controladores.SedeJpaController;
import Controladores.TipodocumentoJpaController;
import Controladores.UsuarioJpaController;
import Controladores.VotanteJpaController;
import Entidades.AgendaVotacion;
import Entidades.Formacion;
import Entidades.InformacionVoto;
import Entidades.Sede;
import Entidades.Tipodocumento;
import Entidades.Usuario;
import Entidades.Votante;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Peralta
 */
@WebServlet(name = "ServletVotantes", urlPatterns = {"/ServletVotantes"})
public class ServletVotantes extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String boton = request.getParameter("action");

        switch (boton) {
            case "Guardar":
                botonGuardar(request, response);
                break;
            case "Eliminar":
                botonEliminar(request, response);
                break;
            case "Editar":
                botonEditar(request, response);
                break;
            default:
                throw new AssertionError();
        }
    }

    public void botonGuardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int tpdocumento = Integer.parseInt(request.getParameter("tipoDocumento"));
        int cedula = Integer.parseInt(request.getParameter("cedula"));
        String nombreCompleto = request.getParameter("nombreCompleto");
        int formacion = Integer.parseInt(request.getParameter("formacion"));
        int sede = Integer.parseInt(request.getParameter("sede"));
        int agenda = Integer.parseInt(request.getParameter("agenda"));

        // Encriptar la clave antes de guardarla//TABLA DE INFORMACION DE USUARIO
        String nombreCompleto2 = request.getParameter("nombreCompleto");//TABLA DE INFORMACION DE USUARIO
        int codigo = Integer.parseInt(request.getParameter("cedula"));

        UsuarioJpaController controladorVoto = new UsuarioJpaController();//INFORMACION DE USUARIO
        Usuario guardarUsuario = new Usuario();//INFORMACION DE USUARIO

        VotanteJpaController controladorVotante = new VotanteJpaController();
        Votante guardarVotante = new Votante();

        TipodocumentoJpaController tipo = new TipodocumentoJpaController();
        FormacionJpaController forma = new FormacionJpaController();
        SedeJpaController sedeC = new SedeJpaController();
        AgendaVotacionJpaController vota = new AgendaVotacionJpaController();

        String clave = request.getParameter("cedula");
        String claveEncriptada = controladorVotante.EncryptarClave(clave);

        String mensaje;

        try {
            if (controladorVoto.findUsuario(cedula) != null) {
                mensaje = "Existe";
                response.sendRedirect("Vistas/votantes.jsp?respuesta=" + mensaje);

            } else {

                Tipodocumento to = tipo.findTipodocumento(tpdocumento);
                guardarVotante.setTipoDocumentoFk(to);
                guardarVotante.setIdVotante(cedula);
                guardarVotante.setNombresCompleto(nombreCompleto);
                Formacion forr = forma.findFormacion(formacion);
                guardarVotante.setFormacionFk(forr);
                Sede lista = sedeC.findSede(sede);
                guardarVotante.setSedeFk(lista);
                AgendaVotacion ag = vota.findAgendaVotacion(agenda);
                guardarVotante.setAgendaFk(ag);

                guardarUsuario.setCodigo(codigo);
                guardarUsuario.setClave(claveEncriptada);
                guardarUsuario.setNombres(nombreCompleto2);
                guardarUsuario.setRol(1);
                guardarUsuario.setEstado(0);

                controladorVotante.create(guardarVotante);
                controladorVoto.create(guardarUsuario);

                mensaje = "Guardado";
                response.sendRedirect("Vistas/votantes.jsp?respuesta=" + mensaje);
            }

        } catch (Exception e) {
            mensaje = "errorguardar";
                response.sendRedirect("Vistas/votantes.jsp?respuesta=" + mensaje);
        }

    }

    public void botonEliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int codigoUsuario = Integer.parseInt(request.getParameter("cedula2"));

        VotanteJpaController controladorVotante = new VotanteJpaController();
        UsuarioJpaController controladorUsuario = new UsuarioJpaController();
        InformacionVotoJpaController controladorVotov = new InformacionVotoJpaController();

        try {
            // Buscar y eliminar el votante
            Votante votante = controladorVotante.findVotante(codigoUsuario);
            if (votante != null) {
                // Obtener la lista de información de voto asociada al votante
                List<InformacionVoto> informacionVotoList = votante.getInformacionVotoList();
                // Eliminar cada registro de información de voto asociado al votante
                for (InformacionVoto infoVoto : informacionVotoList) {
                    controladorVotov.destroy(infoVoto.getPk());
                }
                // Eliminar el votante
                controladorVotante.destroy(votante.getIdVotante());
            }

            // Buscar y eliminar el usuario
            Usuario usuario = controladorUsuario.findUsuario(codigoUsuario);
            if (usuario != null) {
                controladorUsuario.destroy(usuario.getCodigo());
            }

            // Redirigir después de eliminar
            String mensaje = "Eliminado";
            response.sendRedirect("Vistas/votantes.jsp?respuesta=" + mensaje);
        } catch (Exception e) {
            // Manejar cualquier excepción que ocurra durante la eliminación
            e.printStackTrace();
            String mensaje = "Error";
            response.sendRedirect("Vistas/votantes.jsp?respuesta=" + mensaje);
        }
    }

    public void botonEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int tpdocumento = Integer.parseInt(request.getParameter("tipoDocumento2"));
        int cedula = Integer.parseInt(request.getParameter("cedula2"));
        String nombreCompleto = request.getParameter("nombreCompleto2");
        int formacion = Integer.parseInt(request.getParameter("formacion2"));
        int sede = Integer.parseInt(request.getParameter("sede2"));
        int agenda = Integer.parseInt(request.getParameter("agenda2"));

        String clave = request.getParameter("cedula2");//TABLA DE INFORMACION DE USUARIO
        String nombreCompleto2 = request.getParameter("nombreCompleto2");//TABLA DE INFORMACION DE USUARIO
        int codigo = Integer.parseInt(request.getParameter("cedula2"));

        UsuarioJpaController controladorVoto = new UsuarioJpaController();//INFORMACION DE USUARIO
        Usuario guardarUsuario = new Usuario();//INFORMACION DE USUARIO

        VotanteJpaController controladorVotante = new VotanteJpaController();
        Votante editarVotante = controladorVotante.findVotante(cedula);

        TipodocumentoJpaController tipo = new TipodocumentoJpaController();
        FormacionJpaController forma = new FormacionJpaController();
        SedeJpaController sedeC = new SedeJpaController();
        AgendaVotacionJpaController vota = new AgendaVotacionJpaController();
        String mensaje;

        if (editarVotante != null) {

            Tipodocumento to = tipo.findTipodocumento(tpdocumento);
            editarVotante.setTipoDocumentoFk(to);
            editarVotante.setIdVotante(cedula);
            editarVotante.setNombresCompleto(nombreCompleto);
            Formacion forr = forma.findFormacion(formacion);
            editarVotante.setFormacionFk(forr);
            Sede lista = sedeC.findSede(sede);
            editarVotante.setSedeFk(lista);
            AgendaVotacion ag = vota.findAgendaVotacion(agenda);
            editarVotante.setAgendaFk(ag);

            guardarUsuario.setCodigo(codigo);
            guardarUsuario.setClave(clave);
            guardarUsuario.setNombres(nombreCompleto2);
            guardarUsuario.setRol(1);
            guardarUsuario.setEstado(0);

            try {

                controladorVoto.edit(guardarUsuario);
                controladorVotante.edit(editarVotante);
                mensaje = "Editado";
                response.sendRedirect("Vistas/votantes.jsp?respuesta=" + mensaje);

            } catch (Exception e) {
                 mensaje = "erroreditar";
                response.sendRedirect("Vistas/votantes.jsp?respuesta=" + mensaje);
            }

        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
