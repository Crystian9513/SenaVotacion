/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Controladores.AgendaVotacionJpaController;
import Entidades.AgendaVotacion;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.persistence.exceptions.JSONException;

/**
 *
 * @author Peralta
 */
@WebServlet(name = "ServletAgenda", urlPatterns = {"/ServletAgenda"})
public class ServletAgenda extends HttpServlet {

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
            throws ServletException, IOException, ParseException, JSONException {

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
            throws ServletException, IOException, ParseException {

        String nombre = request.getParameter("nombre");

        String fechaInicio = request.getParameter("fechaInicio");
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha1 = formato.parse(fechaInicio);

        String fechaFinal = request.getParameter("fechaFinal");
        SimpleDateFormat formato2 = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha2 = formato2.parse(fechaFinal);

        String horaInicio = request.getParameter("horaInicio");
        SimpleDateFormat formato3 = new SimpleDateFormat("HH:mm");
        Date hora1 = formato3.parse(horaInicio);

        String horaFinal = request.getParameter("horaFinal");
        SimpleDateFormat formato4 = new SimpleDateFormat("HH:mm");
        Date hora2 = formato4.parse(horaFinal);

        String descripcion = request.getParameter("Descripcion");

        AgendaVotacionJpaController contrAgenda = new AgendaVotacionJpaController();
        AgendaVotacion guardarAgenda = contrAgenda.findAgendaVotacionByNombre(nombre);

        if (guardarAgenda == null) {
            // Crear una nueva instancia de AgendaVotacion
            guardarAgenda = new AgendaVotacion();

            try {
                guardarAgenda.setNombre(nombre);
                guardarAgenda.setFechaInicio(fecha1);
                guardarAgenda.setFechaFin(fecha2);
                guardarAgenda.setHoraInicio(hora1);
                guardarAgenda.setHoraFin(hora2);
                guardarAgenda.setDescrpcion(descripcion);

                contrAgenda.create(guardarAgenda);

                String mensaje = "Guardado";
                response.sendRedirect("Vistas/agendaDeVotacion.jsp?respuesta=" + mensaje);

            } catch (Exception e) {
                String mensaje = "ErrorGuarda";
                response.sendRedirect("Vistas/agendaDeVotacion.jsp?respuesta=" + mensaje);
            }

        } else {
            // Si la agenda ya existe, mostrar un mensaje de error
            String mensaje = "agendaexiste";
            response.sendRedirect("Vistas/agendaDeVotacion.jsp?respuesta=" + mensaje);
        }
    }

    public void botonEliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {

        String mensaje;
        int idAgenda = Integer.parseInt(request.getParameter("id2"));

        AgendaVotacionJpaController controlador = new AgendaVotacionJpaController();
        AgendaVotacion agenda = controlador.findAgendaVotacion(idAgenda);

        try {

            if (agenda != null) {
                controlador.destroy(agenda.getIdAgenda());
                mensaje = "Eliminado";
                response.sendRedirect("Vistas/agendaDeVotacion.jsp?respuesta=" + mensaje);
            }

        } catch (Exception e) {

            mensaje = "Error";
            response.sendRedirect("Vistas/agendaDeVotacion.jsp?respuesta=" + mensaje);
        }

    }

    public void botonEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

        int idAgenda = Integer.parseInt(request.getParameter("id2")); // Obtener el ID de la agenda a editar

        String nombre = request.getParameter("nombre2");
        String fechaInicio = request.getParameter("fechaInicio2");
        String fechaFinal = request.getParameter("fechaFinal2");
        String horaInicio = request.getParameter("horaInicio2");
        String horaFinal = request.getParameter("horaFinal2");
        String descripcion = request.getParameter("Descripcion2");

        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

        try {
            AgendaVotacionJpaController contrAgenda = new AgendaVotacionJpaController();
            AgendaVotacion agenda = contrAgenda.findAgendaVotacion(idAgenda); // Buscar la agenda por su ID

            if (agenda != null) {
                // Actualizar los atributos de la agenda con los nuevos valores
                agenda.setNombre(nombre);
                agenda.setFechaInicio(formatoFecha.parse(fechaInicio));
                agenda.setFechaFin(formatoFecha.parse(fechaFinal));
                agenda.setHoraInicio(formatoHora.parse(horaInicio));
                agenda.setHoraFin(formatoHora.parse(horaFinal));
                agenda.setDescrpcion(descripcion);

                // Guardar los cambios en la base de datos
                contrAgenda.edit(agenda);

                String mensaje = "Editado"; // Mensaje de éxito
                response.sendRedirect("Vistas/agendaDeVotacion.jsp?respuesta=" + mensaje);
            } else {

            }

        } catch (Exception e) {
            String mensaje = "ErrorEditar"; // Mensaje de error genérico
            response.sendRedirect("Vistas/agendaDeVotacion.jsp?respuesta=" + mensaje);
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ServletAgenda.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(ServletAgenda.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ServletAgenda.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(ServletAgenda.class.getName()).log(Level.SEVERE, null, ex);
        }
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
