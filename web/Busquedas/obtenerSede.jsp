<%@page import="Entidades.Sede"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.SedeJpaController"%>
<%
    int id = Integer.parseInt(request.getParameter("idsede"));
    
    
    SedeJpaController se = new SedeJpaController();
    List lista = se.findSedeEntities();

    for (int i = 0; i < lista.size(); i++) {
        Sede de = (Sede) lista.get(i);
        
        if(de.getIdSede()==id)
        {
        out.print("<option value='" + de.getIdSede() + "' selected>");
        out.print(de.getNombre());
        out.print("</option>");
        }
        else
        {
        out.print("<option value='" + de.getIdSede() + "'>");
        out.print(de.getNombre());
        out.print("</option>");
    }}
%>

