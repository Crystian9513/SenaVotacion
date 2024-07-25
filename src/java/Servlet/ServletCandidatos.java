/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Controladores.AgendaVotacionJpaController;
import Controladores.CandidatoJpaController;
import Controladores.FormacionJpaController;
import Controladores.SedeJpaController;
import Controladores.TipodocumentoJpaController;
import Entidades.AgendaVotacion;
import Entidades.Candidato;
import Entidades.Formacion;
import Entidades.Sede;
import Entidades.Tipodocumento;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Peralta
 */
@MultipartConfig //Para archivos siempre va esto
@WebServlet(name = "ServletCandidatos", urlPatterns = {"/ServletCandidatos"})
public class ServletCandidatos extends HttpServlet {

    private String pathFiles = "";
    private File uploads;
    private String[] extensiones = {".ico", ".png", ".jpg", ".jpeg"};

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

        String mensaje;

        int codigo = Integer.parseInt(request.getParameter("codigo"));
        int tipoDocumento = Integer.parseInt(request.getParameter("tipoDocumento"));
        int cedula = Integer.parseInt(request.getParameter("cedula"));
        String nombre = request.getParameter("nombres");
        String apellido = request.getParameter("apellidos");
        int agendaVoto = Integer.parseInt(request.getParameter("agenda"));
        int formaciones = Integer.parseInt(request.getParameter("formacion"));
        int sedes = Integer.parseInt(request.getParameter("sede"));
        Part part = request.getPart("foto");

        String propuestas = request.getParameter("propuesta");

        CandidatoJpaController controlador = new CandidatoJpaController();
        Candidato guardarCandidatos = new Candidato();
        TipodocumentoJpaController tipo = new TipodocumentoJpaController();
        AgendaVotacionJpaController vota = new AgendaVotacionJpaController();
        FormacionJpaController forma = new FormacionJpaController();
        SedeJpaController sedeC = new SedeJpaController();

