<%@page import="java.io.File"%>
<%@page import="net.sf.jasperreports.engine.JasperRunManager"%>
<%@page import="java.io.File"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.HashMap" %>
<%@page import="java.io.File"%>
<%@page import="net.sf.jasperreports.engine.JasperRunManager"%>
<%@page import="java.io.File"%>
<%
    Connection coneccion;
    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
    coneccion = DriverManager.getConnection("jdbc:mysql://localhost/dbvotaciones", "root", "27478426*cP");

    String agendaIdStr = request.getParameter("agendaId2");
    int agendaId = Integer.parseInt(agendaIdStr);

    // Crear un HashMap para almacenar los parámetros del informe
    HashMap<String, Object> parameters = new HashMap<>();
    System.out.println("Valor de agendaId nueva: " + agendaId); // Imprime el valor de agendaId en la consola

    // Agregar el parámetro agendaId al HashMap
    parameters.put("id", agendaId);

    // Obtener la ruta del archivo Jasper
    File reportFile = new File(application.getRealPath("Reportes/Votante.jasper"));

    // Generar el informe PDF con los parámetros
    byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters, coneccion);

    response.setContentType("application/pdf");
    response.setContentLength(bytes.length);
    ServletOutputStream ouputStream = response.getOutputStream();
    ouputStream.write(bytes, 0, bytes.length);
    ouputStream.flush();
    ouputStream.close();
%>
