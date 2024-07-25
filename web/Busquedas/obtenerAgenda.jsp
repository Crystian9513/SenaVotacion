<%@page import="Entidades.AgendaVotacion"%>
<%@page import="java.util.List"%>
<%@page import="Controladores.AgendaVotacionJpaController"%>
<%
    int id = Integer.parseInt(request.getParameter("agendaTipos"));

    AgendaVotacionJpaController ses = new AgendaVotacionJpaController();
    List lista = ses.findAgendaVotacionEntities();

    for (int i = 0; i < lista.size(); i++) {
        AgendaVotacion tipo2 = (AgendaVotacion) lista.get(i);

        if (tipo2.getIdAgenda() == id) {

            out.print("<option value='" + tipo2.getIdAgenda() + "' selected>");
            out.print(tipo2.getNombre());
            out.print("</option>");
        } else {
            out.print("<option value='" + tipo2.getIdAgenda() + "' >");
            out.print(tipo2.getNombre());
            out.print("</option>");

        }

    }
%>