        try {
            // Verificar si el código ya existe en la base de datos
            if (controlador.findCandidato(codigo) != null) {
                mensaje = "ElcodigoExiste";
                response.sendRedirect("Vistas/candidatos.jsp?respuesta=" + mensaje);
                return; // Terminar el método si el código ya existe
            }

            if (part == null) {

                return;
            }
            pathFiles = getServletContext().getResource("Vistas/fotos").getPath().replace("build", "");
            uploads = new File(pathFiles);

            if (isExtension(part.getSubmittedFileName(), extensiones)) {
                String photo = saveFile(part, uploads);

                guardarCandidatos.setIdCandidato(codigo);
                Tipodocumento listaTipo = tipo.findTipodocumento(tipoDocumento);
                guardarCandidatos.setTipoDocumentoFk(listaTipo);
                guardarCandidatos.setNumeroDocumento(cedula);
                guardarCandidatos.setNombres(nombre);
                guardarCandidatos.setApellidos(apellido);
                AgendaVotacion votacion = vota.findAgendaVotacion(agendaVoto);
                guardarCandidatos.setAgendaFk(votacion);
                Formacion formac = forma.findFormacion(formaciones);
                guardarCandidatos.setFormacionFk(formac);
                Sede listaSede = sedeC.findSede(sedes);
                guardarCandidatos.setSedeFk(listaSede);
                guardarCandidatos.setFotografia(photo);
                guardarCandidatos.setPropuestaCampana(propuestas);

                controlador.create(guardarCandidatos);

                mensaje = "Guardado";
                response.sendRedirect("Vistas/candidatos.jsp?respuesta=" + mensaje);
            }

        } catch (Exception e) {
            mensaje = "ErrorGuardar";
                response.sendRedirect("Vistas/candidatos.jsp?respuesta=" + mensaje);
        }
    }

  public void botonEditar(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String mensaje;

    int codigo = Integer.parseInt(request.getParameter("codigoEliminar"));
    int tipoDocumento = Integer.parseInt(request.getParameter("tipoDocumento20"));
    int cedula = Integer.parseInt(request.getParameter("cedula20"));
    String nombre = request.getParameter("nombres20");
    String apellido = request.getParameter("apellidos20");
    int agendaVoto = Integer.parseInt(request.getParameter("agenda20"));
    int formaciones = Integer.parseInt(request.getParameter("formacion20"));
    int sedes = Integer.parseInt(request.getParameter("sede22"));

    String propuestas = request.getParameter("propuesta20");

    CandidatoJpaController controlador = new CandidatoJpaController();
    TipodocumentoJpaController tipo = new TipodocumentoJpaController();
    AgendaVotacionJpaController vota = new AgendaVotacionJpaController();
    FormacionJpaController forma = new FormacionJpaController();
    SedeJpaController sedeC = new SedeJpaController();

    // Obtener el archivo de la foto
    Part part = request.getPart("foto20");
    String photo = null;

    // Verificar si se proporcionó una nueva foto
    if (part != null && part.getSize() > 0) {
        // Guardar la nueva foto en el servidor
        pathFiles = getServletContext().getResource("Vistas/fotos").getPath().replace("build", "");
        uploads = new File(pathFiles);
        if (isExtension(part.getSubmittedFileName(), extensiones)) {
            photo = saveFile(part, uploads);
        }
    }

    try {
        // Obtener el candidato a editar
        int idCandidato = Integer.parseInt(request.getParameter("codigoEliminar"));
        Candidato candidato = controlador.findCandidato(idCandidato);

        if (candidato != null) {
            // Actualizar los datos del candidato
            candidato.setTipoDocumentoFk(tipo.findTipodocumento(tipoDocumento));
            candidato.setNumeroDocumento(cedula);
            candidato.setNombres(nombre);
            candidato.setApellidos(apellido);
            candidato.setAgendaFk(vota.findAgendaVotacion(agendaVoto));
            candidato.setFormacionFk(forma.findFormacion(formaciones));
            candidato.setSedeFk(sedeC.findSede(sedes));
            candidato.setPropuestaCampana(propuestas);

            // Actualizar la foto si se proporcionó una nueva
            if (photo != null) {
                candidato.setFotografia(photo);
            }

            // Guardar los cambios en la base de datos
            controlador.edit(candidato);
            mensaje = "candidatoEditado";
            response.sendRedirect("Vistas/candidatos.jsp?respuesta=" + mensaje);
        }
    } catch (Exception e) {
       mensaje = "erroreditar";
            response.sendRedirect("Vistas/candidatos.jsp?respuesta=" + mensaje);
    }
}


    public void botonEliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         String mensaje;
        int codigo = Integer.parseInt(request.getParameter("codigoEliminar"));
        CandidatoJpaController controlador = new CandidatoJpaController();

        try {
            // Obtener el candidato a eliminar
            Candidato candidato = controlador.findCandidato(codigo);

            // Verificar si el candidato existe
            if (candidato != null) {
                // Obtener la ruta de la foto del candidato
                String fotoCandidato = candidato.getFotografia();
                
                // Eliminar el candidato de la base de datos
                controlador.destroy(codigo);
                
                // Verificar si se proporcionó una ruta de foto
                if (fotoCandidato != null && !fotoCandidato.isEmpty()) {
                    // Eliminar la foto del candidato del servidor
                    File fileToDelete = new File(getServletContext().getRealPath("Vistas/fotos" + fotoCandidato));
                    if (fileToDelete.exists()) {
                        fileToDelete.delete();
                    }
                }

                mensaje = "Eliminado";
                response.sendRedirect("Vistas/candidatos.jsp?respuesta=" + mensaje);
            } else {
               
            }

        } catch (Exception e) {
            mensaje = "errorEliminar";
            response.sendRedirect("Vistas/candidatos.jsp?respuesta=" + mensaje);
        }

    }

    private String saveFile(Part part, File pathUploads) {
        String pathAbsolute = "";

        try {

            Path path = Paths.get(part.getSubmittedFileName());
            String fileName = path.getFileName().toString();
            InputStream input = part.getInputStream();

            if (input != null) {
                File file = new File(pathUploads, fileName);
                pathAbsolute = "fotos/" + fileName;
                Files.copy(input, file.toPath());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pathAbsolute;
    }

    private boolean isExtension(String fileName, String[] extensions) {
        for (String et : extensions) {
            if (fileName.toLowerCase().endsWith(et)) {
                return true;
            }
        }

        return false;
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
