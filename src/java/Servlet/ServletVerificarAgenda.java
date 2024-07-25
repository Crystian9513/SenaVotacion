/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Controladores.AgendaVotacionJpaController;
import Controladores.CandidatoJpaController;
import Entidades.AgendaVotacion;
import Entidades.Candidato;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Peralta
 */
@WebServlet(name = "ServletVerificarAgenda", urlPatterns = {"/ServletVerificarAgenda"})
public class ServletVerificarAgenda extends HttpServlet {

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
            throws ServletException, IOException, Exception {

        String boton = request.getParameter("action");

        switch (boton) {
            case "Entrar":
                botonEntrar(request, response);
                break;
           
            default:
                throw new AssertionError();
        }

    }

    protected void botonEntrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        // Obtener todas las agendas de votación disponibles
        List<AgendaVotacion> agendas = obtenerAgendasDisponibles();

        // Encontrar todas las agendas activas
        List<AgendaVotacion> agendasActivas = new ArrayList<>(); // Lista para almacenar las agendas activas

        for (AgendaVotacion agenda : agendas) {

            // Convertir Date a LocalDate
            LocalDate fechaInicio = agenda.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fechaFin = agenda.getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Obtener la fecha actual en el sistema
            LocalDate fechaActual = LocalDate.now();
            // Obtener la hora actual en el sistema
            LocalTime horaActual = LocalTime.now();

            // Convertir Date a LocalTime
            LocalTime horaInicio = agenda.getHoraInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
            LocalTime horaFin = agenda.getHoraFin().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

            // Verificar si la agenda actual está activa
            if ((fechaActual.isEqual(fechaInicio) || fechaActual.isAfter(fechaInicio))
                    && (fechaActual.isEqual(fechaFin) || fechaActual.isBefore(fechaFin))
                    && (horaActual.isAfter(horaInicio) && horaActual.isBefore(horaFin))) {

                // Si la agenda está activa, la agregamos a la lista de agendas activas
                agendasActivas.add(agenda);
            }
        }

        // Si hay alguna agenda activa, redirigir a la página de sesión de votante
        if (!agendasActivas.isEmpty()) {
            // Obtener los candidatos asociados a cada agenda activa
            CandidatoJpaController candidatoController = new CandidatoJpaController();
            for (AgendaVotacion agenda : agendasActivas) {
                List<Candidato> candidatos = candidatoController.obtenerCandidatosPorIdAgenda(agenda.getIdAgenda());
                // Pasa la lista de candidatos como un atributo de solicitud a la página JSP
                request.setAttribute("candidatos_" + agenda.getIdAgenda(), candidatos);
            }
            // Pasa la lista de agendas activas como un atributo de solicitud a la página JSP
            request.setAttribute("agendasActivas", agendasActivas);
            String mensaje = "entro";
            request.getRequestDispatcher("Vistas/votacionesEnVivo.jsp?respuesta=" + mensaje).forward(request, response);
        } else {
            // Si ninguna agenda está activa, redirigir a la página principal
            String mensaje = "agendaVotacionInactiva";
            response.sendRedirect("Vistas/menuPrincipal.jsp?respuesta=" + mensaje);
        }

    }

    private List<AgendaVotacion> obtenerAgendasDisponibles() {
        // Crear una instancia del controlador de JPA para AgendaVotacion
        AgendaVotacionJpaController agendaController = new AgendaVotacionJpaController();

        // Obtener todas las agendas de votación disponibles
        List<AgendaVotacion> agendasDisponibles = agendaController.findAgendaVotacionEntities();

        return agendasDisponibles;

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

        } catch (Exception ex) {
            Logger.getLogger(ServletVerificarAgenda.class
                    .getName()).log(Level.SEVERE, null, ex);
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

        } catch (Exception ex) {
            Logger.getLogger(ServletVerificarAgenda.class
                    .getName()).log(Level.SEVERE, null, ex);
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
