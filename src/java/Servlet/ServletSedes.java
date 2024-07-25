/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Controladores.SedeJpaController;
import Entidades.Sede;
import java.io.IOException;
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
@WebServlet(name = "ServletSedes", urlPatterns = {"/ServletSedes"})
public class ServletSedes extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws org.json.JSONException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

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

        int codigo = Integer.parseInt(request.getParameter("codigo"));
        String nombre = request.getParameter("nombre");

        SedeJpaController se = new SedeJpaController();
        Sede guardaSede = new Sede();

        try {
            // Verificar si el código ya existe en la base de datos
            if (se.findSede(codigo) != null) {
                // El código ya existe, enviar notificación
                String mensaje = "Existe";
                response.sendRedirect("Vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
            } else {
                // El código no existe, proceder con la creación de la sede
                guardaSede.setIdSede(codigo);
                guardaSede.setNombre(nombre);

                se.create(guardaSede);
                String mensaje = "Guardado";
                response.sendRedirect("Vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
            }
        } catch (Exception e) {
            String mensaje = "errorguardar";
                response.sendRedirect("Vistas/sedesFormaciones.jsp?respuesta=" + mensaje);

        }
    }

    protected void botonEliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mensaje;
        try {

            int codigo = Integer.parseInt(request.getParameter("codigoEliminar"));

            SedeJpaController sedeController = new SedeJpaController();
            sedeController.destroy(codigo);

            mensaje = "SedeEliminada";
            response.sendRedirect("Vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
        } catch (Exception e) {

        }

    }

    public void botonEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int codigo = Integer.parseInt(request.getParameter("codigoEliminar"));
        String nombre = request.getParameter("nombreEliminar");

        SedeJpaController sedeController = new SedeJpaController();
        Sede sedeExistente = sedeController.findSede(codigo);

        try {
            if (sedeExistente != null) {
                // La sede existe, proceder con la edición
                sedeExistente.setNombre(nombre);
                sedeController.edit(sedeExistente);

                String mensaje = "Editado";

                response.sendRedirect("Vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
            }
        } catch (Exception e) {
            String mensaje = "erroreditar";

                response.sendRedirect("Vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
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
        } catch (JSONException ex) {
            Logger.getLogger(ServletSedes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServletSedes.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (JSONException ex) {
            Logger.getLogger(ServletSedes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServletSedes.class.getName()).log(Level.SEVERE, null, ex);
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
