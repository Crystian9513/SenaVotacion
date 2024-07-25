/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Controladores.AdministradorJpaController;
import Controladores.UsuarioJpaController;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.Administrador;
import Entidades.Usuario;
import java.io.IOException;
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
@WebServlet(name = "ServletAdministradores", urlPatterns = {"/ServletAdministradores"})
public class ServletAdministradores extends HttpServlet {

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
            throws ServletException, IOException, NonexistentEntityException {

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

    protected void botonGuardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        int cedula = Integer.parseInt(request.getParameter("cedula"));
        String NombreCompleto = nombre + " " + apellido;

        AdministradorJpaController controladorAdm = new AdministradorJpaController();
        Administrador guardarAdmin = new Administrador();
        UsuarioJpaController controlUsuario = new UsuarioJpaController();
        Usuario guardaUsuario = new Usuario();

        String clave = request.getParameter("clave");
        String claveEncriptada = controladorAdm.EncryptarClave(clave);

        try {

            if (controladorAdm.findAdministrador(cedula) != null && controlUsuario.findUsuario(cedula) != null) {
                String mensaje = "ExisteAdmin";
                response.sendRedirect("Vistas/administradores.jsp?respuesta=" + mensaje);
            } else {
                guardarAdmin.setNombres(nombre);
                guardarAdmin.setApellidos(apellido);
                guardarAdmin.setCedula(cedula);
                guardarAdmin.setClave(clave);
                controladorAdm.create(guardarAdmin);

                guardaUsuario.setCodigo(cedula);
                guardaUsuario.setNombres(NombreCompleto);
                guardaUsuario.setClave(claveEncriptada);
                guardaUsuario.setEstado(0);
                guardaUsuario.setRol(2);
                controlUsuario.create(guardaUsuario);

                String mensaje = "Guardado";
                response.sendRedirect("Vistas/administradores.jsp?respuesta=" + mensaje);
            }

        } catch (Exception e) {
            String mensaje = "ErrorGuardar";
            response.sendRedirect("Vistas/administradores.jsp?respuesta=" + mensaje);
        }

    }

    protected void botonEliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NonexistentEntityException {

        int cedulaEliminar = Integer.parseInt(request.getParameter("cedula2"));

        AdministradorJpaController controladorAdm = new AdministradorJpaController();
        controladorAdm.destroyByCedula(cedulaEliminar);

        UsuarioJpaController controlUsuario = new UsuarioJpaController();
        controlUsuario.destroy(cedulaEliminar);

        String mensaje = "AdministradorEliminado";
        response.sendRedirect("Vistas/administradores.jsp?respuesta=" + mensaje);

    }

 protected void botonEditar(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, NonexistentEntityException {

    int codigo = Integer.parseInt(request.getParameter("codigo2"));
    String nombre = request.getParameter("nombre2");
    String apellido = request.getParameter("apellido2");
    int cedula = Integer.parseInt(request.getParameter("cedula2"));
    String claveNueva = request.getParameter("clave2");

    AdministradorJpaController controladorAdm = new AdministradorJpaController();
    Administrador adminEditar = controladorAdm.findAdministrador(codigo);
    UsuarioJpaController controlUsuario = new UsuarioJpaController();

    if (adminEditar != null) {
        try {
            // Obtener el usuario correspondiente al administrador
            Usuario editarUsuario = controlUsuario.findUsuario(cedula);
            if (editarUsuario != null) {
                // Recuperar la clave encriptada actual del usuario
                String claveActualEncriptada = editarUsuario.getClave();

                // Verificar si la nueva clave es diferente de la actual
                if (!claveNueva.equals(claveActualEncriptada)) {
                    // Encriptar la nueva clave
                    String claveNuevaEncriptada = controladorAdm.EncryptarClave(claveNueva);

                    // Actualizar la entrada del usuario con la nueva clave encriptada
                    editarUsuario.setClave(claveNuevaEncriptada);
                    controlUsuario.edit(editarUsuario);
                } else {
                    // Si la nueva clave es igual a la actual, no se realiza ninguna acción
                    String mensaje = "ErrorClave";
                    response.sendRedirect("Vistas/administradores.jsp?respuesta=" + mensaje);
                    return;
                }
            }

            // Actualizar los datos del administrador
            adminEditar.setNombres(nombre);
            adminEditar.setApellidos(apellido);
            adminEditar.setCedula(cedula);
            adminEditar.setClave(claveNueva); // Actualizar la clave en el objeto Administrador
            controladorAdm.edit(adminEditar);

            // Redireccionar con mensaje de éxito
            String mensaje = "Editado";
            response.sendRedirect("Vistas/administradores.jsp?respuesta=" + mensaje);
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir durante la edición
            e.printStackTrace();
            String mensaje = "ErrorEditar";
            response.sendRedirect("Vistas/administradores.jsp?respuesta=" + mensaje);
        }
    } else {
        
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
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ServletAdministradores.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ServletAdministradores.class.getName()).log(Level.SEVERE, null, ex);
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
